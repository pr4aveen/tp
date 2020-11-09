package seedu.momentum.model;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.GuiWindowSettings;
import seedu.momentum.commons.core.LogsCenter;
import seedu.momentum.commons.core.StatisticTimeframeSettings;
import seedu.momentum.logic.SettingsUpdateManager;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.Task;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.project.comparators.CompletionStatusCompare;
import seedu.momentum.model.project.comparators.CreatedDateCompare;
import seedu.momentum.model.project.comparators.DeadlineCompare;
import seedu.momentum.model.project.comparators.NameCompare;
import seedu.momentum.model.project.comparators.SortType;
import seedu.momentum.model.reminder.ReminderManager;
import seedu.momentum.model.tag.Tag;

/**
 * Represents the in-memory model of the project book data.
 */
public class ModelManager implements Model {
    private static final Logger LOGGER = LogsCenter.getLogger(ModelManager.class);

    private final VersionedProjectBook versionedProjectBook;
    private final ReminderManager reminderManager;
    private final ObservableList<TrackedItem> runningTimers;
    private Predicate<TrackedItem> currentPredicate;
    private SortType currentSortType;
    private boolean isCurrentSortAscending;
    private boolean isCurrentSortByCompletionStatus;
    private final BooleanProperty isTagsVisible;
    private ViewMode viewMode;
    private Project currentProject;
    private ObservableList<TrackedItem> itemList;
    private Comparator<TrackedItem> currentComparator;
    private final ObjectProperty<ObservableList<TrackedItem>> displayList;
    private UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given projectBook and userPrefs.
     */
    public ModelManager(ReadOnlyProjectBook projectBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(projectBook, userPrefs);

        LOGGER.fine("Initializing with project book: " + projectBook + " and user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);

        this.viewMode = ViewMode.PROJECTS;
        this.currentPredicate = PREDICATE_SHOW_ALL_TRACKED_ITEMS;
        this.currentSortType = SortType.ALPHA;
        this.isCurrentSortAscending = true;
        this.isCurrentSortByCompletionStatus = true;
        this.isTagsVisible = new SimpleBooleanProperty();
        this.isTagsVisible.set(true);
        this.currentComparator = getComparatorNullType(true, this.isCurrentSortByCompletionStatus);

        this.versionedProjectBook = new VersionedProjectBook(projectBook, viewMode, currentProject, currentPredicate,
                currentComparator, isTagsVisible.get(), userPrefs);
        this.reminderManager = new ReminderManager(this);
        this.itemList = this.versionedProjectBook.getTrackedItemList();
        this.displayList = new SimpleObjectProperty<>(this.itemList);

        runningTimers = FXCollections.observableArrayList();

        rescheduleReminders();
        viewProjects();
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

    //@@author khoodehui
    @Override
    public GuiThemeSettings getGuiThemeSettings() {
        return userPrefs.getGuiThemeSettings();
    }

    @Override
    public void setGuiThemeSettings(GuiThemeSettings guiThemeSettings) {
        requireNonNull(guiThemeSettings);
        userPrefs = userPrefs.returnChangedGuiThemeSettings(guiThemeSettings);
    }

    @Override
    public StatisticTimeframeSettings getStatisticTimeframeSettings() {
        return userPrefs.getStatisticTimeframeSettings();
    }

    @Override
    public void setStatisticTimeframeSettings(StatisticTimeframeSettings statisticTimeframeSettings) {
        requireNonNull(statisticTimeframeSettings);
        userPrefs = userPrefs.returnChangedStatisticsTimeframeSettings(statisticTimeframeSettings);
    }
    //@@author

    @Override
    public Path getProjectBookFilePath() {
        return userPrefs.getProjectBookFilePath();
    }

    @Override
    public void setProjectBookFilePath(Path projectBookFilePath) {
        requireNonNull(projectBookFilePath);
        userPrefs = userPrefs.returnChangedProjectBookFilePath(projectBookFilePath);
    }

    //=========== ProjectBook ================================================================================
    //@@author kkangs0226
    @Override
    public void setVersionedProjectBook(ReadOnlyProjectBook versionedProjectBook) {
        this.versionedProjectBook.resetData(versionedProjectBook);
        rescheduleReminders();
    }
    //@@author

    @Override
    public VersionedProjectBook getProjectBook() {
        return versionedProjectBook;
    }

    //@@author pr4aveen
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
        updatePredicate(currentPredicate);
        rescheduleReminders();
    }

    @Override
    public void addTrackedItem(TrackedItem trackedItem) {
        versionedProjectBook.addTrackedItem(trackedItem);
        rescheduleReminders();
        updateOrder(currentSortType, isCurrentSortAscending);
        updatePredicate(PREDICATE_SHOW_ALL_TRACKED_ITEMS);
    }

    @Override
    public void setTrackedItem(TrackedItem target, TrackedItem editedTrackedItem) {
        requireAllNonNull(target, editedTrackedItem);

        versionedProjectBook.setTrackedItem(target, editedTrackedItem);
        if (currentProject != null && currentProject.isSameAs(target)) {
            currentProject = (Project) editedTrackedItem;
            resetUi(viewMode);
        }
        rescheduleReminders();
        updateOrder(currentSortType, isCurrentSortAscending);
    }

    //@@author boundtotheearth
    @Override
    public void viewProjects() {
        viewMode = ViewMode.PROJECTS;
        LOGGER.log(Level.INFO, "View mode changed to project view");
        itemList = versionedProjectBook.getTrackedItemList();
        currentPredicate = PREDICATE_SHOW_ALL_TRACKED_ITEMS;
        updateDisplayList();
    }

    /**
     * Updates the view to project view with specific predicate and comparator.
     */
    private void viewProjectsMaintainState() {
        viewMode = ViewMode.PROJECTS;
        LOGGER.log(Level.INFO, "View mode changed to project view");
        itemList = versionedProjectBook.getTrackedItemList();
        updateDisplayList();
    }

    @Override
    public void viewTasks(Project project) {
        requireNonNull(project);
        currentProject = project;
        viewMode = ViewMode.TASKS;
        LOGGER.log(Level.INFO, "View mode changed to task view");
        itemList = project.getTaskList();
        currentPredicate = PREDICATE_SHOW_ALL_TRACKED_ITEMS;
        updateDisplayList();
    }

    /**
     * Update view to task view with specific predicate and comparator.
     */
    private void viewTasksMaintainState() {
        requireNonNull(currentProject);
        viewMode = ViewMode.TASKS;
        LOGGER.log(Level.INFO, "View mode changed to task view");
        itemList = currentProject.getTaskList();
        updateDisplayList();
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

    //@@author pr4aveen
    @Override
    public int getTotalNumberOfItems() {
        return itemList.size();
    }

    //=========== Tags =======================================================================================
    @Override
    public Set<Tag> getVisibleTags() {
        Set<Tag> tags = new HashSet<>();
        ObservableList<TrackedItem> visibleList = displayList.get();
        for (TrackedItem trackedItem : visibleList) {
            tags.addAll(trackedItem.getTags());
        }
        return tags;
    }

    //@@author claracheong4
    @Override
    public void showOrHideTags() {
        boolean isVisible = this.isTagsVisible.get();
        this.isTagsVisible.set(!isVisible);
    }

    @Override
    public BooleanProperty getIsTagsVisible() {
        return this.isTagsVisible;
    }

    //=========== Filtered Project List Accessors ============================================================
    //@@author boundtotheearth
    /**
     * Returns an unmodifiable view of the list of {@code TrackedItem} backed by the internal list of
     * {@code versionedProjectBook}.
     *
     * @return The filtered tracked item list.
     */
    @Override
    public ObservableList<TrackedItem> getDisplayList() {
        return displayList.get();
    }

    @Override
    public ObjectProperty<ObservableList<TrackedItem>> getObservableDisplayList() {
        return displayList;
    }

    //@@author pr4aveen
    @Override
    public void updatePredicate(Predicate<TrackedItem> predicate) {
        requireNonNull(predicate);
        currentPredicate = predicate;
        updateDisplayList();
    }

    //@@author boundtotheearth
    private void updateDisplayList() {
        displayList.setValue(new SortedList<>(new FilteredList<>(itemList, currentPredicate), currentComparator));
    }

    //=========== Reminders ==================================================================================
    //@@author claracheong4
    private void rescheduleReminders() {
        reminderManager.rescheduleReminder();
    }

    @Override
    public void rescheduleReminder() {
        this.versionedProjectBook.rescheduleReminder(reminderManager);
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
    public void removeReminderShown() {
        reminderManager.removeReminder();
    }

    @Override
    public void removeReminder(Project project) {
        Project newProject = this.versionedProjectBook.removeReminder(project);
        if (currentProject != null && currentProject.isSameAs(project)) {
            currentProject = newProject;
            resetUi(viewMode);
        }
        rescheduleReminders();
    }

    @Override
    public void removeReminder(Project project, Task task) {
        Project newProject = this.versionedProjectBook.removeReminder(project);
        if (currentProject != null && currentProject.isSameAs(project)) {
            currentProject = newProject;
            resetUi(viewMode);
        }
        rescheduleReminders();
    }

    //=========== Timers =====================================================================================
    //@@author boundtotheearth
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
                updateTaskRunningTimers(trackedItem);
            }
        }
    }

    private void updateTaskRunningTimers(TrackedItem trackedItem) {
        Project project = (Project) trackedItem;
        for (TrackedItem taskItem : project.getTaskList()) {
            if (taskItem.isRunning()) {
                runningTimers.add(taskItem);
            }
        }
    }

    //=========== Undo/Redo ==================================================================================
    //@@author kkangs0226
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
        versionedProjectBook.commit(viewMode, currentProject, currentPredicate, currentComparator, isTagsVisible.get(),
                userPrefs);
    }

    @Override
    public void undoCommand() {

        assert canUndoCommand();
        versionedProjectBook.undo();

        // extract view mode details from ProjectBook version after undo
        viewMode = versionedProjectBook.getCurrentViewMode();
        currentProject = versionedProjectBook.getCurrentProject();
        currentPredicate = versionedProjectBook.getCurrentPredicate();
        currentComparator = versionedProjectBook.getCurrentComparator();

        resetUi(viewMode);

        rescheduleReminders();
    }

    @Override
    public void redoCommand() {

        assert canRedoCommand();
        versionedProjectBook.redo();

        // extract both timer related and ViewMode details from ProjectBook version after redo
        viewMode = versionedProjectBook.getCurrentViewMode();
        currentProject = versionedProjectBook.getCurrentProject();
        currentPredicate = versionedProjectBook.getCurrentPredicate();
        currentComparator = versionedProjectBook.getCurrentComparator();

        resetUi(viewMode);

        rescheduleReminders();
    }

    @Override
    public void resetUi(ViewMode viewMode) {
        requireNonNull(viewMode);

        isTagsVisible.setValue(versionedProjectBook.isTagsVisible());
        SettingsUpdateManager.updateApplicationSettings(versionedProjectBook.getUserPrefs());

        switch (viewMode) {
        case PROJECTS:
            viewProjectsMaintainState();
            break;
        case TASKS:
            assert currentProject != null;
            viewTasksMaintainState();
            break;
        default:
            break;
        }
    }

    //=========== Sorting ====================================================================================
    @Override
    public void updateOrder(SortType sortType, boolean isAscending, boolean changeSortByCompletionStatus) {
        requireAllNonNull(sortType, isAscending, changeSortByCompletionStatus);
        isCurrentSortAscending = isAscending;
        if (changeSortByCompletionStatus) {
            isCurrentSortByCompletionStatus = !isCurrentSortByCompletionStatus;
        }
        currentComparator = getComparator(sortType, isAscending, isCurrentSortByCompletionStatus);
        updateDisplayList();
    }

    @Override
    public void updateOrder(SortType sortType, boolean isAscending) {
        updateOrder(sortType, isAscending, false);
    }

    @Override
    public boolean getIsCurrentSortByCompletionStatus() {
        return isCurrentSortByCompletionStatus;
    }


    /**
     * Sets the order of the list of tracked items according to given {@code sortType} and {@code isAscending}.
     *
     * @param sortType                   Type of sort.
     * @param isAscending                Order of sort.
     * @param isSortedByCompletionStatus Sort by creation status.
     */
    private Comparator<TrackedItem> getComparator(SortType sortType, boolean isAscending,
                                                 boolean isSortedByCompletionStatus) {
        requireNonNull(sortType);
        Comparator<TrackedItem> comparator;

        switch (sortType) {
        case ALPHA:
            comparator = getComparatorAlphaType(isAscending);
            break;
        case DEADLINE:
            comparator = getComparatorDeadlineType(isAscending);
            break;
        case CREATED:
            comparator = getComparatorCreatedType(isAscending);
            break;
        case NULL:
            comparator = getComparatorNullType(isAscending, isSortedByCompletionStatus);
            break;
        default:
            // Will always be one of the above. Default does nothing.
            return null;
        }

        if (isSortedByCompletionStatus) {
            return new CompletionStatusCompare().thenComparing(comparator);
        }

        return comparator;
    }

    private Comparator<TrackedItem> factorIsAscending(Comparator<TrackedItem> comparator, boolean isAscending) {
        return isAscending ? comparator : comparator.reversed();
    }

    private Comparator<HashMap<String, Object>> factorIsAscendingHashMap(Comparator<HashMap<String, Object>> comparator,
                                                                         boolean isAscending) {
        return isAscending ? comparator : comparator.reversed();
    }
    /**
     * Sets the order of list of tracked items by alphabetical order, ascending or descending based on user input.
     *
     * @param isAscending Order of sort specified by user.
     */
    private Comparator<TrackedItem> getComparatorAlphaType(boolean isAscending) {
        Comparator<TrackedItem> nameCompare = factorIsAscending(new NameCompare(), isAscending);
        this.currentSortType = SortType.ALPHA;
        return nameCompare;
    }

    /**
     * Sets the order of list of tracked items by deadline order, ascending or descending based on user input.
     *
     * @param isAscending Order of sort specified by user.
     */
    private Comparator<TrackedItem> getComparatorDeadlineType(boolean isAscending) {
        Comparator<TrackedItem> nameCompare = new NameCompare();
        Comparator<HashMap<String, Object>> deadlineCompareHashMap =
                factorIsAscendingHashMap(new DeadlineCompare(), isAscending);
        Comparator<TrackedItem> deadlineCompare = Comparator.comparing(TrackedItem::getNullOrDeadline,
                Comparator.nullsLast(deadlineCompareHashMap));
        deadlineCompare = deadlineCompare.thenComparing(nameCompare);
        this.currentSortType = SortType.DEADLINE;

        return deadlineCompare;
    }

    /**
     * Sets the order of list of tracked items by created date order, ascending or descending based on user input.
     *
     * @param isAscending Order of sort specified by user.
     */
    private Comparator<TrackedItem> getComparatorCreatedType(boolean isAscending) {
        Comparator<TrackedItem> createdDateCompare = factorIsAscending(new CreatedDateCompare(), isAscending);
        this.currentSortType = SortType.CREATED;
        return createdDateCompare;
    }

    /**
     * Sets the order of the list of tracked items to current sort type with specified order
     * if sort type has not been specified by user.
     *
     * @param isAscending                Order of sort specified by user.
     * @param isSortedByCompletionStatus Sort by creation status.
     * @return The comparator based on the currently specified sort type and completion status.
     */
    private Comparator<TrackedItem> getComparatorNullType(boolean isAscending, boolean isSortedByCompletionStatus) {
        return getComparator(currentSortType, isAscending, isSortedByCompletionStatus);
    }
    //@@author

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
                && displayList.get().equals(other.displayList.get())
                && runningTimers.equals(other.runningTimers)
                && viewMode.equals(other.viewMode)
                && Objects.equals(currentProject, other.currentProject);
    }

}
