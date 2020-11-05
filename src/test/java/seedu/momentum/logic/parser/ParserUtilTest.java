package seedu.momentum.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.momentum.testutil.Assert.assertThrows;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.DateTimeWrapper;
import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.commons.core.StatisticTimeframe;
import seedu.momentum.commons.core.Theme;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.project.Deadline;
import seedu.momentum.model.project.Description;
import seedu.momentum.model.project.Name;
import seedu.momentum.model.reminder.Reminder;
import seedu.momentum.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DATE = "2021-42-12";
    private static final String INVALID_DEADLINE_DATE = "1000-09-12";
    private static final String INVALID_REMINDER_EARLY = "2021-10-23T13:21:25";
    private static final String INVALID_REMINDER = "3000-12-1202:31:23";
    private static final String INVALID_TIME = "42:12:12";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_THEME = "transparent";
    private static final String INVALID_STATISTIC_TIMEFRAME = "yearly";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_DESCRIPTION = "Loves to eat";
    private static final String VALID_DATE = "2021-12-12";
    private static final String VALID_TIME = "12:12:12";
    private static final String VALID_REMINDER = "2025-12-23T13:21:25";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_THEME = "dark";
    private static final String VALID_STATISTIC_TIMEFRAME = "daily";

    private static final String WHITESPACE = " \t\r\n";

    private static final DateWrapper VALID_CREATED_DATE_WRAPPER = new DateWrapper(VALID_DATE);

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseDeadline_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDeadline(
                Optional.of(INVALID_DATE), Optional.empty(), VALID_CREATED_DATE_WRAPPER));
        assertThrows(ParseException.class, () -> ParserUtil.parseDeadline(
                Optional.of(INVALID_DATE), Optional.of(INVALID_TIME), VALID_CREATED_DATE_WRAPPER));
        // deadline is before created date
        assertThrows(ParseException.class, () -> ParserUtil.parseDeadline(
                Optional.of(INVALID_DEADLINE_DATE), Optional.empty(), VALID_CREATED_DATE_WRAPPER));
        assertThrows(ParseException.class, () -> ParserUtil.parseDeadline(
                Optional.of(INVALID_DEADLINE_DATE), Optional.of(VALID_TIME), VALID_CREATED_DATE_WRAPPER));
    }

    @Test
    public void parseDeadline_validValueWithoutWhitespace_returnsDeadline() throws Exception {
        Deadline expectedDeadline = new Deadline(VALID_DATE, VALID_CREATED_DATE_WRAPPER);
        assertEquals(expectedDeadline,
                ParserUtil.parseDeadline(Optional.of(VALID_DATE), Optional.empty(), VALID_CREATED_DATE_WRAPPER));

        expectedDeadline = new Deadline(VALID_DATE, VALID_TIME, VALID_CREATED_DATE_WRAPPER);
        assertEquals(expectedDeadline,
                ParserUtil.parseDeadline(Optional.of(VALID_DATE), Optional.of(VALID_TIME), VALID_CREATED_DATE_WRAPPER));
    }

    @Test
    public void parseDeadline_validValueWithWhitespace_returnsTrimmedDeadline() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        String timeWithWhitespace = WHITESPACE + VALID_TIME + WHITESPACE;
        Deadline expectedDeadline = new Deadline(VALID_DATE, VALID_TIME, VALID_CREATED_DATE_WRAPPER);
        assertEquals(expectedDeadline,
                ParserUtil.parseDeadline(Optional.of(dateWithWhitespace),
                        Optional.of(timeWithWhitespace),
                        VALID_CREATED_DATE_WRAPPER));
    }

    @Test
    public void parseReminder_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseReminder(
                Optional.of(VALID_DATE)));
        assertThrows(ParseException.class, () -> ParserUtil.parseReminder(
                Optional.of(VALID_TIME)));
        assertThrows(ParseException.class, () -> ParserUtil.parseReminder(
                Optional.of(INVALID_REMINDER)));
        Clock.initFixed(new DateTimeWrapper(INVALID_REMINDER_EARLY).plus(1, ChronoUnit.DAYS));
        assertThrows(ParseException.class, () -> ParserUtil.parseReminder(
                Optional.of(INVALID_REMINDER_EARLY)));
    }

    @Test
    public void parseReminder_validValueWithoutWhitespace_returnsReminder() throws Exception {
        Reminder expectedReminder = new Reminder(VALID_REMINDER);
        assertEquals(expectedReminder,
                ParserUtil.parseReminder(Optional.of(VALID_REMINDER)));

        expectedReminder = new Reminder();
        assertEquals(expectedReminder, ParserUtil.parseReminder(Optional.empty()));
    }

    @Test
    public void parseReminder_validValueWithWhitespace_returnsTrimmedReminder() throws Exception {
        String reminderWithWhiteSpace = WHITESPACE + VALID_REMINDER + WHITESPACE;
        Reminder expectedReminder = new Reminder(VALID_REMINDER);
        assertEquals(expectedReminder, ParserUtil.parseReminder(Optional.of(reminderWithWhiteSpace)));
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() {
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(VALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedDescription() {
        String descriptionWithWhitespace = WHITESPACE + VALID_DESCRIPTION + WHITESPACE;
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(descriptionWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseTheme_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTheme(null));
    }

    @Test
    public void parseTheme_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTheme(INVALID_THEME));
    }

    @Test
    public void parseTheme_validValueWithoutWhitespace_returnsTheme() throws Exception {
        Theme expectedTheme = new Theme(VALID_THEME);
        assertEquals(expectedTheme, ParserUtil.parseTheme(VALID_THEME));
    }

    @Test
    public void parseTheme_validValueWithWhitespace_returnsTrimmedTheme() throws Exception {
        String themeWithWhitespace = WHITESPACE + VALID_THEME + WHITESPACE;
        Theme expectedTheme = new Theme(VALID_THEME);
        assertEquals(expectedTheme, ParserUtil.parseTheme(themeWithWhitespace));
    }

    @Test
    public void parseStatisticTimeframe_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStatisticTimeframe(null));
    }

    @Test
    public void parseStatisticTimeframe_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStatisticTimeframe(INVALID_STATISTIC_TIMEFRAME));
    }

    @Test
    public void parseStatisticTimeframe_validValueWithoutWhitespace_returnsStatisticTimeframe() throws Exception {
        StatisticTimeframe expectedStatisticTimeframe = new StatisticTimeframe(VALID_STATISTIC_TIMEFRAME);
        assertEquals(expectedStatisticTimeframe, ParserUtil.parseStatisticTimeframe(VALID_STATISTIC_TIMEFRAME));
    }

    @Test
    public void parseStatisticTimeframe_validValueWithWhitespace_returnsTrimmedStatisticTimeframe() throws Exception {
        String statisticTimeframeWithWhitespace = WHITESPACE + VALID_STATISTIC_TIMEFRAME + WHITESPACE;
        StatisticTimeframe expectedStatisticTimeframe = new StatisticTimeframe(VALID_STATISTIC_TIMEFRAME);
        assertEquals(expectedStatisticTimeframe, ParserUtil.parseStatisticTimeframe(statisticTimeframeWithWhitespace));
    }
}
