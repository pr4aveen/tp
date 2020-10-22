package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import static seedu.momentum.testutil.TypicalProjects.BOB;

import org.junit.jupiter.api.Test;

import seedu.momentum.testutil.ProjectBuilder;

public class ProjectTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Project project = new ProjectBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> project.getTags().remove(0));
    }

    @Test
    public void isSameProject() {
        // same object -> returns true
        assertTrue(ALICE.isSameTrackedItem(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameTrackedItem(null));

        // different description -> returns false
        Project editedAlice = new ProjectBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB).build();
        assertFalse(ALICE.isSameTrackedItem(editedAlice));

        // different name -> returns false
        editedAlice = new ProjectBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameTrackedItem(editedAlice));

        // different created date -> returns false
        editedAlice = new ProjectBuilder(ALICE).withCreatedDate(VALID_CREATED_DATE_BOB).build();
        assertFalse(ALICE.isSameTrackedItem(editedAlice));

        // different deadline -> returns false
        editedAlice = new ProjectBuilder(ALICE).withDeadline(VALID_DEADLINE_DATE_BOB, VALID_CREATED_DATE_BOB).build();
        assertFalse(ALICE.isSameTrackedItem(editedAlice));
        Project editedBob = new ProjectBuilder(BOB)
                .withDeadline(VALID_DEADLINE_DATE_AMY, VALID_DEADLINE_TIME_AMY, VALID_CREATED_DATE_AMY).build();
        assertFalse(BOB.isSameTrackedItem(editedBob));

        // same name, same phone, different attributes -> returns true
        //        editedAlice = new ProjectBuilder(ALICE).withEmail(VALID_EMAIL_BOB)
        //                .withTags(VALID_TAG_HUSBAND).build();
        //        assertTrue(ALICE.isSameProject(editedAlice));

        // same name, same email, different attributes -> returns true
        //        editedAlice = new ProjectBuilder(ALICE).withPhone(VALID_PHONE_BOB)
        //                .withTags(VALID_TAG_HUSBAND).build();
        //        assertTrue(ALICE.isSameProject(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        //        editedAlice = new ProjectBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        //        assertTrue(ALICE.isSameProject(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Project aliceCopy1 = new ProjectBuilder(ALICE).build();
        Project aliceCopy2 = new ProjectBuilder(ALICE).build();
        assertTrue(aliceCopy1.equals(aliceCopy2));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different project -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Project editedAlice = new ProjectBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different description -> returns false
        editedAlice = new ProjectBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different created date -> returns false
        editedAlice = new ProjectBuilder(ALICE).withCreatedDate(VALID_CREATED_DATE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different deadline -> returns false
        editedAlice = new ProjectBuilder(ALICE).withDeadline(VALID_DEADLINE_DATE_BOB, VALID_CREATED_DATE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ProjectBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
