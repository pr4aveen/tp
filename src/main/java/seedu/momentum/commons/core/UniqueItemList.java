//@@author boundtotheearth

package seedu.momentum.commons.core;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.momentum.commons.exceptions.DuplicateItemException;
import seedu.momentum.commons.exceptions.ItemNotFoundException;

/**
 * A list of items that enforces uniqueness between its elements and does not allow nulls.
 * An item is considered unique by comparing using {@code UniqueItem#isSameAs(UniqueItem)}. As such, adding
 * and updating of items uses {@code UniqueItem#isSameAs(UniqueItem)} for equality so as to ensure that
 * the item being added or updated is unique in terms of identity in the UniqueItemList. However, the
 * removal of a item uses {@code equals(Object)} so as to ensure that the item with exactly the
 * same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see UniqueItem#isSameAs(Object) (UniqueItem)
 */
public class UniqueItemList<T extends UniqueItem<T>> implements Iterable<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent item as the given argument.
     *
     * @param toCheck The other item to check against.
     * @return True if the two items are equivalent, false otherwise.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAs);
    }

    /**
     * Adds a item to the list.
     * The item must not already exist in the list.
     *
     * @param toAdd The item to add.
     * @throws DuplicateItemException if the item already exists in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateItemException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the item {@code target} in the list with {@code editedItem}.
     * {@code target} must exist in the list.
     * The item identity of {@code editedItem} must not be the same as another existing item
     * in the list.
     *
     * @param target The item to replace.
     * @param editedItem The new item.
     * @throws ItemNotFoundException If the target does not exist in the list.
     * @throws DuplicateItemException If the new item is equivalent to some other item in the list.
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
     *
     * @param toRemove The item to be removed.
     * @throws ItemNotFoundException If the item does not exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ItemNotFoundException();
        }
    }

    /**
     * Replaces all items in the list with a new list of items from another {@code UniqueItemList}.
     *
     * @param replacement The list of replacement items.
     */
    public void setItems(UniqueItemList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code items}.
     * {@code items} must not contain duplicate items.
     *
     * @param items The list of new items to be added.
     * @throws DuplicateItemException If the new list of items contains duplicate items.
     */
    public void setItems(List<T> items) {
        requireAllNonNull(items);
        if (!itemsAreUnique(items)) {
            throw new DuplicateItemException();
        }

        internalList.setAll(items);
    }

    //@@author kkangs0226
    /**
     * Creates a duplicate {@code UniqueItemList}
     *
     * @return Duplicate {@code UniqueItemList} list.
     */
    public UniqueItemList<T> copy() {
        UniqueItemList<T> newList = new UniqueItemList<>();
        for (T t : internalList) {
            newList.add(t);
        }
        return newList;
    }
    //@@author

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
     * Returns true if {@code items} contains only unique elements,
     * as determined by {@code UniqueItem#isSameAs(UniqueItem)}.
     *
     * @param items The list of items to check.
     * @return true if all items are unique, false otherwise.
     */
    private boolean itemsAreUnique(List<T> items) {
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
