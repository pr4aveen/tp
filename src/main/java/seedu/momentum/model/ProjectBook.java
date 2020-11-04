package seedu.momentum.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.momentum.commons.core.LogsCenter;
import seedu.momentum.commons.core.UniqueItemList;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.Task;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.reminder.ReminderManager;
import seedu.momentum.model.tag.Tag;

/**
 * Wraps all data at the project-book level
 * Duplicates are not allowed (by .isSameProject comparison)
 */
public class ProjectBook implements ReadOnlyProjectBook {

    private static final Logger LOGGER = LogsCenter.getLogger(ProjectBook.class);
    private final UniqueItemList<TrackedItem> trackedProjects;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        trackedProjects = new UniqueItemList<>();
    }

    public ProjectBook() {
    }

    /**
     * Creates an ProjectBook using the Projects in the {@code toBeCopied}
     */
    public ProjectBook(ReadOnlyProjectBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the project list with {@code trackedProjects}.
     * {@code trackedProjects} must not contain duplicate tracked projects.
     */
    public void setTrackedProjects(List<TrackedItem> trackedProjects) {
        this.trackedProjects.setItems(trackedProjects);
    }

    /**
     * Resets the existing data of this {@code ProjectBook} with {@code newData}.
     */
    public void resetData(ReadOnlyProjectBook newData) {
        requireNonNull(newData);

        setTrackedProjects(newData.getTrackedItemList());
    }

    //// project-level operations

    /**
     * Returns true if a tracked item with the same identity as {@code trcakedItem} exists in the project book.
     */
    public boolean hasTrackedItem(TrackedItem trackedItem) {
        requireNonNull(trackedItem);
        return trackedProjects.contains(trackedItem);
    }

    /**
     * Adds a tracked item to the project book.
     * The tracked item must not already exist in the project book.
     */
    public void addTrackedItem(TrackedItem trackedItem) {
        trackedProjects.add(trackedItem);
    }

    /**
     * Replaces the given tracked item {@code target} in the list with {@code editedTrackedItem}.
     * {@code target} must exist in the project book.
     * The tracked item identity of {@code trackedItem} must not be the same as another existing tracked item in
     * the project book.
     */
    public void setTrackedItem(TrackedItem target, TrackedItem editedTrackedItem) {
        requireNonNull(editedTrackedItem);

        trackedProjects.set(target, editedTrackedItem);
    }

    /**
     * Removes {@code key} from this {@code ProjectBook}.
     * {@code key} must exist in the project book.
     */
    public void removeTrackedItem(TrackedItem key) {
        trackedProjects.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return trackedProjects.asUnmodifiableObservableList().size() + " projects";
        // TODO: refine later
    }

    @Override
    public ObservableList<TrackedItem> getTrackedItemList() {
        return trackedProjects.asUnmodifiableObservableList();
    }

    @Override
    public Set<Tag> getTrackedItemTags() {
        Set<Tag> tags = new HashSet<>();
        getTrackedItemList().forEach(project -> tags.addAll(project.getTags()));
        return tags;
    }

    private void updateExpiredReminders() {
        UniqueItemList<TrackedItem> itemList = new UniqueItemList<>();
        for (TrackedItem item : trackedProjects) {
            TrackedItem newItem = item.updateExpiredReminder();
            itemList.add(newItem);
        }
        trackedProjects.setItems(itemList);
    }

    /**
     * Reschedule all reminders in the model.
     */
    public void rescheduleReminder(ReminderManager reminderManager) {
        updateExpiredReminders();
        for (TrackedItem item : trackedProjects) {
            reminderManager.rescheduleReminder((Project) item);
        }
    }

    /**
     * Remove the reminder of a trackedItem.
     *
     * @param project project that contains the task with a reminder to be removed.
     */
    public void removeReminder(Project project) {
        Project newProject = project.removeReminder();
        trackedProjects.set(project, newProject);
        LOGGER.info("Reminder of project removed: " + project.getName());
    }

    /**
     * Remove the reminder of a trackedItem.
     *
     * @param project project that contains the task with a reminder to be removed.
     * @param task    task with a reminder to be removed.
     */
    public void removeReminder(Project project, Task task) {
        Project newProject = project.removeReminder(task);
        trackedProjects.set(project, newProject);
        LOGGER.info("Reminder of task of project removed: " + task.getName() + " " + project.getName());
    }

    public UniqueItemList<TrackedItem> getTrackedProjects() {
        return trackedProjects;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProjectBook // instanceof handles nulls
                && trackedProjects.equals(((ProjectBook) other).trackedProjects));
    }

    @Override
    public int hashCode() {
        return trackedProjects.hashCode();
    }
}
