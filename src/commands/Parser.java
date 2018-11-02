package commands;

import java.util.ArrayList;
import java.util.List;

import fileSystem.FileSystem;

public abstract class Parser {
    FileSystem fileSystem;
    List<String> result;

    protected Parser(List<String> result, FileSystem fileSystem) {
        this.result = result;
        this.fileSystem = fileSystem;
    }

    protected List<String> getResult() {
        return result;
    }

    protected void setResult(List<String> result) {
        this.result = result;
    }

    protected String getPath() {
        return fileSystem.getPath();
    }

    public boolean executeCommand(String arguments, List<String> result) throws Exception {
        setResult(result);

        List<String> argumentsList = new ArrayList<>();
        argumentsList = ((result != null && !result.isEmpty()) ? result : getArgumentsList(arguments));

        return execute(argumentsList, fileSystem);
    }

    protected abstract List<String> getArgumentsList(String arguments);

    protected abstract boolean execute(List<String> arguments, FileSystem fileSystem) throws Exception;

}
