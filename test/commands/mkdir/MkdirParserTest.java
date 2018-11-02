package commands.mkdir;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import fileSystem.FileSystem;
import fileSystem.VirtualFileSystem;

public class MkdirParserTest {
    FileSystem fileSystem = new VirtualFileSystem();
    MkdirParser mkdirParser = new MkdirParser(null, fileSystem);

    @Test
    public void testGetArgumentsList_ReturnsEmptyList_WhenGivenEmptyString() {
        assertTrue(mkdirParser.getArgumentsList("").isEmpty());
    }

    @Test
    public void testGetArgumentsList_ReturnsFilledList_WhenGivenFilledString() {
        assertEquals(Arrays.asList("dir"), mkdirParser.getArgumentsList("dir"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExecute_ThrowsIndexOutOfBoundsException_WhenGivenNoArguments() throws Exception {
        mkdirParser.execute(Arrays.asList(), fileSystem);
    }

    @Test
    public void testMakeDirectory_ReturnsFalse_WhenGivenExistingNameOfDirectory() throws Exception {
        assertTrue(mkdirParser.execute(Arrays.asList("dir1"), fileSystem));
        assertFalse(mkdirParser.execute(Arrays.asList("dir1"), fileSystem));
    }

    @Test
    public void testMakeDirectory_ReturnsTrue_WhenGivenDistinctNameOfDirectory() throws Exception {
        assertTrue(mkdirParser.execute(Arrays.asList("dir1"), fileSystem));
        assertTrue(mkdirParser.execute(Arrays.asList("dir2"), fileSystem));
    }

}
