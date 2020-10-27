package seedu.momentum.testutil;

import seedu.momentum.model.timer.TimerWrapper;

public class TypicalTimers {
    public static final TimerWrapper DAY =
            new TimerWrapper(TypicalTimes.DAY, TypicalTimes.DAY_ADD_DAY, false);
    public static final TimerWrapper HOUR =
            new TimerWrapper(TypicalTimes.DAY, TypicalTimes.DAY_ADD_HOUR, false);
    public static final TimerWrapper WEEK =
            new TimerWrapper(TypicalTimes.DAY, TypicalTimes.DAY_ADD_WEEK, false);
}
