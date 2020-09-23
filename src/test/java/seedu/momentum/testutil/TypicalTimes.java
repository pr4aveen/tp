package seedu.momentum.testutil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import seedu.momentum.model.timer.Time;

public class TypicalTimes {
    public static final Time DAY =
            new Time(LocalDateTime.of(2020, 1, 1, 0, 0));
    public static final Time DAY_ADD_DAY =
            new Time(LocalDateTime.of(2020, 1, 1, 0, 0)
                    .plus(1, ChronoUnit.DAYS));
    public static final Time DAY_ADD_WEEK =
            new Time(LocalDateTime.of(2020, 1, 1, 0, 0)
                    .plus(1, ChronoUnit.WEEKS));
    public static final Time DAY_ADD_MONTH =
            new Time(LocalDateTime.of(2020, 1, 1, 0, 0)
                    .plus(1, ChronoUnit.MONTHS));
    public static final Time DAY_ADD_YEAR =
            new Time(LocalDateTime.of(2020, 1, 1, 0, 0)
                    .plus(1, ChronoUnit.YEARS));
}
