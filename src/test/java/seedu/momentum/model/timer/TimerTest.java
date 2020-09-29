package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.model.timer.Timer;
import seedu.momentum.testutil.TypicalTimes;

public class TimerTest {

    @Test
    public void start_success() {
        Clock.initFixed(TypicalTimes.DAY);
        Timer timer = new Timer().start();
        Timer expectedTimer = new Timer(TypicalTimes.DAY, TypicalTimes.DAY, true);
        assertEquals(expectedTimer, timer);
    }

    @Test
    public void isRunning_timerStarted_true() {
        Clock.initFixed(TypicalTimes.DAY);
        Timer timer = new Timer().start();
        Timer expectedTimer = new Timer(TypicalTimes.DAY, TypicalTimes.DAY, true);
        assertEquals(expectedTimer.isRunning(), timer.isRunning());
    }

    @Test
    public void isRunning_timerNotStarted_false() {
        Clock.initFixed(TypicalTimes.DAY);
        Timer timer = new Timer();
        Timer expectedTimer = new Timer(TypicalTimes.DAY, TypicalTimes.DAY, false);
        assertEquals(expectedTimer.isRunning(), timer.isRunning());
    }

    @Test
    public void stop_success() {
        Clock.initFixed(TypicalTimes.DAY);
        Timer timer = new Timer().start().stop();
        Timer expectedTimer = new Timer(TypicalTimes.DAY, TypicalTimes.DAY, false);
        assertEquals(expectedTimer, timer);
    }

    @Test
    public void getStartTime() {
        Clock.initFixed(TypicalTimes.DAY);
        Timer timer = new Timer().start();
        assertEquals(TypicalTimes.DAY, timer.getStartTime());
    }

    @Test
    public void getStopTime() {
        Clock.initFixed(TypicalTimes.DAY);
        Timer timer = new Timer().start().stop();
        assertEquals(TypicalTimes.DAY, timer.getStopTime());
    }

    @Test
    public void equals() {
        Clock.initFixed(TypicalTimes.DAY);
        Timer timer1 = new Timer();
        Timer timer2 = new Timer();

        assertEquals(timer1, timer1);
        assertEquals(timer1, timer2);

        Timer differentStart = new Timer(TypicalTimes.DAY_ADD_DAY, TypicalTimes.DAY, false);
        assertNotEquals(timer1, differentStart);

        Timer differentEnd = new Timer(TypicalTimes.DAY, TypicalTimes.DAY_ADD_WEEK, false);
        assertNotEquals(timer1, differentEnd);

        Timer differentIsRunning = new Timer(TypicalTimes.DAY, TypicalTimes.DAY, true);
        assertNotEquals(timer1, differentIsRunning);
    }
}
