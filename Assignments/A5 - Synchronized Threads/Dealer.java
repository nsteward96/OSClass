import java.util.*;
import java.util.concurrent.*;

public class Dealer extends Person {
    
    public Dealer(Semaphore t, Semaphore p, Semaphore m, Semaphore d) {
        super(t, p, m, d);
    }
    
    public Boolean call() {
        try {
            Random random = new Random();
            int flip = -1;
            double waitTime = -1.0;
            while (true) {
                m_dealer.acquire();
                waitTime = (random.nextDouble() / 2.0) + 0.5;
                flip = random.nextInt(3);
                if (flip == 0) {
                    System.out.println("Dealer is putting paper on the table...");
                    m_paper.release();
                    System.out.println("Dealer is putting matches on the table...");
                    m_matches.release();
                } else if (flip == 1) {
                    System.out.println("Dealer is putting tobacco on the table...");
                    m_tobacco.release();
                    System.out.println("Dealer is putting matches on the table...");
                    m_matches.release();
                } else if (flip == 2) {
                    System.out.println("Dealer is putting tobacco on the table...");
                    m_tobacco.release();
                    System.out.println("Dealer is putting paper on the table...");
                    m_paper.release();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}