package seedu.momentum.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.momentum.model.project.SortType;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.project.UniqueTrackedItemList;
import seedu.momentum.model.tag.Tag;

/**
 * Wraps all data at the project-book level
 * Duplicates are not allowed (by .isSameProject comparison)
 */
public class ProjectBook implements ReadOnlyProjectBook {

    protected final UniqueTrackedItemList trackedItems;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        trackedItems = new UniqueTrackedItemList();
    }

    public ProjectBook() {}

    /**
     * Creates an ProjectBook using the Projects in the {@code toBeCopied}
     */
    public ProjectBook(ReadOnlyProjectBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the project list with {@code trackedItems}.
     * {@code trackedItems} must not contain duplicate tracked items.
     */
    public void setTrackedItems(List<TrackedItem> trackedItems) {
        this.trackedItems.setTrackedItems(trackedItems);
    }

    /**
     * Resets the existing data of this {@code ProjectBook} with {@code newData}.
     */
    public void resetData(ReadOnlyProjectBook newData) {
        requireNonNull(newData);

        setTrackedItems(newData.getTrackedItemList());
    }

    /// sort operations

    /**
     * Sets the order of the list of projects according to given {@code sortType} and {@code isAscending}.
     *
     * @param sortType type of sort.
     * @param isAscending order of sort.
     */
    public void setOrder(SortType sortType, boolean isAscending) {
        requireNonNull(sortType);
        trackedItems.setOrder(sortType, isAscending);
    }

    //// project-level operations

    /**
     * Returns true if a tracked item with the same identity as {@code trcakedItem} exists in the project book.
     */
    public boolean hasTrackedItem(TrackedItem trackedItem) {
        requireNonNull(trackedItem);
        return trackedItems.contains(trackedItem);
    }

    /**
     * Adds a tracked item to the project book.
     * The tracked item must not already exist in the project book.
     */
    public void addTrackedItem(TrackedItem trackedItem) {
        trackedItems.add(trackedItem);
    }

    /**
     * Replaces the given tracked item {@code target} in the list with {@code editedTrackedItem}.
     * {@code target} must exist in the project book.
     * The tracked item identity of {@code trackedItem} must not be the same as another existing tracked item in
     * the project book.
     */
    public void setTrackedItem(TrackedItem target, TrackedItem editedTrackedItem) {
        requireNonNull(editedTrackedItem);

        trackedItems.setTrackedItem(target, editedTrackedItem);
    }

    /**
     * Removes {@code key} from this {@code ProjectBook}.
     * {@code key} must exist in the project book.
     */
    public void renameTrackedItem(TrackedItem key) {
        trackedItems.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return trackedItems.asUnmodifiableObservableList().size() + " projects";
        // TODO: refine later
    }

    @Override
    public ObservableList<TrackedItem> getTrackedItemList() {
        return trackedItems.asUnmodifiableObservableList();
    }

    @Override
    public Set<Tag> getTrackedItemTags() {
        Set<Tag> tags = new HashSet<>();
        getTrackedItemList().forEach(project -> tags.addAll(project.getTags()));
        return tags;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProjectBook // instanceof handles nulls
                && trackedItems.equals(((ProjectBook) other).trackedItems));
    }

    @Override
    public int hashCode() {
        return trackedItems.hashCode();
    }
}
