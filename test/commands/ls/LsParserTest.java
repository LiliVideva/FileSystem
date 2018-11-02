package commands.ls;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import fileSystem.FileSystem;
import fileSystem.VirtualFileSystem;

public class LsParserTest {
    FileSystem fileSystem = new VirtualFileSystem();
    LsParser lsParser = new LsParser(null, fileSystem);

    @Test
    public void testGetArgumentsList_ReturnsZero() {
        assertEquals(Arrays.asList("0"), lsParser.getArgumentsList("0"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExecute_ThrowsIndexOutOfBoundsException_WhenGivenEmptyArgumentsList() throws Exception {
        lsParser.execute(new ArrayList<>(), fileSystem);
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenCorrectData() throws Exception {
        assertTrue(lsParser.execute(Arrays.asList("0"), fileSystem));
    }

}
