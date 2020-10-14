package seedu.momentum.testutil;

import seedu.momentum.logic.commands.SortCommand;
import seedu.momentum.model.project.SortType;

public class SortCommandUtil {

    public static final SortCommand DEFAULT_SORT_COMMAND =
            new SortCommand(SortType.NULL, true, true);

    public static final SortCommand ALPHA_ASCENDING_COMMAND =
            new SortCommand(SortType.ALPHA, true, false);

    public static final SortCommand ALPHA_DESCENDING_COMMAND =
            new SortCommand(SortType.ALPHA, false, false);

    public static final SortCommand DEADLINE_ASCENDING_COMMAND =
            new SortCommand(SortType.DEADLINE, true, false);

    public static final SortCommand DEADLINE_DESCENDING_COMMAND =
            new SortCommand(SortType.DEADLINE, false, false);

    public static final SortCommand CREATED_DATE_ASCENDING_COMMAND =
            new SortCommand(SortType.CREATED, true, false);

    public static final SortCommand CREATED_DATE_DESCENDING_COMMAND =
            new SortCommand(SortType.CREATED, false, false);

    public static final SortCommand NULL_SORT_TYPE_ASCENDING_NON_DEFAULT_COMMAND =
            new SortCommand(SortType.NULL, true, false);

    public static final SortCommand NULL_SORT_TYPE_DESCENDING_NON_DEFAULT_COMMAND =
            new SortCommand(SortType.NULL, false, false);
}
