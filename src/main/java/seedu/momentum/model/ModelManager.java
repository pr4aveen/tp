package seedu.momentum.model;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
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
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.SortType;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.project.comparators.CompletionStatusCompare;
import seedu.momentum.model.project.comparators.CreatedDateCompare;
import seedu.momentum.model.project.comparators.DeadlineCompare;
import seedu.momentum.model.project.comparators.NameCompare;
import seedu.momentum.model.reminder.ReminderManager;
import seedu.momentum.model.tag.Tag;

/**
 * Represents the in-memory model of the project book data.
 */
public class ModelManager implements Model {
    private static final Logger LOGGER = LogsCenter.getLogger(ModelManager.class);

    private final VersionedProjectBook versionedProjectBook;
    private final UserPrefs userPrefs;
    private final ReminderManager reminderManager;
    private final ObservableList<TrackedItem> runningTimers;
    private Predicate<TrackedItem> currentPredicate;
    private SortType currentSortType;
    private boolean isCurrentSortAscending;
    private boolean isCurrentSortByCompletionStatus;
    private BooleanProperty isTagsVisible;
    private ViewMode viewMode;
    private Project currentProject;
    private ObservableList<TrackedItem> itemList;
    private Comparator<TrackedItem> currentComparator;
    private ObjectProperty<ObservableList<TrackedItem>> displayList;

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
                currentComparator);
        this.reminderManager = new ReminderManager(this.versionedProjectBook);
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

    @Override
    public GuiThemeSettings getGuiThemeSettings() {
        return userPrefs.getGuiThemeSettings();
    }

    @Override
    public void setGuiThemeSettings(GuiThemeSettings guiThemeSettings) {
        requireNonNull(guiThemeSettings);
        //here use returnChangedGuiThemeSettings and replace UserPrefs in ModelManager with updated UserPrefs
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
    public Set<Tag> getVisibleTags() {
        Set<Tag> tags = new HashSet<>();
        ObservableList<TrackedItem> visibleList = displayList.get();
        for (TrackedItem trackedItem : visibleList) {
            tags.addAll(trackedItem.getTags());
        }
        return tags;
    }

    //=========== Tags ================================================================================
    @Override
    public void showOrHideTags() {
        boolean isVisible = this.isTagsVisible.get();
        this.isTagsVisible.set(!isVisible);
    }

    @Override
    public BooleanProperty getIsTagsVisible() {
        return this.isTagsVisible;
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
        updatePredicate(currentPredicate);
        rescheduleReminders();
    }

    @Override
    public void addTrackedItem(TrackedItem trackedItem) {
        versionedProjectBook.addTrackedItem(trackedItem);
        rescheduleReminders();
        updateOrder(currentSortType, isCurrentSortAscending, isCurrentSortByCompletionStatus);
        updatePredicate(PREDICATE_SHOW_ALL_TRACKED_ITEMS);
    }

    @Override
    public void setTrackedItem(TrackedItem target, TrackedItem editedTrackedItem) {
        requireAllNonNull(target, editedTrackedItem);

        versionedProjectBook.setTrackedItem(target, editedTrackedItem);
        if (currentProject != null && currentProject.isSameAs(target)) {
            currentProject = (Project) editedTrackedItem;
            resetUi(viewMode, currentProject);
        }
        rescheduleReminders();
        updateOrder(currentSortType, isCurrentSortAscending, isCurrentSortByCompletionStatus);
    }

    //=========== Filtered Project List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code TrackedItem} backed by the internal list of
     * {@code versionedProjectBook}.
     *
     * @return the filtered tracked item list.
     */
    @Override
    public ObservableList<TrackedItem> getDisplayList() {
        return displayList.get();
    }

    @Override
    public ObjectProperty<ObservableList<TrackedItem>> getObservableDisplayList() {
        return displayList;
    }

    @Override
    public void updatePredicate(Predicate<TrackedItem> predicate) {
        requireNonNull(predicate);
        currentPredicate = predicate;
        updateDisplayList();
    }

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
    private void viewTasksMaintainState(Project project) {
        requireNonNull(project);
        currentProject = project;
        viewMode = ViewMode.TASKS;
        LOGGER.log(Level.INFO, "View mode changed to task view");
        itemList = project.getTaskList();
        updateDisplayList();
    }

    @Override
    public void viewAll() {
        ObservableList<TrackedItem> allItems = FXCollections.observableArrayList();
        for (TrackedItem projectItem : versionedProjectBook.getTrackedItemList()) {
            allItems.add(projectItem);
            Project project = (Project) projectItem;
            allItems.addAll(project.getTaskList());
        }
        this.itemList = allItems;
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

    private void updateDisplayList() {
        displayList.setValue(new SortedList<>(new FilteredList<>(itemList, currentPredicate), currentComparator));
    }

    public int getTotalNumberOfItems() {
        return itemList.size();
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
        versionedProjectBook.commit(viewMode, currentProject, currentPredicate, currentComparator);
    }

    @Override
    public void undoCommand() {

        // extract timer related details of ProjectBook version before undo
        currentProject = versionedProjectBook.getCurrentProject();

        versionedProjectBook.undo();
        Project newProject = versionedProjectBook.getCurrentProject();

        // extract view mode details from ProjectBook version after undo
        viewMode = versionedProjectBook.getCurrentViewMode();

        currentPredicate = versionedProjectBook.getCurrentPredicate();

        currentComparator = versionedProjectBook.getCurrentComparator();

        resetUi(viewMode, newProject);
    }

    @Override
    public void resetUi(ViewMode viewMode, Project project) {
        requireNonNull(viewMode);

        switch (viewMode) {
        case PROJECTS:
            viewProjectsMaintainState();
            LOGGER.log(Level.INFO, "View mode changed to project view");
            break;
        case TASKS:
            assert project != null;
            viewTasksMaintainState(project);
            LOGGER.log(Level.INFO, "View mode changed to task view");
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
        currentPredicate = versionedProjectBook.getCurrentPredicate();
        currentComparator = versionedProjectBook.getCurrentComparator();

        resetUi(viewMode, currentProject);
    }

    //=========== Sorting ================================================================================

    /**
     * Sets the order of the list of tracked items according to given {@code sortType} and {@code isAscending}.
     *
     * @param sortType                   type of sort.
     * @param isAscending                order of sort.
     * @param isSortedByCompletionStatus sort by creation status.
     */
    public Comparator<TrackedItem> getComparator(SortType sortType, boolean isAscending,
                                                 boolean isSortedByCompletionStatus) {
        requireNonNull(sortType);

        switch (sortType) {
        case ALPHA:
            return getComparatorAlphaType(isAscending, isSortedByCompletionStatus);
        case DEADLINE:
            return getComparatorDeadlineType(isAscending, isSortedByCompletionStatus);
        case CREATED:
            return getComparatorCreatedType(isAscending, isSortedByCompletionStatus);
        case NULL:
            return getComparatorNullType(isAscending, isSortedByCompletionStatus);
        default:
            // Will always be one of the above. Default does nothing.
            return null;
        }
    }

    private Comparator<TrackedItem> factorIsAscending(Comparator<TrackedItem> comparator, boolean isAscending) {
        return isAscending ? comparator : comparator.reversed();
    }

    private Comparator<HashMap<String, Object>> factorIsAscendingHashMap(Comparator<HashMap<String, Object>> comparator,
                                                                         boolean isAscending) {
        return isAscending ? comparator : comparator.reversed();
    }

    private Comparator<TrackedItem> factorIsSortedByCompletionStatus(Comparator<TrackedItem> comparator,
                                                                     boolean isSortedByCompletionStatus) {
        return isSortedByCompletionStatus
                ? new CompletionStatusCompare().thenComparing(comparator)
                : comparator;
    }

    /**
     * Sets the order of list of tracked items by alphabetical order, ascending or descending based on user input.
     *
     * @param isAscending                order of sort specified by user.
     * @param isSortedByCompletionStatus sort by creation status.
     */
    private Comparator<TrackedItem> getComparatorAlphaType(boolean isAscending, boolean isSortedByCompletionStatus) {
        Comparator<TrackedItem> nameCompare = factorIsAscending(new NameCompare(), isAscending);
        this.currentSortType = SortType.ALPHA;
        return factorIsSortedByCompletionStatus(nameCompare, isSortedByCompletionStatus);
    }

    /**
     * Sets the order of list of tracked items by deadline order, ascending or descending based on user input.
     *
     * @param isAscending                order of sort specified by user.
     * @param isSortedByCompletionStatus sort by creation status.
     */
    private Comparator<TrackedItem> getComparatorDeadlineType(boolean isAscending, boolean isSortedByCompletionStatus) {
        Comparator<TrackedItem> nameCompare = new NameCompare();
        Comparator<HashMap<String, Object>> deadlineCompareHashMap =
                factorIsAscendingHashMap(new DeadlineCompare(), isAscending);
        Comparator<TrackedItem> deadlineCompare = Comparator.comparing(TrackedItem::getNullOrDeadline,
                Comparator.nullsLast(deadlineCompareHashMap));
        deadlineCompare = deadlineCompare.thenComparing(nameCompare);
        this.currentSortType = SortType.DEADLINE;

        return factorIsSortedByCompletionStatus(deadlineCompare, isSortedByCompletionStatus);
    }

    /**
     * Sets the order of list of tracked items by created date order, ascending or descending based on user input.
     *
     * @param isAscending                order of sort specified by user.
     * @param isSortedByCompletionStatus sort by creation status.
     */
    private Comparator<TrackedItem> getComparatorCreatedType(boolean isAscending, boolean isSortedByCompletionStatus) {
        Comparator<TrackedItem> createdDateCompare = factorIsAscending(new CreatedDateCompare(), isAscending);
        this.currentSortType = SortType.CREATED;
        return factorIsSortedByCompletionStatus(createdDateCompare, isSortedByCompletionStatus);
    }

    /**
     * Sets the order of the list of tracked items to current sort type with specified order
     * if sort type has not been specified by user.
     *
     * @param isAscending                order of sort specified by user.
     * @param isSortedByCompletionStatus sort by creation status.
     */
    private Comparator<TrackedItem> getComparatorNullType(boolean isAscending, boolean isSortedByCompletionStatus) {
        return getComparator(currentSortType, isAscending, isSortedByCompletionStatus);
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
                && displayList.get().equals(other.displayList.get())
                && runningTimers.equals(other.runningTimers)
                && viewMode.equals(other.viewMode);
        //&& currentProject.equals(other.currentProject)
    }

}
