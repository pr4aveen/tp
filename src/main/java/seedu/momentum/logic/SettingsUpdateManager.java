package seedu.momentum.logic;

import java.util.Optional;
import java.util.logging.Logger;

import seedu.momentum.commons.core.LogsCenter;
import seedu.momentum.commons.core.StatisticTimeframe;
import seedu.momentum.commons.core.Theme;
import seedu.momentum.logic.statistic.StatisticGenerator;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.ReadOnlyUserPrefs;
import seedu.momentum.ui.Ui;

/**
 * Class that helps to update application interface when settings change.
 */
public class SettingsUpdateManager {
    private static final Logger LOGGER = LogsCenter.getLogger(ModelManager.class);

    private static final String UI_NOT_PRESENT = "Ui not found. Ui changes will not be applied for now.";
    private static final String STAT_NOT_PRESENT = "Statistics not found. Statistics timeframe is not changed for now.";

    private static final Runnable LOG_UI_NOT_PRESENT = () -> LOGGER.warning(UI_NOT_PRESENT);
    private static final Runnable LOG_STAT_NOT_PRESENT = () -> LOGGER.warning(STAT_NOT_PRESENT);

    private static Ui ui;
    private static StatisticGenerator statistic;

    /**
     * Initializes the {@code Ui} and {@code StatisticGenerator} to be maintained.
     *
     * @param appUi Ui to change the settings of.
     * @param appStatistic Statistic generator to change the settings of.
     */
    public static void initSettingsUpdateManager(Ui appUi, StatisticGenerator appStatistic) {
        ui = appUi;
        statistic = appStatistic;
    }

    /**
     * Updates the theme of the application.
     *
     * @param theme New theme to update the application Ui to.
     */
    public static void updateTheme(Theme theme) {
        Optional.ofNullable(ui).ifPresentOrElse(ui -> ui.getMainWindow().updateTheme(theme), LOG_UI_NOT_PRESENT);
    }

    /**
     * Updates the timeframe for the statistics tracked.
     *
     * @param timeframe New timeframe to update the application statistics to.
     */
    public static void updateStatisticTimeframe(StatisticTimeframe timeframe) {
        Optional.ofNullable(statistic).ifPresentOrElse(stat -> stat.updateStatisticTimeframe(timeframe),
            LOG_STAT_NOT_PRESENT);
        Optional.ofNullable(ui).ifPresentOrElse(ui -> ui.getMainWindow().updateStatList(timeframe),
            LOG_UI_NOT_PRESENT);
    }

    /**
     * Updates the application settings from the provided {@code userPrefs}.
     *
     * @param userPrefs The set of user preferences containing the settings to change.
     */
    public static void updateApplicationSettings(ReadOnlyUserPrefs userPrefs) {
        updateTheme(userPrefs.getGuiThemeSettings().getTheme());
        updateStatisticTimeframe(userPrefs.getStatisticTimeframeSettings().getStatTimeframe());
    }

}
