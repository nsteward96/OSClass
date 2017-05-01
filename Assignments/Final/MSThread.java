import java.util.*;
import java.util.concurrent.*;

public class MSThread implements Callable<List<Integer>> {
    List<Integer> m_set1;
    List<Integer> m_set2;
    
    public MSThread(List<Integer> set1, List<Integer> set2) {
        m_set1 = set1;
        m_set2 = set2;
    }
    
    public List<Integer> call() {
        List<Integer> mergeSortedList = new ArrayList<Integer>();
        int lengthOfLoop = m_set1.size() + m_set2.size();
        // Take each set of numbers, sort it, and combine it.
        for (int i = 0; i < lengthOfLoop; i++) {
            int smallest_number;
            int setIndex = -1;
            int set = -1;
            
            // Initializes the smallest number accordingly
            if (m_set1.size() > 0) {
                smallest_number = m_set1.get(0);
                setIndex = 0;
                set = 1;
            } else {
                smallest_number = m_set2.get(0);
                setIndex = 0;
                set = 2;
            }
            
            // Checks through numbers in set 1 for the newest smallest number
            for (int j = 0; j < m_set1.size(); j++) {
                if (mergeSortedList.size() > 0) {
                    if (m_set1.get(j) >= mergeSortedList.get(mergeSortedList.size()-1) && m_set1.get(j) < smallest_number) {
                        smallest_number = m_set1.get(j);
                        setIndex = j;
                        set = 1;
                    }
                } else if (smallest_number > m_set1.get(j)) {
                    smallest_number = m_set1.get(j);
                    setIndex = j;
                    set = 1;
                }
            }
            
            // Checks through numbers in set 2 for the newest smallest number
            for (int k = 0; k < m_set2.size(); k++) {
                if (mergeSortedList.size() > 0) {
                    if (m_set2.get(k) >= mergeSortedList.get(mergeSortedList.size()-1) && m_set2.get(k) < smallest_number) {
                        smallest_number = m_set2.get(k);
                        setIndex = k;
                        set = 2;
                    }
                } else if (smallest_number > m_set2.get(k)) {
                    smallest_number = m_set2.get(k);
                    setIndex = k;
                    set = 2;
                }
            }
            // Removes the smallest number from whichever set it was found in.
            if (set == 1) {
                m_set1.remove(setIndex);
            } else if (set == 2) {
                m_set2.remove(setIndex);
            }
            // Add the smallest number to the merge-sorted list.
            mergeSortedList.add(smallest_number);
        }
        return mergeSortedList;
    }
    
}