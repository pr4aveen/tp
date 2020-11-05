package seedu.momentum.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;

/**
 * Represents a project book that keeps track of its state, so that it can undo/redo changes.
 */
public class VersionedProjectBook extends ProjectBook {

    private static final String UNDO = "undo";
    private static final String REDO = "redo";
    private static final String COMMIT = "commit";

    private List<ProjectBookWithUi> projectBookStateList;
    private int currentStatePointer;

    /**
     * Constructs a {@code VersionedProjectBook}.
     */
    public VersionedProjectBook(ReadOnlyProjectBook projectBook,
                                ViewMode viewMode,
                                Project currentProject,
                                Predicate<TrackedItem> currentPredicate,
                                Comparator<TrackedItem> currentComparator,
                                boolean isTagsVisible,
                                ReadOnlyUserPrefs userPrefs) {
        super(projectBook);
        this.projectBookStateList = new ArrayList<>();
        projectBookStateList.add(new ProjectBookWithUi(projectBook, viewMode,
                currentProject, currentPredicate, currentComparator, isTagsVisible, userPrefs));
        currentStatePointer = 0;
    }

    /**
     * Flushes out versions to be redone after the {@code currentStatePointer} and
     * commits current {@code VersionedProjectBook} into {@code projectBookStateList}.
     */
    public void commit(ViewMode viewMode, Project currentProject, Predicate<TrackedItem> currentPredicate,
                       Comparator<TrackedItem> currentComparator, boolean isTagsVisible, ReadOnlyUserPrefs userPrefs) {
        int historySize = projectBookStateList.size();
        if (currentStatePointer < historySize - 1) {
            flushRedoVersions();
        }
        projectBookStateList.add(new ProjectBookWithUi(this, viewMode,
                currentProject, currentPredicate, currentComparator, isTagsVisible, userPrefs));
        shiftPointer(COMMIT);
    }

    /**
     * Undoes command that was previously executed.
     */
    public void undo() {
        assert canUndoCommand();
        shiftPointer(UNDO);
        ReadOnlyProjectBook undoVersion = projectBookStateList.get(currentStatePointer);
        resetData(undoVersion);
    }

    /**
     * Redoes the command that was previously undone.
     */
    public void redo() {
        assert canRedoCommand();
        shiftPointer(REDO);
        ReadOnlyProjectBook redoVersion = projectBookStateList.get(currentStatePointer);
        resetData(redoVersion);
    }

    private void shiftPointer(String command) {
        if (command.equals(REDO) || command.equals(COMMIT)) {
            currentStatePointer++;
        } else {
            currentStatePointer--;
        }
    }

    private void flushRedoVersions() {
        int historySize = projectBookStateList.size();
        int nextPointer = currentStatePointer + 1;
        for (int i = nextPointer; i < historySize; i++) {
            projectBookStateList.remove(nextPointer);
        }
    }

    /**
     * Checks if {@code VersionedProjectBook} is able to undo commands.
     *
     * @return true if {@code VersionedProjectBook} has commands to undo.
     */
    public boolean canUndoCommand() {
        return currentStatePointer != 0;
    }

    /**
     * Checks if {@code VersionedProjectBook} is able to redo commands.
     *
     * @return true if {@code VersionedProjectBook} has commands to redo.
     */
    public boolean canRedoCommand() {
        int size = projectBookStateList.size();
        return currentStatePointer < size - 1;

    }

    public List<ProjectBookWithUi> getProjectBookStateList() {
        return projectBookStateList;
    }

    public int getCurrentStatePointer() {
        return currentStatePointer;
    }

    public ProjectBookWithUi getCurrentProjectBookWithUi() {
        return projectBookStateList.get(currentStatePointer);
    }

    public ViewMode getCurrentViewMode() {
        return getCurrentProjectBookWithUi().getViewMode();
    }

    public Project getCurrentProject() {
        return getCurrentProjectBookWithUi().getProject();
    }

    public Predicate<TrackedItem> getCurrentPredicate() {
        return getCurrentProjectBookWithUi().getPredicate();
    }

    public Comparator<TrackedItem> getCurrentComparator() {
        return getCurrentProjectBookWithUi().getComparator();
    }

    public boolean isTagsVisible() {
        return getCurrentProjectBookWithUi().isTagsVisible();
    }

    public ReadOnlyUserPrefs getUserPrefs() {
        return getCurrentProjectBookWithUi().getUserPrefs();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other == this // short circuit if same object
                || (other instanceof VersionedProjectBook // instanceof handles nulls
                && projectBookStateList.equals(((VersionedProjectBook) other).getProjectBookStateList())
                && currentStatePointer == ((VersionedProjectBook) other).getCurrentStatePointer()));
    }

}
