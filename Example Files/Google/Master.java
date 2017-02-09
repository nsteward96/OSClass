import java.io.*;
import java.util.*;

public class Master
{
  public static void main(String args[]) throws Exception
  {
    new Master().go(args[0]);
  }

  private void go(String s) throws Exception
  {
    List<Process> list = new ArrayList<Process>();
    for (int i = 0; i < 26; i++)
    {
      char start = (char)(97+i);
      System.out.printf("Starting process for files starting with %c ...%n",start);
      Process p = new ProcessBuilder("java", "Worker", start+"", s+"").start();
      list.add(p);
    }

    for (Process p : list)
    {
      getFiles(p);
    }
  }

  private void getFiles(Process p) throws Exception
  {
    p.waitFor();
    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
    String line = br.readLine();
    while (line != null)
    {
      System.out.println(line);
      line = br.readLine();
    }
  }
}
