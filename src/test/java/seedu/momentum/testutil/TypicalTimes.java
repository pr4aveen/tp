package seedu.momentum.testutil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import seedu.momentum.commons.core.DateTime;

public class TypicalTimes {
    public static final DateTime DAY =
            new DateTime(LocalDateTime.of(2019, 1, 1, 0, 0));
    public static final DateTime DAY_ADD_HOUR =
            new DateTime(LocalDateTime.of(2019, 1, 1, 0, 0)
                    .plus(1, ChronoUnit.HOURS));
    public static final DateTime DAY_ADD_DAY =
            new DateTime(LocalDateTime.of(2019, 1, 1, 0, 0)
                    .plus(1, ChronoUnit.DAYS));
    public static final DateTime DAY_ADD_WEEK =
            new DateTime(LocalDateTime.of(2019, 1, 1, 0, 0)
                    .plus(1, ChronoUnit.WEEKS));
    public static final DateTime DAY_ADD_MONTH =
            new DateTime(LocalDateTime.of(2019, 1, 1, 0, 0)
                    .plus(1, ChronoUnit.MONTHS));
    public static final DateTime DAY_ADD_YEAR =
            new DateTime(LocalDateTime.of(2019, 1, 1, 0, 0)
                    .plus(1, ChronoUnit.YEARS));
}
