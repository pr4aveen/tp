package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        Name name = new Name("name");
        // same object -> returns true
        assertTrue(name.equals(name));

        // same name -> returns true
        assertTrue(name.equals(new Name("name")));

        // different types -> returns false
        assertFalse(name.equals("1"));

        // null -> return false
        assertFalse(name.equals(null));

        // different name -> return false
        assertFalse(name.equals("NAME"));
        assertFalse(name.equals("name"));
        assertFalse(name.equals("gqwrf"));
    }

    @Test
    public void compareTo_returnsCorrectValue() {

        Name aaa = new Name("AAA");
        Name bbb = new Name("bbb");
        Name aaaLower = new Name("aaa");

        // second name alphabetically later
        assertTrue(aaa.compareTo(bbb) < 0);

        // second name alphabetically earlier
        assertTrue(bbb.compareTo(aaa) > 0);

        // names are same alphabetically
        assertTrue(aaa.compareTo(aaaLower) == 0);

    }
}
