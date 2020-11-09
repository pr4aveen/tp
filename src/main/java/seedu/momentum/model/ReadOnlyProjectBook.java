package seedu.momentum.model;

import java.util.Set;

import javafx.collections.ObservableList;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.tag.Tag;

/**
 * Unmodifiable view of an project book.
 */
public interface ReadOnlyProjectBook {

    /**
     * Returns an unmodifiable view of the tracked item list.
     * This list will not contain any duplicate tracked items.
     */
    ObservableList<TrackedItem> getTrackedItemList();

    /**
     * Returns the collection of all tags that the user has entered for the tracked item.
     */
    Set<Tag> getTrackedItemTags();

    ///**
    // * Returns the current predicate being used to filter the displayed list.
    // */
    //Predicate<TrackedItem> getCurrentPredicate();

    ///**
    // * Returns the current project being viewed.
    // * Should only be called in task view.
    // */
    //Project getCurrentProject();
}
