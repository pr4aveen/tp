package seedu.momentum.logic.statistic;

import static seedu.momentum.testutil.TypicalWorkDuration.DURATION_ONE_HOUR;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import seedu.momentum.commons.core.StatisticTimeframe;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.Project;
import seedu.momentum.testutil.ProjectBuilder;

/**
 * Dummy data used for testing statistic generation.
 * Contains pre-calculated "correct" statistics to be compared to.
 */
public class StatisticTestUtil {

    //Projects
    public static final Project ALPHA = new ProjectBuilder()
            .withName("Alpha")
            .withDescription("Alpha Project")
            .withTags("CompanyA", "CompanyB", "CompanyC")
            .withDurations(DURATION_ONE_HOUR)
            .build();
    public static final Project BETA = new ProjectBuilder()
            .withName("Beta")
            .build();
    public static final Project CHARLIE = new ProjectBuilder()
            .withName("Charlie")
            .withDurations(DURATION_ONE_HOUR)
            .build();
    public static final Project DELTA = new ProjectBuilder()
            .withName("Delta")
            .withDescription("Delta Project")
            .withDurations(DURATION_ONE_HOUR)
            .build();

    // Model
    public static final Model TEST_MODEL = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    // Statistics
    public static final PeriodicTotalTimeStatistic TEST_WEEKLY_TIME_PER_PROJECT =
            new PeriodicTotalTimeStatistic(new StatisticTimeframe(StatisticTimeframe.Timeframe.WEEKLY),
                ChronoUnit.MINUTES,
                    FXCollections.observableArrayList(
                            new StatisticEntry("Alpha", 60),
                            new StatisticEntry("Beta", 0),
                            new StatisticEntry("Charlie", 60),
                            new StatisticEntry("Delta", 60)
                    ));
    public static final Statistic[] TEST_STATISTIC_LIST = {TEST_WEEKLY_TIME_PER_PROJECT};
    public static final StatisticGenerator TEST_STATISTICS = new StatisticManager(TEST_MODEL, TEST_STATISTIC_LIST);

    public static ProjectBook getTypicalProjectBook() {
        ProjectBook projectBook = new ProjectBook();
        for (Project project : getTypicalProjects()) {
            projectBook.addTrackedItem(project);
        }
        return projectBook;
    }

    public static List<Project> getTypicalProjects() {
        return new ArrayList<>(Arrays.asList(ALPHA, BETA, CHARLIE, DELTA));
    }
}
