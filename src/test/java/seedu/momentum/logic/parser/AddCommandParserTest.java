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
import static seedu.momentum.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.momentum.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.momentum.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.momentum.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DEADLINE_DATE_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.momentum.testutil.TypicalProjects.AMY;
import static seedu.momentum.testutil.TypicalProjects.BOB;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Date;
import seedu.momentum.logic.commands.AddCommand;
import seedu.momentum.model.project.Name;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.testutil.ProjectBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Project expectedProject = new ProjectBuilder(BOB).withTags(VALID_TAG_FRIEND)
                .withCurrentCreatedDate().build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + DESCRIPTION_DESC_BOB + DEADLINE_DATE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedProject));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + DESCRIPTION_DESC_BOB + DEADLINE_DATE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedProject));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, NAME_DESC_BOB + DESCRIPTION_DESC_AMY + DESCRIPTION_DESC_BOB + DEADLINE_DATE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedProject));

        // multiple tags - all accepted
        Project expectedProjectMultipleTags = new ProjectBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .withCurrentCreatedDate().build();
        assertParseSuccess(parser, NAME_DESC_BOB + DESCRIPTION_DESC_BOB + DEADLINE_DATE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedProjectMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no description
        Project expectedProject = new ProjectBuilder(AMY).withEmptyDescription().withCurrentCreatedDate().build();
        assertParseSuccess(parser, NAME_DESC_AMY + DEADLINE_DATE_DESC_AMY + DEADLINE_TIME_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedProject));

        // no deadline
        expectedProject = new ProjectBuilder(AMY).withEmptyDeadline().withCurrentCreatedDate().build();
        assertParseSuccess(parser, NAME_DESC_AMY + DESCRIPTION_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedProject));

        // no time in deadline
        expectedProject = new ProjectBuilder(AMY).withDeadline(VALID_DEADLINE_DATE_AMY)
                .withCurrentCreatedDate().build();
        assertParseSuccess(parser, NAME_DESC_AMY + DESCRIPTION_DESC_AMY + DEADLINE_DATE_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedProject));

        // zero tags
        expectedProject = new ProjectBuilder(AMY).withTags().withCurrentCreatedDate().build();
        assertParseSuccess(parser, NAME_DESC_AMY + DESCRIPTION_DESC_AMY + DEADLINE_DATE_DESC_AMY
                + DEADLINE_TIME_DESC_AMY, new AddCommand(expectedProject));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + VALID_DESCRIPTION_BOB,
                expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + DESCRIPTION_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid deadline with invalid date
        assertParseFailure(parser, NAME_DESC_BOB + DESCRIPTION_DESC_BOB + INVALID_DEADLINE_DATE_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Date.MESSAGE_CONSTRAINTS);

        // invalid deadline with invalid time
        assertParseFailure(parser, NAME_DESC_BOB + DESCRIPTION_DESC_BOB
                + INVALID_DEADLINE_DATE_DESC + INVALID_DEADLINE_TIME_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Date.MESSAGE_CONSTRAINTS);

        // invalid deadline with invalid date and time
        assertParseFailure(parser, NAME_DESC_BOB + DESCRIPTION_DESC_BOB + INVALID_DEADLINE_DATE_DESC
                + INVALID_DEADLINE_TIME_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Date.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + DESCRIPTION_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + DESCRIPTION_DESC_BOB + INVALID_TAG_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + DESCRIPTION_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
