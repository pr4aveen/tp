package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.commons.core.Messages.MESSAGE_TEXT_PROJECT;
import static seedu.momentum.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.GuiWindowSettings;
import seedu.momentum.commons.core.StatisticTimeframeSettings;
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
import seedu.momentum.model.tag.Tag;
import seedu.momentum.testutil.ProjectBuilder;

public class AddProjectCommandTest {

    @Test
    public void constructor_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddProjectCommand(null));
    }

    @Test
    public void execute_projectAcceptedByModel_addSuccessful() throws Exception {
        ModelStubSetModelManager modelStub = new ModelStubSetModelManager();
        Project validProject = new ProjectBuilder().build();

        CommandResult commandResult = new AddProjectCommand(validProject).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, MESSAGE_TEXT_PROJECT, validProject),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validProject), modelStub.projectsAdded);
    }

    @Test
    public void execute_duplicateProject_throwsCommandException() {
        Project validProject = new ProjectBuilder().build();
        AddProjectCommand addCommand = new AddProjectCommand(validProject);
        ModelStub modelStub = new ModelStubWithProject(validProject);
        String expectedMessage = String.format(AddCommand.MESSAGE_DUPLICATE_ENTRY, MESSAGE_TEXT_PROJECT);
        assertThrows(CommandException.class, expectedMessage, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Project alice = new ProjectBuilder().withName("Alice").build();
        Project bob = new ProjectBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddProjectCommand(alice);
        AddCommand addBobCommand = new AddProjectCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddProjectCommand(alice);
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
        public GuiWindowSettings getGuiWindowSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiWindowSettings(GuiWindowSettings guiWindowSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiThemeSettings getGuiThemeSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiThemeSettings(GuiThemeSettings guiThemeSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public StatisticTimeframeSettings getStatisticTimeframeSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStatisticTimeframeSettings(StatisticTimeframeSettings statisticTimeframeSettings) {
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
        public ObservableList<TrackedItem> getDisplayList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObjectProperty<ObservableList<TrackedItem>> getObservableDisplayList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateOrder(SortType sortType, boolean isAscending,
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
        public void setTrackedItem(TrackedItem target, TrackedItem editedTrackedItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePredicate(Predicate<TrackedItem> predicate) {
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
        public void resetUi(ViewMode viewMode, Project project) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoCommand() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Set<Tag> getVisibleTags() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getTotalNumberOfItems() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void showOrHideTags() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public BooleanProperty getIsTagsVisible() {
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
            return this.project.isSameAs(trackedItem);
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
            return projectsAdded.stream().anyMatch(trackedItem::isSameAs);
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
        private ViewMode viewMode = ViewMode.PROJECTS;
        private Project currentProject = null;
        private Predicate<TrackedItem> currentPredicate = PREDICATE_SHOW_ALL_TRACKED_ITEMS;
        private Comparator<TrackedItem> currentComparator = null;
        private final VersionedProjectBook versionedProjectBook = new VersionedProjectBook(new ProjectBook(),
                viewMode, currentProject, currentPredicate, currentComparator);

        @Override
        public void commitToHistory() {
            versionedProjectBook.commit(viewMode, currentProject, currentPredicate, currentComparator);
        }
    }
}
