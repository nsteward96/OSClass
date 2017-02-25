import java.util.*;
import java.io.*;

public class Worker {
    
    private int bestPointIndex = 0;
    
    public static void main(String[] args) throws Exception {
        Worker worker = new Worker();
        worker.go();
    }
    
    public void go() throws Exception {
        List<Point> points = new ArrayList<Point>();
        Scanner scan = new Scanner(System.in);
        
        int start = scan.nextInt();
        int end = scan.nextInt();
        String dumbyString = scan.nextLine(); // Random empty string that gets received even when not explicitly sent in master >:(
        String point = scan.nextLine().replace("(", "").replace(")", "");
        String[] referenceTokens = point.split(", ");
        Point referencePoint = new Point(Integer.parseInt(referenceTokens[0]), Integer.parseInt(referenceTokens[1]));
        for (int i = start; i < end; i++) {
            // Add all the points passed from master into its own personal ArrayList.
            Thread.sleep(20);
            point = scan.nextLine().replace("(", "").replace(")", "");
            String[] tokens = point.split(", ");
            points.add(new Point(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])));
        }
        for (int i = 1; i < points.size(); i++) {
            // Finds and records which point is closest to the reference point in its range.
            if (Point.distanceBetween(points.get(i), referencePoint) < Point.distanceBetween(points.get(bestPointIndex), referencePoint)) {
                bestPointIndex = i;
            }
        }
        System.out.printf("Process found the best point in its range is at %s, %d away from the reference point...%n", points.get(bestPointIndex).toString(), Point.distanceBetween(points.get(bestPointIndex), referencePoint));
        scan.close();
    }
}