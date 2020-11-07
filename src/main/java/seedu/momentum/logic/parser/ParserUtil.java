//@@author

package seedu.momentum.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.momentum.commons.core.DateTimeWrapper;
import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.commons.core.StatisticTimeframe;
import seedu.momentum.commons.core.Theme;
import seedu.momentum.commons.core.TimeWrapper;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.commons.util.StringUtil;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.project.Deadline;
import seedu.momentum.model.project.Description;
import seedu.momentum.model.project.Name;
import seedu.momentum.model.reminder.Reminder;
import seedu.momentum.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException If the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    //@@author kkangs0226

    /**
     * Parses a {@code String description} into an {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param description The description of the TrackedItem.
     * @return The parsed description.
     */
    public static Description parseDescription(String description) {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        return new Description(trimmedDescription);
    }

    //@@author claracheong4

    /**
     * Parses {@code Optional<String> date} and {@code Optional<String> time}into a {@code Deadline}.
     * Leading and trailing whitespaces will be trimmed.
     * If either date is not available, an empty Deadline is returned.
     *
     * @param date               The date of the deadline.
     * @param time               The time of the deadline.
     * @param createdDateWrapper The date when the item was created.
     * @throws ParseException If the date or time is invalid, or if the provided date and time is before the created
     *                        date.
     */
    public static Deadline parseDeadline(Optional<String> date, Optional<String> time, DateWrapper createdDateWrapper)
            throws ParseException {
        if (date.isEmpty() || date.get().isBlank()) {
            return new Deadline();
        }

        String trimmedDate = date.get().trim();
        if (!DateWrapper.isValid(trimmedDate)) {
            throw new ParseException(DateWrapper.MESSAGE_CONSTRAINTS);
        }

        if (Deadline.isBeforeCreatedDate(trimmedDate, createdDateWrapper)) {
            throw new ParseException(Deadline.CREATED_DATE_MESSAGE_CONSTRAINT);
        }

        if (time.isEmpty() || time.get().isBlank()) {
            return new Deadline(trimmedDate, createdDateWrapper);
        }

        String trimmedTime = time.get().trim();
        if (!TimeWrapper.isValid(trimmedTime)) {
            throw new ParseException(TimeWrapper.MESSAGE_CONSTRAINTS);
        }

        return new Deadline(trimmedDate, trimmedTime, createdDateWrapper);
    }

    /**
     * Parses {@code Optional<String> dateTime} into a {@code Reminder}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param dateTime The date and time of the reminder.
     * @throws ParseException If dateTime is invalid.
     */
    public static Reminder parseReminder(Optional<String> dateTime)
            throws ParseException {
        if (dateTime.isEmpty() || dateTime.get().isBlank()) {
            return new Reminder();
        }

        String trimmedDateTime = dateTime.get().trim();
        if (!DateTimeWrapper.isValid(trimmedDateTime)) {
            throw new ParseException(DateTimeWrapper.MESSAGE_CONSTRAINTS);
        }

        if (!Reminder.isValid(trimmedDateTime)) {
            throw new ParseException(Reminder.REMINDER_MESSAGE_CONSTRAINTS);
        }

        return new Reminder(trimmedDateTime);
    }
    //@@author

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     *
     * @throws ParseException If any of the tags in the set are invalid.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    //@@author khoodehui

    /**
     * Parses a {@code String theme} into a {@code Theme}.
     *
     * @param theme The theme of the application.
     * @return The parsed theme.
     * @throws ParseException If the given {@code theme} is invalid.
     */
    public static Theme parseTheme(String theme) throws ParseException {
        requireNonNull(theme);
        String trimmedTheme = theme.trim();
        if (!Theme.isValid(trimmedTheme)) {
            throw new ParseException(Theme.MESSAGE_CONSTRAINTS);
        }
        return new Theme(trimmedTheme);
    }

    /**
     * Parses a {@code String statisticTimeframe} into a {@code StatisticTimeframe}.
     *
     * @param statisticTimeframe The timeframe of the statistics displayed.
     * @return The parsed statistic timeframe.
     * @throws ParseException If the give {@code statisticTimeframe} is invalid.
     */
    public static StatisticTimeframe parseStatisticTimeframe(String statisticTimeframe) throws ParseException {
        requireNonNull(statisticTimeframe);
        String trimmedTimeframe = statisticTimeframe.trim();
        if (!StatisticTimeframe.isValid(trimmedTimeframe)) {
            throw new ParseException(StatisticTimeframe.MESSAGE_CONSTRAINTS);
        }
        return new StatisticTimeframe(trimmedTimeframe);
    }
}
