package commands.wc;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import fileSystem.FileSystem;
import fileSystem.VirtualFileSystem;

public class WcWParserTest {
    FileSystem fileSystem = new VirtualFileSystem();
    WcWParser wcWParser = new WcWParser(null, fileSystem);

    @Test
    public void testGetArgumentsList_ReturnsEmptyList_WhenGivenEmptyString() {
        assertTrue(wcWParser.getArgumentsList("").isEmpty());
    }

    @Test
    public void testGetArgumentsList_ReturnsFilledList_WhenGivenFilledString() {
        assertEquals(Arrays.asList("file.txt"), wcWParser.getArgumentsList("file.txt"));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenCorrectData() throws Exception {
        assertTrue(wcWParser.execute(Arrays.asList("file.txt"), fileSystem));
    }

}
