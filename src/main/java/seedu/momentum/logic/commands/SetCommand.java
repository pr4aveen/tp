package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.logic.parser.CliSyntax.SET_THEME;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.Theme;
import seedu.momentum.commons.util.CollectionUtil;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;

/**
 * Adjust various settings in the application.
 */
public class SetCommand extends Command {

    public static final String COMMAND_WORD = "set";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adjust various settings in the application.\n"
        + "Parameters: "
        + "[" + SET_THEME + "THEME]";

    public static final String MESSAGE_EDIT_PROJECT_SUCCESS =
        "Settings updated. A restart is needed for the new settings to take effect.";
    public static final String MESSAGE_NOT_CHANGED = "At least one setting must be changed.";

    private final SettingsToChange settingsToChange;

    /**
     * Creates a SetCommand that changes application settings.
     *
     * @param settingsToChange settings to change.
     */
    public SetCommand(SettingsToChange settingsToChange) {
        requireNonNull(settingsToChange);
        this.settingsToChange = new SettingsToChange(settingsToChange);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, ParseException {
        requireNonNull(model);

        if (settingsToChange.getTheme().isPresent()) {
            model.setGuiThemeSettings(new GuiThemeSettings(settingsToChange.getTheme().get()));
        }

        return new CommandResult(MESSAGE_EDIT_PROJECT_SUCCESS);
    }

    public static class SettingsToChange {
        private Theme theme;
        private ChronoUnit statTimeframe;

        public SettingsToChange() {
        }

        /**
         * Copy constructor.
         */
        public SettingsToChange(SettingsToChange toCopy) {
            setTheme(toCopy.theme);
        }

        /**
         * Returns true if at least one setting is changed.
         */
        public boolean isAnySettingChanged() {
            return CollectionUtil.isAnyNonNull(theme, statTimeframe);
        }

        public void setTheme(Theme theme) {
            this.theme = theme;
        }

        public Optional<Theme> getTheme() {
            return Optional.ofNullable(theme);
        }

        public void setStatTimeframe(ChronoUnit statTimeframe) {
            this.statTimeframe = statTimeframe;
        }

        public Optional<ChronoUnit> getStatTimeframe() {
            return Optional.ofNullable(statTimeframe);
        }
    }
}
