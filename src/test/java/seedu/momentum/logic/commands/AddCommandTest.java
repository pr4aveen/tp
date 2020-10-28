package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import seedu.momentum.commons.core.GuiSettings;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.ReadOnlyProjectBook;
import seedu.momentum.model.ReadOnlyUserPrefs;
import seedu.momentum.model.VersionedProjectBook;
import seedu.momentum.model.ViewMode;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.SortType;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.testutil.ProjectBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_projectAcceptedByModel_addSuccessful() throws Exception {
        ModelStubSetModelManager modelStub = new ModelStubSetModelManager();
        Project validProject = new ProjectBuilder().build();

        CommandResult commandResult = new AddCommand(validProject).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validProject), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validProject), modelStub.projectsAdded);
    }

    @Test
    public void execute_duplicateProject_throwsCommandException() {
        Project validProject = new ProjectBuilder().build();
        AddCommand addCommand = new AddCommand(validProject);
        ModelStub modelStub = new ModelStubWithProject(validProject);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PROJECT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Project alice = new ProjectBuilder().withName("Alice").build();
        Project bob = new ProjectBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different project -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getProjectBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProjectBookFilePath(Path projectBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTrackedItem(TrackedItem trackedItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setVersionedProjectBook(ReadOnlyProjectBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProjectBook getProjectBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTrackedItem(TrackedItem trackedItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTrackedItem(TrackedItem target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<TrackedItem> getFilteredTrackedItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void orderFilteredProjectList(SortType sortType, boolean isAscending,
                                             boolean isSortedByCompletionStatus) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<TrackedItem> getRunningTimers() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRunningTimers() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void rescheduleReminders() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public BooleanProperty isReminderEmpty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public StringProperty getReminder() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeReminder() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ViewMode getViewMode() {
            return ViewMode.PROJECTS;
        }

        @Override
        public void viewTasks(Project project) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTrackedItem(ViewMode viewMode, TrackedItem target, TrackedItem editedTrackedItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredProjectList(Predicate<TrackedItem> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void viewProjects() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Project getCurrentProject() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void viewAll() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetView() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoCommand() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoCommand() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitToHistory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoCommand() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetUi(boolean isUndo, ViewMode viewMode, boolean isPreviousCommandTimer,
                       Project project, TrackedItem runningTimer, boolean toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIsPreviousCommandTimerToTrue() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIsPreviousCommandTimerToFalse() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoCommand() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single project.
     */
    private class ModelStubWithProject extends ModelStub {
        private final Project project;

        ModelStubWithProject(Project project) {
            requireNonNull(project);
            this.project = project;
        }

        @Override
        public boolean hasTrackedItem(TrackedItem trackedItem) {
            requireNonNull(trackedItem);
            return this.project.isSameTrackedItem(trackedItem);
        }
    }

    /**
     * A Model stub that always accept the project being added.
     */
    private class ModelStubAcceptingProjectAdded extends ModelStub {
        public final ArrayList<TrackedItem> projectsAdded = new ArrayList<>();

        @Override
        public boolean hasTrackedItem(TrackedItem trackedItem) {
            requireNonNull(trackedItem);
            return projectsAdded.stream().anyMatch(trackedItem::isSameTrackedItem);
        }

        @Override
        public void addTrackedItem(TrackedItem trackedItem) {
            requireNonNull(trackedItem);
            projectsAdded.add(trackedItem);
        }

        @Override
        public ReadOnlyProjectBook getProjectBook() {
            return new ProjectBook();
        }
    }

    /**
     * A Model stub that resets {@code ModelManager}.
     */
    private class ModelStubSetModelManager extends ModelStubAcceptingProjectAdded {
        private boolean isPreviousCommandTimer;
        private ViewMode viewMode = ViewMode.PROJECTS;
        private Project currentProject = null;
        private TrackedItem runningTimer = null;
        private boolean toAdd = false;
        private final VersionedProjectBook versionedProjectBook =
                new VersionedProjectBook(new ProjectBook(), viewMode,
                        isPreviousCommandTimer, currentProject, runningTimer, toAdd);

        @Override
        public void setIsPreviousCommandTimerToTrue() {
            isPreviousCommandTimer = true;
        }

        @Override
        public void setIsPreviousCommandTimerToFalse() {
            isPreviousCommandTimer = false;
        }

        @Override
        public void commitToHistory() {
            versionedProjectBook.commit(viewMode, isPreviousCommandTimer, currentProject, runningTimer, toAdd);
        }
    }
}
