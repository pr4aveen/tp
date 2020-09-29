package seedu.momentum.testutil;

import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.project.Project;

/**
 * A utility class containing a list of {@code Project} objects to be used in tests.
 */
public class TypicalProjects {

    public static final Project ALICE = new ProjectBuilder().withName("Alice Pauline")
            .withDescription("Likes coding")
            .withTags("friends").build();
    public static final Project BENSON = new ProjectBuilder().withName("Benson Meier")
            .withDescription("Likes dogs")
            .withTags("owesMoney", "friends")
            .withDurations(TypicalWorkDuration.DURATION_ONE_DAY)
            .withTimer(TypicalTimers.DAY).build();
    public static final Project CARL = new ProjectBuilder().withName("Carl Kurz")
            .withDescription("Likes poodles").build();
    public static final Project DANIEL = new ProjectBuilder().withName("Daniel Meier")
            .withDescription("Likes cats").withTags("friends").build();
    public static final Project ELLE = new ProjectBuilder().withName("Elle Meyer")
            .withDescription("Likes elephants").build();
    public static final Project FIONA = new ProjectBuilder().withName("Fiona Kunz")
            .withDescription("Likes starbucks").build();
    public static final Project GEORGE = new ProjectBuilder().withName("George Best")
            .withDescription("Likes you").build();

    // Manually added
    public static final Project HOON = new ProjectBuilder().withName("Hoon Meier")
            .build();
    public static final Project IDA = new ProjectBuilder().withName("Ida Mueller")
            .build();

    // Manually added - Project's details found in {@code CommandTestUtil}
    public static final Project AMY = new ProjectBuilder().withName(VALID_NAME_AMY)
            .withDescription(VALID_DESCRIPTION_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Project BOB = new ProjectBuilder().withName(VALID_NAME_BOB)
            .withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalProjects() {
    } // prevents instantiation

    /**
     * Returns an {@code ProjectBook} with all the typical projects.
     */
    public static ProjectBook getTypicalProjectBook() {
        ProjectBook projectBook = new ProjectBook();
        for (Project project : getTypicalProjects()) {
            projectBook.addProject(project);
        }
        return projectBook;
    }

    public static List<Project> getTypicalProjects() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
