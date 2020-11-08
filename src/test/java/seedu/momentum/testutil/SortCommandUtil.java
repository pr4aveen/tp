package seedu.momentum.testutil;

import seedu.momentum.logic.commands.SortCommand;
import seedu.momentum.model.project.SortType;

/**
 * Contains helper {@SortCommand} for testing {@code SortCommand} methods.
 */
public class SortCommandUtil {

    public static final SortCommand DEFAULT_SORT_COMMAND =
            new SortCommand(SortType.NULL, true, true, false);

    public static final SortCommand ALPHA_ASCENDING_COMMAND_TOGGLE_COMPLETION_STATUS =
            new SortCommand(SortType.ALPHA, true, false, true);

    public static final SortCommand ALPHA_ASCENDING_COMMAND =
            new SortCommand(SortType.ALPHA, true, false, false);

    public static final SortCommand ALPHA_DESCENDING_COMMAND_TOGGLE_COMPLETION_STATUS =
            new SortCommand(SortType.ALPHA, false, false, true);

    public static final SortCommand ALPHA_DESCENDING_COMMAND =
            new SortCommand(SortType.ALPHA, false, false, false);

    public static final SortCommand DEADLINE_ASCENDING_COMMAND_TOGGLE_COMPLETION_STATUS =
            new SortCommand(SortType.DEADLINE, true, false, true);

    public static final SortCommand DEADLINE_ASCENDING_COMMAND =
            new SortCommand(SortType.DEADLINE, true, false, false);

    public static final SortCommand DEADLINE_DESCENDING_COMMAND_TOGGLE_COMPLETION_STATUS =
            new SortCommand(SortType.DEADLINE, false, false, true);

    public static final SortCommand DEADLINE_DESCENDING_COMMAND =
            new SortCommand(SortType.DEADLINE, false, false, false);

    public static final SortCommand CREATED_DATE_ASCENDING_COMMAND_TOGGLE_COMPLETION_STATUS =
            new SortCommand(SortType.CREATED, true, false, true);

    public static final SortCommand CREATED_DATE_ASCENDING_COMMAND =
            new SortCommand(SortType.CREATED, true, false, false);

    public static final SortCommand CREATED_DATE_DESCENDING_COMMAND_TOGGLE_COMPLETION_STATUS =
            new SortCommand(SortType.CREATED, false, false, true);

    public static final SortCommand CREATED_DATE_DESCENDING_COMMAND =
            new SortCommand(SortType.CREATED, false, false, false);

    public static final SortCommand NULL_SORT_TYPE_ASCENDING_NON_DEFAULT_COMMAND_TOGGLE_COMPLETION_STATUS =
            new SortCommand(SortType.NULL, true, false, true);

    public static final SortCommand NULL_SORT_TYPE_ASCENDING_NON_DEFAULT_COMMAND =
            new SortCommand(SortType.NULL, true, false, false);

    public static final SortCommand NULL_SORT_TYPE_DESCENDING_NON_DEFAULT_COMMAND_TOGGLE_COMPLETION_STATUS =
            new SortCommand(SortType.NULL, false, false, true);

    public static final SortCommand NULL_SORT_TYPE_DESCENDING_NON_DEFAULT_COMMAND =
            new SortCommand(SortType.NULL, false, false, false);
}
