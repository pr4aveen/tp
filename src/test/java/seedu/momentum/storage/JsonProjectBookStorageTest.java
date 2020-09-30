package seedu.momentum.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.momentum.testutil.Assert.assertThrows;
import static seedu.momentum.testutil.TypicalProjects.ALICE;
import static seedu.momentum.testutil.TypicalProjects.HOON;
import static seedu.momentum.testutil.TypicalProjects.IDA;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.momentum.commons.exceptions.DataConversionException;
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.ReadOnlyProjectBook;

public class JsonProjectBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonProjectBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readProjectBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readProjectBook(null));
    }

    private java.util.Optional<ReadOnlyProjectBook> readProjectBook(String filePath) throws Exception {
        return new JsonProjectBookStorage(Paths.get(filePath)).readProjectBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readProjectBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readProjectBook("notJsonFormatProjectBook.json"));
    }

    //    @Test
    //    public void readProjectBook_invalidProjectProjectBook_throwDataConversionException() {
    //        assertThrows(DataConversionException.class, () -> readProjectBook("invalidProjectProjectBook.json"));
    //    }
    //
    //    @Test
    //    public void readProjectBook_invalidAndValidProjectProjectBook_throwDataConversionException() {
    //        assertThrows(DataConversionException.class,
    //              () -> readProjectBook("invalidAndValidProjectProjectBook.json"));
    //    }

    @Test
    public void readAndSaveProjectBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempProjectBook.json");
        ProjectBook original = getTypicalProjectBook();
        JsonProjectBookStorage jsonProjectBookStorage = new JsonProjectBookStorage(filePath);

        // Save in new file and read back
        jsonProjectBookStorage.saveProjectBook(original, filePath);
        ReadOnlyProjectBook readBack = jsonProjectBookStorage.readProjectBook(filePath).get();
        assertEquals(original, new ProjectBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addProject(HOON);
        original.renameProject(ALICE);
        jsonProjectBookStorage.saveProjectBook(original, filePath);
        readBack = jsonProjectBookStorage.readProjectBook(filePath).get();
        assertEquals(original, new ProjectBook(readBack));

        // Save and read without specifying file path
        original.addProject(IDA);
        jsonProjectBookStorage.saveProjectBook(original); // file path not specified
        readBack = jsonProjectBookStorage.readProjectBook().get(); // file path not specified
        assertEquals(original, new ProjectBook(readBack));

    }

    @Test
    public void saveProjectBook_nullProjectBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveProjectBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code projectBook} at the specified {@code filePath}.
     */
    private void saveProjectBook(ReadOnlyProjectBook projectBook, String filePath) {
        try {
            new JsonProjectBookStorage(Paths.get(filePath))
                    .saveProjectBook(projectBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveProjectBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveProjectBook(new ProjectBook(), null));
    }
}
