package files;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Directory extends Sizeable {
    private String name;
    private Directory parent;
    private Files files;
    private Map<String, Directory> subdirectories;

    public Directory(Object name, Directory parent) {
        super();
        this.name = (String) name;
        files = new Files();
        this.parent = parent;
        subdirectories = new LinkedHashMap<>();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((files == null) ? 0 : files.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        result = prime * result + ((subdirectories == null) ? 0 : subdirectories.hashCode());
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

        Directory other = (Directory) obj;

        return (((files == null && other.files == null) || files.equals(other.files))
                && ((name == null && other.name == null) || name.equals(other.name))
                && ((parent == null && other.parent == null) || parent.equals(other.parent))
                && ((subdirectories == null && other.subdirectories == null)
                        || subdirectories.equals(other.subdirectories))
                && size == other.size);
    }

    public boolean make(String name) throws FileAlreadyExistsException {
        if (!subdirectories.containsKey(name)) {
            subdirectories.put(name, new Directory(name, this));
            return true;
        }
        throw new FileAlreadyExistsException("Directory already present!");
    }

    public Directory change(String name) {
        return subdirectories.get(name);
    }

    public List<String> displayContent(int sortDirection) {
        Map<String, Sizeable> filesAndSubdirectories = new LinkedHashMap<>();
        filesAndSubdirectories.putAll(files.getAvailableFiles());
        filesAndSubdirectories.putAll(subdirectories);

        List<String> directoryContent = new ArrayList<>();

        if (sortDirection == 0) {
            filesAndSubdirectories.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(x -> {
                directoryContent.add(x.getKey() + " " + x.getValue().getSize());
            });
        } else {
            filesAndSubdirectories.entrySet().stream().sorted(Map.Entry.comparingByValue(new Comparator<Sizeable>() {

                @Override
                public int compare(Sizeable o1, Sizeable o2) {
                    return o1.compareTo(o2);
                }
            })).forEachOrdered(x -> {
                directoryContent.add(x.getKey() + " " + x.getValue().getSize());
            });
        }

        if (sortDirection == -1) {
            Collections.reverse(directoryContent);
        }

        return directoryContent;
    }

    public boolean addFile(String fileName) throws FileAlreadyExistsException {
        return files.add(fileName);
    }

    public boolean removeFile(String fileName) throws NullPointerException {
        if (files.remove(fileName)) {
            calculateSize();
            return true;
        }
        return false;
    }

    public List<String> readFileContent(String name) throws NullPointerException {
        return files.readContent(name);
    }

    public boolean writeFileContent(String fileName, boolean toOverwrite, Line line) throws NullPointerException {
        if (files.writeContent(fileName, toOverwrite, line)) {
            calculateSize();
            return true;
        }
        return false;
    }

    public boolean deleteFileContent(String fileName, int beginLine, int endLine) throws NullPointerException {
        if (files.deleteContent(fileName, beginLine, endLine)) {
            calculateSize();
            return true;
        }
        return false;
    }

    public int countFileContent(List<String> arguments, char parts) throws IndexOutOfBoundsException {
        return files.countContent(arguments, parts);
    }

    public String getName() {
        return name;
    }

    public Files getFiles() {
        return files;
    }

    public Directory getParent() {
        return parent;
    }

    public Map<String, Directory> getSubdirectories() {
        return subdirectories;
    }

    private void calculateSize() {
        size = files.getSize();

        for (Directory directory : subdirectories.values()) {
            size += directory.getSize();
        }
    }
}
