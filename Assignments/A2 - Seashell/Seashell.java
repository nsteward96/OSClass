/* 
    By Nathan Steward
    2-8-17
    Seashell - A personal shell program restricted in its usefulness. But it's cool!
*/

import java.util.*;
import java.io.*;

public class Seashell {
    
    boolean running = true;
    Scanner keyboard = new Scanner(System.in);
    private File userLocation = new File("./");
    
    public static void main(String args[]) throws Exception {
        Seashell seashell = new Seashell();
        seashell.go();
    }
    
    // private function go() runs the functions of the program.
    // parameters: none
    // returns: none
    private void go() throws Exception {
        String input = "";
        System.out.println("Welcome to seashell!");
        while (running == true) {
            input = getUserInputString();
            handleUserInput(input);
        }
        System.out.println("bye");
    }
    
    // private function getUserInputString() takes in the user's shell commands.
    // parameters: none
    // returns: String input, the user's input command.
    private String getUserInputString() {
        System.out.print("seashell>");
        String input = keyboard.nextLine();
        return input;
    }
    
    // private function handleUserInput() interprets the user's commands and calls the 
    // respective function.
    // parameters: String input, the input string from function getUserInputString().
    // returns: none
    private void handleUserInput(String input) throws Exception {
        String[] tokens = input.split(" ");
        
        String command = tokens[0];
        String argument = "";
        for (int i = 1; i < tokens.length; i++) {
            argument += (" " + tokens[i]);
        }
        argument = argument.trim();

        // Handle different user input cases
        switch (command) {
            // List contents of current directory
            case "l":
            case "list":
                listDirectoryContents();
                break;
            
            // Moves down to a specified directory
            case "d":
            case "down":
                if(argument.equals("")) {
                    System.out.println("Please provide a directory to move down to, eg. 'd CatPhotos'");
                } else {
                    moveDownDirectory(argument);
                }
                break;
            
            // Moves up one directory
            case "u":
            case "up":
                moveUpDirectory();
                break;
            
            // Prints the current directory (a la 'Whereami')
            case "w":
            case "wai":
                printCurrentDirectory();
                break;
            
            // Exits the program.
            case "e":
            case "exit":
                exitShell();
                break;
            
            // Prints a list of the supported commands
            default:
                printCommands();   
                break;
        }
    }
    
    // private function listDirectoryContents() lists the files within the current directory.
    // parameters: none
    // returns: none
    private void listDirectoryContents() {
        File[] listOfFiles = userLocation.listFiles();
        for (File file : listOfFiles) {
            String fileString = "";
            if (file.isDirectory()) {
                fileString = "(d) ";
            } else {
                fileString = "(f) ";
            }
            fileString += file.getName();
            System.out.println(fileString);
        }
    }
    
    // private function moveDownDirectory() moves down into a folder from the current folder.
    // parameters: String dir, the user's directory entered after the command in their input.
    // returns: none.
    private void moveDownDirectory(String dir) throws Exception {
        // Check to see if the user's inputted dir ends or begins with a "/".
        char[] tokens = dir.toCharArray();
        if(tokens[tokens.length-1] == '/') {
            dir = dir.substring(dir.length() - 2);
        }
        if(tokens[0] != '/') {
            dir = '/' + dir;
        }
        
        // testingNewLocation will be used to ensure the location exists before altering userLocation.
        File testingNewLocation = new File(userLocation.getCanonicalFile() + dir);
        if(testingNewLocation.exists() && testingNewLocation.isDirectory()) {
            File newUserLocation = new File(userLocation.getCanonicalFile() + dir);
            userLocation = newUserLocation;
            System.out.println(userLocation.getCanonicalFile());
        } else {
            System.out.println("Location does not exist or is not a directory!");
        }
    }
    
    // private function moveUpDirectory() moves the user up a folder from the current folder.
    // parameters: none
    // returns: none
    private void moveUpDirectory() throws Exception {
        String[] userLocationTokens = userLocation.getCanonicalFile().toString().split("/");
        if (userLocationTokens.length <= 1) {
            System.out.println("Cannot move up any more directories!");
        } else {
            String finalUserLocation = "";
            for (int i = 0; i < userLocationTokens.length-1; i++) {
                finalUserLocation += (File.separator + userLocationTokens[i]);
            }
            File newUserLocation = new File(finalUserLocation);
            userLocation = newUserLocation;
            System.out.println(userLocation.getCanonicalFile());
        }
    }
    
    // private function printCurrentDirectory() prints the folder the user is currently in.
    // parameters: none
    // returns: none
    private void printCurrentDirectory() throws Exception {
        System.out.println(userLocation.getCanonicalFile());
    }
    
    // private function exitShell() stops the 'while' loop that runs the program.
    // parameters: none
    // returns: none
    private void exitShell() {
        running = false;
    }
    
    // private function printCommands() prints the commands available to the user through the shell.
    // params: none
    // returns: none
    private void printCommands() {
        System.out.println("\t\tSeashell commands: ");
        System.out.println("\t\t(l)ist: lists contents of current directory");
        System.out.println("\t\t(d)own [dir]: moves into the specified child directory");
        System.out.println("\t\t(u)p: moves to the parent directory");
        System.out.println("\t\t(w)ai: prints the current directory");
        System.out.println("\t\t(e)xit: exits the shell");
        System.out.println("\t\t(h)elp: prints a list of the supported commands");
    }
}