package commands.ls;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import fileSystem.FileSystem;
import fileSystem.VirtualFileSystem;

public class LsSortedParserTest {
    FileSystem fileSystem = new VirtualFileSystem();
    LsSortedParser lsSortedParser = new LsSortedParser(null, fileSystem);

    @Test
    public void testGetArgumentsList_ReturnsEmptyList_WhenGivenEmptyString() {
        assertTrue(lsSortedParser.getArgumentsList("").isEmpty());
    }

    @Test
    public void testGetArgumentsList_ReturnsEmptyList_WhenGivenInvalidSortDirection() {
        assertTrue(lsSortedParser.getArgumentsList("none").isEmpty());
    }

    @Test
    public void testGetArgumentsList_ReturnsFilledList_WhenGivenCorrectSortDirection() {
        assertEquals(Arrays.asList("-1"), lsSortedParser.getArgumentsList("desc"));
        assertEquals(Arrays.asList("1"), lsSortedParser.getArgumentsList("asc"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExecute_ThrowsIndexOutOfBoundsException_WhenGivenEmptyArgumentsList() throws Exception {
        lsSortedParser.execute(new ArrayList<>(), fileSystem);
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenCorrectData() throws Exception {
        assertTrue(lsSortedParser.execute(Arrays.asList("-1"), fileSystem));
        assertTrue(lsSortedParser.execute(Arrays.asList("1"), fileSystem));
    }

}
