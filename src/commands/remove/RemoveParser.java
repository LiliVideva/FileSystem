package commands.remove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import commands.Parser;
import fileSystem.FileSystem;

public class RemoveParser extends Parser {

    public RemoveParser(List<String> result, FileSystem fileSystem) {
        super(result, fileSystem);
    }

    @Override
    protected List<String> getArgumentsList(String arguments) {
        List<String> argumentsList = (arguments.length() == 0 ? new ArrayList<>()
                : Arrays.asList(arguments.replace("-", " ").trim().split(" ")));

        return argumentsList;
    }

    @Override
    protected boolean execute(List<String> arguments, FileSystem fileSystem) throws Exception {
        return fileSystem.deleteFileContent(arguments);
    }

}
