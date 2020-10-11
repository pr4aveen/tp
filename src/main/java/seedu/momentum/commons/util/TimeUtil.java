package seedu.momentum.commons.util;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Contains utility methods used for parsing and formatting LocalTime objects.
 */
public class TimeUtil {
    public static final DateTimeFormatter FORMAT_TIME_LONG =
            DateTimeFormatter.ofLocalizedTime(FormatStyle.LONG);
    public static final DateTimeFormatter FORMAT_TIME_MEDIUM =
            DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
    public static final DateTimeFormatter FORMAT_TIME_SHORT =
            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    public static final DateTimeFormatter FORMAT_DATA = DateTimeFormatter.ISO_TIME;

}
