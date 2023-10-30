package unicash.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileUtilTest {

    @TempDir
    public Path temporaryFolder;

    @Test
    public void isValidPath() {
        // valid path
        assertTrue(FileUtil.isValidPath("valid/file/path"));

        // invalid path
        assertFalse(FileUtil.isValidPath("a\0"));

        // null path -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));
    }

    @Test
    public void isFileExists_nonExistentFile_returnsFalse() {
        assertFalse(FileUtil.isFileExists(Path.of("invalid")));
    }

    @Test
    public void createFile_existingFile_earlyReturns() throws IOException {
        var path = temporaryFolder.resolve("tempfile");
        Files.createFile(path);

        FileUtil.createFile(path);
    }

}
