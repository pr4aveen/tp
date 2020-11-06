//@@author pr4aveen

package seedu.momentum.logic.commands;

import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_COMPLETION_STATUS;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_DATE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_TIME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.commons.core.UniqueItemList;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.commons.util.CollectionUtil;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.ViewMode;
import seedu.momentum.model.project.CompletionStatus;
import seedu.momentum.model.project.Deadline;
import seedu.momentum.model.project.Description;
import seedu.momentum.model.project.Name;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.Task;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.reminder.Reminder;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.model.timer.WorkDuration;

/**
 * Represents a command that edits the details of an existing item in Momentum.
 */
public abstract class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + "INDEX "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_COMPLETION_STATUS + "] "
            + String.format("[%sDEADLINE_DATE [%sDEADLINE_TIME] ] ", PREFIX_DEADLINE_DATE, PREFIX_DEADLINE_TIME)
            + "[" + PREFIX_REMINDER + "REMINDER_DATE_AND_TIME] "
            + "[" + PREFIX_TAG + "TAG]...";

    public static final String MESSAGE_EDIT_PROJECT_SUCCESS = "Edited Project: %1$s";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists in the project book.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the project book.";


    protected final Index index;
    protected final EditTrackedItemDescriptor editTrackedItemDescriptor;

    /**
     * Create a EditCommand that edits an item.
     *
     * @param index                     Of the item in the model to edit.
     * @param editTrackedItemDescriptor The new details of the item.
     */
    public EditCommand(Index index, EditTrackedItemDescriptor editTrackedItemDescriptor) {
        requireAllNonNull(index, editTrackedItemDescriptor);
        this.index = index;
        this.editTrackedItemDescriptor = new EditTrackedItemDescriptor(editTrackedItemDescriptor);
    }

    /**
     * Edits an item in the provided model.
     *
     * @param model {@code Model} containing the item to edit.
     * @return Feedback message of editing result, for display.
     * @throws CommandException If an error occurs during editing process.
     */
    @Override
    public abstract CommandResult execute(Model model) throws CommandException;
    
    protected static Deadline getUpdatedDeadline(TrackedItem trackedItemToEdit,
            EditTrackedItemDescriptor editTrackedItemDescriptor, DateWrapper createdDateWrapper) throws CommandException{
        Optional<Deadline> editedDeadline = editTrackedItemDescriptor.getDeadline();
        Deadline updatedDeadline = editedDeadline.orElse(trackedItemToEdit.getDeadline());
        // deadline is before created date
        // created date wrapped by LocalDate.EPOCH by default
        if (editedDeadline.isPresent()
                && !editedDeadline.get().isEmpty()
                && Deadline.isBeforeCreatedDate(updatedDeadline.getDate().toString(), createdDateWrapper)) {
            throw new CommandException(Deadline.CREATED_DATE_MESSAGE_CONSTRAINT); // show message constraints
        }
        return updatedDeadline;
    }

    //@@author
    /**
     * Creates and returns a {@code Project} or {@code Task} with the details of {@code trackedItemToEdit}
     * edited with {@code editTrackedItemDescriptor}.
     *
     * @param trackedItemToEdit The original item to edit.
     * @param editTrackedItemDescriptor The new details of the item.
     * @param model Provides context under which the item is being edited.
     */
    protected static TrackedItem createEditedTrackedItem(TrackedItem trackedItemToEdit,
            EditTrackedItemDescriptor editTrackedItemDescriptor, Model model) throws CommandException {
        // Name
        Name updatedName = editTrackedItemDescriptor.getName().orElse(trackedItemToEdit.getName());

        //@@author kkangs0226
        // Description
        Description updatedDescription =
                editTrackedItemDescriptor.getDescription().orElse(trackedItemToEdit.getDescription());

        //@@author claracheong4
        // Completion Status
        CompletionStatus updatedCompletionStatus = trackedItemToEdit.getCompletionStatus();
        if (editTrackedItemDescriptor.getCompletionStatus().isPresent()) {
            updatedCompletionStatus = updatedCompletionStatus.reverse();
        }

        // Created Date
        DateWrapper createdDateWrapper = trackedItemToEdit.getCreatedDate();

        // Deadline
        Deadline updatedDeadline = getUpdatedDeadline(trackedItemToEdit, editTrackedItemDescriptor, createdDateWrapper);

        // Reminder
        Reminder updatedReminder = editTrackedItemDescriptor.getReminder().orElse(trackedItemToEdit.getReminder());
        //@@author

        // Tags
        Set<Tag> updatedTags = editTrackedItemDescriptor.getTags().orElse(trackedItemToEdit.getTags());

        // WorkDurations
        UniqueItemList<WorkDuration> durationList = new UniqueItemList<>();
        durationList.setItems(trackedItemToEdit.getDurationList());

        // Return Project or Task depending on view mode
        if (model.getViewMode() == ViewMode.PROJECTS) {
            Project projectToEdit = (Project) trackedItemToEdit;
            UniqueItemList<TrackedItem> taskList = new UniqueItemList<>();
            taskList.setItems(projectToEdit.getTaskList());

            return new Project(updatedName, updatedDescription, updatedCompletionStatus, createdDateWrapper,
                    updatedDeadline, updatedReminder, updatedTags, durationList, trackedItemToEdit.getTimer(),
                    taskList);
        } else {
            return new Task(updatedName, updatedDescription, updatedCompletionStatus, createdDateWrapper,
                    updatedDeadline, updatedReminder, updatedTags, durationList, trackedItemToEdit.getTimer());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editTrackedItemDescriptor.equals(e.editTrackedItemDescriptor);
    }

    //@@author
    /**
     * Stores the details to edit the tracked item with. Each non-empty field value will replace the
     * corresponding field value of the tracked item.
     */
    public static class EditTrackedItemDescriptor {
        private Name name;
        private Description description;
        private CompletionStatus completionStatus;
        private Deadline deadline;
        private Reminder reminder;
        private Set<Tag> tags;

        public EditTrackedItemDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTrackedItemDescriptor(EditTrackedItemDescriptor toCopy) {
            setName(toCopy.name);
            setDescription(toCopy.description);
            setCompletionStatus(toCopy.completionStatus);
            setDeadline(toCopy.deadline);
            setReminder(toCopy.reminder);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, description, completionStatus, deadline, reminder, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }
        //@@author kkangs0226
        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }
        //@@author claracheong4
        public void setCompletionStatus(CompletionStatus completionStatus) {
            this.completionStatus = completionStatus;
        }

        public Optional<CompletionStatus> getCompletionStatus() {
            return Optional.ofNullable(completionStatus);
        }

        public void setDeadline(Deadline deadline) {
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return Optional.ofNullable(deadline);
        }

        public void setReminder(Reminder reminder) {
            this.reminder = reminder;
        }

        public Optional<Reminder> getReminder() {
            return Optional.ofNullable(reminder);
        }
        //@@author
        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTrackedItemDescriptor)) {
                return false;
            }

            // state check
            EditTrackedItemDescriptor e = (EditTrackedItemDescriptor) other;

            return getName().equals(e.getName())
                    && getDescription().equals(e.getDescription())
                    && getCompletionStatus().equals(e.getCompletionStatus())
                    && getDeadline().equals(e.getDeadline())
                    && getReminder().equals(e.getReminder())
                    && getTags().equals(e.getTags());
        }
    }
}
