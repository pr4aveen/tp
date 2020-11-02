package seedu.momentum.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.momentum.model.project.comparators.CompletionStatusCompare;
import seedu.momentum.model.project.comparators.CreatedDateCompare;
import seedu.momentum.model.project.comparators.DeadlineCompare;
import seedu.momentum.model.project.comparators.NameCompare;
import seedu.momentum.model.project.exceptions.DuplicateTrackableItemException;
import seedu.momentum.model.project.exceptions.TrackableItemNotFoundException;

/**
 * A list of tracked items that enforces uniqueness between its elements and does not allow nulls.
 * A project is considered unique by comparing using {@code TrackedItem#isSameTrackedItem(TrackedItem)}. As such, adding
 * and updating of tracked items uses TrackedItem#isSameTrackedItem(TrackedItem) for equality so as to ensure that
 * the tracked item being added or updated is unique in terms of identity in the UniqueTrackedItemList. However, the
 * removal of a tracked item uses TrackedItem#equals(Object) so as to ensure that the tracked item with exactly the
 * same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see TrackedItem#isSameTrackedItem(TrackedItem)
 */
public class UniqueTrackedItemList implements Iterable<TrackedItem> {

    public static final SortType DEFAULT_SORT_TYPE = SortType.ALPHA;
    private SortType sortType = DEFAULT_SORT_TYPE;
    private final ObservableList<TrackedItem> internalList = FXCollections.observableArrayList();
    private final ObservableList<TrackedItem> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent tracked item as the given argument.
     */
    public boolean contains(TrackedItem toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTrackedItem);
    }

    /**
     * Adds a tracked item to the list.
     * The tracked item must not already exist in the list.
     */
    public void add(TrackedItem toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTrackableItemException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds a task to the list.
     * The task must not already exist in the list.
     */
    public UniqueTrackedItemList addTask(TrackedItem toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTrackableItemException();
        }
        UniqueTrackedItemList newList;
        newList = this.copy();
        newList.internalList.add(toAdd);

        return newList;
    }

    /**
     * Replaces the tracked item {@code target} in the list with {@code editedTrackedItem}.
     * {@code target} must exist in the list.
     * The tracked item identity of {@code editedTrackedItem} must not be the same as another existing tracked item
     * in the list.
     */
    public void setTrackedItem(TrackedItem target, TrackedItem editedTrackedItem) {
        requireAllNonNull(target, editedTrackedItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TrackableItemNotFoundException();
        }

        if (!target.isSameTrackedItem(editedTrackedItem) && contains(editedTrackedItem)) {
            throw new DuplicateTrackableItemException();
        }

        internalList.set(index, editedTrackedItem);
    }

    /**
     * Duplicates list of tasks and replaces the task {@code target} in the list with {@code editedTrackedItem}.
     * {@code target} must exist in the list.
     * The task identity of {@code editedTrackedItem} must not be the same as another existing task
     * in the list.
     *
     * @return duplicate {@code UniqueTrackedItemList} list with new updated task.
     */
    public UniqueTrackedItemList setTasks(TrackedItem target, TrackedItem editedTrackedItem) {
        requireAllNonNull(target, editedTrackedItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TrackableItemNotFoundException();
        }

        if (!target.isSameTrackedItem(editedTrackedItem) && contains(editedTrackedItem)) {
            throw new DuplicateTrackableItemException();
        }

        UniqueTrackedItemList newList;
        newList = this.copy();
        newList.internalList.set(index, editedTrackedItem);

        return newList;
    }

    /**
     * Removes the equivalent tracked item from the list.
     * The tracked item must exist in the list.
     */
    public void remove(TrackedItem toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TrackableItemNotFoundException();
        }
    }

    /**
     * Duplicates existing list and removes the equivalent task from the list.
     * The task must exist in the list.
     *
     * @return duplicate {@code UniqueTrackedItemList} list with removed task.
     */
    public UniqueTrackedItemList removeTask(TrackedItem toRemove) {
        requireNonNull(toRemove);

        UniqueTrackedItemList newList;
        newList = this.copy();

        if (!newList.internalList.remove(toRemove)) {
            throw new TrackableItemNotFoundException();
        }

        return newList;
    }

    public void setTrackedItems(UniqueTrackedItemList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code trackedItems}.
     * {@code trackedItems} must not contain duplicate tracked items.
     */
    public void setTrackedItems(List<TrackedItem> trackedItems) {
        requireAllNonNull(trackedItems);
        if (!projectsAreUnique(trackedItems)) {
            throw new DuplicateTrackableItemException();
        }

        internalList.setAll(trackedItems);
    }

    /**
     * Creates a duplicate {@code UniqueTrackedItemList}
     *
     * @return duplicate {@code UniqueTrackedItemList} list.
     */
    public UniqueTrackedItemList copy() {
        UniqueTrackedItemList newList = new UniqueTrackedItemList();
        for (TrackedItem t : internalList) {
            newList.add(t);
        }
        return newList;
    }

    /**
     * Sets the order of the list of tracked items according to given {@code sortType} and {@code isAscending}.
     *
     * @param sortType                   type of sort.
     * @param isAscending                order of sort.
     * @param isSortedByCompletionStatus sort by creation status.
     */
    public void setOrder(SortType sortType, boolean isAscending, boolean isSortedByCompletionStatus) {
        requireNonNull(sortType);

        switch (sortType) {
        case ALPHA:
            setOrderAlphaType(isAscending, isSortedByCompletionStatus);
            break;
        case DEADLINE:
            setOrderDeadlineType(isAscending, isSortedByCompletionStatus);
            break;
        case CREATED:
            setOrderCreatedDateType(isAscending, isSortedByCompletionStatus);
            break;
        case NULL:
            setOrderNullType(isAscending, isSortedByCompletionStatus);
            break;
        default:
            // Will always be one of the above. Default does nothing.
            break;
        }
    }

    /**
     * Sets the order of list of tracked items by alphabetical order, ascending or descending based on user input.
     *
     * @param isAscending                order of sort specified by user.
     * @param isSortedByCompletionStatus sort by creation status.
     */
    private void setOrderAlphaType(boolean isAscending, boolean isSortedByCompletionStatus) {
        Comparator<TrackedItem> nameCompare = new NameCompare();
        nameCompare = isAscending ? nameCompare : nameCompare.reversed();
        sortType = SortType.ALPHA;

        Comparator<TrackedItem> compare;
        if (isSortedByCompletionStatus) {
            compare = new CompletionStatusCompare().thenComparing(nameCompare);
        } else {
            compare = nameCompare;
        }
        internalList.sort(compare);
    }

    /**
     * Sets the order of list of tracked items by deadline order, ascending or descending based on user input.
     *
     * @param isAscending                order of sort specified by user.
     * @param isSortedByCompletionStatus sort by creation status.
     */
    private void setOrderDeadlineType(boolean isAscending, boolean isSortedByCompletionStatus) {
        Comparator<TrackedItem> nameCompare = new NameCompare();
        Comparator<HashMap<String, Object>> deadlineCompareHashMap = new DeadlineCompare();
        deadlineCompareHashMap = isAscending ? deadlineCompareHashMap : deadlineCompareHashMap.reversed();
        Comparator<TrackedItem> deadlineCompare = Comparator.comparing(TrackedItem::getNullOrDeadline,
                Comparator.nullsLast(deadlineCompareHashMap));
        deadlineCompare = deadlineCompare.thenComparing(nameCompare);
        sortType = SortType.DEADLINE;

        Comparator<TrackedItem> compare;
        if (isSortedByCompletionStatus) {
            compare = new CompletionStatusCompare().thenComparing(deadlineCompare);
        } else {
            compare = deadlineCompare;
        }
        internalList.sort(compare);
    }

    /**
     * Sets the order of list of tracked items by created date order, ascending or descending based on user input.
     *
     * @param isAscending                order of sort specified by user.
     * @param isSortedByCompletionStatus sort by creation status.
     */
    private void setOrderCreatedDateType(boolean isAscending, boolean isSortedByCompletionStatus) {
        Comparator<TrackedItem> createdDateCompare = new CreatedDateCompare();
        createdDateCompare = isAscending ? createdDateCompare : createdDateCompare.reversed();
        this.sortType = SortType.CREATED;

        Comparator<TrackedItem> compare;
        if (isSortedByCompletionStatus) {
            compare = new CompletionStatusCompare().thenComparing(createdDateCompare);
        } else {
            compare = createdDateCompare;
        }
        internalList.sort(compare);
    }

    /**
     * Sets the order of the list of tracked items to current sort type with specified order
     * if sort type has not been specified by user.
     *
     * @param isAscending                order of sort specified by user.
     * @param isSortedByCompletionStatus sort by creation status.
     */
    private void setOrderNullType(boolean isAscending, boolean isSortedByCompletionStatus) {
        setOrder(SortType.ALPHA, isAscending, isSortedByCompletionStatus);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<TrackedItem> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<TrackedItem> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTrackedItemList // instanceof handles nulls
                && internalList.equals(((UniqueTrackedItemList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code trackedItems} contains only unique projects.
     */
    private boolean projectsAreUnique(List<TrackedItem> trackedItems) {
        for (int i = 0; i < trackedItems.size() - 1; i++) {
            for (int j = i + 1; j < trackedItems.size(); j++) {
                if (trackedItems.get(i).isSameTrackedItem(trackedItems.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
