/*
    Nathan Steward
    3-7-17
    Multithreading
 */
     /* The Main class should ask the user to enter the filenames that
    represent matrix A and matrix B. Next, it should multiply the
    two matrices and print the resulting matrix C, and the cell
    total for C. Your multiplication algorithm should use one thread
    for each cell in matrix C. Please use a fixed thread pool of size
    50, and use a lambda expression for your Callable tasks. Each 
    Callable t ask should read values directly from A and B and place the
    result into matrix C. The CAllable tasks should return true if they are
    successful and false if they encounter an error. Finally, your program
    should print an error and exit if the number of columns in A does
    not equal the number of columns in B. */
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.go();
    }
    
    private void go() throws Exception {
        String filenameA = askUser("Which file contains Matrix A?: ");
        String filenameB = askUser("Which file contains Matrix B?: ");
        Matrix a = readFile(filenameA);
        Matrix b = readFile(filenameB);
        Matrix c = multiply(a, b);
        writeToFile(c.toString());
    }
    
    private String askUser(String prompt) {
        Scanner keyboard = new Scanner(System.in);
        System.out.print(prompt);
        String input = keyboard.nextLine();
        return input;
    }
    
    private Matrix readFile(String filename) throws Exception {
        List<List<Integer>> matrixValues = new ArrayList<List<Integer>>();
        File file = new File(filename);
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = in.readLine();
        
        while (line != null) {
            List<Integer> row = new ArrayList<Integer>();
            String[] tokens = line.split(", ");
            for (String token : tokens) {
                row.add(Integer.parseInt(token));
            }
            matrixValues.add(row);
            line = in.readLine();
        }
        Matrix newMatrix = new Matrix(matrixValues);
        return newMatrix;
    }
    
    private Matrix multiply(Matrix a, Matrix b) {
        List<List<Integer>> matrixValuesC = new ArrayList<List<Integer>>();
        for (int i = 0; i < a.getRows(); i++) {
            List<Integer> row = new ArrayList<Integer>();
            for (int j = 0; j < b.getCols(); j++) {
                int value = 0;
                for (int k = 0; k < b.getRows(); k++) {
                    value += (a.getCell(i, k) * b.getCell(k, j));
                }
                row.add(value);
            }
            matrixValuesC.add(row);
        }
        Matrix c = new Matrix(matrixValuesC);
        return c;
    }
    
    private void writeToFile(String output) throws Exception {
        File file = new File("output.txt");
        PrintWriter p = new PrintWriter(file);
        p.print(output);
        p.close();
    }
}