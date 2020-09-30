package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.momentum.testutil.EditProjectDescriptorBuilder;

public class EditProjectDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditProjectDescriptor descriptorWithSameValues = new EditCommand.EditProjectDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditCommand.EditProjectDescriptor editedAmy =
                new EditProjectDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different description -> returns false
        editedAmy = new EditProjectDescriptorBuilder(DESC_AMY).withDescription(VALID_DESCRIPTION_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditProjectDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
