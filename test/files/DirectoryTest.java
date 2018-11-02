package files;

import static org.junit.Assert.*;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class DirectoryTest {
    Directory root = new Directory("/", null);

    @Test(expected = FileAlreadyExistsException.class)
    public void testMake_ReturnsFalse_WhenGivenExistingNameOfDirectory() throws FileAlreadyExistsException {
        assertTrue(root.make("dir1"));
        root.make("dir1");
    }

    @Test
    public void testMake_ReturnsTrue_WhenGivenDistinctNameOfDirectory() throws FileAlreadyExistsException {
        assertTrue(root.make("dir1"));
        assertTrue(root.make("dir2"));
    }

    @Test
    public void testChange_ReturnsNull_WhenGivenNotExistingNameOfDirectory() {
        Directory root = new Directory("/", null);
        assertEquals(null, root.change("dir"));
    }

    @Test
    public void testChange_ReturnsDirectory_WhenGivenExistingNameOfDirectory() throws FileAlreadyExistsException {
        Directory root = new Directory("/", null);
        root.make("dir");

        assertEquals("dir", root.change("dir").getName());
        assertEquals(root, root.change("dir").getParent());
    }

    @Test
    public void testDisplayContent_ReturnsEmptyContent_WhenNoContentInDirectory() {
        assertTrue(root.displayContent(0).isEmpty());
    }

    @Test
    public void testDisplayContent_ReturnsContentSortedBySizeDescending_WhenContentInDirectoryAndOptionMinusOne()
            throws FileAlreadyExistsException {
        assertTrue(root.make("dir"));

        assertTrue(root.addFile("file1.txt"));
        assertTrue(root.writeFileContent("file1.txt", false, new Line(5, "file1")));

        assertTrue(root.addFile("file2.txt"));
        assertTrue(root.writeFileContent("file2.txt", false, new Line(3, "file2")));

        assertEquals(Arrays.asList("file1.txt 10", "file2.txt 8", "dir 0"), root.displayContent(-1));
    }

    @Test
    public void testDisplayContent_ReturnsContentSortedByName_WhenContentInDirectoryAndOptionZero()
            throws FileAlreadyExistsException {
        assertTrue(root.make("dir"));

        assertTrue(root.addFile("file1.txt"));
        assertTrue(root.writeFileContent("file1.txt", false, new Line(5, "file1")));

        assertTrue(root.addFile("file2.txt"));
        assertTrue(root.writeFileContent("file2.txt", false, new Line(3, "file2")));

        assertEquals(Arrays.asList("dir 0", "file1.txt 10", "file2.txt 8"), root.displayContent(0));
    }

    @Test
    public void testDisplayContent_ReturnsContentSortedBySizeAscending_WhenContentInDirectoryAndOptionOne()
            throws FileAlreadyExistsException {
        assertTrue(root.make("dir"));

        assertTrue(root.addFile("file1.txt"));
        assertTrue(root.writeFileContent("file1.txt", false, new Line(5, "file1")));

        assertTrue(root.addFile("file2.txt"));
        assertTrue(root.writeFileContent("file2.txt", false, new Line(3, "file2")));

        assertEquals(Arrays.asList("dir 0", "file2.txt 8", "file1.txt 10"), root.displayContent(1));
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void testAddFile_ReturnsFalse_WhenGivenExistingNameOfFile() throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        root.addFile("file1.txt");
    }

    @Test
    public void testAddFile_ReturnsTrue_WhenGivenDistinctNameOfFile() throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        assertTrue(root.addFile("file2.txt"));
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveFile_ReturnsFalse_WhenGivenNotExistingNameOfFile() throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        root.removeFile("file2.txt");
    }

    @Test
    public void testRemoveFile_ReturnsTrue_WhenGivenExistingNameOfFile() throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        assertTrue(root.removeFile("file1.txt"));
    }

    @Test(expected = NullPointerException.class)
    public void testReadFileContent_ReturnsNull_WhenGivenNotExistingFile() {
        root.readFileContent("file1.txt");
    }

    @Test
    public void testReadFileContent_ReturnsEmptyFileContent_WhenNoFileContentInExistingFile()
            throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        assertTrue(root.readFileContent("file1.txt").isEmpty());
    }

    @Test
    public void testReadFileContent_ReturnsFileContent_WhenFileContentInExistingFile()
            throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        assertTrue(root.writeFileContent("file1.txt", false, new Line(3, "This is a test.")));
        assertTrue(root.writeFileContent("file1.txt", false, new Line(5, "Test for reading")));

        List<String> FileContent = root.readFileContent("file1.txt");
        assertEquals(5, FileContent.size());
        assertEquals("This is a test.", FileContent.get(2));
        assertEquals("Test for reading", FileContent.get(4));
    }

    @Test(expected = NullPointerException.class)
    public void testWriteFileContent_ReturnsFalse_WhenGivenNotExistingFile() throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        root.writeFileContent("file2.txt", false, new Line(3, "This is a test."));
    }

    @Test
    public void testWriteFileContent_ReturnsTrue_WhenGivenExisitngFileAndNoOptionOverwrite()
            throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        assertTrue(root.writeFileContent("file1.txt", false, new Line(3, "This is a test.")));
        assertTrue(root.writeFileContent("file1.txt", false, new Line(3, "Test for appending.")));
        assertEquals("This is a test.Test for appending.", root.readFileContent("file1.txt").get(2));
    }

    @Test
    public void testWriteFileContent_ReturnsTrue_WhenGivenExistingFileAndSetOptionOverwrite()
            throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        assertTrue(root.writeFileContent("file1.txt", false, new Line(3, "This is a test.")));
        assertTrue(root.writeFileContent("file1.txt", true, new Line(3, "Test for overwriting.")));
        assertEquals("Test for overwriting.", root.readFileContent("file1.txt").get(2));
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteFileContent_ReturnsFalse_WhenGivenNotExistingFile() throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        root.deleteFileContent("file2.txt", 1, 5);
    }

    @Test
    public void testDeleteFileContent_ReturnsTrue_WhenGivenExistingFile() throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        assertTrue(root.writeFileContent("file1.txt", false, new Line(3, "This is a test.")));
        assertTrue(root.writeFileContent("file1.txt", false, new Line(5, "Test for deleting.")));
        assertTrue(root.deleteFileContent("file1.txt", 1, 4));
        assertEquals(2, root.readFileContent("file1.txt").size());
        assertEquals("Test for deleting.", root.readFileContent("file1.txt").get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testCountFileContent_ReturnsMinusOne_WhenGivenWrongNumberOfArguments()
            throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        assertTrue(root.writeFileContent("file1.txt", false, new Line(3, "This is a test.")));
        assertTrue(root.writeFileContent("file1.txt", false, new Line(5, "Test for counting.")));
        root.countFileContent(new ArrayList<>(), 'c');
    }

    @Test
    public void testCountFileContent_ReturnsZeroCharacters_WhenGivenEmptyFileAndOptionCharacters()
            throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        assertEquals(0, root.countFileContent(Arrays.asList("file1.txt"), 'c'));
    }

    @Test
    public void testCountFileContent_ReturnsNumberOfCharacters_WhenGivenFilledFileAndOptionCharacters()
            throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        assertTrue(root.writeFileContent("file1.txt", false, new Line(3, "test chars")));
        assertEquals(10, root.countFileContent(Arrays.asList("file1.txt"), 'c'));
    }

    @Test
    public void testCountFileContent_ReturnsNumberOfCharacters_WhenGivenTextAndOptionCharacters() {
        assertEquals(9, root.countFileContent(Arrays.asList("test", "chars"), 'c'));
    }

    @Test
    public void testCountFileContent_ReturnsZeroWords_WhenGivenEmptyFileAndOptionWords()
            throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        assertEquals(0, root.countFileContent(Arrays.asList("file1.txt"), 'w'));
    }

    @Test
    public void testCountFileContent_ReturnsNumberOfWords_WhenGivenFilledFileAndOptionWords()
            throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        assertTrue(root.writeFileContent("file1.txt", false, new Line(3, "test words")));
        assertEquals(2, root.countFileContent(Arrays.asList("file1.txt"), 'w'));
    }

    @Test
    public void testCountFileContent_ReturnsNumberOfWords_WhenGivenTextAndOptionWords() {
        assertEquals(2, root.countFileContent(Arrays.asList("test", "words"), 'w'));
    }

    @Test
    public void testCountFileContent_ReturnsZeroLines_WhenGivenEmptyFileAndOptionLines()
            throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        assertEquals(0, root.countFileContent(Arrays.asList("file1.txt"), 'l'));
    }

    @Test
    public void testCountFileContent_ReturnsNumberOfLines_WhenGivenFilledFileAndOptionLines()
            throws FileAlreadyExistsException {
        assertTrue(root.addFile("file1.txt"));
        assertTrue(root.writeFileContent("file1.txt", false, new Line(3, "test lines")));
        assertEquals(3, root.countFileContent(Arrays.asList("file1.txt"), 'l'));
    }

    @Test
    public void testCountFileContent_ReturnsNumberOfLines_WhenGivenTextAndOptionLines() {
        assertEquals(3, root.countFileContent(Arrays.asList("test", "", "words"), 'l'));
    }
}
