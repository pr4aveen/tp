package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_COMPLETION_STATUS_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_CREATED_DATE_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_CREATED_DATE_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DEADLINE_DATE_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DEADLINE_DATE_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DEADLINE_TIME_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.momentum.testutil.Assert.assertThrows;
import static seedu.momentum.testutil.TypicalProjects.ALICE;
import static seedu.momentum.testutil.TypicalTasks.XAVIER;
import static seedu.momentum.testutil.TypicalTasks.YVETTE;

import org.junit.jupiter.api.Test;

import seedu.momentum.testutil.TaskBuilder;

public class TaskTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Task task = new TaskBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> task.getTags().remove(0));
    }

    @Test
    public void isSameProject() {
        // same object -> returns true
        assertTrue(YVETTE.isSameAs(YVETTE));

        // null -> returns false
        assertFalse(YVETTE.isSameAs(null));

        // different name -> returns false
        Task editedYvette = new TaskBuilder(YVETTE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameAs(editedYvette));

        // different description -> returns false
        editedYvette = new TaskBuilder(YVETTE).withDescription(VALID_DESCRIPTION_BOB).build();
        assertFalse(ALICE.isSameAs(editedYvette));

        // different completion status -> returns false
        editedYvette = new TaskBuilder(YVETTE).withCompletionStatus(VALID_COMPLETION_STATUS_BOB).build();
        assertFalse(ALICE.isSameAs(editedYvette));

        // different deadline -> returns false
        editedYvette = new TaskBuilder(YVETTE).withDeadline(VALID_DEADLINE_DATE_BOB, VALID_CREATED_DATE_BOB).build();
        assertFalse(ALICE.isSameAs(editedYvette));

        Task editedXavier = new TaskBuilder(XAVIER)
                .withDeadline(VALID_DEADLINE_DATE_AMY, VALID_DEADLINE_TIME_AMY, VALID_CREATED_DATE_AMY).build();
        assertFalse(XAVIER.isSameAs(editedXavier));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Task yvetteCopy1 = new TaskBuilder(YVETTE).build();
        Task yvetteCopy2 = new TaskBuilder(YVETTE).build();
        assertTrue(yvetteCopy1.equals(yvetteCopy2));

        // same object -> returns true
        assertTrue(YVETTE.equals(YVETTE));

        // null -> returns false
        assertFalse(YVETTE.equals(null));

        // different type -> returns false
        assertFalse(YVETTE.equals(5));

        // different project -> returns false
        assertFalse(YVETTE.equals(XAVIER));

        // different name -> returns false
        Task editedYvette = new TaskBuilder(YVETTE).withName(VALID_NAME_BOB).build();
        assertFalse(YVETTE.equals(editedYvette));

        // different description -> returns false
        editedYvette = new TaskBuilder(YVETTE).withDescription(VALID_DESCRIPTION_BOB).build();
        assertFalse(ALICE.equals(editedYvette));

        // different completion status -> returns false
        editedYvette = new TaskBuilder(YVETTE).withCompletionStatus(VALID_COMPLETION_STATUS_BOB).build();
        assertFalse(ALICE.equals(editedYvette));

        // different deadline -> returns false
        editedYvette = new TaskBuilder(YVETTE).withDeadline(VALID_DEADLINE_DATE_BOB, VALID_CREATED_DATE_BOB).build();
        assertFalse(ALICE.equals(editedYvette));

        // different tags -> returns false
        editedYvette = new TaskBuilder(YVETTE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedYvette));
    }

    @Test
    void isTask_returnsTrue() {
        assertTrue(new TaskBuilder().build().isTask());
    }
}
