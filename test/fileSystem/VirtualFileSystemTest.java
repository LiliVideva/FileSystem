package fileSystem;

import static org.junit.Assert.*;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class VirtualFileSystemTest {
    VirtualFileSystem virtualFileSystem = new VirtualFileSystem();

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMakeDirectory_ThrowsIndexOutOfBoundsException_WhenGivenNoArguments()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        virtualFileSystem.makeDirectory(Arrays.asList());
    }

    @Test
    public void testMakeDirectory_ReturnsFalse_WhenGivenExistingNameOfDirectory()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.makeDirectory(Arrays.asList("dir1")));
        assertFalse(virtualFileSystem.makeDirectory(Arrays.asList("dir1")));
    }

    @Test
    public void testMakeDirectory_ReturnsTrue_WhenGivenDistinctNameOfDirectory()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.makeDirectory(Arrays.asList("dir1")));
        assertTrue(virtualFileSystem.makeDirectory(Arrays.asList("dir2")));
    }

    @Test(expected = NullPointerException.class)
    public void testChangeDirectory_ReturnsNull_WhenGivenNotExistingNameOfDirectory() {
        virtualFileSystem.changeDirectory(Arrays.asList("dir"));
    }

    @Test
    public void testChangeDirectory_ReturnsThePreviousDirectory_WhenGivenPathToDirectory() {
        virtualFileSystem.changeDirectory(Arrays.asList(".."));

        assertEquals("root", virtualFileSystem.getCurrent().getName());
        assertEquals(null, virtualFileSystem.getCurrent().getParent());
    }

    @Test
    public void testChangeDirectory_ReturnsTheCurrentDirectory_WhenGivenPathToDirectory() {
        virtualFileSystem.changeDirectory(Arrays.asList("."));

        assertEquals("home", virtualFileSystem.getCurrent().getName());
        assertEquals("root", virtualFileSystem.getCurrent().getParent().getName());
    }

    @Test
    public void testChangeDirectory_ReturnsTheNewCurrentDirectory_WhenGivenExistingNameOfDirectory() {
        assertTrue(virtualFileSystem.makeDirectory(Arrays.asList("dir")));
        virtualFileSystem.changeDirectory(Arrays.asList("dir"));

        assertEquals("dir", virtualFileSystem.getCurrent().getName());
        assertEquals("home", virtualFileSystem.getCurrent().getParent().getName());
    }

    @Test
    public void testChangeDirectory_ReturnsTheNewCurrentDirectory_WhenGivenPathToDirectory() {
        assertTrue(virtualFileSystem.makeDirectory(Arrays.asList("dir")));
        virtualFileSystem.changeDirectory(Arrays.asList("dir"));
        assertTrue(virtualFileSystem.makeDirectory(Arrays.asList("subdir")));
        virtualFileSystem.changeDirectory(Arrays.asList("subdir"));
        virtualFileSystem.changeDirectory(Arrays.asList("../.."));

        assertEquals("home", virtualFileSystem.getCurrent().getName());
        assertEquals("root", virtualFileSystem.getCurrent().getParent().getName());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testDisplayDirectoryContent_ReturnsNull_WhenGivenNoArguments() {
        assertEquals(null, virtualFileSystem.displayDirectoryContent(Arrays.asList()));
    }

    @Test
    public void testDisplayDirectoryContent_ReturnsEmptyContent_WhenNoContentInDirectory() {
        assertTrue(virtualFileSystem.displayDirectoryContent(Arrays.asList("0")).isEmpty());
    }

    @Test
    public void testDisplayDirectoryContent_ReturnsContentSortedBySizeDescending_WhenContentInDirectoryAndOptionMinusOne()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.makeDirectory(Arrays.asList("dir")));

        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file1.txt", "5", "file1"), false));

        assertTrue(virtualFileSystem.addFile(Arrays.asList("file2.txt")));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file2.txt", "3", "file2"), false));

        assertEquals(Arrays.asList("file1.txt 10", "file2.txt 8", "dir 0"),
                virtualFileSystem.displayDirectoryContent(Arrays.asList("-1")));
    }

    @Test
    public void testDisplayDirectoryContent_ReturnsContentSortedByName_WhenContentInDirectoryAndOptionZero()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.makeDirectory(Arrays.asList("dir")));

        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file1.txt", "5", "file1"), false));

        assertTrue(virtualFileSystem.addFile(Arrays.asList("file2.txt")));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file2.txt", "3", "file2"), false));

        assertEquals(Arrays.asList("dir 0", "file1.txt 10", "file2.txt 8"),
                virtualFileSystem.displayDirectoryContent(Arrays.asList("0")));
    }

    @Test
    public void testDisplayContent_ReturnsContentSortedBySizeAscending_WhenContentInDirectoryAndOptionOne()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.makeDirectory(Arrays.asList("dir")));

        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file1.txt", "5", "file1"), false));

        assertTrue(virtualFileSystem.addFile(Arrays.asList("file2.txt")));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file2.txt", "3", "file2"), false));

        assertEquals(Arrays.asList("dir 0", "file2.txt 8", "file1.txt 10"),
                virtualFileSystem.displayDirectoryContent(Arrays.asList("1")));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddFile_ThrowsIndexOutOfBoundsException_WhenGivenNoArguments() throws FileAlreadyExistsException {
        assertFalse(virtualFileSystem.addFile(Arrays.asList()));
    }

    @Test
    public void testAddFile_ReturnsFalse_WhenGivenInvalidNameOfFile()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertFalse(virtualFileSystem.addFile(Arrays.asList("file")));
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void testAddFile_ThrowsFileAlreadyExistsException_WhenGivenExistingNameOfFile()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        virtualFileSystem.addFile(Arrays.asList("file1.txt"));
    }

    @Test
    public void testAddFile_ReturnsTrue_WhenGivenDistinctNameOfFile()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file2.txt")));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveFile_ThrowsIndexOutOfBoundsException_WhenGivenNoArguments() {
        virtualFileSystem.removeFile(Arrays.asList());
    }

    @Test
    public void testRemoveFile_ReturnsFalse_WhenGivenInvalidNameOfFile() {
        assertFalse(virtualFileSystem.removeFile(Arrays.asList("file")));
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveFile_ThrowsNullPointerException_WhenGivenNotExistingNameOfFile()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        virtualFileSystem.removeFile(Arrays.asList("file2.txt"));
    }

    @Test
    public void testRemoveFile_ReturnsTrue_WhenGivenExistingNameOfFile()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(virtualFileSystem.removeFile(Arrays.asList("file1.txt")));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testReadFileContent_ThrowsIndexOutOfBoundsException_WhenGivenNoArguments() {
        virtualFileSystem.readFileContent(Arrays.asList());
    }

    @Test
    public void testReadFileContent_ReturnsNull_WhenGivenInvalidNameOfFile() {
        assertEquals(null, virtualFileSystem.readFileContent(Arrays.asList("file")));
    }

    @Test(expected = NullPointerException.class)
    public void testReadFileContent_ThrowsNullPointerException_WhenGivenNotExistingFile() {
        virtualFileSystem.readFileContent(Arrays.asList("file1.txt"));
    }

    @Test
    public void testReadFileContent_ReturnsEmptyFileContent_WhenNoFileContentInExistingFile()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(virtualFileSystem.readFileContent(Arrays.asList("file1.txt")).isEmpty());
    }

    @Test
    public void testReadFileContent_ReturnsFileContent_WhenFileContentInExistingFile()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file1.txt", "3", "This is a test."), false));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file1.txt", "5", "Test for reading"), false));

        List<String> FileContent = virtualFileSystem.readFileContent(Arrays.asList("file1.txt"));
        assertEquals(5, FileContent.size());
        assertEquals("This is a test.", FileContent.get(2));
        assertEquals("Test for reading", FileContent.get(4));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testWriteFileContent_ThrowsIndexOutOfBoundsException_WhenGivenNoArguments() {
        virtualFileSystem.writeFileContent(Arrays.asList(), false);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testWriteFileContent_ThrowsIndexOutOfBoundsException_WhenGivenInvalidNameOfFile() {
        virtualFileSystem.writeFileContent(Arrays.asList("file"), false);
    }

    @Test(expected = NullPointerException.class)
    public void testWriteFileContent_ThrowsNullPointerException_WhenGivenNotExistingFile()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        virtualFileSystem.writeFileContent(Arrays.asList("file2.txt", "3", "This is a test."), false);
    }

    @Test
    public void testWriteFileContent_ReturnsTrue_WhenGivenExisitngFileAndNoOptionOverwrite()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file1.txt", "3", "This is a test."), false));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file1.txt", "3", "Test for appending."), false));
        assertEquals("This is a test.Test for appending.",
                virtualFileSystem.readFileContent(Arrays.asList("file1.txt")).get(2));
    }

    @Test
    public void testWriteFileContent_ReturnsTrue_WhenGivenExistingFileAndSetOptionOverwrite()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file1.txt", "3", "This is a test."), false));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file1.txt", "3", "Test for overwriting."), true));
        assertEquals("Test for overwriting.", virtualFileSystem.readFileContent(Arrays.asList("file1.txt")).get(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testDeleteFileContent_ThrowsIndexOutOfBoundsException_WhenGivenNoArguments() {
        virtualFileSystem.deleteFileContent(Arrays.asList());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testDeleteFileContent_ThrowsIndexOutOfBoundsException_WhenGivenInvalidNumberOfArguments() {
        virtualFileSystem.deleteFileContent(Arrays.asList("file", "1"));
    }

    @Test
    public void testDeleteFileContent_ReturnsFalse_WhenGivenInvalidNameOfFile() {
        assertFalse(virtualFileSystem.deleteFileContent(Arrays.asList("file", "1", "2")));
    }

    @Test
    public void testDeleteFileContent_ReturnsFalse_WhenGivenInvalidBeginLineNumber() {
        assertFalse(virtualFileSystem.deleteFileContent(Arrays.asList("file", "-1", "2")));
    }

    @Test
    public void testDeleteFileContent_ReturnsFalse_WhenGivenInvalidEndLineNumber() {
        assertFalse(virtualFileSystem.deleteFileContent(Arrays.asList("file", "1", "0")));
    }

    @Test
    public void testDeleteFileContent_ReturnsFalse_WhenGivenInvalidLineNumbers() {
        assertFalse(virtualFileSystem.deleteFileContent(Arrays.asList("file", "-5", "-2")));
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteFileContent_ThrowsNullPointerException_WhenGivenNotExistingFile()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        virtualFileSystem.deleteFileContent(Arrays.asList("file2.txt", "1", "5"));
    }

    @Test
    public void testDeleteFileContent_ReturnsTrue_WhenGivenExistingFile()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file1.txt", "3", "This is a test."), false));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file1.txt", "5", "Test for deleting."), false));
        assertTrue(virtualFileSystem.deleteFileContent(Arrays.asList("file1.txt", "1", "4")));
        assertEquals(2, virtualFileSystem.readFileContent(Arrays.asList("file1.txt")).size());
        assertEquals("Test for deleting.", virtualFileSystem.readFileContent(Arrays.asList("file1.txt")).get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testCountFileContent_ThrowsIndexOutOfBoundsException_WhenGivenWrongNumberOfArguments()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file1.txt", "3", "This is a test."), false));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file1.txt", "5", "Test for counting."), false));
        virtualFileSystem.countFileContent(new ArrayList<>(), 'c');
    }

    @Test
    public void testCountFileContent_ReturnsZeroCharacters_WhenGivenEmptyFileAndOptionCharacters()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        assertEquals(0, virtualFileSystem.countFileContent(Arrays.asList("file1.txt"), 'c'));
    }

    @Test
    public void testCountFileContent_ReturnsNumberOfCharacters_WhenGivenFilledFileAndOptionCharacters()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file1.txt", "3", "test chars"), false));
        assertEquals(10, virtualFileSystem.countFileContent(Arrays.asList("file1.txt"), 'c'));
    }

    @Test
    public void testCountFileContent_ReturnsNumberOfCharacters_WhenGivenTextAndOptionCharacters() {
        assertEquals(9, virtualFileSystem.countFileContent(Arrays.asList("test", "chars"), 'c'));
    }

    @Test
    public void testCountFileContent_ReturnsZeroWords_WhenGivenEmptyFileAndOptionWords()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        assertEquals(0, virtualFileSystem.countFileContent(Arrays.asList("file1.txt"), 'w'));
    }

    @Test
    public void testCountFileContent_ReturnsNumberOfWords_WhenGivenFilledFileAndOptionWords()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file1.txt", "3", "test words"), false));
        assertEquals(2, virtualFileSystem.countFileContent(Arrays.asList("file1.txt"), 'w'));
    }

    @Test
    public void testCountFileContent_ReturnsNumberOfWords_WhenGivenTextAndOptionWords() {
        assertEquals(2, virtualFileSystem.countFileContent(Arrays.asList("test", "words"), 'w'));
    }

    @Test
    public void testCountFileContent_ReturnsZeroLines_WhenGivenEmptyFileAndOptionLines()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        assertEquals(0, virtualFileSystem.countFileContent(Arrays.asList("file1.txt"), 'l'));
    }

    @Test
    public void testCountFileContent_ReturnsNumberOfLines_WhenGivenFilledFileAndOptionLines()
            throws FileAlreadyExistsException, IndexOutOfBoundsException {
        assertTrue(virtualFileSystem.addFile(Arrays.asList("file1.txt")));
        assertTrue(virtualFileSystem.writeFileContent(Arrays.asList("file1.txt", "3", "test lines"), false));
        assertEquals(3, virtualFileSystem.countFileContent(Arrays.asList("file1.txt"), 'l'));
    }

    @Test
    public void testCountFileContent_ReturnsNumberOfLines_WhenGivenTextAndOptionLines() {
        assertEquals(3, virtualFileSystem.countFileContent(Arrays.asList("test", "", "words"), 'l'));
    }
}
