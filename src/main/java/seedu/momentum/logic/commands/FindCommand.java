//@@author pr4aveen

package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.logic.parser.CliSyntax.FIND_TYPE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_COMPLETION_STATUS;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Predicate;

import seedu.momentum.commons.core.Messages;
import seedu.momentum.model.Model;
import seedu.momentum.model.ViewMode;
import seedu.momentum.model.project.TrackedItem;

/**
 * Finds and lists all items in Momentum that match a defined criteria.
 * This is done by applying a predicate to filter the list of items.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + "[" + FIND_TYPE + "FIND_TYPE ] "
            + "[" + PREFIX_NAME + "NAME_KEYWORD [MORE_NAME_KEYWORDS]... ] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION_KEYWORD [MORE_DESCRIPTION_KEYWORDS]... ] "
            + "[" + PREFIX_COMPLETION_STATUS + "COMPLETION_STATUS_KEYWORD ] "
            + "[" + PREFIX_TAG + "TAG_KEYWORD [MORE_TAG_KEYWORDS]... ]";

    // Defines the criteria to filter items by
    private final Predicate<TrackedItem> predicate;

    /**
     * Creates a FindCommand that uses the provided predicate to filter out items.
     *
     * @param predicate A predicate that specifies the criteria used to filter the items.
     */
    public FindCommand(Predicate<TrackedItem> predicate) {
        this.predicate = predicate;
    }

    /**
     * Updates the provided model to show only the items that match the criteria defined in the predicate.
     *
     * @param model {@code Model} containing the model to update.
     * @return Feedback message of find result, for display.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updatePredicate(predicate);
        model.commitToHistory();
        String resultMessage = model.getViewMode() == ViewMode.PROJECTS
                ? Messages.MESSAGE_PROJECTS_LISTED_OVERVIEW
                : Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
        return new CommandResult(String.format(resultMessage, model.getDisplayList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
