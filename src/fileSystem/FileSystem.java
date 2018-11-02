package fileSystem;

import java.util.List;

public abstract class FileSystem {
    protected String path;

    public abstract boolean makeDirectory(List<String> arguments) throws Exception;

    public abstract void changeDirectory(List<String> arguments) throws Exception;

    public abstract List<String> displayDirectoryContent(List<String> arguments) throws Exception;

    public abstract boolean addFile(List<String> arguments) throws Exception;

    public abstract boolean removeFile(List<String> arguments) throws Exception;

    public abstract List<String> readFileContent(List<String> arguments) throws Exception;

    public abstract boolean writeFileContent(List<String> arguments, boolean toOverwrite) throws Exception;

    public abstract boolean deleteFileContent(List<String> arguments) throws Exception;

    public abstract int countFileContent(List<String> arguments, char parts) throws Exception;

    protected int parseNumber(String line) {
        int number = 0;

        try {
            number = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Not a number!");
        }
        return number;
    }

    protected boolean validateLineNumber(int lineNumber) {
        if (lineNumber > 0) {
            return true;
        }
        System.out.println("Not a positive line number!");
        return false;
    }

    public String getPath() {
        return path;
    }

}
