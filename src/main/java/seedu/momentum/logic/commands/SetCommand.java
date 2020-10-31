package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.logic.parser.CliSyntax.SET_STATISTIC_TIMEFRAME;
import static seedu.momentum.logic.parser.CliSyntax.SET_THEME;

import java.util.Optional;

import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.StatisticTimeframe;
import seedu.momentum.commons.core.StatisticTimeframeSettings;
import seedu.momentum.commons.core.Theme;
import seedu.momentum.commons.util.CollectionUtil;
import seedu.momentum.logic.SettingsUpdateManager;
import seedu.momentum.model.Model;

/**
 * Adjust various settings in the application.
 */
public class SetCommand extends Command {

    public static final String COMMAND_WORD = "set";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
        + "[" + SET_THEME + "THEME]"
        + "[" + SET_STATISTIC_TIMEFRAME + "TIMEFRAME]";

    public static final String MESSAGE_UPDATE_SETTINGS_SUCCESS = "Settings updated.";
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
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (settingsToChange.getTheme().isPresent()) {
            Theme newTheme = settingsToChange.getTheme().get();
            model.setGuiThemeSettings(new GuiThemeSettings(newTheme));
            SettingsUpdateManager.updateTheme(newTheme);
        }

        if (settingsToChange.getStatTimeframe().isPresent()) {
            StatisticTimeframe newTimeframe = settingsToChange.getStatTimeframe().get();
            model.setStatisticTimeframeSettings(new StatisticTimeframeSettings(
                settingsToChange.getStatTimeframe().get()));
            SettingsUpdateManager.updateStatisticTimeframe(newTimeframe);
        }

        return new CommandResult(MESSAGE_UPDATE_SETTINGS_SUCCESS);
    }

    public static class SettingsToChange {

        private Theme theme;
        private StatisticTimeframe statTimeframe;

        public SettingsToChange() {
        }

        /**
         * Copy constructor.
         */
        public SettingsToChange(SettingsToChange toCopy) {
            setTheme(toCopy.theme);
            setStatTimeframe(toCopy.statTimeframe);
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

        public void setStatTimeframe(StatisticTimeframe statTimeframe) {
            this.statTimeframe = statTimeframe;
        }

        public Optional<StatisticTimeframe> getStatTimeframe() {
            return Optional.ofNullable(statTimeframe);
        }
    }
}
