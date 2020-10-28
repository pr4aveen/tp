package seedu.momentum.testutil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import seedu.momentum.commons.core.DateTimeWrapper;

public class TypicalTimes {
    public static final DateTimeWrapper DAY =
            new DateTimeWrapper(LocalDateTime.of(2019, 1, 1, 0, 0));
    public static final DateTimeWrapper DAY_ADD_HOUR =
            new DateTimeWrapper(LocalDateTime.of(2019, 1, 1, 0, 0)
                    .plus(1, ChronoUnit.HOURS));
    public static final DateTimeWrapper DAY_ADD_DAY =
            new DateTimeWrapper(LocalDateTime.of(2019, 1, 1, 0, 0)
                    .plus(1, ChronoUnit.DAYS));
    public static final DateTimeWrapper DAY_ADD_WEEK =
            new DateTimeWrapper(LocalDateTime.of(2019, 1, 1, 0, 0)
                    .plus(1, ChronoUnit.WEEKS));
    public static final DateTimeWrapper DAY_ADD_MONTH =
            new DateTimeWrapper(LocalDateTime.of(2019, 1, 1, 0, 0)
                    .plus(1, ChronoUnit.MONTHS));
    public static final DateTimeWrapper DAY_ADD_YEAR =
            new DateTimeWrapper(LocalDateTime.of(2019, 1, 1, 0, 0)
                    .plus(1, ChronoUnit.YEARS));
}
