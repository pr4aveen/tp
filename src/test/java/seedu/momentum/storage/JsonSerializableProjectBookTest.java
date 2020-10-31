package seedu.momentum.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.momentum.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.exceptions.IllegalValueException;
import seedu.momentum.commons.util.JsonUtil;
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.project.SortType;
import seedu.momentum.testutil.TypicalProjects;

public class JsonSerializableProjectBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableProjectBookTest");
    private static final Path TYPICAL_PROJECT_FILE = TEST_DATA_FOLDER.resolve("typicalProjectsProjectBook.json");
    private static final Path DUPLICATE_PROJECT_FILE = TEST_DATA_FOLDER.resolve("duplicateProjectProjectBook.json");

    @Test
    public void toModelType_typicalProjectsFile_success() throws Exception {
        JsonSerializableProjectBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PROJECT_FILE,
                JsonSerializableProjectBook.class).get();
        ProjectBook projectBookFromFile = dataFromFile.toModelType();
        ProjectBook typicalProjectsProjectBook = TypicalProjects.getTypicalProjectBook();
        assertEquals(projectBookFromFile, typicalProjectsProjectBook);
    }

    //    @Test
    //    public void toModelType_invalidProjectFile_throwsIllegalValueException() throws Exception {
    //        JsonSerializableProjectBook dataFromFile = JsonUtil.readJsonFile(INVALID_PROJECT_FILE,
    //                JsonSerializableProjectBook.class).get();
    //        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    //    }

    @Test
    public void toModelType_duplicateProjects_throwsIllegalValueException() throws Exception {
        JsonSerializableProjectBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PROJECT_FILE,
                JsonSerializableProjectBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableProjectBook.MESSAGE_DUPLICATE_PROJECT,
                dataFromFile::toModelType);
    }

}
