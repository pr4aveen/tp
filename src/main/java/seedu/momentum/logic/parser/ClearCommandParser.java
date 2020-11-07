//@@author
package seedu.momentum.logic.parser;

import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import seedu.momentum.logic.commands.ClearCommand;
import seedu.momentum.logic.commands.ClearProjectCommand;
import seedu.momentum.logic.commands.ClearTaskCommand;
import seedu.momentum.model.Model;
import seedu.momentum.model.ViewMode;

/**
 * Parses input arguments and creates an appropriate ClearCommand object.
 */
public class ClearCommandParser implements Parser<ClearCommand> {
    /**
     * Returns the appropriate clear command in the context of the provided model.
     *
     * @param args No additional arguments are required for this command.
     * @param model The current model.
     */
    public ClearCommand parse(String args, Model model) {
        requireAllNonNull(args, model);
        if (model.getViewMode() == ViewMode.PROJECTS) {
            return new ClearProjectCommand();
        }
        return new ClearTaskCommand();
    }
}
