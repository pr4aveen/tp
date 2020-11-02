package seedu.momentum.logic.commands;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import seedu.momentum.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    public static final String SHOWING_HELP_IN_BROWSER = "Opened user guide in default browser.";

    @Override
    public CommandResult execute(Model model) {
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI userGuideUrl = new URI("https://ay2021s1-cs2103t-t10-1.github.io/tp/UserGuide.html");
            desktop.browse(userGuideUrl);
            return new CommandResult(SHOWING_HELP_IN_BROWSER, false, false);
        } catch (URISyntaxException | IOException e) {
            return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        }
    }
}
