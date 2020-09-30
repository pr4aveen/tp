package seedu.momentum.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.momentum.commons.exceptions.DataConversionException;
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.ReadOnlyProjectBook;

/**
 * Represents a storage for {@link ProjectBook}.
 */
public interface ProjectBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getProjectBookFilePath();

    /**
     * Returns ProjectBook data as a {@link ReadOnlyProjectBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyProjectBook> readProjectBook() throws DataConversionException, IOException;

    /**
     * @see #getProjectBookFilePath()
     */
    Optional<ReadOnlyProjectBook> readProjectBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyProjectBook} to the storage.
     * @param projectBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveProjectBook(ReadOnlyProjectBook projectBook) throws IOException;

    /**
     * @see #saveProjectBook(ReadOnlyProjectBook)
     */
    void saveProjectBook(ReadOnlyProjectBook projectBook, Path filePath) throws IOException;

}
