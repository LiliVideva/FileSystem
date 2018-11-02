package commands.write;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import fileSystem.FileSystem;
import fileSystem.VirtualFileSystem;

public class WriteOverwriteParserTest {
    FileSystem fileSystem = new VirtualFileSystem();
    WriteOverwriteParser writeOverwriteParser = new WriteOverwriteParser(null, fileSystem);

    @Test
    public void testGetArgumentsList_ReturnsEmptyList_WhenGivenEmptyString() {
        assertTrue(writeOverwriteParser.getArgumentsList("").isEmpty());
    }

    @Test
    public void testGetArgumentsList_ReturnsFilledList_WhenGivenFilledString() {
        assertEquals(Arrays.asList("file.txt", "2", "Write"),
                writeOverwriteParser.getArgumentsList("file.txt 2 Write"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExecute_ThrowsIndexOutOfBoundsException_WhenGivenNoArguments() throws Exception {
        writeOverwriteParser.execute(Arrays.asList(), fileSystem);
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenInvalidNameOfFile() throws Exception {
        assertFalse(writeOverwriteParser.execute(Arrays.asList("file", "1", "Overwrite"), fileSystem));
    }

    @Test(expected = NullPointerException.class)
    public void testExecute_ThrowsNullPointerException_WhenGivenNotExistingFile() throws Exception {
        assertTrue(fileSystem.addFile(Arrays.asList("file1.txt")));
        writeOverwriteParser.execute(Arrays.asList("file2.txt", "3", "This is a test."), fileSystem);
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenExistingFileAndSetOptionOverwrite() throws Exception {
        assertTrue(fileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(fileSystem.writeFileContent(Arrays.asList("file1.txt", "3", "This is a test."), false));
        assertTrue(writeOverwriteParser.execute(Arrays.asList("file1.txt", "3", "Test for overwriting."), fileSystem));
    }

}
