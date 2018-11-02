package commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fileSystem.InputScanner;

public class ParserFactory {
    private static final String PROGRAM_TERMINATOR = "exit";
    private Map<String, ParserFactory> commandsList;
    private List<String> result;
    private String currentLocation;

    protected ParserFactory() {

    }

    public ParserFactory(Map<String, ParserFactory> commandsList, List<String> result) {
        this.commandsList = commandsList;
        this.result = result;
        this.currentLocation = "root/home/>";
    }

    public boolean processLine(InputScanner scanner) throws Exception {
        System.out.print(currentLocation);
        String line = scanner.nextLine();
        String[] splittedLine = line.split("\\|");
        boolean successfulCommand = true;

        for (int i = 0; i < splittedLine.length; i++) {
            if (splittedLine[i].trim().equals("exit")) {
                return false;
            }
            successfulCommand = processCommand(splittedLine[i].trim());

            if (!successfulCommand) {
                System.out.println("Command(s) execution failed!");
                return false;
            }
        }
        if (result != null && !result.isEmpty()) {
            for (String string : result) {
                System.out.println(string);
            }
            result = null;
        }
        // dSystem.out.println("Command(s) executed successfully!");
        return true;

    }

    private boolean processCommand(String line) throws Exception {
        if (!line.equals(PROGRAM_TERMINATOR)) {
            String[] splittedLine = line.split(" ", 2);
            String command = splittedLine[0];
            String optionsAndArguments = "";
            String arguments = "";
            final List<String> options = new ArrayList<>();

            try {
                optionsAndArguments = splittedLine[1];

                final Matcher m = Pattern.compile("(-{1,2}[a-z]+)").matcher(optionsAndArguments);

                while (m.find()) {
                    options.add(m.group(0));
                }
            } catch (IndexOutOfBoundsException e) {
                // TODO: handle exception
            }

            arguments = optionsAndArguments.replaceAll("(-{1,2}[a-z]+[\\s]*)", "");

            try {
                ParserFactory parserFactory = getCustomParserFactory(command);

                if (options.isEmpty()) {
                    Parser parser = parserFactory.getOptionsParser("none");
                    return processOption(parser, arguments);
                } else {
                    for (String option : options) {
                        Parser parser = parserFactory.getOptionsParser(option);
                        if (!processOption(parser, arguments)) {
                            return false;
                        }
                    }
                }
                return true;
            } catch (NullPointerException e) {
                throw new NullPointerException("No such command to be executed!");
            }

        }
        return false;
    }

    protected Parser getOptionsParser(String option) throws NullPointerException {
        // TODO Auto-generated method stub
        return null;
    }

    private boolean processOption(Parser parser, String arguments) throws Exception {
        if (parser.executeCommand(arguments, result)) {
            result = parser.getResult();
            currentLocation = parser.getPath() + "/>";
            return true;
        }
        return false;
    }

    private ParserFactory getCustomParserFactory(String command) throws NullPointerException {
        return commandsList.get(command);
    }
}
