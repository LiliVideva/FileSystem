package fileSystem;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.List;

public class RealFileSystem extends FileSystem {
    private VirtualFileSystem virtualFileSystem;
    private Path current;

    public RealFileSystem() throws IOException {
        virtualFileSystem = new VirtualFileSystem();
        Path rootPath = Paths.get("root");
        current = Files.exists(rootPath) ? rootPath : Files.createDirectory(rootPath);

        Path homePath = Paths.get(current.toString(), "home");
        current = Files.exists(homePath) ? homePath : Files.createDirectory(homePath);
        path = current.toString();

        restoreContent();
    }

    private void restoreContent() throws IOException {
        Files.walk(current).filter(Files::isDirectory).forEach(x -> {
            try {
                if (!path.equals(x.toString())) {
                    virtualFileSystem.makeDirectory(Arrays.asList(x.toString()));
                }
            } catch (IndexOutOfBoundsException e) {

            }
        });
        Files.walk(current).filter(Files::isRegularFile).forEach(x -> {
            try {
                String filePath = x.toString();
                virtualFileSystem.addFile(Arrays.asList(filePath));
                if (new File(x.toString()).length() != 0) {
                    String line;
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(x.toString()))) {
                        int i = 1;
                        while ((line = bufferedReader.readLine()) != null) {
                            virtualFileSystem.writeFileContent(Arrays.asList(filePath, i + "", line), false);
                            i++;
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("No such file!");
                    } catch (IOException e) {
                        System.out.println("Can't read from file!");
                    }
                }
            } catch (FileAlreadyExistsException | IndexOutOfBoundsException e) {
            }
        });
    }

    @Override
    public boolean makeDirectory(List<String> arguments)
            throws IndexOutOfBoundsException, InvalidPathException, IOException {
        String directory = arguments.get(0);
        try {
            Files.createDirectory(Paths.get(path, directory));
            virtualFileSystem.makeDirectory(arguments);
            return true;
        } catch (FileAlreadyExistsException e) {
            System.out.println("Directory already exists!");
        } catch (IOException e) {
            throw new IOException("Parent directory doesn't exist!");
        }
        return false;
    }

    @Override
    public void changeDirectory(List<String> arguments) throws IndexOutOfBoundsException, InvalidPathException {
        virtualFileSystem.changeDirectory(arguments);
        path = virtualFileSystem.getPath();
    }

    @Override
    public List<String> displayDirectoryContent(List<String> arguments) throws IndexOutOfBoundsException {

        return virtualFileSystem.displayDirectoryContent(arguments);
    }

    @Override
    public boolean addFile(List<String> arguments) throws IndexOutOfBoundsException, InvalidPathException, IOException {
        try {
            Files.createFile(Paths.get(path, arguments.get(0)));
            virtualFileSystem.addFile(arguments);
            return true;
        } catch (FileAlreadyExistsException e) {
            System.out.println("Can't create file!");
        } catch (IOException e) {
            throw new IOException("Parent directory doesn't exist!");
        }
        return false;
    }

    @Override
    public boolean removeFile(List<String> arguments)
            throws IndexOutOfBoundsException, InvalidPathException, IOException {
        try {
            Files.delete(Paths.get(path, arguments.get(0)));
            virtualFileSystem.removeFile(arguments);
            return true;
        } catch (NoSuchFileException e) {
            throw new NoSuchFileException("No such file!");
        } catch (IOException e) {
            throw new IOException("Can't delete file!");
        }
    }

    @Override
    public List<String> readFileContent(List<String> arguments) throws IndexOutOfBoundsException, NullPointerException {
        return virtualFileSystem.readFileContent(arguments);
    }

    @Override
    public boolean writeFileContent(List<String> arguments, boolean toOverwrite)
            throws IndexOutOfBoundsException, IOException {

        if (virtualFileSystem.writeFileContent(arguments, toOverwrite)) {
            String fileName = arguments.get(0);
            List<String> content = virtualFileSystem.readFileContent(Arrays.asList(fileName));
            return modifyFileContent(fileName, content);

        }
        return false;

    }

    @Override
    public boolean deleteFileContent(List<String> arguments)
            throws IndexOutOfBoundsException, InvalidPathException, IOException {

        if (virtualFileSystem.deleteFileContent(arguments)) {
            String fileName = arguments.get(0);
            List<String> content = virtualFileSystem.readFileContent(Arrays.asList(fileName));
            return modifyFileContent(fileName, content);

        }
        return false;
    }

    @Override
    public int countFileContent(List<String> arguments, char parts)
            throws IndexOutOfBoundsException, InvalidPathException, IOException {

        return virtualFileSystem.countFileContent(arguments, parts);
    }

    private boolean modifyFileContent(String fileName, List<String> content) throws IOException {
        Path filePath = Paths.get(path, fileName);
        Files.deleteIfExists(filePath);
        Files.createFile(filePath);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath.toString(), true))) {
            int i = 0;
            while (i < content.size() - 1) {
                String line = content.get(i);
                if (line.length() != 0) {
                    bufferedWriter.write(line);
                }
                bufferedWriter.newLine();
                i++;
            }
            bufferedWriter.write(content.get(i));
            return true;
        } catch (IOException e) {
            throw new IOException("Can't write in file!");
        }
    }

}
