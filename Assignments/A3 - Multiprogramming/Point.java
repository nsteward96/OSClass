import java.util.*;

public class Point {
    
    private int m_x;
    private int m_y;
    
    public Point(int x_pos, int y_pos) {
        m_x = x_pos;
        m_y = y_pos;
    }
    
    public int getX() {
        return m_x;
    }
    
    public int getY() {
        return m_y;
    }

    // Pass it 2 points and it returns the distance.
    public static int distanceBetween(Point referencePoint, Point point) {
        int xDistance = (int)Math.pow((referencePoint.getX()-point.getX()), 2);
        int yDistance = (int)Math.pow((referencePoint.getY()-point.getY()), 2);
        return (int)Math.sqrt(xDistance + yDistance);
    }
    
    // Prints a coordinate format for each point, i.e. (2, 7)
    @Override
    public String toString() {
        return String.format("(%d, %d)", m_x, m_y);
    }
    
}