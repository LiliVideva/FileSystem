package commands.ls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import fileSystem.FileSystem;

public class LsSortedParser extends LsParser {
    Map<String, String> sortDirection;

    public LsSortedParser(List<String> result, FileSystem fileSystem) {
        super(result, fileSystem);
        sortDirection = new LinkedHashMap<>();
        sortDirection.put("desc", "-1");
        sortDirection.put("asc", "1");
    }

    @Override
    protected List<String> getArgumentsList(String arguments) {
        String direction = null;

        try {
            direction = sortDirection.get(arguments);
        } catch (NullPointerException e) {
            System.out.println("No such sort direction!");
        }
        return (direction == null ? new ArrayList<>() : Arrays.asList(direction));
    }

    @Override
    protected boolean execute(List<String> arguments, FileSystem fileSystem) throws Exception {
        setResult(fileSystem.displayDirectoryContent(arguments));
        return true;
    }

}
