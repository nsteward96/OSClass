import java.util.*;
import java.util.concurrent.*;

public class SmokerTobacco extends Person {
	
	public SmokerTobacco(Semaphore t, Semaphore p, Semaphore m, Semaphore d) {
        super(t, p, m, d);
    }
	
    private final static int BUILD_CIGARETTE_TIME = 1000;
    
    public Boolean call() {
	    try {
	        while (true) {
	        	m_paper.acquire();
	        	if (m_matches.tryAcquire()) {
	        	    System.out.println("Tobacco Smoker is smoking . . .");
	        	    Thread.sleep(BUILD_CIGARETTE_TIME);
	        	    m_dealer.release();
	        	} else {
	        	    m_paper.release();
	        	}
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return true;
	}
}