package seedu.address.model.work_duration;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DurationUtil {
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
}
