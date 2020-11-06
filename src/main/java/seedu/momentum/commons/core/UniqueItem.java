//@@author boundtotheearth

package seedu.momentum.commons.core;

/**
 * An interface to be implemented by objects used in UniqueItemList.
 */
public interface UniqueItem<T> {
    /**
     * Checks if this {@code UniqueItem} is the same as another {@code UniqueItem}.
     *
     * @param otherItem The other item to check against.
     * @return True if both items are considered to be the same, false otherwise.
     */
    boolean isSameAs(T otherItem);
}
