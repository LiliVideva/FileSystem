package commands.cat;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import fileSystem.FileSystem;
import fileSystem.VirtualFileSystem;

public class CatParserTest {
    FileSystem fileSystem = new VirtualFileSystem();
    CatParser catParser = new CatParser(null, fileSystem);

    @Test
    public void testGetArgumentsList_ReturnsEmptyList_WhenGivenEmptyString() {
        assertTrue(catParser.getArgumentsList("").isEmpty());
    }

    @Test
    public void testGetArgumentsList_ReturnsFilledList_WhenGivenFilledString() {
        assertEquals(Arrays.asList("file.txt"), catParser.getArgumentsList("file.txt"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExecute_ThrowsIndexOutOfBoundsException_WhenGivenEmptyArgumentsList() throws Exception {
        catParser.execute(new ArrayList<>(), fileSystem);
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenCorrectData() throws Exception {
        assertTrue(fileSystem.addFile(Arrays.asList("file.txt")));
        assertTrue(catParser.execute(Arrays.asList("file.txt"), fileSystem));
    }

}
