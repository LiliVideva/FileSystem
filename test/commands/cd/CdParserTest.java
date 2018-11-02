package commands.cd;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import commands.cd.CdParser;
import fileSystem.FileSystem;
import fileSystem.VirtualFileSystem;

public class CdParserTest {

    FileSystem fileSystem = new VirtualFileSystem();
    CdParser cdParser = new CdParser(null, fileSystem);

    @Test
    public void testGetArgumentsList_ReturnsEmptyList_WhenGivenEmptyString() {
        assertTrue(cdParser.getArgumentsList("").isEmpty());
    }

    @Test
    public void testGetArgumentsList_ReturnsFilledList_WhenGivenFilledString() {
        assertEquals(Arrays.asList("dir"), cdParser.getArgumentsList("dir"));
    }

}
