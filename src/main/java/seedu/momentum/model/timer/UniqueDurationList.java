package seedu.momentum.model.work_duration;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.work_duration.exceptions.DuplicateDurationException;
import seedu.address.model.work_duration.exceptions.DurationNotFoundException;

/**
 * A list of timers that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see WorkDuration#isSameDuration(WorkDuration)
 */
public class UniqueDurationList implements Iterable<WorkDuration> {

    private final ObservableList<WorkDuration> internalList = FXCollections.observableArrayList();
    private final ObservableList<WorkDuration> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent timer as the given argument.
     */
    public boolean contains(WorkDuration toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDuration);
    }

    /**
     * Adds a timer to the list.
     * The timer must not already exist in the list.
     */
    public void add(WorkDuration toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDurationException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the duration {@code target} in the list with {@code editedDuration}.
     * {@code target} must exist in the list.
     * The timer identity of {@code editedDuration} must not be the same as another existing duration in the list.
     */
    public void setTimer(WorkDuration target, WorkDuration editedDuration) {
        requireAllNonNull(target, editedDuration);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DurationNotFoundException();
        }

        if (!target.isSameDuration(editedDuration) && contains(editedDuration)) {
            throw new DuplicateDurationException();
        }

        internalList.set(index, editedDuration);
    }

    /**
     * Removes the equivalent timer from the list.
     * The timer must exist in the list.
     */
    public void remove(WorkDuration toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DurationNotFoundException();
        }
    }

    public void setPersons(UniqueDurationList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<WorkDuration> durations) {
        requireAllNonNull(durations);
        if (!timersAreUnique(durations)) {
            throw new DuplicateDurationException();
        }

        internalList.setAll(durations);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<WorkDuration> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<WorkDuration> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueDurationList // instanceof handles nulls
                && internalList.equals(((UniqueDurationList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean timersAreUnique(List<WorkDuration> durations) {
        for (int i = 0; i < durations.size() - 1; i++) {
            for (int j = i + 1; j < durations.size(); j++) {
                if (durations.get(i).isSameDuration(durations.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
