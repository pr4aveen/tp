package seedu.momentum.commons.util;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Contains utility methods used for parsing and formatting LocalDateTime objects.
 */
public class DateTimeUtil {
    public static final DateTimeFormatter FORMAT_DATE_TIME_LONG =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
    public static final DateTimeFormatter FORMAT_DATE_TIME_MEDIUM =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    public static final DateTimeFormatter FORMAT_DATE_TIME_SHORT =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    public static final DateTimeFormatter FORMAT_DATA = DateTimeFormatter.ISO_DATE_TIME;

}
