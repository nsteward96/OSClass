    /* The matrix class should have a constructor that creates a 
    matrix initialized with values in a file. This constructor
    should take the filename as a parameter. It should have a 
    second constructor that creates a matrix initialized will all
    zeros. This constructor should take the number of rows and columns
    in the matrix. Your class should also have a getCellTotal() that
    returns the sum of the values in the matrix. Your class should have
    the following methods: getRows(), getCols(), getCell(row, col),
    setCell(row, col, value), and toString(). */
import java.util.*;

public class Matrix {
    List<List<Integer>> matrix = new ArrayList<List<Integer>>();
    
    public Matrix(List<List<Integer>> matrixValues) {
        matrix = matrixValues;
    }
    
    public int getCols() {
        return matrix.get(0).size();
    }
    
    public int getRows() {
        return matrix.size();
    }
    
    public int getCell(int row, int col) {
        return matrix.get(row).get(col);
    }
    
    private void setCell(int row, int col, int value) {
        matrix.get(row).set(col, value);
    }
    
    @Override
    public String toString() {
        String matrixToString = "";
        for (List<Integer> row : matrix) {
            matrixToString = matrixToString.concat("[");
            for (Integer number : row) {
                matrixToString = matrixToString.concat(String.format(" %d ", number));
            }
            matrixToString = matrixToString.concat(String.format("]%n"));
        }
        return matrixToString;
    }
}