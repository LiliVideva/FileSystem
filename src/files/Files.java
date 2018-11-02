package files;

import java.nio.file.FileAlreadyExistsException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class Files {
    private Map<String, File> availableFiles;
    private Queue<File> removedFiles;
    private int size;

    Files() {
        availableFiles = new LinkedHashMap<>();
        removedFiles = new LinkedList<>();
        size = 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((availableFiles == null) ? 0 : availableFiles.hashCode());
        result = prime * result + ((removedFiles == null) ? 0 : removedFiles.hashCode());
        result = prime * result + size;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Files other = (Files) obj;

        return (((availableFiles == null && other.availableFiles == null)
                || availableFiles.equals(other.availableFiles))
                && ((removedFiles == null && other.removedFiles == null) || removedFiles.equals(other.removedFiles))
                && size == other.size);
    }

    boolean add(String name) throws FileAlreadyExistsException {
        File file = (!removedFiles.isEmpty() ? removedFiles.remove() : new File());
        file.setName(name);

        if (!availableFiles.containsKey(name)) {
            availableFiles.put(name, file);
            return true;
        }
        throw new FileAlreadyExistsException("File already present!");
    }

    boolean remove(String fileName) {
        File file = availableFiles.remove(fileName);

        try {
            int oldFileSize = file.getSize();
            file.reset();
            removedFiles.add(file);
            updateSize(file.getSize(), oldFileSize);
            return true;
        } catch (NullPointerException e) {
            throw new NullPointerException("No such file to remove!");
        }
    }

    List<String> readContent(String fileName) {
        File file = availableFiles.get(fileName);

        try {
            return file.read();
        } catch (NullPointerException e) {
            throw new NullPointerException("No such file to read from!");
        }
    }

    boolean writeContent(String fileName, boolean toOverwrite, Line line) {
        File file = availableFiles.get(fileName);

        try {
            int oldFileSize = file.write(toOverwrite, line);
            availableFiles.put(fileName, file);

            updateSize(file.getSize(), oldFileSize);
            return true;
        } catch (NullPointerException e) {
            throw new NullPointerException("No such file to write in!");
        }

    }

    boolean deleteContent(String fileName, int beginLine, int endLine) {
        File file = availableFiles.get(fileName);

        try {
            int oldFileSize = file.delete(beginLine, endLine);

            updateSize(file.getSize(), oldFileSize);
            return true;
        } catch (NullPointerException e) {
            throw new NullPointerException("No such file to delete content!");
        }
    }

    int countContent(List<String> arguments, char parts) throws IndexOutOfBoundsException {
        File file = availableFiles.get(arguments.get(0));

        if (file == null) {
            file = new File(arguments);
        }

        switch (parts) {
        case 'c': {
            return file.countCharacters();
        }
        case 'l': {
            return file.countLines();
        }
        case 'w': {
            return file.countWords();
        }
        }
        return -1;
    }

    Queue<File> getRemovedFiles() {
        return removedFiles;
    }

    Map<String, File> getAvailableFiles() {
        return availableFiles;
    }

    int getSize() {
        return size;
    }

    private void updateSize(int newSize, int oldSize) {
        size += (newSize - oldSize);
    }

}
