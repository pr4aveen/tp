package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_DATE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_TIME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.momentum.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.momentum.commons.core.Date;
import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.commons.util.CollectionUtil;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.Deadline;
import seedu.momentum.model.project.Description;
import seedu.momentum.model.project.Name;
import seedu.momentum.model.project.Project;
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
            + String.format("[%sDEADLINE_DATE [%sDEADLINE_TIME] ] ", PREFIX_DEADLINE_DATE, PREFIX_DEADLINE_TIME)
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_EDIT_PROJECT_SUCCESS = "Edited Project: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists in the project book.";

    private final Index index;
    private final EditProjectDescriptor editProjectDescriptor;

    /**
     * @param index                 of the project in the filtered project list to edit
     * @param editProjectDescriptor details to edit the project with
     */
    public EditCommand(Index index, EditProjectDescriptor editProjectDescriptor) {
        requireNonNull(index);
        requireNonNull(editProjectDescriptor);

        this.index = index;
        this.editProjectDescriptor = new EditProjectDescriptor(editProjectDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws ParseException, CommandException {
        requireNonNull(model);
        List<Project> lastShownList = model.getFilteredProjectList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToEdit = lastShownList.get(index.getZeroBased());
        Project editedProject = createEditedProject(projectToEdit, editProjectDescriptor);

        if (!projectToEdit.isSameProject(editedProject) && model.hasProject(editedProject)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
        }

        model.setProject(projectToEdit, editedProject);
        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_EDIT_PROJECT_SUCCESS, editedProject));
    }

    /**
     * Creates and returns a {@code Project} with the details of {@code projectToEdit}
     * edited with {@code editProjectDescriptor}.
     */
    private static Project createEditedProject(Project projectToEdit,
                                               EditProjectDescriptor editProjectDescriptor) throws ParseException {
        assert projectToEdit != null;

        Name updatedName = editProjectDescriptor.getName().orElse(projectToEdit.getName());
        Description updatedDescription = editProjectDescriptor.getDescription().orElse(projectToEdit.getDescription());
        Date createdDate = projectToEdit.getCreatedDate();
        Deadline updatedDeadline = editProjectDescriptor.getDeadline().orElse(projectToEdit.getDeadline());
        if (editProjectDescriptor.getDeadline().isPresent() 
                && !Deadline.isBeforeCreatedDate(updatedDeadline.getDate().toString(), createdDate)) {
            // deadline is before created date
            // createdDate was 0001-01-01 by default
            throw new ParseException(Deadline.CREATED_DATE_MESSAGE_CONSTRAINT); // show message constraints
        }
        Set<Tag> updatedTags = editProjectDescriptor.getTags().orElse(projectToEdit.getTags());
        UniqueDurationList durationList = new UniqueDurationList();
        durationList.setDurations(projectToEdit.getDurationList());

        return new Project(updatedName, updatedDescription, createdDate, updatedDeadline, updatedTags,
                durationList, projectToEdit.getTimer());
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
                && editProjectDescriptor.equals(e.editProjectDescriptor);
    }

    /**
     * Stores the details to edit the project with. Each non-empty field value will replace the
     * corresponding field value of the project.
     */
    public static class EditProjectDescriptor {
        private Name name;
        private Description description;
        private Deadline deadline;
        private Set<Tag> tags;

        public EditProjectDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditProjectDescriptor(EditProjectDescriptor toCopy) {
            setName(toCopy.name);
            setDescription(toCopy.description);
            setDeadline(toCopy.deadline);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, description, deadline, tags);
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

        public void setDeadline(Deadline deadline) {
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return Optional.ofNullable(deadline);
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
            if (!(other instanceof EditProjectDescriptor)) {
                return false;
            }

            // state check
            EditProjectDescriptor e = (EditProjectDescriptor) other;

            return getName().equals(e.getName())
                    && getDescription().equals(e.getDescription())
                    && getDeadline().equals(e.getDeadline())
                    && getTags().equals(e.getTags());
        }
    }
}
