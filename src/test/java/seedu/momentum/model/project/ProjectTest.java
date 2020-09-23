package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        assertTrue(ALICE.isSameProject(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameProject(null));

        // different phone and email -> returns false
        //        new ProjectBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        //        assertFalse(ALICE.isSameProject(editedAlice));

        // different name -> returns false
        //        Project editedAlice = editedAlice = new ProjectBuilder(ALICE).withName(VALID_NAME_BOB).build();
        //        assertFalse(ALICE.isSameProject(editedAlice));

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
        Project aliceCopy = new ProjectBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

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

        // different tags -> returns false
        editedAlice = new ProjectBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
