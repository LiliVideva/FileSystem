package fileSystem;

import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.List;

import files.Directory;
import files.Line;

public class VirtualFileSystem extends FileSystem {
    private Directory current;

    public VirtualFileSystem() {
        current = new Directory("root", null);
        path = current.getName();

        makeDirectory(Arrays.asList("home"));
        changeDirectory(Arrays.asList("home"));
    }

    public boolean makeDirectory(List<String> arguments) throws IndexOutOfBoundsException {
        String directoryName = resolveFilePath(arguments.get(0));

        if (!directoryName.matches("[\\s]*")) {
            try {
                return current.make(directoryName);
            } catch (FileAlreadyExistsException e) {
                System.out.println(e.toString());
            }
        }
        return false;
    }

    public void changeDirectory(List<String> arguments) throws IndexOutOfBoundsException {
        String pathToChange = arguments.get(0);

        if (!pathToChange.matches("\\s")) {
            current = switchDirectory(pathToChange);
        }
    }

    private Directory switchDirectory(String newPath) {
        String[] directories = newPath.split("/");

        for (int i = 0; i < directories.length; i++) {
            String name = directories[i];

            if (name.equals("root")) {
                while (!current.getName().equals("root")) {
                    int pathLength = path.length();
                    path = path.substring(0, pathLength - current.getName().length() - 1);
                    current = current.getParent();
                }
            } else if (name.equals(".")) {
                return current;
            } else if (name.equals("..")) {
                if (current.getName().equals("root")) {
                    return current;
                } else {
                    int pathLength = path.length();
                    path = path.substring(0, pathLength - current.getName().length() - 1);
                    current = current.getParent();
                }
            } else {
                current = current.change(name);
                path = path.concat("/" + current.getName());
            }

        }
        return current;
    }

    public List<String> displayDirectoryContent(List<String> arguments) throws IndexOutOfBoundsException {
        int sortDirection = parseNumber(arguments.get(0));

        return current.displayContent(sortDirection);
    }

    public boolean addFile(List<String> arguments) throws IndexOutOfBoundsException, FileAlreadyExistsException {
        String fileName = resolveFilePath(arguments.get(0));

        if (validateFileName(fileName)) {
            return current.addFile(fileName);
        }
        return false;
    }

    public boolean removeFile(List<String> arguments) throws IndexOutOfBoundsException, NullPointerException {
        String fileName = resolveFilePath(arguments.get(0));

        if (validateFileName(fileName)) {
            return current.removeFile(fileName);
        }
        return false;
    }

    public List<String> readFileContent(List<String> arguments) throws IndexOutOfBoundsException, NullPointerException {
        String fileName = resolveFilePath(arguments.get(0));

        if (validateFileName(fileName)) {
            return current.readFileContent(fileName);
        }
        return null;
    }

    public boolean writeFileContent(List<String> arguments, boolean toOverwrite)
            throws IndexOutOfBoundsException, NullPointerException {
        String fileName = resolveFilePath(arguments.get(0));
        int lineNumber = parseNumber(arguments.get(1));
        String text = arguments.get(2);
        Line line = new Line(lineNumber, text);

        if (validateFileName(fileName) && validateLineNumber(lineNumber)) {
            return current.writeFileContent(fileName, toOverwrite, line);
        }
        return false;
    }

    public boolean deleteFileContent(List<String> arguments) throws IndexOutOfBoundsException, NullPointerException {
        String fileName = resolveFilePath(arguments.get(0));
        int beginLine = parseNumber(arguments.get(1));
        int endLine = parseNumber(arguments.get(2));

        if (validateFileName(fileName) && validateLineNumber(beginLine) && validateLineNumber(endLine)) {
            return current.deleteFileContent(fileName, beginLine, endLine);
        }
        return false;
    }

    public int countFileContent(List<String> arguments, char parts) throws IndexOutOfBoundsException {
        arguments.set(0, resolveFilePath(arguments.get(0)));
        return current.countFileContent(arguments, parts);
    }

    public String getPath() {
        return path;
    }

    public Directory getCurrent() {
        return current;
    }

    private String resolveFilePath(String filePath) {
        filePath = filePath.replace('\\', '/');
        int index = filePath.lastIndexOf('/');

        if (index != -1) {
            changeDirectory(Arrays.asList(filePath.substring(0, index + 1)));
            return filePath.substring(index + 1);
        }
        return filePath;
    }

    private boolean validateFileName(String name) {
        if (name.matches(".+\\..+")) {
            return true;
        }
        System.out.println("Wrong filename format!");
        return false;
    }
}
