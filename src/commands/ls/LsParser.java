package commands.ls;

import java.util.Arrays;
import java.util.List;

import commands.Parser;
import fileSystem.FileSystem;

public class LsParser extends Parser {

    public LsParser(List<String> result, FileSystem fileSystem) {
        super(result, fileSystem);
    }

    @Override
    protected List<String> getArgumentsList(String arguments) {
        return Arrays.asList("0");
    }

    @Override
    protected boolean execute(List<String> arguments, FileSystem fileSystem) throws Exception {
        setResult(fileSystem.displayDirectoryContent(arguments));
        return true;
    }

}
