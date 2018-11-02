package commands.write;

import java.util.List;

import fileSystem.FileSystem;

public class WriteOverwriteParser extends WriteParser {

    public WriteOverwriteParser(List<String> result, FileSystem fileSystem) {
        super(result, fileSystem);
    }

    @Override
    protected List<String> getArgumentsList(String arguments) {
        return super.getArgumentsList(arguments);
    }

    @Override
    protected boolean execute(List<String> arguments, FileSystem fileSystem) throws Exception {
        return fileSystem.writeFileContent(arguments, true);
    }

}
