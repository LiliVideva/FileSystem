package fileSystem;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import commands.ParserFactory;
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

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> result = null;
        FileSystem fileSystem = null;

        Map<String, FileSystem> fileSystemsList = new LinkedHashMap<>();
        fileSystemsList.put("virtual", new VirtualFileSystem());
        fileSystemsList.put("real", new RealFileSystem());

        fileSystem = fileSystemsList.get("real");

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

        ParserFactory parserFactory = new ParserFactory(commandsList, result);
        Terminal terminal = new Terminal(parserFactory);
        System.out.println("Welcome to Lili's raum! (To stop, write: exit) Enter command: ");
        terminal.start();
        System.out.println("Goodbye! See you soon!");

    }

}
