package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.model.timer.TimerWrapper;
import seedu.momentum.testutil.TypicalTimes;

public class TimerWrapperTest {

    @Test
    public void start_success() {
        Clock.initFixed(TypicalTimes.DAY);
        TimerWrapper timerWrapper = new TimerWrapper().start();
        TimerWrapper expectedTimerWrapper = new TimerWrapper(TypicalTimes.DAY, TypicalTimes.DAY, true);
        assertEquals(expectedTimerWrapper, timerWrapper);
    }

    @Test
    public void isRunning_timerStarted_true() {
        Clock.initFixed(TypicalTimes.DAY);
        TimerWrapper timerWrapper = new TimerWrapper().start();
        TimerWrapper expectedTimerWrapper = new TimerWrapper(TypicalTimes.DAY, TypicalTimes.DAY, true);
        assertEquals(expectedTimerWrapper.isRunning(), timerWrapper.isRunning());
    }

    @Test
    public void isRunning_timerNotStarted_false() {
        Clock.initFixed(TypicalTimes.DAY);
        TimerWrapper timerWrapper = new TimerWrapper();
        TimerWrapper expectedTimerWrapper = new TimerWrapper(TypicalTimes.DAY, TypicalTimes.DAY, false);
        assertEquals(expectedTimerWrapper.isRunning(), timerWrapper.isRunning());
    }

    @Test
    public void stop_success() {
        Clock.initFixed(TypicalTimes.DAY);
        TimerWrapper timerWrapper = new TimerWrapper().start().stop();
        TimerWrapper expectedTimerWrapper = new TimerWrapper(TypicalTimes.DAY, TypicalTimes.DAY, false);
        assertEquals(expectedTimerWrapper, timerWrapper);
    }

    @Test
    public void getStartTime() {
        Clock.initFixed(TypicalTimes.DAY);
        TimerWrapper timerWrapper = new TimerWrapper().start();
        assertEquals(TypicalTimes.DAY, timerWrapper.getStartTime());
    }

    @Test
    public void getStopTime() {
        Clock.initFixed(TypicalTimes.DAY);
        TimerWrapper timerWrapper = new TimerWrapper().start().stop();
        assertEquals(TypicalTimes.DAY, timerWrapper.getStopTime());
    }

    @Test
    public void equals() {
        Clock.initFixed(TypicalTimes.DAY);
        TimerWrapper timerWrapper1 = new TimerWrapper();
        TimerWrapper timerWrapper2 = new TimerWrapper();

        assertEquals(timerWrapper1, timerWrapper1);
        assertEquals(timerWrapper1, timerWrapper2);

        TimerWrapper differentStart = new TimerWrapper(TypicalTimes.DAY_ADD_DAY, TypicalTimes.DAY, false);
        assertNotEquals(timerWrapper1, differentStart);

        TimerWrapper differentEnd = new TimerWrapper(TypicalTimes.DAY, TypicalTimes.DAY_ADD_WEEK, false);
        assertNotEquals(timerWrapper1, differentEnd);

        TimerWrapper differentIsRunning = new TimerWrapper(TypicalTimes.DAY, TypicalTimes.DAY, true);
        assertNotEquals(timerWrapper1, differentIsRunning);
    }
}
