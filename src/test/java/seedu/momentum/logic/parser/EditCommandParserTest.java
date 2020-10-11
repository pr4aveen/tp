package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.commands.CommandTestUtil.DEADLINE_DATE_DESC_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.DEADLINE_DATE_DESC_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.DEADLINE_TIME_DESC_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.DESCRIPTION_DESC_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.INVALID_DEADLINE_DATE_DESC;
import static seedu.momentum.logic.commands.CommandTestUtil.INVALID_DEADLINE_TIME_DESC;
import static seedu.momentum.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.momentum.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.momentum.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.momentum.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DEADLINE_DATE_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DEADLINE_DATE_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DEADLINE_TIME_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_THIRD_PROJECT;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Date;
import seedu.momentum.commons.core.Time;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.EditCommand;
import seedu.momentum.model.project.Name;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.testutil.EditProjectDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_DEADLINE_DATE_DESC,
                Date.MESSAGE_CONSTRAINTS); // invalid date in deadline
        assertParseFailure(parser, "1" + DEADLINE_DATE_DESC_AMY + INVALID_DEADLINE_TIME_DESC,
                Time.MESSAGE_CONSTRAINTS); // invalid time in deadline
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        //        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Project} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        // assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_PHONE_AMY,
        //        Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PROJECT;
        String userInput = targetIndex.getOneBased() + TAG_DESC_HUSBAND + NAME_DESC_AMY + DESCRIPTION_DESC_AMY
                + DEADLINE_DATE_DESC_AMY + DEADLINE_TIME_DESC_AMY + TAG_DESC_FRIEND;

        EditCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder().withName(VALID_NAME_AMY)
                .withDescription(VALID_DESCRIPTION_AMY)
                .withDeadline(VALID_DEADLINE_DATE_AMY, VALID_DEADLINE_TIME_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    //    @Test
    //    public void parse_someFieldsSpecified_success() {
    //        Index targetIndex = INDEX_FIRST_PROJECT;
    //        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB;
    //
    //        EditCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder()
    //                .withPhone(VALID_PHONE_BOB).build();
    //        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
    //
    //        assertParseSuccess(parser, userInput, expectedCommand);
    //    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PROJECT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditCommand.EditProjectDescriptor descriptor =
                new EditProjectDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_AMY;
        descriptor = new EditProjectDescriptorBuilder().withDescription(VALID_DESCRIPTION_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // deadline with date
        userInput = targetIndex.getOneBased() + DEADLINE_DATE_DESC_BOB;
        descriptor = new EditProjectDescriptorBuilder().withDeadline(VALID_DEADLINE_DATE_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // deadline with date and time
        userInput = targetIndex.getOneBased() + DEADLINE_DATE_DESC_BOB + DEADLINE_TIME_DESC_AMY;
        descriptor = new EditProjectDescriptorBuilder()
                .withDeadline(VALID_DEADLINE_DATE_BOB, VALID_DEADLINE_TIME_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditProjectDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PROJECT;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_AMY + TAG_DESC_FRIEND
                + DESCRIPTION_DESC_AMY + TAG_DESC_FRIEND + DESCRIPTION_DESC_BOB + TAG_DESC_HUSBAND;

        EditCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    //    @Test
    //    public void parse_invalidValueFollowedByValidValue_success() {
    //        // no other valid values specified
    //        Index targetIndex = INDEX_FIRST_PROJECT;
    //        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
    //        EditCommand.EditProjectDescriptor descriptor =
    //                new EditProjectDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
    //        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
    //        assertParseSuccess(parser, userInput, expectedCommand);
    //
    //        // other valid values specified
    //        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC
    //                + PHONE_DESC_BOB;
    //        descriptor = new EditProjectDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
    //              .build();
    //        expectedCommand = new EditCommand(targetIndex, descriptor);
    //        assertParseSuccess(parser, userInput, expectedCommand);
    //    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PROJECT;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
