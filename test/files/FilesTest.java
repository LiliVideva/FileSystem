package files;

import static org.junit.Assert.*;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilesTest {
    Files files = new Files();

    @Test(expected = FileAlreadyExistsException.class)
    public void testAdd_ThrowsFileAlreadyExistsException_WhenGivenExistingNameOfFile()
            throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        files.add("file1.txt");
    }

    @Test
    public void testAdd_ReturnsTrue_WhenGivenDistinctNameOfFile() throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        assertTrue(files.add("file2.txt"));
    }

    @Test(expected = NullPointerException.class)
    public void testRemove_ThrowsNullPointerException_WhenGivenNotExistingNameOfFile()
            throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        files.remove("file2.txt");
        assertTrue(files.getRemovedFiles().isEmpty());
    }

    @Test
    public void testRemove_ReturnsTrue_WhenGivenExistingNameOfFile() throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        assertTrue(files.remove("file1.txt"));
        assertEquals(1, files.getRemovedFiles().size());
    }

    @Test(expected = NullPointerException.class)
    public void testReadContent_ThrowsNullPointerException_WhenGivenNotExistingFile() {
        files.readContent("file1.txt");
    }

    @Test
    public void testReadContent_ReturnsEmptyContent_WhenNoContentInExistingFile() throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        assertTrue(files.readContent("file1.txt").isEmpty());
    }

    @Test
    public void testReadContent_ReturnsContent_WhenContentInExistingFile() throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        files.writeContent("file1.txt", false, new Line(3, "This is a test."));
        files.writeContent("file1.txt", false, new Line(5, "Test for reading"));

        List<String> content = files.readContent("file1.txt");
        assertEquals(5, content.size());
        assertEquals("This is a test.", content.get(2));
        assertEquals("Test for reading", content.get(4));
    }

    @Test(expected = NullPointerException.class)
    public void testWriteContent_ThrowsNullPointerException_WhenGivenNotExistingFile()
            throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        files.writeContent("file2.txt", false, new Line(3, "This is a test."));
    }

    @Test
    public void testWriteContent_ReturnsTrue_WhenGivenExisitngFileAndNoOptionOverwrite()
            throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        assertTrue(files.writeContent("file1.txt", false, new Line(3, "This is a test.")));
        assertTrue(files.writeContent("file1.txt", false, new Line(3, "Test for appending.")));
        assertEquals("This is a test.Test for appending.", files.readContent("file1.txt").get(2));
    }

    @Test
    public void testWriteContent_ReturnsTrue_WhenGivenExistingFileAndSetOptionOverwrite()
            throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        assertTrue(files.writeContent("file1.txt", false, new Line(3, "This is a test.")));
        assertTrue(files.writeContent("file1.txt", true, new Line(3, "Test for overwriting.")));
        assertEquals("Test for overwriting.", files.readContent("file1.txt").get(2));
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteContent_ThrowsNullPointerException_WhenGivenNotExistingFile()
            throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        files.deleteContent("file2.txt", 1, 5);
    }

    @Test
    public void testDeleteContent_ReturnsTrue_WhenGivenExistingFile() throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        assertTrue(files.writeContent("file1.txt", false, new Line(3, "This is a test.")));
        assertTrue(files.writeContent("file1.txt", false, new Line(5, "Test for deleting.")));
        assertTrue(files.deleteContent("file1.txt", 1, 4));
        assertEquals(2, files.readContent("file1.txt").size());
        assertEquals("Test for deleting.", files.readContent("file1.txt").get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testCountContent_ThrowsIndexOutOfBoundsException_WhenGivenWrongNumberOfArguments()
            throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        assertTrue(files.writeContent("file1.txt", false, new Line(3, "This is a test.")));
        assertTrue(files.writeContent("file1.txt", false, new Line(5, "Test for counting.")));
        files.countContent(new ArrayList<>(), 'c');
    }

    @Test
    public void testCountContent_ReturnsZeroCharacters_WhenGivenEmptyFileAndOptionCharacters()
            throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        assertEquals(0, files.countContent(Arrays.asList("file1.txt"), 'c'));
    }

    @Test
    public void testCountContent_ReturnsNumberOfCharacters_WhenGivenFilledFileAndOptionCharacters()
            throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        assertTrue(files.writeContent("file1.txt", false, new Line(3, "test chars")));
        assertEquals(10, files.countContent(Arrays.asList("file1.txt"), 'c'));
    }

    @Test
    public void testCountContent_ReturnsNumberOfCharacters_WhenGivenTextAndOptionCharacters() {
        assertEquals(9, files.countContent(Arrays.asList("test", "chars"), 'c'));
    }

    @Test
    public void testCountContent_ReturnsZeroWords_WhenGivenEmptyFileAndOptionWords() throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        assertEquals(0, files.countContent(Arrays.asList("file1.txt"), 'w'));
    }

    @Test
    public void testCountContent_ReturnsNumberOfWords_WhenGivenFilledFileAndOptionWords()
            throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        assertTrue(files.writeContent("file1.txt", false, new Line(3, "test words")));
        assertEquals(2, files.countContent(Arrays.asList("file1.txt"), 'w'));
    }

    @Test
    public void testCountContent_ReturnsNumberOfWords_WhenGivenTextAndOptionWords() {
        assertEquals(2, files.countContent(Arrays.asList("test", "words"), 'w'));
    }

    @Test
    public void testCountContent_ReturnsZeroLines_WhenGivenEmptyFileAndOptionLines() throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        assertEquals(0, files.countContent(Arrays.asList("file1.txt"), 'l'));
    }

    @Test
    public void testCountContent_ReturnsNumberOfLines_WhenGivenFilledFileAndOptionLines()
            throws FileAlreadyExistsException {
        assertTrue(files.add("file1.txt"));
        assertTrue(files.writeContent("file1.txt", false, new Line(3, "test lines")));
        assertEquals(3, files.countContent(Arrays.asList("file1.txt"), 'l'));
    }

    @Test
    public void testCountContent_ReturnsNumberOfLines_WhenGivenTextAndOptionLines() {
        assertEquals(3, files.countContent(Arrays.asList("test", "", "words"), 'l'));
    }

}
