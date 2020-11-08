//@@author claracheong4

package seedu.momentum.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.momentum.commons.core.DateTimeWrapper;
import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.commons.core.TimeWrapper;
import seedu.momentum.commons.core.UniqueItemList;
import seedu.momentum.commons.exceptions.IllegalValueException;
import seedu.momentum.model.project.CompletionStatus;
import seedu.momentum.model.project.Deadline;
import seedu.momentum.model.project.Description;
import seedu.momentum.model.project.Name;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.reminder.Reminder;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.model.timer.TimerWrapper;
import seedu.momentum.model.timer.WorkDuration;

/**
 * Contains methods that converts a json field to a model field.
 */
public class JsonToModel {

    /**
     * Convert json description to model description.
     *
     * @param description The string representation of the description.
     * @return The description to be used in model.
     */
    protected static Description getModelDescription(String description) {
        return new Description(description);
    }

    /**
     * Convert json name to model name.
     *
     * @param name      The string representation of the name.
     * @param exception The exception message to be shown if the name is invalid.
     * @return The name to be used in model.
     * @throws IllegalValueException If the name is invalid.
     */
    protected static Name getModelName(String name, String exception) throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(exception, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(name);
    }

    /**
     * Convert json completion status to model completion status.
     *
     * @param completionStatus The boolean representation of the completion status.
     * @return The completion status to be used in model.
     */
    protected static CompletionStatus getModelCompletionStatus(boolean completionStatus) {
        if (completionStatus) {
            return new CompletionStatus().reverse();
        }
        return new CompletionStatus();
    }

    /**
     * Convert json created date to model created date.
     *
     * @param createdDate The string representation of the created date.
     * @return The created date to be used in model.
     * @throws IllegalValueException If the created date is invalid.
     */
    protected static DateWrapper getModelCreatedDate(String createdDate) throws IllegalValueException {
        if (!DateWrapper.isValid(createdDate)) {
            throw new IllegalValueException(DateWrapper.MESSAGE_CONSTRAINTS);
        }
        return new DateWrapper(createdDate);
    }

    /**
     * Convert json deadline to model deadline.
     *
     * @param deadline                The deadline in json format.
     * @param modelCreatedDateWrapper The model created date wrapper.
     * @return The deadline to be used in model.
     * @throws IllegalValueException If the deadline is invalid.
     */
    protected static Deadline getModelDeadline(JsonAdaptedDeadline deadline, DateWrapper modelCreatedDateWrapper)
            throws IllegalValueException {
        return deadline == null ? new Deadline() : deadline.toModelType(modelCreatedDateWrapper);
    }

    /**
     * Convert json deadline to model deadline.
     *
     * @param date                    The string representation of the date of the deadline.
     * @param time                    The string representation of the time of the deadline.
     * @param modelCreatedDateWrapper The model created date wrapper.
     * @return The deadline to be used in model.
     * @throws IllegalValueException If the deadline is invalid.
     */
    protected static Deadline getModelDeadline(String date, String time, DateWrapper modelCreatedDateWrapper)
            throws IllegalValueException {
        if (date == null) {
            return new Deadline();
        }
        if (!DateWrapper.isValid(date)) {
            throw new IllegalValueException(DateWrapper.MESSAGE_CONSTRAINTS);
        }

        if (time == null) {
            return new Deadline(date, modelCreatedDateWrapper);
        }
        if (!TimeWrapper.isValid(time)) {
            throw new IllegalValueException(TimeWrapper.MESSAGE_CONSTRAINTS);
        }

        return new Deadline(date, time, modelCreatedDateWrapper);
    }

    /**
     * Convert json reminder to model reminder.
     *
     * @param reminder The string representation of the reminder.
     * @return The reminder to be used in model.
     * @throws IllegalValueException If the reminder is invalid.
     */
    protected static Reminder getModelReminder(String reminder) throws IllegalValueException {
        return Reminder.recreateReminder(reminder);
    }

    /**
     * Convert json tags to model tags.
     *
     * @param tags The tags in json format.
     * @return The tags to be used in model.
     * @throws IllegalValueException If the tag list is invalid.
     */
    protected static Set<Tag> getModelTags(List<JsonAdaptedTag> tags) throws IllegalValueException {
        final List<Tag> trackedItemTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            trackedItemTags.add(tag.toModelType());
        }
        return new HashSet<>(trackedItemTags);
    }

    /**
     * Convert json tag to model tag.
     *
     * @param tagName The string representation of a tag.
     * @return The tag to be used in model.
     * @throws IllegalValueException If the tag is invalid.
     */
    protected static Tag getModelTag(String tagName) throws IllegalValueException {
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(tagName);
    }

    /**
     * Convert json timer to model timer.
     *
     * @param timer The timer in json format.
     * @return The timer to be used in model.
     * @throws IllegalValueException If the timer is invalid.
     */
    protected static TimerWrapper getModelTimerWrapper(JsonAdaptedTimer timer) throws IllegalValueException {
        return timer == null ? new TimerWrapper() : timer.toModelType();
    }

    //@@author boundtotheearth

    /**
     * Convert json dateTime to model dateTime.
     *
     * @param dateTime  The string representation of the dateTime.
     * @param exception The exception message to be shown if the name is invalid.
     * @return The dateTime to be used in model.
     * @throws IllegalValueException If the dateTime is invalid.
     */
    protected static DateTimeWrapper getModelDateTimeWrapper(String dateTime, String exception)
            throws IllegalValueException {
        if (dateTime == null) {
            throw new IllegalValueException(String.format(exception,
                    DateTimeWrapper.class.getSimpleName()));
        }

        if (!DateTimeWrapper.isValid(dateTime)) {
            throw new IllegalValueException(DateTimeWrapper.MESSAGE_CONSTRAINTS);
        }

        return new DateTimeWrapper(dateTime);
    }

    /**
     * Convert json durations to model durations.
     *
     * @param durations The work durations in json format.
     * @return The durations to be used in model.
     * @throws IllegalValueException If the duration list is invalid.
     */
    protected static UniqueItemList<WorkDuration> getModelDurations(List<JsonAdaptedWorkDuration> durations)
            throws IllegalValueException {
        final List<WorkDuration> projectDurations = new ArrayList<>();
        for (JsonAdaptedWorkDuration duration : durations) {
            projectDurations.add(duration.toModelType());
        }

        UniqueItemList<WorkDuration> modelDurations = new UniqueItemList<>();
        modelDurations.setItems(projectDurations);
        return modelDurations;
    }

    /**
     * Convert json tasks to model tasks.
     *
     * @param taskList The task list in json format.
     * @return The tasks to be used in model.
     * @throws IllegalValueException If the task list is invalid.
     */
    protected static UniqueItemList<TrackedItem> getModelTasks(List<JsonAdaptedTask> taskList)
            throws IllegalValueException {
        final List<TrackedItem> projectTasks = new ArrayList<>();
        for (JsonAdaptedTask task : taskList) {
            projectTasks.add(task.toModelType());
        }
        UniqueItemList<TrackedItem> modelTasks = new UniqueItemList<>();
        modelTasks.setItems(projectTasks);
        return modelTasks;
    }
}
