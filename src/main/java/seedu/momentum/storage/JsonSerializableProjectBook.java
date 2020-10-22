package seedu.momentum.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.momentum.commons.exceptions.IllegalValueException;
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.ReadOnlyProjectBook;
import seedu.momentum.model.project.Project;

/**
 * An Immutable ProjectBook that is serializable to JSON format.
 */
@JsonRootName(value = "projectbook")
class JsonSerializableProjectBook {

    public static final String MESSAGE_DUPLICATE_PROJECT = "Projects list contains duplicate project(s).";

    private final List<JsonAdaptedProject> projects = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableProjectBook} with the given projects.
     */
    @JsonCreator
    public JsonSerializableProjectBook(@JsonProperty("projects") List<JsonAdaptedProject> projects) {
        this.projects.addAll(projects);
    }

    /**
     * Converts a given {@code ReadOnlyProjectBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableProjectBook}.
     */
    public JsonSerializableProjectBook(ReadOnlyProjectBook source) {
        projects.addAll(source.getTrackedItemList().stream().map(JsonAdaptedProject::new).collect(Collectors.toList()));
    }

    /**
     * Converts this project book into the model's {@code ProjectBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ProjectBook toModelType() throws IllegalValueException {
        ProjectBook projectBook = new ProjectBook();
        for (JsonAdaptedProject jsonAdaptedProject : projects) {
            Project project = jsonAdaptedProject.toModelType();
            if (projectBook.hasTrackedItem(project)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PROJECT);
            }
            projectBook.addTrackedItem(project);
        }
        return projectBook;
    }

}
