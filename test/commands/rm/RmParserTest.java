package commands.rm;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import fileSystem.FileSystem;
import fileSystem.VirtualFileSystem;

public class RmParserTest {
    FileSystem fileSystem = new VirtualFileSystem();
    RmParser rmParser = new RmParser(null, fileSystem);

    @Test
    public void testGetArgumentsList_ReturnsEmptyList_WhenGivenEmptyString() {
        assertTrue(rmParser.getArgumentsList("").isEmpty());
    }

    @Test
    public void testGetArgumentsList_ReturnsFilledList_WhenGivenFilledString() {
        assertEquals(Arrays.asList("file.txt"), rmParser.getArgumentsList("file.txt"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExecute_ThrowsIndexOutOfBoundsException_WhenGivenNoArguments() throws Exception {
        rmParser.execute(Arrays.asList(), fileSystem);
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenInvalidNameOfFile() throws Exception {
        assertFalse(rmParser.execute(Arrays.asList("file"), fileSystem));
    }

    @Test(expected = NullPointerException.class)
    public void testExecute_ThrowsNullPointerException_WhenGivenNotExistingNameOfFile() throws Exception {
        assertTrue(fileSystem.addFile(Arrays.asList("file1.txt")));
        rmParser.execute(Arrays.asList("file2.txt"), fileSystem);
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenExistingNameOfFile() throws Exception {
        assertTrue(fileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(rmParser.execute(Arrays.asList("file1.txt"), fileSystem));
    }

}
