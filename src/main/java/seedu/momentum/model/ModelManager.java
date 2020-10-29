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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.GuiWindowSettings;
import seedu.momentum.commons.core.LogsCenter;
import seedu.momentum.commons.core.StatisticTimeframeSettings;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.SortType;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.reminder.ReminderManager;

/**
 * Represents the in-memory model of the project book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedProjectBook versionedProjectBook;
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

        this.userPrefs = new UserPrefs(userPrefs);

        currentPredicate = PREDICATE_SHOW_ALL_TRACKED_ITEMS;
        currentSortType = SortType.ALPHA;
        isCurrentSortAscending = true;
        isCurrentSortIsByCompletionStatus = true;
        viewMode = ViewMode.PROJECTS;

        this.versionedProjectBook = new VersionedProjectBook(projectBook, viewMode, currentProject);
        this.reminderManager = new ReminderManager(this.versionedProjectBook);
        rescheduleReminders();
        this.viewList = this.versionedProjectBook.getTrackedItemList();
        filteredTrackedItems = new SimpleObjectProperty<>(new FilteredList<>(viewList, currentPredicate));
        //filteredTrackedItems = new FilteredList<>(viewList);
        viewProjects();

        runningTimers = FXCollections.observableArrayList();
        updateRunningTimers();

    }

    public ModelManager() {
        this(new ProjectBook(), new UserPrefs());
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
    public GuiWindowSettings getGuiWindowSettings() {
        return userPrefs.getGuiWindowSettings();
    }

    @Override
    public void setGuiWindowSettings(GuiWindowSettings guiWindowSettings) {
        requireNonNull(guiWindowSettings);
        userPrefs.setGuiWindowSettings(guiWindowSettings);
    }

    @Override
    public GuiThemeSettings getGuiThemeSettings() {
        return userPrefs.getGuiThemeSettings();
    }

    @Override
    public void setGuiThemeSettings(GuiThemeSettings guiThemeSettings) {
        requireNonNull(guiThemeSettings);
        userPrefs.setGuiThemeSettings(guiThemeSettings);
    }

    @Override
    public StatisticTimeframeSettings getStatisticTimeframeSettings() {
        return userPrefs.getStatisticTimeframeSettings();
    }

    @Override
    public void setStatisticTimeframeSettings(StatisticTimeframeSettings statisticTimeframeSettings) {
        requireNonNull(statisticTimeframeSettings);
        userPrefs.setStatisticTimeframeSettings(statisticTimeframeSettings);
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
    public void setVersionedProjectBook(ReadOnlyProjectBook versionedProjectBook) {
        this.versionedProjectBook.resetData(versionedProjectBook);
        rescheduleReminders();
    }

    @Override
    public VersionedProjectBook getProjectBook() {
        return versionedProjectBook;
    }

    @Override
    public Project getCurrentProject() {
        assert viewMode == ViewMode.TASKS : "Project can only be accessed in task view";

        return currentProject;
    }

    @Override
    public boolean hasTrackedItem(TrackedItem trackedItem) {
        requireNonNull(trackedItem);
        return versionedProjectBook.hasTrackedItem(trackedItem);
    }

    @Override
    public void deleteTrackedItem(TrackedItem target) {
        versionedProjectBook.removeTrackedItem(target);
        rescheduleReminders();
    }

    @Override
    public void addTrackedItem(TrackedItem trackedItem) {
        versionedProjectBook.addTrackedItem(trackedItem);
        rescheduleReminders();
        orderFilteredProjectList(currentSortType, isCurrentSortAscending, isCurrentSortIsByCompletionStatus);
        updateFilteredProjectList(PREDICATE_SHOW_ALL_TRACKED_ITEMS);
    }

    @Override
    public void setTrackedItem(TrackedItem target, TrackedItem editedTrackedItem) {
        requireAllNonNull(target, editedTrackedItem);

        versionedProjectBook.setTrackedItem(target, editedTrackedItem);
        rescheduleReminders();
        orderFilteredProjectList(currentSortType, isCurrentSortAscending, isCurrentSortIsByCompletionStatus);

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
    public void orderFilteredProjectList(SortType sortType, boolean isAscending, boolean isSortedByCompletionStatus) {
        requireAllNonNull(sortType, isAscending, isSortedByCompletionStatus);
        isCurrentSortAscending = isAscending;
        isCurrentSortIsByCompletionStatus = isSortedByCompletionStatus;
        currentSortType = sortType;
        versionedProjectBook.setOrder(sortType, isAscending, isSortedByCompletionStatus);
        updateFilteredProjectList(currentPredicate);
    }

    @Override
    public void viewProjects() {
        viewMode = ViewMode.PROJECTS;
        logger.log(Level.INFO, "View mode changed to project view");
        viewList = versionedProjectBook.getTrackedItemList();
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
        for (TrackedItem projectItem : versionedProjectBook.getTrackedItemList()) {
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

    /**
     * Update the running timers list.
     */
    public void updateRunningTimers() {
        runningTimers.clear();
        for (TrackedItem trackedItem : versionedProjectBook.getTrackedItemList()) {
            if (trackedItem.isRunning()) {
                runningTimers.add(trackedItem);
            }

            if (!trackedItem.isTask()) {
                Project project = (Project) trackedItem;
                for (TrackedItem taskItem : project.getTaskList()) {
                    if (taskItem.isRunning()) {
                        runningTimers.add(taskItem);
                    }
                }
            }
        }
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
        return versionedProjectBook.equals(other.versionedProjectBook)
                && userPrefs.equals(other.userPrefs)
                && reminderManager.equals(other.reminderManager)
                && filteredTrackedItems.get().equals(other.filteredTrackedItems.get())
                && runningTimers.equals(other.runningTimers)
                && viewMode.equals(other.viewMode);
                //&& currentProject.equals(other.currentProject)
    }

    //=========== Undo/Redo ================================================================================

    @Override
    public boolean canUndoCommand() {
        return versionedProjectBook.canUndoCommand();
    }

    @Override
    public boolean canRedoCommand() {
        return versionedProjectBook.canRedoCommand();
    }

    @Override
    public void commitToHistory() {
        versionedProjectBook.commit(viewMode, currentProject);
    }

    @Override
    public void undoCommand() {

        // extract timer related details of ProjectBook version before undo
        currentProject = versionedProjectBook.getCurrentProject();

        versionedProjectBook.undo();
        Project newProject = versionedProjectBook.getCurrentProject();

        // extract view mode details from ProjectBook version after undo
        viewMode = versionedProjectBook.getCurrentViewMode();

        resetUi(viewMode, currentProject);
        if (viewMode == ViewMode.TASKS) {
            viewTasks(newProject);
        }
    }

    @Override
    public void resetUi(ViewMode viewMode, Project project) {
        requireNonNull(viewMode);

        switch (viewMode) {
        case PROJECTS:
            viewProjects();
            logger.log(Level.INFO, "View mode changed to project view");
            break;
        case TASKS:
            assert project != null;
            viewTasks(project);
            logger.log(Level.INFO, "View mode changed to task view");
            break;
        default:
            break;
        }

    }

    @Override
    public void redoCommand() {

        versionedProjectBook.redo();

        // extract both timer related and ViewMode details from ProjectBook version after redo
        viewMode = versionedProjectBook.getCurrentViewMode();
        currentProject = versionedProjectBook.getCurrentProject();

        resetUi(viewMode, currentProject);
        if (viewMode == ViewMode.TASKS) {
            viewTasks(currentProject);
        }
    }
}
