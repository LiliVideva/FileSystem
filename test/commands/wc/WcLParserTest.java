package commands.wc;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import fileSystem.FileSystem;
import fileSystem.VirtualFileSystem;

public class WcLParserTest {
    FileSystem fileSystem = new VirtualFileSystem();
    WcLParser wcLParser = new WcLParser(null, fileSystem);

    @Test
    public void testGetArgumentsList_ReturnsEmptyList_WhenGivenEmptyString() {
        assertTrue(wcLParser.getArgumentsList("").isEmpty());
    }

    @Test
    public void testGetArgumentsList_ReturnsFilledList_WhenGivenFilledString() {
        assertEquals(Arrays.asList("file.txt"), wcLParser.getArgumentsList("file.txt"));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenCorrectData() throws Exception {
        assertTrue(wcLParser.execute(Arrays.asList("file.txt"), fileSystem));
    }

}
