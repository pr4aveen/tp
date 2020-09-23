package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.momentum.testutil.Assert.assertThrows;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.timer.Timer;

public class TimerTest {

    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Timer(null));
    }

    @Test
    public void start_success() {
        Timer timer = new Timer(model.getFilteredProjectList().get(0));
        timer.start();
        assertEquals(LocalDateTime.now().getSecond(), timer.getStartTime().getTime().getSecond());
    }

    @Test
    public void stop_success() {
        Timer timer = new Timer(model.getFilteredProjectList().get(0));
        timer.stop();
        assertEquals(LocalDateTime.now().getSecond(), timer.getStopTime().getTime().getSecond());
    }
}
