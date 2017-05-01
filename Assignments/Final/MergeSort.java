// Merge Sort
// Generates a list of random numbers in a random order in separate arrays, 
// then merges the arrays while sorting, through threads.
// Written by Nate Steward, finished 5/1/17.

import java.util.*;
import java.util.concurrent.*;

public class MergeSort {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        MergeSort program = new MergeSort();
        program.go();
    }
    
    public void go() throws InterruptedException, ExecutionException {
        List<List<Integer>> sets = new ArrayList<List<Integer>>();
        Scanner keyboard = new Scanner(System.in);
        Random rand = new Random();
        // Program running, prompt user for # of numbers to start with
        System.out.print("Running... Please enter a number that is a power of 2 (4, 8, 16, 32, etc): ");
        int userInput = keyboard.nextInt();
        // Add generated sets of numbers to the list of sets.
        for (int i = 0; i < userInput; i++) {
            int number = rand.nextInt(userInput * 4);
            List<Integer> numberSet = new ArrayList<Integer>();
            numberSet.add(number);
            sets.add(numberSet);
        }
        // Print unorganized list
        System.out.println("Unorganized list of numbers: ");
        for (List<Integer> set : sets) {
            System.out.print(set.get(0) + " ");
        }
        // Run the sort program
        for (int i = userInput; i > 1; i/=2) { // How many numbers there are
            List<Callable<List<Integer>>> threads = new ArrayList<Callable<List<Integer>>>();
            for (int j = 0; j < i/2; j++) { // Create threads, add to list of threads
                Callable<List<Integer>> task = new MSThread(sets.get(j*2), sets.get((j*2)+1));
                threads.add(task);
            }
            ExecutorService executor = Executors.newFixedThreadPool(64);
            List<Future<List<Integer>>> futures = executor.invokeAll(threads);
            sets.clear();
            for (Future<List<Integer>> f : futures) {
                sets.add(f.get());
            }
            executor.shutdown();
        }
        // Print organized list
        System.out.println("\nOrganized list of numbers: ");
        System.out.println(sets.get(0));
    }
}