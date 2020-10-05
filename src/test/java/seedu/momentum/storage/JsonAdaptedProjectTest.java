package seedu.momentum.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.momentum.storage.JsonAdaptedProject.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.momentum.testutil.Assert.assertThrows;
import static seedu.momentum.testutil.TypicalProjects.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Date;
import seedu.momentum.commons.exceptions.IllegalValueException;
import seedu.momentum.model.project.Name;

public class JsonAdaptedProjectTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_CREATED_DATE = "2019-42-99";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_START_TIME = "as;dlkfj";
    private static final String INVALID_STOP_TIME = "as;dlkfj";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_DESCRIPTION = BENSON.getDescription().toString();
    private static final String VALID_CREATED_DATE = BENSON.getCreatedDate().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedWorkDuration> VALID_DURATIONS =
            BENSON.getDurationList().stream().map(JsonAdaptedWorkDuration::new).collect(Collectors.toList());
    private static final JsonAdaptedTimer VALID_TIMER = new JsonAdaptedTimer(BENSON.getTimer());

    @Test
    public void toModelType_validProjectDetails_returnsProject() throws Exception {
        JsonAdaptedProject project = new JsonAdaptedProject(BENSON);
        assertEquals(BENSON, project.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedProject project =
                new JsonAdaptedProject(INVALID_NAME, VALID_DESCRIPTION, VALID_CREATED_DATE, VALID_TAGS,
                        VALID_DURATIONS, VALID_TIMER);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedProject project =
                new JsonAdaptedProject(null, VALID_DESCRIPTION, VALID_CREATED_DATE, VALID_TAGS, VALID_DURATIONS,
                        VALID_TIMER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_invalidCreatedDate_throwsIllegalValueException() {
        JsonAdaptedProject project =
                new JsonAdaptedProject(VALID_NAME, VALID_DESCRIPTION, INVALID_CREATED_DATE, VALID_TAGS,
                        VALID_DURATIONS, VALID_TIMER);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedProject project =
                new JsonAdaptedProject(VALID_NAME, VALID_DESCRIPTION, VALID_CREATED_DATE, invalidTags,
                        VALID_DURATIONS, VALID_TIMER);
        assertThrows(IllegalValueException.class, project::toModelType);
    }

    @Test
    public void toModelType_invalidDurations_throwsIllegalValueException() {
        List<JsonAdaptedWorkDuration> invalidDurations = new ArrayList<>(VALID_DURATIONS);
        invalidDurations.add(new JsonAdaptedWorkDuration(INVALID_START_TIME, INVALID_STOP_TIME));
        JsonAdaptedProject project =
                new JsonAdaptedProject(VALID_NAME, VALID_DESCRIPTION, VALID_CREATED_DATE, VALID_TAGS,
                        invalidDurations, VALID_TIMER);
        assertThrows(IllegalValueException.class, project::toModelType);
    }
}
