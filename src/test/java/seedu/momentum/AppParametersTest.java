package seedu.momentum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import javafx.application.Application;

public class AppParametersTest {

    private static final String CONFIG = "config";
    private static final String CONFIG_JSON = CONFIG + ".json";

    private final ParametersStub parametersStub = new ParametersStub();
    private final AppParameters expected = new AppParameters();

    @Test
    public void parse_validConfigPath_success() {
        parametersStub.namedParameters.put(CONFIG, CONFIG_JSON);
        expected.setConfigPath(Paths.get(CONFIG_JSON));
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void parse_nullConfigPath_success() {
        parametersStub.namedParameters.put(CONFIG, null);
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void parse_invalidConfigPath_success() {
        parametersStub.namedParameters.put(CONFIG, "a\0");
        expected.setConfigPath(null);
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(expected.equals(expected));

        // null -> returns false
        assertFalse(expected.equals(null));

        // different types -> returns false
        assertFalse(expected.equals(1));
    }

    @Test
    public void hashCodeTest() {
        expected.setConfigPath(Paths.get(CONFIG_JSON));
        int expectedHashCode = expected.hashCode();

        // same values -> returns true
        AppParameters actual = new AppParameters();
        actual.setConfigPath(Paths.get(CONFIG_JSON));
        assertEquals(expectedHashCode, actual.hashCode());

        // different parameters
        actual.setConfigPath(Paths.get("configs.json"));
        assertNotEquals(expectedHashCode, actual.hashCode());
    }

    private static class ParametersStub extends Application.Parameters {
        private Map<String, String> namedParameters = new HashMap<>();

        @Override
        public List<String> getRaw() {
            throw new AssertionError("should not be called");
        }

        @Override
        public List<String> getUnnamed() {
            throw new AssertionError("should not be called");
        }

        @Override
        public Map<String, String> getNamed() {
            return Collections.unmodifiableMap(namedParameters);
        }
    }
}
