package files;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class FileTest {
    File file = new File();

    @Test
    public void testRead_ReturnsEmptyContent_WhenNoContentInFile() {
        assertTrue(file.read().isEmpty());
    }

    @Test
    public void testRead_ReturnsContent_WhenContentInFile() {
        file.write(false, new Line(3, "This is a test."));
        file.write(false, new Line(5, "Test for reading"));

        List<String> content = file.read();
        assertEquals(5, content.size());
        assertEquals("This is a test.", content.get(2));
        assertEquals("Test for reading", content.get(4));
    }

    @Test
    public void testWrite_WritesTheLineAndReturnsZero_WhenWritingForTheFirstTime() {
        assertEquals(0, file.write(false, new Line(3, "This is a test.")));
        assertEquals("This is a test.", file.read().get(2));
        assertEquals(3, file.countLines());
    }

    @Test
    public void testWrite_AppendsTextOnTheChosenLineAndReturnsPreviousSize_WhenNoOptionOverwrite() {
        file.write(false, new Line(3, "This is a test."));
        int oldSize = file.getSize();
        assertEquals(oldSize, file.write(false, new Line(3, "Test for appending.")));
        assertEquals("This is a test.Test for appending.", file.read().get(2));
    }

    @Test
    public void testWrite_OverwritesTextOnTheChosenLineAndReturnsPreviousSize_WhenSetOptionOverwrite() {
        file.write(false, new Line(3, "This is a test."));
        int oldSize = file.getSize();

        assertEquals(oldSize, file.write(true, new Line(3, "Test for overwriting.")));
        assertEquals("Test for overwriting.", file.read().get(2));
    }

    @Test
    public void testDelete_ReturnsZero_WhenFileIsEmpty() {
        assertEquals(0, file.delete(0, 0));
    }

    @Test
    public void testDelete_DeletesLineasAndReturnsPreviousSize_WhenDeletingLines() {
        file.write(false, new Line(3, "This is a test."));
        file.write(false, new Line(5, "Test for deleting"));
        int oldSize = file.getSize();

        assertEquals(oldSize, file.delete(1, 4));
        assertEquals(2, file.countLines());
        assertEquals("Test for deleting", file.read().get(1));
    }

    @Test
    public void testCountCharacters_ReturnsZero_WhenGivenEmptyFile() {
        assertEquals(0, file.countCharacters());
    }

    @Test
    public void testCountCharacters_ReturnsNumberOfCharacters_WhenGivenFilledFile() {
        file.write(false, new Line(3, "test chars"));

        assertEquals(10, file.countCharacters());
    }

    @Test
    public void testCountWords_ReturnsZero_WhenGivenEmptyFile() {
        assertEquals(0, file.countWords());
    }

    @Test
    public void testCountWords_ReturnsNumberOfWords_WhenGivenFilledFile() {
        file.write(false, new Line(3, "test words"));

        assertEquals(2, file.countWords());
    }

    @Test
    public void testCountLines_ReturnsZero_WhenGivenEmptyFile() {
        assertEquals(0, file.countLines());
    }

    @Test
    public void testCountLines_ReturnsNumberOfLines_WhenGivenFilledFile() {
        file.write(false, new Line(3, "test words"));

        assertEquals(3, file.countLines());
    }
}
