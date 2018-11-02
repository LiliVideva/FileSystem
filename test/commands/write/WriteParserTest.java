package commands.write;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import fileSystem.FileSystem;
import fileSystem.VirtualFileSystem;

public class WriteParserTest {
    FileSystem fileSystem = new VirtualFileSystem();
    WriteParser writeParser = new WriteParser(null, fileSystem);

    @Test
    public void testGetArgumentsList_ReturnsEmptyList_WhenGivenEmptyString() {
        assertTrue(writeParser.getArgumentsList("").isEmpty());
    }

    @Test
    public void testGetArgumentsList_ReturnsFilledList_WhenGivenFilledString() {
        assertEquals(Arrays.asList("file.txt", "2", "Write"), writeParser.getArgumentsList("file.txt 2 Write"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExecute_ThrowsIndexOutOfBoundsException_WhenGivenNoArguments() throws Exception {
        writeParser.execute(Arrays.asList(), fileSystem);
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenInvalidNameOfFile() throws Exception {
        assertFalse(writeParser.execute(Arrays.asList("file", "1", "Write"), fileSystem));
    }

    @Test(expected = NullPointerException.class)
    public void testExecute_ThrowsNullPointerException_WhenGivenNotExistingFile() throws Exception {
        assertTrue(fileSystem.addFile(Arrays.asList("file1.txt")));
        writeParser.execute(Arrays.asList("file2.txt", "3", "This is a test."), fileSystem);
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenExisitngFile() throws Exception {
        assertTrue(fileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(writeParser.execute(Arrays.asList("file1.txt", "3", "This is a test."), fileSystem));
        assertTrue(writeParser.execute(Arrays.asList("file1.txt", "3", "Test for appending."), fileSystem));
    }

}
