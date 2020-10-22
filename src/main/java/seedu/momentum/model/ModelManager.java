package seedu.momentum.model;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.momentum.commons.core.GuiSettings;
import seedu.momentum.commons.core.LogsCenter;
import seedu.momentum.commons.core.Messages;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.SortType;

/**
 * Represents the in-memory model of the project book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ProjectBook projectBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Project> filteredProjects;
    private final ObservableList<Project> runningTimers;
    private Predicate<Project> currentPredicate;
    private SortType currentSortType;
    private boolean currentSortIsAscending;
    private ViewMode viewMode;
    private ObservableList<Project> viewList;

    /**
     * Initializes a ModelManager with the given projectBook and userPrefs.
     */
    public ModelManager(ReadOnlyProjectBook projectBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(projectBook, userPrefs);

        logger.fine("Initializing with project book: " + projectBook + " and user prefs " + userPrefs);

        this.projectBook = new ProjectBook(projectBook);
        this.userPrefs = new UserPrefs(userPrefs);

        currentPredicate = PREDICATE_SHOW_ALL_PROJECTS;
        currentSortType = SortType.ALPHA;
        currentSortIsAscending = true;
        viewMode = ViewMode.PROJECTS;

        this.viewList = FXCollections.observableArrayList();
        filteredProjects = new FilteredList<>(viewList);
        viewProjects();

        runningTimers = FXCollections.observableArrayList();
        initializeRunningTimers();
    }

    public ModelManager() {
        this(new ProjectBook(), new UserPrefs());
    }

    private void initializeRunningTimers() {
        for (Project project : filteredProjects) {
            if (project.isRunning()) {
                runningTimers.add(project);
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
    }

    @Override
    public ReadOnlyProjectBook getProjectBook() {
        return projectBook;
    }

    @Override
    public boolean hasProject(Project project) {
        requireNonNull(project);
        return projectBook.hasProject(project);
    }

    @Override
    public void deleteProject(Project target) {
        projectBook.renameProject(target);
    }

    @Override
    public void addProject(Project project) {
        projectBook.addProject(project);
        orderFilteredProjectList(currentSortType, currentSortIsAscending);
        updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
    }

    @Override
    public void setProject(Project target, Project editedProject) {
        requireAllNonNull(target, editedProject);

        projectBook.setProject(target, editedProject);
    }

    //=========== Filtered Project List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Project} backed by the internal list of
     * {@code versionedProjectBook}
     */
    @Override
    public ObservableList<Project> getFilteredProjectList() {
        return filteredProjects;
    }

    @Override
    public void updateFilteredProjectList(Predicate<Project> predicate) {
        requireNonNull(predicate);
        currentPredicate = predicate;
        filteredProjects.setPredicate(predicate);
    }

    @Override
    public void orderFilteredProjectList(SortType orderType, boolean isAscending) {
        requireAllNonNull(orderType, isAscending);
        currentSortIsAscending = isAscending;
        currentSortType = orderType;
        projectBook.setOrder(orderType, isAscending);
        updateFilteredProjectList(currentPredicate);
    }

    @Override
    public void viewProjects() {
        viewMode = ViewMode.PROJECTS;
        this.viewList.setAll(projectBook.getProjectList());
        this.projectBook.getProjectList().addListener(
                (ListChangeListener<Project>) c -> viewList.setAll(projectBook.getProjectList())
        );

        updateFilteredProjectList(currentPredicate);
    }

    @Override
    public void viewTasks(Project project) {
        viewMode = ViewMode.TASKS;

        this.viewList.setAll(project.getTaskList());
        project.getTaskList().addListener(
                (ListChangeListener<Project>) c -> viewList.setAll(project.getTaskList())
        );

        System.out.println("View Project's Tasks");
    }

    @Override
    public ViewMode getViewMode() {
        return viewMode;
    }

    //=========== Timers =============================================================

    @Override
    public ObservableList<Project> getRunningTimers() {
        return runningTimers;
    }

    @Override
    public void addRunningTimer(Project project) {
        assert (project.isRunning());

        runningTimers.add(project);
    }

    @Override
    public void removeRunningTimer(Project project) {
        assert (project.isRunning());
        assert (runningTimers.contains(project));

        runningTimers.remove(project);
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
                && filteredProjects.equals(other.filteredProjects)
                && runningTimers.equals(other.runningTimers);
    }

}
