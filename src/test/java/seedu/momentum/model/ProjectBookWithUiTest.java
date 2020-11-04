package seedu.momentum.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.project.comparators.NameCompare;
import seedu.momentum.testutil.ProjectBuilder;

public class ProjectBookWithUiTest {

    private final Project validProject = new ProjectBuilder().withName("TEST").build();
    private final Comparator<TrackedItem> nameCompare = new NameCompare();

    private final ProjectBookWithUi projectBookWithUi =
            new ProjectBookWithUi(new ProjectBook(), ViewMode.TASKS, validProject,
                    Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS, nameCompare);

    @Test
    public void getViewMode_returnsViewMode() {
        ViewMode viewMode = projectBookWithUi.getViewMode();
        assertEquals(viewMode, ViewMode.TASKS);
    }

    @Test
    public void getProject_returnsProject() {
        Project project = projectBookWithUi.getProject();
        assertEquals(project, validProject);
    }

    @Test
    public void getPredicate_returnsPredicate() {
        Predicate<TrackedItem> predicate = projectBookWithUi.getPredicate();
        assertEquals(predicate, Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS);
    }

    @Test
    public void getComparator_returnsComparator() {
        Comparator<TrackedItem> comparator = projectBookWithUi.getComparator();
        assertEquals(comparator, nameCompare);
    }
}
