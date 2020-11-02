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
import seedu.momentum.model.project.TrackedItem;

/**
 * Finds and lists all projects in project book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + "[" + FIND_TYPE + "FIND_TYPE ] "
            + "[" + PREFIX_NAME + "NAME_KEYWORD [MORE_NAME_KEYWORDS]... ] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION_KEYWORD [MORE_DESCRIPTION_KEYWORDS]... ] "
            + "[" + PREFIX_COMPLETION_STATUS + "COMPLETION_STATUS_KEYWORD ] "
            + "[" + PREFIX_TAG + "TAG_KEYWORD [MORE_TAG_KEYWORDS]... ]";

    private final Predicate<TrackedItem> predicate;

    public FindCommand(Predicate<TrackedItem> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Predicate<TrackedItem> finalPredicate = model.getCurrentPredicate().and(predicate);
        model.updatePredicate(finalPredicate);
        model.commitToHistory();
        return new CommandResult(
                String.format(Messages.MESSAGE_PROJECTS_LISTED_OVERVIEW, model.getDisplayList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
