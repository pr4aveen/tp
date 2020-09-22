package seedu.momentum.testutil;

import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ProjectBook ab = new ProjectBookBuilder().withPerson("John", "Doe").build();}
 */
public class ProjectBookBuilder {

    private ProjectBook projectBook;

    public ProjectBookBuilder() {
        projectBook = new ProjectBook();
    }

    public ProjectBookBuilder(ProjectBook projectBook) {
        this.projectBook = projectBook;
    }

    /**
     * Adds a new {@code Person} to the {@code ProjectBook} that we are building.
     */
    public ProjectBookBuilder withPerson(Person person) {
        projectBook.addPerson(person);
        return this;
    }

    public ProjectBook build() {
        return projectBook;
    }
}
