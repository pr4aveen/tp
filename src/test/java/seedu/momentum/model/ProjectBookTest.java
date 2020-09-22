package seedu.momentum.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.momentum.testutil.Assert.assertThrows;
import static seedu.momentum.testutil.TypicalPersons.ALICE;
import static seedu.momentum.testutil.TypicalPersons.getTypicalProjectBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.momentum.model.person.Person;
import seedu.momentum.model.person.exceptions.DuplicatePersonException;
import seedu.momentum.testutil.PersonBuilder;

public class ProjectBookTest {

    private final ProjectBook projectBook = new ProjectBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), projectBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> projectBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyProjectBook_replacesData() {
        ProjectBook newData = getTypicalProjectBook();
        projectBook.resetData(newData);
        assertEquals(newData, projectBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        ProjectBookStub newData = new ProjectBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> projectBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> projectBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInProjectBook_returnsFalse() {
        assertFalse(projectBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInProjectBook_returnsTrue() {
        projectBook.addPerson(ALICE);
        assertTrue(projectBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInProjectBook_returnsTrue() {
        projectBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(projectBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> projectBook.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyProjectBook whose persons list can violate interface constraints.
     */
    private static class ProjectBookStub implements ReadOnlyProjectBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        ProjectBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
