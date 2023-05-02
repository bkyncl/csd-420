/*TimeTraverse.java
* Module 4 Assignment 
* Name: Brittany Kyncl
* Date: 3.30.23
* Course: CSD420
* Write a test program that stores 50,000 integers in LinkedList and test the time to traverse the list using an iterator vs. using the get(index) method.
* Then, test your program storing 500,000 integers.
* After completing this program and having tested both values, in your comments, explain the results and discuss the time taken using both values and their difference with the get(index) approach.
* Write test code that ensures the code functions correctly.
*/
import java.util.*;
public class TimeTraverse {
    // Define an array to store the sizes of the LinkedLists to be tested
    public static final int[] STORAGE_SIZES = {50000, 500000};

    public static  void main(String[] args) {
        
        // Get specified number of test iterations (One test= (4 results) comparable traversal time using iterator vs. get(i) on both 50,000 and 500,000 collection size)
        int numIterations = 1;
        // Read the user input using Scanner and validate it, ensuring that it is a positive integer value
        try (Scanner scanner = new Scanner(System.in)) {
            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.print("Enter number of test cycles you wish to perform: ");
                    numIterations = Integer.parseInt(scanner.nextLine());
                    if (numIterations <= 0) {
                        throw new IllegalArgumentException("Number of Iterations must be > 0");
                    }
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Input");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        // For each iteration specified by the user, run tests on each storage size in the array
        for (int i = 0; i < numIterations; i++) {
            // In each test iteration messages indicating the beginning and end of the test
            System.out.println("---------------Begin Test " + i + "---------------");

            for (int storage : STORAGE_SIZES) {
                // For each storage size in the array, print a message indicating the size of the LinkedList being tested
                System.out.println("Testing traversal time of " + storage + " integers stored in LinkedList");

                // Create two instances of IteratorTest, one for testing the iterator and the other for testing get(index)
                TraverseTest iteratorTest = new TraverseTest(storage);
                System.out.println("Start iterator");
                long iteratorTime = iteratorTest.testIterator();  // Call testIterator() method and print elapsed time
                System.out.println("End iterator");
                System.out.println("total iterator time: " + iteratorTime);

                
                TraverseTest getIndexTest = new TraverseTest(storage);
                System.out.println("Start .get()");
                long getIndexTime = getIndexTest.testGetIndex();  // Call the testGetIndex() method and print elapsed time
                System.out.println("End .get()");
                System.out.println("total get() time: " + getIndexTime);

                // Print difference in time elapsed between iterator and get(index) test
                System.out.println("Time difference: " + (getIndexTime - iteratorTime));
                System.out.println();
            }
            System.out.println("---------------End Test " + i + "---------------");
        }

        /*  --RESULTS DISCUSSIOIN--
        (sample test result)
        ---------------Begin Test 0---------------
        Testing traversal time of 50000 integers stored in LinkedList
        Start iterator
        End iterator
        total time: 5
        Start .get()
        End .get()
        total time: 994
        Time difference: 989

        Testing traversal time of 500000 integers stored in LinkedList
        Start iterator
        End iterator
        total time: 10
        Start .get()
        End .get()
        total time: 104841
        Time difference: 104831
        ---------------End Test 0---------------
         * After having tested the time taken to traverse a LinkedList with both sizes, 50,000 and 500,000 using either an iterator vs. get(index).
         * We can see that the traversal time using the iterator is consistently faster than using get(index) for both sizes, 50,000 and 500,000.
         * The difference in traverse time between the iterator and the get(index) method becomes more signifigant the larger the list size.
         * Where if the LinkedList  were to get smaller and smaller both methods would eventually take about the same time because the
         * time it would take to perform a lookup operation vs the time to traverse the entire list would have a small difference.
         * However, as the number of elements increases the traverse time using get(index) signifigantly increases compared to using an iterator.
         * The result of the iterator being faster is because it does not require the LinkedList to perform any lookup operation.
         * An iterator provides a way to access elements without a lookup operation, it instead points to the next element in the list and retrieves it.
         * Where on the other hand the get(index) has to search through the list to find the specified element at the index.
         * In conclusion, when a liknedList is larger using an iterator becomes more efficent compared to using get(index).
         */
    }
}
// Stopwatch class that can be used to time the execution of code
class Stopwatch {
    private long startTime;   // var to hold start time
    private long endTime;     // var to hold end time
    private boolean running;  // flag to indicate if stopwatch is running

    // Method to start stopwatch
    public void start() {
        if (!running) {
            startTime = System.currentTimeMillis(); // record time in milliseconds
            running = true; // set running flag to true
        }
    }
    // Method to stop stopwatch
    public void stop() {
        if (running) {
            endTime = System.currentTimeMillis(); // record time
            running = false; // set running flag false
        }
    }
    // Method to return elapsed time between start and stop
    public long getElapsedTime() {
        if (running) {
            return (System.currentTimeMillis() - startTime);
        } else {
            return (endTime - startTime);
        }
    }
    // Method to reset stopwatch time to zero and running flag to false
    public void reset() {
        startTime = 0;
        endTime = 0;
        running = false;
    }
}
/*This testing class provides a way to test the performance of an Iterator and .get() method
for a LinkedList of a given size.*/
class TraverseTest {
    private int storageSize;          // Size of LinkedList to be tested
    private LinkedList<Integer> list; // LinkedList to be tested
    private Stopwatch timer;          // stopwatch to measure test time

    // Iteratortest object constructor taking in specified size
    public TraverseTest(int storageSize) {
        this.storageSize = storageSize;
        list = new LinkedList<>();
        for (int i = 0; i < storageSize; i++) {
            list.add(i);
        }
        timer = new Stopwatch();
    }
    // Test performance of an iterator by iterating over LinkedList and print elapsed time taken to complete
    public long testIterator() {
        Iterator<Integer> iterator = list.iterator();
        timer.start();
        while (iterator.hasNext()) {
            iterator.next();
        }
        timer.stop();
        long timeElapsed = timer.getElapsedTime();
        resetTimer();
        return timeElapsed;
    }
    // Test performance of get() method by accessing every element in LinkedList and print elapsed time taken to complete
    public long testGetIndex() {
        timer.start();
        for (int i = 0; i < storageSize; i++) {
            list.get(i);
        }
        timer.stop();
        long timeElapsed = timer.getElapsedTime();
        resetTimer();
        return timeElapsed;
    }
    // Reset the stopwatch to zero
    public void resetTimer() {
        timer.reset();
    }
}