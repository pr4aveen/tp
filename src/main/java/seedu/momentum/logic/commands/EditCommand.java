package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_COMPLETION_STATUS;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_DATE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_TIME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.momentum.model.Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.commons.core.Messages;
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
import seedu.momentum.model.project.UniqueTrackedItemList;
import seedu.momentum.model.reminder.Reminder;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.model.timer.UniqueDurationList;

/**
 * Edits the details of an existing project in the project book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the project identified "
            + "by the index number used in the displayed project list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_COMPLETION_STATUS + "] "
            + String.format("[%sDEADLINE_DATE [%sDEADLINE_TIME] ] ", PREFIX_DEADLINE_DATE, PREFIX_DEADLINE_TIME)
            + "[" + PREFIX_REMINDER + "REMINDER_DATE_AND_TIME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_EDIT_PROJECT_SUCCESS = "Edited Project: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists in the project book.";

    private final Index index;
    private final EditTrackedItemDescriptor editTrackedItemDescriptor;
    private final Project parentProject;

    /**
     * Create a EditCommand that edits a project.
     *
     * @param index                     of the project in the filtered project list to edit.
     * @param editTrackedItemDescriptor details to edit the project with.
     */
    public EditCommand(Index index, EditTrackedItemDescriptor editTrackedItemDescriptor) {
        requireNonNull(index);
        requireNonNull(editTrackedItemDescriptor);

        this.index = index;
        this.editTrackedItemDescriptor = new EditTrackedItemDescriptor(editTrackedItemDescriptor);
        this.parentProject = null;
    }

    /**
     * Create a EditCommand that edits a task.
     *
     * @param index                     of the project in the filtered project list to edit.
     * @param editTrackedItemDescriptor details to edit the project with.
     * @param parentProject             The parent project of the task to edit.
     */
    public EditCommand(Index index, EditTrackedItemDescriptor editTrackedItemDescriptor, Project parentProject) {
        requireNonNull(index);
        requireNonNull(editTrackedItemDescriptor);

        this.index = index;
        this.editTrackedItemDescriptor = new EditTrackedItemDescriptor(editTrackedItemDescriptor);
        this.parentProject = parentProject;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<TrackedItem> lastShownList = model.getFilteredTrackedItemList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        TrackedItem trackedItemToEdit = lastShownList.get(index.getZeroBased());
        TrackedItem editedTrackedItem = createEditedTrackedItem(trackedItemToEdit, editTrackedItemDescriptor, model);

        if (!trackedItemToEdit.isSameTrackedItem(editedTrackedItem) && model.hasTrackedItem(editedTrackedItem)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
        }

        if (model.getViewMode() == ViewMode.PROJECTS) {
            model.setTrackedItem(trackedItemToEdit, editedTrackedItem);
        } else {
            parentProject.setTask(trackedItemToEdit, editedTrackedItem);
        }

        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_TRACKED_ITEMS);
        return new CommandResult(String.format(MESSAGE_EDIT_PROJECT_SUCCESS, editedTrackedItem));
    }

    /**
     * Creates and returns a {@code Project} with the details of {@code projectToEdit}
     * edited with {@code editTrackedItemDescriptor}.
     */
    private static TrackedItem createEditedTrackedItem(TrackedItem trackedItemToEdit,
                                                       EditTrackedItemDescriptor editTrackedItemDescriptor,
                                                       Model model) throws CommandException {
        assert trackedItemToEdit != null;

        Name updatedName = editTrackedItemDescriptor.getName().orElse(trackedItemToEdit.getName());

        Description updatedDescription =
                editTrackedItemDescriptor.getDescription().orElse(trackedItemToEdit.getDescription());

        CompletionStatus updatedCompletionStatus = trackedItemToEdit.getCompletionStatus();
        if (editTrackedItemDescriptor.getCompletionStatus().isPresent()) {
            updatedCompletionStatus = updatedCompletionStatus.reverse();
        }

        DateWrapper createdDateWrapper = trackedItemToEdit.getCreatedDate();

        Deadline updatedDeadline = editTrackedItemDescriptor.getDeadline().orElse(trackedItemToEdit.getDeadline());
        if (editTrackedItemDescriptor.getDeadline().isPresent()
                && !editTrackedItemDescriptor.getDeadline().get().isEmpty()
                && Deadline.isBeforeCreatedDate(updatedDeadline.getDate().toString(), createdDateWrapper)) {
            // deadline is before created date
            // created date wrapped by LocalDate.EPOCH by default
            throw new CommandException(Deadline.CREATED_DATE_MESSAGE_CONSTRAINT); // show message constraints
        }

        Reminder updatedReminder = editTrackedItemDescriptor.getReminder().orElse(trackedItemToEdit.getReminder());

        Set<Tag> updatedTags = editTrackedItemDescriptor.getTags().orElse(trackedItemToEdit.getTags());

        UniqueDurationList durationList = new UniqueDurationList();
        durationList.setDurations(trackedItemToEdit.getDurationList());

        if (model.getViewMode() == ViewMode.PROJECTS) {
            Project projectToEdit = (Project) trackedItemToEdit;
            UniqueTrackedItemList taskList = new UniqueTrackedItemList();
            taskList.setTrackedItems(projectToEdit.getTaskList());

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

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

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
