// By Nate Steward and Dillon Alvord for MCLA CSCI-36201 course.

import java.io.*;
import java.util.*;

public class Machine {
  // Global variables
  private HashMap<String, String> m_codes = new HashMap<String, String>(5);
  private int instructionLoc = 0;
  private int dataLoc = 15;
  private int currentMemoryTaken = 0;
  private int linecount = 0;
  String[] allCode = new String[16];

  // Main
  public static void main(String[] args) throws FileNotFoundException, Exception {
    Machine machine = new Machine();
    machine.go();
  }

// Function go() breaks the main out of the static context.
// Parameters: none
// Returns: none
  public void go() throws FileNotFoundException, Exception {
    initializeCodes();
    readFile();
    printValues();
  }

// Function initializeCodes() adds 5 different strings that resolve to its binary instruction
//   when method '.get(String instruction)' is called on it.
// Parameters: none
// Returns: none
  public void initializeCodes() {
    m_codes.put("LDA", "0000");
    m_codes.put("ADD", "0001");
    m_codes.put("SUB", "0010");
    m_codes.put("OUT", "1110");
    m_codes.put("HLT", "1111");
  }

// Function readFile() reads the file and then passes data from each line to the pushValues() function.
// Parameters: none
// Returns: none
  public void readFile() throws FileNotFoundException, Exception {
    File m_file = new File("codes.txt");
    Scanner fileReader = new Scanner(m_file);
    while(fileReader.hasNextLine()) {
      linecount++;
      String line = fileReader.nextLine();
      if(line.contains("//"))
      {
    	  continue;
      }
      String[] tokens = line.split(" ");
      String instruction = tokens[0];
      String instruction_binary = m_codes.get(instruction);
      if(instruction_binary == null) {
        // Exception if instruction cannot be found
        throw new Exception("Invalid instruction '" + instruction + "' on line " + linecount + " in codes.txt");
      }
      String data = "";
      for(int i = 0; i < tokens.length; i++) {
        currentMemoryTaken++;
        if(i == 1) {
          data = tokens[1];
          if(Integer.parseInt(data) > 255) {
            // Exception if number is used that cannot be supported with the provided 8 bits of memory.
            throw new Exception("Invalid value on line " + linecount + " - program only accepts numbers between 255 and 0.");
          }
        }
      }
      if (currentMemoryTaken >= 16) {
        // Exception if more than 16 bits of memory are trying to be used at a time.
        throw new Exception("Not enough memory to finish request - remove some commands and try again!");
      }
      pushValues(instruction_binary, data);
    }
    fileReader.close();
  }

// Function pushValues() takes data from function readFile() and formats it into proper binary.
//   The line of binary is then added to array allCode[].
// Parameters: String instruction, the binary representation of the instruction (LDA, etc)
//             String dataString, an integer (if any) being manipulated in the program.
// Returns: none
  public void pushValues(String instruction, String dataString) {
    if(dataString.equals(""))
    {
          allCode[instructionLoc] = instruction + "XXXX";
    }
    else
    {
    	int data = Integer.parseInt(dataString);
    	String bin = ("00000000" + Integer.toBinaryString(data)).substring(Integer.toBinaryString(data).length());
      allCode[dataLoc] = bin;
      String binData = ("0000" + Integer.toBinaryString(dataLoc)).substring(Integer.toBinaryString(dataLoc).length());
      allCode[instructionLoc] = instruction + binData;
      dataLoc--;
    }
    instructionLoc++;
  }

// Function printValues() prints all lines of code in the program.
// Parameters: none
// Returns: none
  public void printValues() {
    for (int i = 0; i < allCode.length; i++) {
    	String binaryPosition = String.format("%4s", Integer.toBinaryString(i)).replace(' ', '0');
      if(allCode[i] == null) {
        allCode[i] = "";
      }
      System.out.printf("%s  %s %n", binaryPosition, allCode[i]);
    }
  }
}
