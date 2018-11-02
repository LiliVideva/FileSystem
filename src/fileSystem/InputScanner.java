package fileSystem;

import java.util.Scanner;

public class InputScanner {

    private final Scanner scanner;

    public InputScanner() {
        this(new Scanner(System.in));
    }

    InputScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public String nextLine() {

        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }

}
