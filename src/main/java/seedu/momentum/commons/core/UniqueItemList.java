package seedu.momentum.commons.core;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.momentum.model.project.exceptions.DuplicateItemException;
import seedu.momentum.model.project.exceptions.DuplicateTrackableItemException;
import seedu.momentum.model.project.exceptions.ItemNotFoundException;

/**
 * A list of tracked items that enforces uniqueness between its elements and does not allow nulls.
 * An item is considered unique by comparing using {@code TrackedItem#isSameTrackedItem(TrackedItem)}. As such, adding
 * and updating of tracked items uses TrackedItem#isSameTrackedItem(TrackedItem) for equality so as to ensure that
 * the tracked item being added or updated is unique in terms of identity in the UniqueTrackedItemList. However, the
 * removal of a tracked item uses TrackedItem#equals(Object) so as to ensure that the tracked item with exactly the
 * same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see TrackedItem#isSameAs(TrackedItem) (TrackedItem)
 */
public class UniqueItemList<T extends UniqueItem<T>> implements Iterable<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent tracked item as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAs);
    }

    /**
     * Adds a tracked item to the list.
     * The tracked item must not already exist in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateItemException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the tracked item {@code target} in the list with {@code editedItem}.
     * {@code target} must exist in the list.
     * The tracked item identity of {@code editedItem} must not be the same as another existing tracked item
     * in the list.
     */
    public void set(T target, T editedItem) {
        requireAllNonNull(target, editedItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ItemNotFoundException();
        }

        if (!target.isSameAs(editedItem) && contains(editedItem)) {
            throw new DuplicateItemException();
        }

        internalList.set(index, editedItem);
    }

    /**
     * Removes the equivalent item from the list.
     * The item must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ItemNotFoundException();
        }
    }

    public void setItems(UniqueItemList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code items}.
     * {@code items} must not contain duplicate tracked items.
     */
    public void setItems(List<T> items) {
        requireAllNonNull(items);
        if (!projectsAreUnique(items)) {
            throw new DuplicateTrackableItemException();
        }

        internalList.setAll(items);
    }

    /**
     * Creates a duplicate {@code UniqueTrackedItemList}
     *
     * @return duplicate {@code UniqueTrackedItemList} list.
     */
    public UniqueItemList<T> copy() {
        UniqueItemList<T> newList = new UniqueItemList<>();
        for (T t : internalList) {
            newList.add(t);
        }
        return newList;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueItemList<?> // instanceof handles nulls
                && internalList.equals(((UniqueItemList<?>) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code items} contains only unique projects.
     */
    private boolean projectsAreUnique(List<T> items) {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = i + 1; j < items.size(); j++) {
                if (items.get(i).isSameAs(items.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
