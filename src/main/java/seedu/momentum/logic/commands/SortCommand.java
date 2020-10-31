package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_COMPLETION_STATUS;
import static seedu.momentum.logic.parser.CliSyntax.SORT_ORDER;
import static seedu.momentum.logic.parser.CliSyntax.SORT_TYPE;

import seedu.momentum.model.Model;
import seedu.momentum.model.ViewMode;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.SortType;

/**
 * Sorts the projects displayed in a particular order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String INPUT_ASCENDING_ORDER = "asc";
    public static final String INPUT_DESCENDING_ORDER = "dsc";
    public static final String INPUT_ALPHA_TYPE = "alpha";
    public static final String INPUT_DEADLINE_TYPE = "deadline";
    public static final String INPUT_CREATED_TYPE = "created";
    public static final String OUTPUT_ALPHA_TYPE = "alphabetical, ";
    public static final String OUTPUT_DEADLINE_TYPE = "deadline, ";
    public static final String OUTPUT_CREATED_TYPE = "created date, ";
    public static final String OUTPUT_DEFAULT_TYPE = "default alphabetical, ascending";
    public static final String OUTPUT_ASCENDING_ORDER = "ascending";
    public static final String OUTPUT_DESCENDING_ORDER = "descending";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + "[" + SORT_TYPE + "SORT_TYPE ] "
            + "[" + SORT_ORDER + "SORT_ORDER ] "
            + "[" + PREFIX_COMPLETION_STATUS + "]";

    public static final String MESSAGE_INVALID_SORT_TYPE_OR_ORDER = "Sort type can only be one of the following: \n"
            + "Alphabetical: alpha; Deadline: deadline; Created Date: created. \n"
            + "Sort order can only be one of the following: \n"
            + "Ascending: asc; Descending: dsc. \n"
            + "Example: " + COMMAND_WORD + " " + SORT_TYPE + "alpha " + SORT_ORDER + "asc";

    public static final String MESSAGE_SORT_SUCCESS_PROJECTS = "Projects have been sorted in %1$s%2$s order";
    public static final String MESSAGE_SORT_SUCCESS_TASKS = "Tasks have been sorted in %1$s%2$s order";

    private SortType sortType;
    private final boolean isAscending;
    private final boolean isDefault;
    private final boolean isSortedByCompletionStatus;
    private Project parentProject;

    /**
     * Creates a SortCommand to sort the list of projects.
     *
     * @param sortType                   Type of sort applied to projects.
     * @param isAscending                Boolean value to check if order of sort applied to projects is ascending.
     * @param isDefault                  Boolean value to check if SortCommand is default.
     * @param isSortedByCompletionStatus Boolean value to check if SortCommand is sorted by completion status.
     */
    public SortCommand(SortType sortType, boolean isAscending, boolean isDefault, boolean isSortedByCompletionStatus) {
        this.sortType = sortType;
        this.isAscending = isAscending;
        this.isDefault = isDefault;
        this.isSortedByCompletionStatus = isSortedByCompletionStatus;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        String type = "";
        String order = isDefault ? OUTPUT_DEFAULT_TYPE : isAscending
                ? OUTPUT_ASCENDING_ORDER : OUTPUT_DESCENDING_ORDER;

        switch (sortType) {
        case ALPHA:
            type = OUTPUT_ALPHA_TYPE;
            break;
        case DEADLINE:
            type = OUTPUT_DEADLINE_TYPE;
            break;
        case CREATED:
            type = OUTPUT_CREATED_TYPE;
            break;
        default:
            break;
        }

        if (isDefault) {
            sortType = SortType.ALPHA;
        }

        if (model.getViewMode() == ViewMode.PROJECTS) {
            model.orderFilteredProjectList(sortType, isAscending, isSortedByCompletionStatus);
            model.commitToHistory();
            return new CommandResult(String.format(MESSAGE_SORT_SUCCESS_PROJECTS, type, order));
        } else {
            Project projectBeforeSort = model.getCurrentProject();
            Project projectAfterSort = model.getCurrentProject()
                    .orderTaskList(sortType, isAscending, isSortedByCompletionStatus);
            model.setTrackedItem(projectBeforeSort, projectAfterSort);
            model.viewTasks(projectAfterSort);
            model.commitToHistory();
            return new CommandResult(String.format(MESSAGE_SORT_SUCCESS_TASKS, type, order));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && sortType.equals(((SortCommand) other).sortType) // field check
                && isAscending == ((SortCommand) other).isAscending // field check
                && isDefault == ((SortCommand) other).isDefault // field check
                && isSortedByCompletionStatus == ((SortCommand) other).isSortedByCompletionStatus); // field check
    }

}
