package ua.com.foxminded.task7sql.util;

import java.util.Scanner;

public class ConsoleReader {
    private static final Scanner scanner = new Scanner(System.in);
    
    public String readFromConsole(String message) {
        System.out.println(message);
        if(scanner.hasNext()) {
            return scanner.nextLine();
        } else {
            return "";
        }
    }
    
    public void closeScanner() {
        scanner.close();
    }
}
