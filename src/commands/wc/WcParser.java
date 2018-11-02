package commands.wc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import commands.Parser;
import fileSystem.FileSystem;
import fileSystem.InputScanner;

public class WcParser extends Parser {

    public WcParser(List<String> result, FileSystem fileSystem) {
        super(result, fileSystem);
    }

    @Override
    protected List<String> getArgumentsList(String arguments) {
        return (arguments.length() == 0 ? new ArrayList<>() : Arrays.asList(arguments));
    }

    @Override
    protected boolean execute(List<String> arguments, FileSystem fileSystem) throws Exception {
        arguments = checkArguments(arguments);
        List<String> result = new ArrayList<>();

        result.add("Characters: " + fileSystem.countFileContent(arguments, 'c'));
        result.add("Words: " + fileSystem.countFileContent(arguments, 'w'));
        result.add("Lines: " + fileSystem.countFileContent(arguments, 'l') + "");
        setResult(result);
        return true;
    }

    protected List<String> checkArguments(List<String> arguments) {
        return (arguments.isEmpty() ? getInput() : arguments);
    }

    private List<String> getInput() {
        List<String> input = new ArrayList<>();
        InputScanner scanner = new InputScanner();
        System.out.println("To stop, write: end");
        String line = scanner.nextLine();

        while (!line.equals("end")) {
            input.add(line);
            line = scanner.nextLine();
        }

        return input;
    }
}
