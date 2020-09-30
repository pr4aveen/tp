package seedu.momentum.testutil;

import seedu.momentum.model.timer.Timer;

public class TypicalTimers {
    public static final Timer DAY =
            new Timer(TypicalTimes.DAY, TypicalTimes.DAY_ADD_DAY, false);
    public static final Timer HOUR =
            new Timer(TypicalTimes.DAY, TypicalTimes.DAY_ADD_HOUR, false);
    public static final Timer WEEK =
            new Timer(TypicalTimes.DAY, TypicalTimes.DAY_ADD_WEEK, false);
}
