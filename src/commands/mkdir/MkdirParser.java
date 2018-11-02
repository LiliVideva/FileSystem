package commands.mkdir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import commands.Parser;
import fileSystem.FileSystem;

public class MkdirParser extends Parser {

    public MkdirParser(List<String> result, FileSystem fileSystem) {
        super(result, fileSystem);
    }

    @Override
    protected List<String> getArgumentsList(String arguments) {
        return (arguments.length() == 0 ? new ArrayList<>() : Arrays.asList(arguments));
    }

    @Override
    protected boolean execute(List<String> arguments, FileSystem fileSystem) throws Exception {
        return fileSystem.makeDirectory(arguments);
    }
}
