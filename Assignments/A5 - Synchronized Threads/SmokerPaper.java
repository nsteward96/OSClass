import java.util.*;
import java.util.concurrent.*;

public class SmokerPaper extends Person {
	
	public SmokerPaper(Semaphore t, Semaphore p, Semaphore m, Semaphore d) {
        super(t, p, m, d);
    }
	
    private final static int BUILD_CIGARETTE_TIME = 1000;
	
	public Boolean call() {
	    try {
	    	
	        while (true) {
	        	m_matches.acquire();
	        	if (m_tobacco.tryAcquire()) {
	        		System.out.println("Paper Smoker is smoking . . .");
	        		Thread.sleep(BUILD_CIGARETTE_TIME);
	        		m_dealer.release();
	        	} else {
	        		m_matches.release();
	        	}
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return true;
	}
}