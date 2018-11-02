package commands.cd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import commands.Parser;
import fileSystem.FileSystem;

public class CdParser extends Parser {

    public CdParser(List<String> result, FileSystem fileSystem) {
        super(result, fileSystem);
    }

    @Override
    protected List<String> getArgumentsList(String arguments) {
        return (arguments.length() == 0 ? new ArrayList<>() : Arrays.asList(arguments));
    }

    @Override
    protected boolean execute(List<String> arguments, FileSystem fileSystem) throws Exception {
        fileSystem.changeDirectory(arguments);
        return true;
    }
}
