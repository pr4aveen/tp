package seedu.momentum.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.ReadOnlyProjectBook;
import seedu.momentum.model.project.TrackedItem;

public class SampleDataUtilTest {
    @Test
    public void getSampleProjectBook() {
        ReadOnlyProjectBook actualProjectBook = SampleDataUtil.getSampleProjectBook();
        ProjectBook expectedProjectBook = new ProjectBook();
        for (TrackedItem item : SampleDataUtil.getSampleProjects()) {
            expectedProjectBook.addTrackedItem(item);
        }
        assertEquals(new ProjectBook(actualProjectBook), actualProjectBook);
    }
}
