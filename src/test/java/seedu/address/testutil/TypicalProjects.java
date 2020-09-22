package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.project.Project;

/**
 * A utility class containing a list of {@code Project} objects to be used in tests.
 */
public class TypicalProjects {

    public static final Project ALICE = new ProjectBuilder().withName("Alice Pauline")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Project BENSON = new ProjectBuilder().withName("Benson Meier")
            .withPhone("98765432").withTags("owesMoney", "friends").build();
    public static final Project CARL = new ProjectBuilder().withName("Carl Kurz").withPhone("95352563").build();
    public static final Project DANIEL = new ProjectBuilder().withName("Daniel Meier").withPhone("87652533")
            .withTags("friends").build();
    public static final Project ELLE = new ProjectBuilder().withName("Elle Meyer").withPhone("9482224")
            .build();
    public static final Project FIONA = new ProjectBuilder().withName("Fiona Kunz").withPhone("9482427").build();
    public static final Project GEORGE = new ProjectBuilder().withName("George Best").withPhone("9482442").build();

    // Manually added
    public static final Project HOON = new ProjectBuilder().withName("Hoon Meier").withPhone("8482424")
            .build();
    public static final Project IDA = new ProjectBuilder().withName("Ida Mueller").withPhone("8482131")
            .build();

    // Manually added - Project's details found in {@code CommandTestUtil}
    public static final Project AMY = new ProjectBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Project BOB = new ProjectBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalProjects() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Project project : getTypicalProjects()) {
            ab.addProject(project);
        }
        return ab;
    }

    public static List<Project> getTypicalProjects() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
