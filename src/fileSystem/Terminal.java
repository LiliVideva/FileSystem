package fileSystem;

import commands.ParserFactory;

public class Terminal {
    private InputScanner scanner;
    private ParserFactory parserFactory;

    public Terminal(ParserFactory parserFactory) {
        this.parserFactory = parserFactory;
        this.scanner = new InputScanner();
    }

    void start() {
        boolean continueWorking = false;

        do {
            try {
                continueWorking = parserFactory.processLine(scanner);
            } catch (Exception e) {
                System.out.println(e.toString());
                continueWorking = false;
            }
        } while (continueWorking);
        scanner.close();
    }
}
