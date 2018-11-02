package commands;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import commands.cat.CatFactory;
import commands.cat.CatParser;
import commands.cd.CdFactory;
import commands.cd.CdParser;
import commands.create_file.CreateFileFactory;
import commands.create_file.CreateFileParser;
import commands.ls.LsFactory;
import commands.ls.LsParser;
import commands.ls.LsSortedParser;
import commands.mkdir.MkdirFactory;
import commands.mkdir.MkdirParser;
import commands.remove.RemoveFactory;
import commands.remove.RemoveParser;
import commands.rm.RmFactory;
import commands.rm.RmParser;
import commands.wc.WcCParser;
import commands.wc.WcFactory;
import commands.wc.WcLParser;
import commands.wc.WcParser;
import commands.wc.WcWParser;
import commands.write.WriteFactory;
import commands.write.WriteOverwriteParser;
import commands.write.WriteParser;
import fileSystem.FileSystem;
import fileSystem.InputScanner;
import fileSystem.VirtualFileSystem;

public class ParserFactoryTest {
    List<String> result = null;
    FileSystem fileSystem = new VirtualFileSystem();

    private Map<String, ParserFactory> initializeCommandsList() {

        Map<String, CatParser> catOptionsList = new LinkedHashMap<>();
        catOptionsList.put("none", new CatParser(result, fileSystem));

        Map<String, CdParser> cdOptionsList = new LinkedHashMap<>();
        cdOptionsList.put("none", new CdParser(result, fileSystem));

        Map<String, CreateFileParser> createFileOptionsList = new LinkedHashMap<>();
        createFileOptionsList.put("none", new CreateFileParser(result, fileSystem));

        Map<String, LsParser> lsOptionsList = new LinkedHashMap<>();
        lsOptionsList.put("none", new LsParser(result, fileSystem));
        lsOptionsList.put("--sorted", new LsSortedParser(result, fileSystem));

        Map<String, MkdirParser> mkdirOptionsList = new LinkedHashMap<>();
        mkdirOptionsList.put("none", new MkdirParser(result, fileSystem));

        Map<String, RemoveParser> removeOptionsList = new LinkedHashMap<>();
        removeOptionsList.put("none", new RemoveParser(result, fileSystem));

        Map<String, RmParser> rmOptionsList = new LinkedHashMap<>();
        rmOptionsList.put("none", new RmParser(result, fileSystem));

        Map<String, WcParser> wcOptionsList = new LinkedHashMap<>();
        wcOptionsList.put("none", new WcParser(result, fileSystem));
        wcOptionsList.put("-c", new WcCParser(result, fileSystem));
        wcOptionsList.put("-l", new WcLParser(result, fileSystem));
        wcOptionsList.put("-w", new WcWParser(result, fileSystem));

        Map<String, WriteParser> writeOptionsList = new LinkedHashMap<>();
        writeOptionsList.put("none", new WriteParser(result, fileSystem));
        writeOptionsList.put("--overwrite", new WriteOverwriteParser(result, fileSystem));

        Map<String, ParserFactory> commandsList = new LinkedHashMap<>();
        commandsList.put("cat", new CatFactory(catOptionsList));
        commandsList.put("cd", new CdFactory(cdOptionsList));
        commandsList.put("create_file", new CreateFileFactory(createFileOptionsList));
        commandsList.put("ls", new LsFactory(lsOptionsList));
        commandsList.put("mkdir", new MkdirFactory(mkdirOptionsList));
        commandsList.put("remove", new RemoveFactory(removeOptionsList));
        commandsList.put("rm", new RmFactory(rmOptionsList));
        commandsList.put("wc", new WcFactory(wcOptionsList));
        commandsList.put("write", new WriteFactory(writeOptionsList));

        return commandsList;
    }

    ParserFactory parserFactory = new ParserFactory(initializeCommandsList(), result);

    @Test(expected = NullPointerException.class)
    public void testProcessLine_ThrowsNullPointerException_WhenGivenInvalidCommand() throws Exception {
        InputScanner mockScanner = mock(InputScanner.class);
        when(mockScanner.nextLine()).thenReturn("list");
        parserFactory.processLine(mockScanner);
    }

    @Test(expected = NullPointerException.class)
    public void testProcessLine_ThrowsNullPointerException_WhenGivenInvalidCommandOption() throws Exception {
        InputScanner mockScanner = mock(InputScanner.class);
        when(mockScanner.nextLine()).thenReturn("ls -l");
        parserFactory.processLine(mockScanner);
    }

    @Test
    public void testProcessLine_ReturnsTrue_WhenGivenCorrectCommand() throws Exception {
        InputScanner mockScanner = mock(InputScanner.class);
        when(mockScanner.nextLine()).thenReturn("ls");
        assertTrue(parserFactory.processLine(mockScanner));
    }

    @Test
    public void testProcessLine_ReturnsTrue_WhenGivenCorrectPipeOfCommands() throws Exception {
        InputScanner mockScanner = mock(InputScanner.class);
        when(mockScanner.nextLine()).thenReturn("create_file file.txt", "write file.txt 2 test", "cat file.txt|wc -l");
        assertTrue(parserFactory.processLine(mockScanner));
    }

}
