package seedu.momentum.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    // Prefix definitions
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_COMPLETION_STATUS = new Prefix("c/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_DEADLINE_DATE = new Prefix("dd/");
    public static final Prefix PREFIX_DEADLINE_TIME = new Prefix("dt/");

    // Search parameters
    public static final Prefix FIND_TYPE = new Prefix("match/");

    // Sort parameters
    public static final Prefix SORT_TYPE = new Prefix("type/");
    public static final Prefix SORT_ORDER = new Prefix("order/");
}
