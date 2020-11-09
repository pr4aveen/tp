//@@author claracheong4

package seedu.momentum.commons.util;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Contains utility methods used for parsing and formatting LocalDate objects.
 */
public class DateUtil {
    public static final DateTimeFormatter FORMAT_DATE_MEDIUM =
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
    public static final DateTimeFormatter FORMAT_DATA = DateTimeFormatter.ISO_DATE;
}
