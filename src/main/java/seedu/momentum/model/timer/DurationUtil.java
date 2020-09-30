package seedu.momentum.model.timer;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Contains utility methods used for parsing and formatting LocalDateTime objects.
 */
public class DurationUtil {
    public static final DateTimeFormatter FORMAT_DATE_TIME = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    public static final DateTimeFormatter FORMAT_DATA = DateTimeFormatter.ISO_DATE_TIME;

}
