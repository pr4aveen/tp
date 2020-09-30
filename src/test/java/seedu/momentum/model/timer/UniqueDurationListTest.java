package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.timer.UniqueDurationList;
import seedu.momentum.model.timer.WorkDuration;
import seedu.momentum.model.timer.exceptions.DuplicateDurationException;
import seedu.momentum.model.timer.exceptions.DurationNotFoundException;
import seedu.momentum.testutil.TypicalWorkDuration;

public class UniqueDurationListTest {

    private final UniqueDurationList uniqueDurationList = new UniqueDurationList();

    @Test
    public void contains_nullDuration_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDurationList.contains(null));
    }

    @Test
    public void contains_durationNotInList_returnsFalse() {
        assertFalse(uniqueDurationList.contains(TypicalWorkDuration.DURATION_ONE_DAY));
    }

    @Test
    public void contains_durationInList_returnsTrue() {
        uniqueDurationList.add(TypicalWorkDuration.DURATION_ONE_DAY);
        assertTrue(uniqueDurationList.contains(TypicalWorkDuration.DURATION_ONE_DAY));
    }

    @Test
    public void add_nullDuration_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDurationList.add(null));
    }

    @Test
    public void add_duplicateDuration_throwsDuplicateProjectException() {
        uniqueDurationList.add(TypicalWorkDuration.DURATION_ONE_DAY);
        assertThrows(DuplicateDurationException.class, () ->
                uniqueDurationList.add(TypicalWorkDuration.DURATION_ONE_DAY));
    }

    @Test
    public void remove_projectDoesNotExist_throwsProjectNotFoundException() {
        assertThrows(DurationNotFoundException.class, () ->
                uniqueDurationList.remove(TypicalWorkDuration.DURATION_ONE_DAY));
    }

    @Test
    public void remove_existingProject_removesProject() {
        uniqueDurationList.add(TypicalWorkDuration.DURATION_ONE_DAY);
        uniqueDurationList.remove(TypicalWorkDuration.DURATION_ONE_DAY);
        UniqueDurationList expectedUniqueDurationList = new UniqueDurationList();
        assertEquals(expectedUniqueDurationList, uniqueDurationList);
    }

    @Test
    public void setProjects_nullUniqueProjectList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDurationList.setDurations((UniqueDurationList) null));
    }

    @Test
    public void setProjects_uniqueProjectList_replacesOwnListWithProvidedUniqueProjectList() {
        uniqueDurationList.add(TypicalWorkDuration.DURATION_ONE_DAY);
        UniqueDurationList expectedUniqueDurationList = new UniqueDurationList();
        expectedUniqueDurationList.add(TypicalWorkDuration.DURATION_ONE_MONTH);
        uniqueDurationList.setDurations(expectedUniqueDurationList);
        assertEquals(expectedUniqueDurationList, uniqueDurationList);
    }

    @Test
    public void setProjects_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDurationList.setDurations((List<WorkDuration>) null));
    }

    @Test
    public void setProjects_list_replacesOwnListWithProvidedList() {
        uniqueDurationList.add(TypicalWorkDuration.DURATION_ONE_DAY);
        List<WorkDuration> durationList = Collections.singletonList(TypicalWorkDuration.DURATION_ONE_MONTH);
        uniqueDurationList.setDurations(durationList);
        UniqueDurationList expectedUniqueDurationList = new UniqueDurationList();
        expectedUniqueDurationList.add(TypicalWorkDuration.DURATION_ONE_MONTH);
        assertEquals(expectedUniqueDurationList, uniqueDurationList);
    }

    @Test
    public void setProjects_listWithDuplicateProjects_throwsDuplicateProjectException() {
        List<WorkDuration> listWithDuplicateDurations = Arrays.asList(TypicalWorkDuration.DURATION_ONE_DAY,
                TypicalWorkDuration.DURATION_ONE_DAY);
        assertThrows(DuplicateDurationException.class, () ->
                uniqueDurationList.setDurations(listWithDuplicateDurations));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueDurationList.asUnmodifiableObservableList().remove(0));
    }
}
