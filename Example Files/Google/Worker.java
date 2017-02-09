import java.file.*;

public class Worker
{
  // can easily be run 'stand alone'...
  public static void main(String args[]) throws Exception
  {
    new Worker().go(args);
  }

  private void go(String args[]) throws Exception
  {
    char fileCharacter = args[0];
    String fileString = args[1];
    File fileFolder = new File("files");
    File[] listOfFiles = folder.listFiles();
    List<String> filesFound = new ArrayList<String>();
    for (file : listOfFiles) {
      Thread.sleep(5);
      if (file.getName().equalsIgnoreCase(fileCharacter)) {
        String line = "";
        while (file.hasNextLine()) {
          line = file.nextLine();
          String[] tokens = line.split(" ");
          for (token : tokens) {
            if (token.equalsIgnoreCase(fileString)) {
              filesFound.add(file.getName());
            }
          }
        }
      }
    }
    if(filesFound.size() > 1) {
      for (filename : filesFound) {
        System.out.printf("Found %d in %d ...", fileString, filename);
      }
    }
  }
}
