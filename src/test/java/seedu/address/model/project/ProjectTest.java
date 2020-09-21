package seedu.address.model.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProjects.ALICE;
import static seedu.address.testutil.TypicalProjects.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ProjectBuilder;

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
        Project editedAlice = new ProjectBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameProject(editedAlice));

        // different name -> returns false
        editedAlice = new ProjectBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameProject(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new ProjectBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameProject(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new ProjectBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameProject(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new ProjectBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameProject(editedAlice));
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

        // different phone -> returns false
        editedAlice = new ProjectBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ProjectBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ProjectBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ProjectBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
