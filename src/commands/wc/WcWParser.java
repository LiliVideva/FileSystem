package commands.wc;

import java.util.Arrays;
import java.util.List;

import fileSystem.FileSystem;

public class WcWParser extends WcParser {

    public WcWParser(List<String> result, FileSystem fileSystem) {
        super(result, fileSystem);
    }

    @Override
    protected List<String> getArgumentsList(String arguments) {
        return super.getArgumentsList(arguments);
    }

    @Override
    protected boolean execute(List<String> arguments, FileSystem fileSystem) throws Exception {
        arguments = checkArguments(arguments);
        setResult(Arrays.asList(fileSystem.countFileContent(arguments, 'w') + ""));
        return true;
    }

}
