package commands.remove;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import fileSystem.FileSystem;
import fileSystem.VirtualFileSystem;

public class RemoveParserTest {
    FileSystem fileSystem = new VirtualFileSystem();
    RemoveParser removeParser = new RemoveParser(null, fileSystem);

    @Test
    public void testGetArgumentsList_ReturnsEmptyList_WhenGivenEmptyString() {
        assertTrue(removeParser.getArgumentsList("").isEmpty());
    }

    @Test
    public void testGetArgumentsList_ReturnsFilledList_WhenGivenStringWithCorrectNumberOfArguments() {
        assertEquals(Arrays.asList("file.txt", "1", "4"), removeParser.getArgumentsList("file.txt 1-4"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExecute_ReturnsFalse_WhenGivenNoArguments() throws Exception {
        removeParser.execute(Arrays.asList(), fileSystem);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExecute_ThrowsIndexOutOfBoundsException_WhenGivenInvalidNumberOfArguments() throws Exception {
        assertFalse(removeParser.execute(Arrays.asList("file", "1"), fileSystem));
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenInvalidNameOfFile() throws Exception {
        assertFalse(removeParser.execute(Arrays.asList("file", "1", "2"), fileSystem));
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenInvalidBeginLineNumber() throws Exception {
        assertFalse(removeParser.execute(Arrays.asList("file", "-1", "2"), fileSystem));
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenInvalidEndLineNumber() throws Exception {
        assertFalse(removeParser.execute(Arrays.asList("file", "1", "0"), fileSystem));
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenInvalidLineNumbers() throws Exception {
        assertFalse(removeParser.execute(Arrays.asList("file", "-5", "-2"), fileSystem));
    }

    @Test(expected = NullPointerException.class)
    public void testExecute_ThrowsNullPointerException_WhenGivenNotExistingFile() throws Exception {
        assertTrue(fileSystem.addFile(Arrays.asList("file1.txt")));
        removeParser.execute(Arrays.asList("file2.txt", "1", "5"), fileSystem);
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenExistingFile() throws Exception {
        assertTrue(fileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(fileSystem.writeFileContent(Arrays.asList("file1.txt", "3", "This is a test."), false));
        assertTrue(fileSystem.writeFileContent(Arrays.asList("file1.txt", "5", "Test for deleting."), false));
        assertTrue(removeParser.execute(Arrays.asList("file1.txt", "1", "4"), fileSystem));
    }

}
