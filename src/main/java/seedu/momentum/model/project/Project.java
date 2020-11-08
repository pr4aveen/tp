package seedu.momentum.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import javafx.collections.ObservableList;
import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.commons.core.UniqueItem;
import seedu.momentum.commons.core.UniqueItemList;
import seedu.momentum.model.reminder.Reminder;
import seedu.momentum.model.reminder.ReminderManager;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.model.timer.TimerWrapper;
import seedu.momentum.model.timer.WorkDuration;

/**
 * Represents a Project in the project book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Project extends TrackedItem implements UniqueItem<TrackedItem> {

    private UniqueItemList<TrackedItem> taskList;

    /**
     * Constructs a {@code Project}.
     *
     * @param name               A valid name.
     * @param description        A description of the project.
     * @param completionStatus   A completion status of the project.
     * @param createdDateWrapper A dateWrapper associated with the creation of the project.
     * @param deadline           A deadline associated with the project.
     * @param reminder           A reminder associated with the tracked item.
     * @param tags               A set of tags associated to the project.
     * @param durations          A list of durations spent working on the project.
     * @param timerWrapper       A timerWrapper associated with the project.
     * @param taskList           UniqueItemList associated with the project.
     */
    public Project(Name name, Description description, CompletionStatus completionStatus,
                   DateWrapper createdDateWrapper, Deadline deadline, Reminder reminder, Set<Tag> tags,
                   UniqueItemList<WorkDuration> durations, TimerWrapper timerWrapper,
                   UniqueItemList<TrackedItem> taskList) {
        super(name, description, completionStatus, createdDateWrapper, deadline, reminder, tags, durations,
                timerWrapper);
        this.taskList = taskList;
    }

    /**
     * Constructs a {@code Project}.
     *
     * @param name               A valid name.
     * @param description        A description of the project.
     * @param completionStatus   A completion status of the project.
     * @param createdDateWrapper A dateWrapper associated with the creation of the project.
     * @param deadline           A deadline associated with the project.
     * @param reminder           A reminder associated with the tracked item.
     * @param tags               A set of tags associated to the project.
     * @param durations          A list of durations spent working on the project.
     * @param timerWrapper       A timerWrapper associated with the project.
     */
    public Project(Name name, Description description, CompletionStatus completionStatus,
                   DateWrapper createdDateWrapper, Deadline deadline, Reminder reminder, Set<Tag> tags,
                   UniqueItemList<WorkDuration> durations, TimerWrapper timerWrapper) {
        super(name, description, completionStatus, createdDateWrapper, deadline, reminder, tags, durations,
                timerWrapper);
        taskList = new UniqueItemList<>();
    }

    /**
     * Constructs a new {@code Project}
     *
     * @param name               A valid name.
     * @param completionStatus   A completion status of the project.
     * @param createdDateWrapper A dateWrapper associated with the creation of the project
     * @param deadline           A deadline associated with the project.
     * @param reminder           A reminder associated with the tracked item.
     * @param description        A description of the project.
     * @param tags               A set of tags associated to the project.
     */
    public Project(Name name, Description description, CompletionStatus completionStatus,
                   DateWrapper createdDateWrapper, Deadline deadline, Reminder reminder, Set<Tag> tags) {
        super(name, description, completionStatus, createdDateWrapper, deadline, reminder, tags);
        taskList = new UniqueItemList<>();
    }

    //@@author boundtotheearth
    /**
     * Returns a copy of this project with its timerWrapper started.
     *
     * @return A copy of this project, but with its timerWrapper started.
     */
    @Override
    public Project startTimer() {
        LOGGER.info("Started Timer For: " + name.fullName);
        TimerWrapper newTimerWrapper = timerWrapper.start();
        return new Project(name, description, completionStatus, createdDateWrapper, deadline, reminder, tags, durations,
                newTimerWrapper, taskList);
    }

    /**
     * Returns a copy of this project with its timerWrapper stopped, then adds the timed duration into
     * the list.
     *
     * @return A copy of this project, but with its timerWrapper stopped.
     */
    @Override
    public Project stopTimer() {
        LOGGER.info("Stopped Timer For: " + name.fullName);
        TimerWrapper newTimerWrapper = timerWrapper.stop();
        WorkDuration duration = new WorkDuration(newTimerWrapper.getStartTime(), newTimerWrapper.getStopTime());
        UniqueItemList<WorkDuration> newDurations = durations.copy();
        newDurations.add(duration);
        return new Project(name, description, completionStatus, createdDateWrapper, deadline, reminder, tags,
                newDurations, newTimerWrapper, taskList);
    }

    //@@author kkangs0226
    /**
     * Adds a task in the {@code Project}'s {@code UniqueTrackedItemList}.
     *
     * @param task Task to be added.
     * @return A copy of this project, but with task added.
     */
    public Project addTask(TrackedItem task) {
        requireNonNull(task);
        LOGGER.info(String.format("Added a Task: %s, To: %s", task, this));
        UniqueItemList<TrackedItem> newList = taskList.copy();
        newList.add(task);
        return new Project(name, description, completionStatus, createdDateWrapper, deadline, reminder,
                tags, durations, timerWrapper, newList);
    }

    /**
     * Deletes a task in the {@code Project}'s {@code UniqueTrackedItemList}.
     *
     * @param task Task to be deleted.
     * @return A copy of this project, but with task removed.
     */
    public Project deleteTask(TrackedItem task) {
        requireNonNull(task);
        LOGGER.info(String.format("Deleted a Task: %s, From: %s", task, this));
        UniqueItemList<TrackedItem> newList = taskList.copy();
        newList.remove(task);
        return new Project(name, description, completionStatus, createdDateWrapper, deadline, reminder,
            tags, durations, timerWrapper, newList);
    }

    /**
     * Edits a task is in the {@code Project}'s {@code UniqueTrackedItemList} and returns new Project.
     *
     * @param target     Task to be replaced.
     * @param editedTask Task to replace the original task with.
     * @return A new copy of the project with the task replaced.
     */
    public Project setTask(TrackedItem target, TrackedItem editedTask) {
        requireAllNonNull(target, editedTask);
        LOGGER.info(String.format("Replaced Task: %s, With: %s, In: %s", target, editedTask, this));
        UniqueItemList<TrackedItem> newList = taskList.copy();
        newList.set(target, editedTask);
        return new Project(name, description, completionStatus, createdDateWrapper, deadline, reminder,
            tags, durations, timerWrapper, newList);
    }

    //@@author pr4aveen
    /**
     * Checks whether a task is in the {@code Project}'s {@code UniqueTrackedItemList}.
     *
     * @param task Task that needs to be checked.
     * @return True if the task exists in the project, false otherwise.
     */
    public boolean hasTask(TrackedItem task) {
        requireNonNull(task);
        return taskList.contains(task);
    }



    //@@author boundtotheearth
    /**
     * Removes all tasks belonging to this project.
     *
     * @return A new copy of the project with all tasks deleted.
     */
    public Project clearTasks() {
        UniqueItemList<TrackedItem> newList = new UniqueItemList<>();
        LOGGER.info(String.format("Cleared All Tasks In: %s", this));
        return new Project(name, description, completionStatus, createdDateWrapper, deadline, reminder,
                tags, durations, timerWrapper, newList);
    }
    //@@author

    public ObservableList<TrackedItem> getTaskList() {
        return taskList.asUnmodifiableObservableList();
    }

    /**
     * Returns true if both tracked item of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two projects.
     *
     * @param otherTrackedItem Item to check against.
     * @return True if the both tracked items are the same, false otherwise.
     */
    @Override
    public boolean isSameAs(TrackedItem otherTrackedItem) {
        if (!(otherTrackedItem instanceof Project)) {
            return false;
        }

        return super.isSameAs(otherTrackedItem);
    }

    //@@author claracheong4
    /**
     * Reschedule all reminders in the task list.
     *
     * @param reminderManager The reminder manager associated with the model of the project.
     */
    public void rescheduleReminder(ReminderManager reminderManager) {
        for (TrackedItem task : taskList) {
            reminderManager.rescheduleReminder(this, (Task) task);
        }
    }

    @Override
    public Project removeReminder() {
        Reminder newReminder = this.reminder.remove();
        return new Project(name, description, completionStatus, createdDateWrapper, deadline, newReminder, tags,
                durations, timerWrapper, taskList);
    }

    /**
     * Remove the reminder of a trackedItem.
     *
     * @param task The task to remove the reminder from.
     * @return A new copy of the project with the reminder removed from the task.
     */
    public Project removeReminder(Task task) {
        Task newTask = task.removeReminder();
        taskList.set(task, newTask);
        return new Project(name, description, completionStatus, createdDateWrapper, deadline, reminder, tags, durations,
                timerWrapper, taskList);
    }

    @Override
    public Project updateExpiredReminder() {
        Reminder newReminder = reminder.updateExpired();

        UniqueItemList<TrackedItem> newTaskList = new UniqueItemList<>();
        for (TrackedItem item : taskList) {
            TrackedItem newItem = item.updateExpiredReminder();
            newTaskList.add(newItem);
        }

        return new Project(name, description, completionStatus, createdDateWrapper, deadline, newReminder, tags,
                durations, timerWrapper, newTaskList);
    }

    //@@author pr4aveen
    /**
     * Checks whether the instance is a Task.
     *
     * @return True if the instance is a Task. Returns false otherwise
     */
    @Override
    public boolean isTask() {
        return false;
    }

    /**
     * Returns true if both tracked items have the same identity and data fields.
     * This defines a stronger notion of equality between two tracked items.
     *
     * @param other Other object to check against.
     * @return True if the object is the same as this project, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Project)) {
            return false;
        }

        return super.equals(other)
                && this.getTaskList().equals(((Project) other).getTaskList());
    }
}
