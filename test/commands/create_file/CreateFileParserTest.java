package commands.create_file;

import static org.junit.Assert.*;

import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;

import org.junit.Test;

import fileSystem.FileSystem;
import fileSystem.VirtualFileSystem;

public class CreateFileParserTest {
    FileSystem fileSystem = new VirtualFileSystem();
    CreateFileParser createFileParser = new CreateFileParser(null, fileSystem);

    @Test
    public void testGetArgumentsList_ReturnsEmptyList_WhenGivenEmptyString() {
        assertTrue(createFileParser.getArgumentsList("").isEmpty());
    }

    @Test
    public void testGetArgumentsList_ReturnsFilledList_WhenGivenFilledString() {
        assertEquals(Arrays.asList("file.txt"), createFileParser.getArgumentsList("file.txt"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExecute_ThrowsIndexOutOfBoundsException_WhenGivenNoArguments() throws Exception {
        createFileParser.execute(Arrays.asList(), fileSystem);
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenInvalidNameOfFile() throws Exception {
        assertFalse(createFileParser.execute(Arrays.asList("file"), fileSystem));
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void testExecute_ThrowsFileAlreadyExistsException_WhenGivenExistingNameOfFile() throws Exception {
        assertTrue(createFileParser.execute(Arrays.asList("file1.txt"), fileSystem));
        createFileParser.execute(Arrays.asList("file1.txt"), fileSystem);
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenDistinctNameOfFile() throws Exception {
        assertTrue(createFileParser.execute(Arrays.asList("file1.txt"), fileSystem));
        assertTrue(createFileParser.execute(Arrays.asList("file2.txt"), fileSystem));
    }
}
