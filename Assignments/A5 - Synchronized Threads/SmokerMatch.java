import java.util.*;
import java.util.concurrent.*;

public class SmokerMatch extends Person {
	
	public SmokerMatch(Semaphore t, Semaphore p, Semaphore m, Semaphore d) {
        super(t, p, m, d);
    }
	
    private final static int BUILD_CIGARETTE_TIME = 1000;
    
    public Boolean call() {
	    try {
	        while (true) {
	        	m_tobacco.acquire();
	        	if (m_paper.tryAcquire()) {
	        	    System.out.println("Matches Smoker is smoking . . .");
	        	    Thread.sleep(BUILD_CIGARETTE_TIME);
	        	    m_dealer.release();
	        	} else {
	        	    m_tobacco.release();
	        	}
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return true;
	}
}