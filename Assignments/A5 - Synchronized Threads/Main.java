import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Running...");
        Semaphore dealer = new Semaphore(1, true);
        Semaphore tobacco = new Semaphore(0, true);
        Semaphore paper = new Semaphore(0, true);
        Semaphore matches = new Semaphore(0, true);
        List<Callable<Boolean>> threads = new ArrayList<Callable<Boolean>>();
        
        threads.add(new SmokerPaper(tobacco, null, matches, dealer));
        threads.add(new SmokerTobacco(null, paper, matches, dealer));
        threads.add(new SmokerMatch(tobacco, paper, null, dealer));
        threads.add(new Dealer(tobacco, paper, matches, dealer));
        
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        executor.invokeAll(threads);
        executor.shutdown();
    }
}