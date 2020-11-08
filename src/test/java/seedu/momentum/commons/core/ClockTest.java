//@@author boundtotheearth
package seedu.momentum.commons.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import seedu.momentum.testutil.TypicalTimes;

class ClockTest {

    @Test
    void advance_manual_success() {
        Clock.initManual(TypicalTimes.DAY);
        assertDoesNotThrow(() -> Clock.advance(1, ChronoUnit.HOURS));
    }

    @Test
    void advance_notManual_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> Clock.advance(1, ChronoUnit.HOURS));
    }

    @Test
    void reverse_manual_success() {
        Clock.initManual(TypicalTimes.DAY);
        assertDoesNotThrow(() -> Clock.reverse(1, ChronoUnit.HOURS));
    }

    @Test
    void reverse_notManual_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> Clock.reverse(1, ChronoUnit.HOURS));
    }

    @Test
    void now_fixed_returnCorrectTime() {
        Clock.initFixed(TypicalTimes.DAY);
        assertEquals(TypicalTimes.DAY, Clock.now());
        Clock.reset();
    }

    @Test
    void now_manualStartTime_returnCorrectTime() {
        Clock.initManual(TypicalTimes.DAY);
        assertEquals(TypicalTimes.DAY, Clock.now());
        Clock.reset();
    }

    @Test
    void now_manualAdvancedTime_returnCorrectTime() {
        Clock.initManual(TypicalTimes.DAY);
        Clock.advance(1, ChronoUnit.HOURS);
        assertEquals(TypicalTimes.DAY_ADD_HOUR, Clock.now());
        Clock.reset();
    }

    @Test
    void now_manualReversedTime_returnCorrectTime() {
        Clock.initManual(TypicalTimes.DAY.plus(1, ChronoUnit.HOURS));
        Clock.reverse(1, ChronoUnit.HOURS);
        assertEquals(TypicalTimes.DAY, Clock.now());
        Clock.reset();
    }
}
