package seedu.momentum.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.momentum.commons.exceptions.DataConversionException;
import seedu.momentum.model.ReadOnlyProjectBook;
import seedu.momentum.model.ReadOnlyUserPrefs;
import seedu.momentum.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ProjectBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getProjectBookFilePath();

    @Override
    Optional<ReadOnlyProjectBook> readProjectBook() throws DataConversionException, IOException;

    @Override
    void saveProjectBook(ReadOnlyProjectBook projectBook) throws IOException;

}
