package commands.wc;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import fileSystem.FileSystem;
import fileSystem.VirtualFileSystem;

public class WcCParserTest {
    FileSystem fileSystem = new VirtualFileSystem();
    WcCParser wcCParser = new WcCParser(null, fileSystem);

    @Test
    public void testGetArgumentsList_ReturnsEmptyList_WhenGivenEmptyString() {
        assertTrue(wcCParser.getArgumentsList("").isEmpty());
    }

    @Test
    public void testGetArgumentsList_ReturnsFilledList_WhenGivenFilledString() {
        assertEquals(Arrays.asList("file.txt"), wcCParser.getArgumentsList("file.txt"));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenCorrectData() throws Exception {
        assertTrue(wcCParser.execute(Arrays.asList("file.txt"), fileSystem));
    }

}
