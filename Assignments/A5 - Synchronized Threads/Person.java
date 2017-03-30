import java.util.*;
import java.util.concurrent.*;

public abstract class Person implements Callable<Boolean> {
    protected final Semaphore m_tobacco;
    protected final Semaphore m_paper;
    protected final Semaphore m_matches;
    protected final Semaphore m_dealer;
	
	public Person(Semaphore t, Semaphore p, Semaphore m, Semaphore d) {
	    m_tobacco = t;
	    m_paper = p;
	    m_matches = m;
	    m_dealer = d;
	}
	
	public abstract Boolean call();
}