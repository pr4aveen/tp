package seedu.momentum.model;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.momentum.commons.core.GuiSettings;
import seedu.momentum.commons.core.LogsCenter;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.SortType;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.reminder.ReminderManager;

/**
 * Represents the in-memory model of the project book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ProjectBook projectBook;
    private final UserPrefs userPrefs;
    private final ReminderManager reminderManager;
    private ObjectProperty<FilteredList<TrackedItem>> filteredTrackedItems;
    private final ObservableList<TrackedItem> runningTimers;
    private Predicate<TrackedItem> currentPredicate;
    private SortType currentSortType;
    private boolean isCurrentSortAscending;
    private boolean isCurrentSortIsByCompletionStatus;
    private ViewMode viewMode;
    private Project currentProject;
    private ObservableList<TrackedItem> viewList;

    /**
     * Initializes a ModelManager with the given projectBook and userPrefs.
     */
    public ModelManager(ReadOnlyProjectBook projectBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(projectBook, userPrefs);

        logger.fine("Initializing with project book: " + projectBook + " and user prefs " + userPrefs);

        this.projectBook = new ProjectBook(projectBook);
        this.reminderManager = new ReminderManager(this.projectBook);
        rescheduleReminders();
        this.userPrefs = new UserPrefs(userPrefs);

        currentPredicate = PREDICATE_SHOW_ALL_TRACKED_ITEMS;
        currentSortType = SortType.ALPHA;
        isCurrentSortAscending = true;
        isCurrentSortIsByCompletionStatus = true;
        viewMode = ViewMode.PROJECTS;

        this.viewList = FXCollections.observableArrayList();
        filteredTrackedItems = new SimpleObjectProperty<>(new FilteredList<>(viewList, currentPredicate));
        //filteredTrackedItems = new FilteredList<>(viewList);
        viewProjects();

        runningTimers = FXCollections.observableArrayList();
        initializeRunningTimers();
    }

    public ModelManager() {
        this(new ProjectBook(), new UserPrefs());
    }

    private void initializeRunningTimers() {
        for (TrackedItem trackedItem : filteredTrackedItems.get()) {
            if (trackedItem.isRunning()) {
                runningTimers.add(trackedItem);
            }
        }
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getProjectBookFilePath() {
        return userPrefs.getProjectBookFilePath();
    }

    @Override
    public void setProjectBookFilePath(Path projectBookFilePath) {
        requireNonNull(projectBookFilePath);
        userPrefs.setProjectBookFilePath(projectBookFilePath);
    }

    //=========== ProjectBook ================================================================================

    @Override
    public void setProjectBook(ReadOnlyProjectBook projectBook) {
        this.projectBook.resetData(projectBook);
        rescheduleReminders();
    }

    @Override
    public ReadOnlyProjectBook getProjectBook() {
        return projectBook;
    }

    @Override
    public Project getCurrentProject() {
        assert viewMode == ViewMode.TASKS : "Project can only be accessed in task view";

        return currentProject;
    }

    @Override
    public boolean hasTrackedItem(TrackedItem trackedItem) {
        requireNonNull(trackedItem);
        return projectBook.hasTrackedItem(trackedItem);
    }

    @Override
    public void deleteTrackedItem(TrackedItem target) {
        projectBook.removeTrackedItem(target);
        rescheduleReminders();
    }

    @Override
    public void addTrackedItem(TrackedItem trackedItem) {
        projectBook.addTrackedItem(trackedItem);
        rescheduleReminders();
        orderFilteredProjectList(currentSortType, isCurrentSortAscending, isCurrentSortIsByCompletionStatus);
        updateFilteredProjectList(PREDICATE_SHOW_ALL_TRACKED_ITEMS);
    }

    @Override
    public void setTrackedItem(TrackedItem target, TrackedItem editedTrackedItem) {
        requireAllNonNull(target, editedTrackedItem);

        projectBook.setTrackedItem(target, editedTrackedItem);
        rescheduleReminders();
    }

    //=========== Filtered Project List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code TrackedItem} backed by the internal list of
     * {@code versionedProjectBook}.
     *
     * @return the filtered tracked item list.
     */
    @Override
    public ObservableList<TrackedItem> getFilteredTrackedItemList() {
        return filteredTrackedItems.get();
    }

    @Override
    public ObjectProperty<FilteredList<TrackedItem>> getObservableFilteredTrackedItemList() {
        return filteredTrackedItems;
    }

    @Override
    public void updateFilteredProjectList(Predicate<TrackedItem> predicate) {
        requireNonNull(predicate);
        currentPredicate = predicate;
        filteredTrackedItems.get().setPredicate(predicate);
    }

    @Override
    public void orderFilteredProjectList(SortType orderType, boolean isAscending, boolean isSortedByCompletionStatus) {
        requireAllNonNull(orderType, isAscending, isSortedByCompletionStatus);
        isCurrentSortAscending = isAscending;
        isCurrentSortIsByCompletionStatus = isSortedByCompletionStatus;
        currentSortType = orderType;
        projectBook.setOrder(orderType, isAscending, isSortedByCompletionStatus);
        updateFilteredProjectList(currentPredicate);
    }

    @Override
    public void viewProjects() {
        viewMode = ViewMode.PROJECTS;
        logger.log(Level.INFO, "View mode changed to project view");
        viewList = projectBook.getTrackedItemList();
        filteredTrackedItems.set(new FilteredList<>(viewList, currentPredicate));
        updateFilteredProjectList(currentPredicate);
    }

    @Override
    public void viewTasks(Project project) {
        requireNonNull(project);
        currentProject = project;
        viewMode = ViewMode.TASKS;
        logger.log(Level.INFO, "View mode changed to task view");
        viewList = project.getTaskList();
        filteredTrackedItems.set(new FilteredList<>(viewList, currentPredicate));
        updateFilteredProjectList(currentPredicate);
    }

    @Override
    public void viewAll() {
        ObservableList<TrackedItem> allItems = FXCollections.observableArrayList();
        for (TrackedItem projectItem : projectBook.getTrackedItemList()) {
            allItems.add(projectItem);
            Project project = (Project) projectItem;
            allItems.addAll(project.getTaskList());
        }
        this.viewList = allItems;
    }

    @Override
    public void resetView() {
        currentPredicate = PREDICATE_SHOW_ALL_TRACKED_ITEMS;
        if (viewMode == ViewMode.PROJECTS) {
            viewProjects();
        } else {
            viewTasks(currentProject);
        }
    }

    @Override
    public ViewMode getViewMode() {
        return viewMode;
    }

    //=========== Reminders =============================================================

    public void rescheduleReminders() {
        reminderManager.rescheduleReminder();
    }

    @Override
    public BooleanProperty isReminderEmpty() {
        return reminderManager.isReminderEmpty();
    }

    @Override
    public StringProperty getReminder() {
        return reminderManager.getReminder();
    }

    @Override
    public void removeReminder() {
        reminderManager.removeReminder();
    }

    //=========== Timers =============================================================

    @Override
    public ObservableList<TrackedItem> getRunningTimers() {
        return runningTimers;
    }

    @Override
    public void addRunningTimer(TrackedItem trackedItem) {
        assert (trackedItem.isRunning());

        runningTimers.add(trackedItem);
    }

    @Override
    public void removeRunningTimer(TrackedItem trackedItem) {
        assert (trackedItem.isRunning());
        assert (runningTimers.contains(trackedItem));

        runningTimers.remove(trackedItem);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return projectBook.equals(other.projectBook)
                && userPrefs.equals(other.userPrefs)
                && reminderManager.equals(other.reminderManager)
                && filteredTrackedItems.equals(other.filteredTrackedItems)
                && runningTimers.equals(other.runningTimers);
    }

}
