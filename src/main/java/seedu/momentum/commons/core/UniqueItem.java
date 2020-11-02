package seedu.momentum.commons.core;

/** An interface to be implemented by objects used in UniqueItemList. */
public interface UniqueItem<T> {
    boolean isSameAs(T otherItem);
}
