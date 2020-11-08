//@@author boundtotheearth
package seedu.momentum.testutil;

import seedu.momentum.model.timer.WorkDuration;

public class TypicalWorkDuration {
    public static final WorkDuration DURATION_ONE_DAY =
            new WorkDuration(TypicalTimes.DAY, TypicalTimes.DAY_ADD_DAY);
    public static final WorkDuration DURATION_ONE_HOUR =
            new WorkDuration(TypicalTimes.DAY, TypicalTimes.DAY_ADD_HOUR);
    public static final WorkDuration DURATION_ONE_WEEK =
            new WorkDuration(TypicalTimes.DAY, TypicalTimes.DAY_ADD_WEEK);
    public static final WorkDuration DURATION_ONE_MONTH =
            new WorkDuration(TypicalTimes.DAY, TypicalTimes.DAY_ADD_MONTH);
    public static final WorkDuration DURATION_ONE_YEAR =
            new WorkDuration(TypicalTimes.DAY, TypicalTimes.DAY_ADD_YEAR);

}
