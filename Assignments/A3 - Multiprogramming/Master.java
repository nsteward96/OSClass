/*  
    Written by Nathan Steward
    2-22-17 to 2-25-17
    Program finds the closest point to a reference point
    in a series of randomly generated points.
*/
import java.util.*;
import java.io.*;
public class Master {
    
    private Random random = new Random();
    private List<Point> points = new ArrayList<Point>();
    private List<Point> closestPoints = new ArrayList<Point>();
    private Point referencePoint;
    private int bestPointIndex = 0;
    private int referenceX = 0;
    private int referenceY = 0;
    
    public static void main(String[] args) throws Exception {
        Master master = new Master();
        master.go();
    }
    
    // Runs the bulk of the program.
    public void go() throws Exception {
        Scanner keyboard = new Scanner(System.in);
        boolean badInputs = false;
        int numPoints = 0;
        int gridReach = 0;
        int numGroups = 0;
        
        try {
            System.out.print("Please enter how many points you would like to be randomly generated: ");
            numPoints = keyboard.nextInt();
            System.out.print("How many units big is your axis? (i.e. 10 is a 10x10 grid): ");
            gridReach = keyboard.nextInt();
            System.out.print("Where would you like the x-location of your reference point to be?: ");
            referenceX = keyboard.nextInt();
            System.out.print("Where would you like the y-location of your reference point to be?: ");
            referenceY = keyboard.nextInt();
            referencePoint = new Point(referenceX, referenceY);
            System.out.print("How many processes would you like to use to find a solution?: ");
            numGroups = keyboard.nextInt();
            while (numPoints % numGroups != 0) {
                System.out.print("Error: Please enter a number of groups that can be evenly divided with the number of points: ");
                numGroups = keyboard.nextInt();
            }
            if (referenceX > gridReach || referenceY > gridReach) {
                throw new Exception();
            }
        } catch (InputMismatchException e) {
            System.out.println("That is not a proper integer number!");
            badInputs = true;
        } catch (Exception e) {
            System.out.println("Your reference point is beyond the grid's axis!");
            badInputs = true;
        }
        if(!badInputs) {
            for (int i = 0; i < numPoints; i++) {
                points.add(new Point(random.nextInt(gridReach), random.nextInt(gridReach)));
            }
            int start = 0;
            int end = 0;
            List<Process> processlist = new ArrayList<Process>();
            for (int i = 0; i < numGroups; i++) {
                start = i*(numPoints/numGroups)+1;
                end = (i+1)*(numPoints/numGroups);
                System.out.printf("Starting process for points %d to %d...%n", start, end);
                Process p = new ProcessBuilder("java", "Worker").start();
                sendParams(p, start, end);
                processlist.add(p);
            }
            for (Process p : processlist) {
                findClosestPoint(p);
            }
        }
        System.out.printf("The point closest to the reference point is %s, only %d away from the reference point!!%n", closestPoints.get(bestPointIndex).toString(), Point.distanceBetween(referencePoint, closestPoints.get(bestPointIndex)));
    }
    
    private void findClosestPoints(Process p) throws Exception {
        p.waitFor();
        Thread.sleep(200);
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
        String line = br.readLine();
        while (line != null) {
            System.out.println(line);
            
            line = line.substring(line.indexOf('(')+1, line.indexOf(')'));
            String[] pointTokens = line.split(", ");
            closestPoints.add(new Point(Integer.parseInt(pointTokens[0]), Integer.parseInt(pointTokens[1])));
            
            line = br.readLine();
        }
        // Keep track of which point in the series of returned points is best.
        for (int i = 0; i < closestPoints.size(); i++) {
            if (Point.distanceBetween(referencePoint, closestPoints.get(i)) < Point.distanceBetween(referencePoint, closestPoints.get(bestPointIndex))) {
                bestPointIndex = i;
            }
        }
    }
    
    // Sends the parameters to each process worker. These are:
    // 0: The integer for the start of the range.
    // 1: The integer for the end of the range.
    // 2: The reference point.
    // 3+: All randomly generated points being checked.
    private void sendParams(Process p, int start, int end) throws Exception {
        System.out.printf("Sending params for points %d to %d...%n", start, end);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(p.getOutputStream(), "UTF-8"));
        pw.println(start-1);
        pw.println(end);
        pw.println(String.format("(%d, %d)", referenceX, referenceY));
        for(int i = start-1; i < end; i++) {
            pw.println(points.get(i).toString());
            pw.flush();
        }
    }
} 