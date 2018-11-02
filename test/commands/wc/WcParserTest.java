package commands.wc;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import fileSystem.FileSystem;
import fileSystem.VirtualFileSystem;

public class WcParserTest {
    FileSystem fileSystem = new VirtualFileSystem();
    WcParser wcParser = new WcParser(null, fileSystem);

    @Test
    public void testGetArgumentsList_ReturnsEmptyList_WhenGivenEmptyString() {
        assertTrue(wcParser.getArgumentsList("").isEmpty());
    }

    @Test
    public void testGetArgumentsList_ReturnsFilledList_WhenGivenFilledString() {
        assertEquals(Arrays.asList("file.txt"), wcParser.getArgumentsList("file.txt"));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenCorrectData() throws Exception {
        assertTrue(wcParser.execute(Arrays.asList("file.txt"), fileSystem));
    }

}
