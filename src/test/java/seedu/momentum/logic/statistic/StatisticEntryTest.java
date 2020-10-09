package seedu.momentum.logic.statistic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class StatisticEntryTest {

    @Test
    public void constructor_nullLabel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StatisticEntry(null, 0));
    }

    @Test
    public void getLabel() {
        StatisticEntry entry = new StatisticEntry("Label", 0);
        assertEquals("Label", entry.getLabel());
    }

    @Test
    public void getValue() {
        StatisticEntry entry = new StatisticEntry("Label", 1);
        assertEquals(1, entry.getValue());
    }

    @Test
    public void toStringTest() {
        StatisticEntry entry = new StatisticEntry("Label", 0);
        String expected = String.format("(%s, %f)", "Label", 0.0);
        assertEquals(expected, entry.toString());
    }

    @Test
    public void equalsTest() {
        StatisticEntry label1value1 = new StatisticEntry("Label1", 1);
        StatisticEntry label1value2 = new StatisticEntry("Label1", 2);
        StatisticEntry label2value1 = new StatisticEntry("Label2", 1);
        StatisticEntry label2value2 = new StatisticEntry("Label2", 2);

        // Same object, returns true
        assertTrue(label1value1.equals(label1value1));

        // Same label, same value, returns true
        assertTrue(label1value1.equals(new StatisticEntry("Label1", 1)));

        // Same label, different value, returns false
        assertFalse(label1value1.equals(label1value2));

        // Different label, same value, returns false
        assertFalse(label1value1.equals(label2value1));

        // Different label, different value, returns false
        assertFalse(label1value1.equals(label2value2));
    }

}
