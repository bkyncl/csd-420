/*BrittThreeThreads.java
* Module 8 Assignment 
* Name: Brittany Kyncl
* Date: 4.22.23
* Course: CSD420
* The program creates three threads: PrintLetter, PrintChar, and PrintNum, each of which generate a set 
* of characters, and a QueueConsumer thread, which continuously takes items from the output queue and appends 
* them to the JTextArea. The ArrayBlockingQueue is used to store the output from each of the three threads which
* provides a shared buffer to store output from each thread in a thread-safe way, avoiding race conditions and 
* synchronization issues that may occur when threads directly access the JTextArea.
* The program uses ExecutorService to create and manage the threads, and once all the threads are created, 
* it shuts down the executor.
* PrintLetter thread displays random letters a -z 
* PrintChar thread  displays random nonalphanumeric characters from a prefixed list
* PrintNum thread displays random numbers 0-9
* Display a minimum of 10,000 of each of the three sets.
*/

import java.util.Random;
import java.util.concurrent.*;
import javax.swing.*;

public class BrittThreeThreads extends JFrame {

    public static void main(String[] args) {
        new BrittThreeThreads(); 
    }

    // Jtextarea where output will be displayed
    private JTextArea outputArea;
    // Queue used to store output from producer threads
    private ArrayBlockingQueue<String> queue;
    // Number of results to output for each thread
    private final int NUMBEROFRESULTS = 10000;

    /**
     * Constructor class initializes the JFrame, JTextArea, and blocking queue.
     * Calls to executes threads to output characters and consumer thread to display output.
     */
    public BrittThreeThreads() {
        // Set the title of the JFrame
        super("Three Threads Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set close operation of JFrame
        setSize(600, 600);
        setLocationRelativeTo(null); // Set JFrame location to center of screen
        
        // Create a JTextArea contained within a scroll pane to display the output
        outputArea = new JTextArea();
        outputArea.setEditable(false); // set text area to not editable
        getContentPane().add(new JScrollPane(outputArea));

        // Make the frame visible
        setVisible(true);

        // Create an array blocking queue with capacity of NUMBEROFRESULTS items
        queue = new ArrayBlockingQueue<>(NUMBEROFRESULTS);

        // Execute threads to output characters and consumer thread to display output
        executeThreads();
        
    }
    /**
     * Executes producer and consumer threads to output and display characters respectively.
     * Uses a fixed thread pool with a maximum of four threads.
     * The producer threads include PrintLetter, PrintChar, and PrintNum.
     * The consumer thread, QueueConsumer, takes items from the queue and appends them to the JTextArea.
     */
    private void executeThreads() {
        // Create a fixed thread pool with maximum four threads
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Submit the producer tasks to the executor as runnable instances
        // The PrintLetter, PrintChar, and PrintNum classes implement Runnable and each will output a set of characters
        // The queue is used to store the output from each of the three threads
        executor.execute(new PrintLetter());
        executor.execute(new PrintChar());
        executor.execute(new PrintNum());

        // Submit the consumer task to the executor to display the output from the queue in the JTextArea
        // The QueueConsumer class implements Runnable and continuously takes items from the queue and appends them to the JTextArea
        executor.execute(new QueueConsumer());

        // Shut down the executor
        executor.shutdown();
    }
    // This class implements Runnable and puts random letter characters a-z into a queue
    private class PrintLetter implements Runnable {

        private Random random = new Random();

        @Override
        public void run() {
            // Loop for NUMBEROFRESULTS iterations
            for (int i = 0; i < NUMBEROFRESULTS; i++) {
                char letter = (char) (random.nextInt(26) + 'a'); // Generate random letter between 'a' and 'z'
                try {
                    // Put the item into the queue
                    queue.put("Letter Thread:  "+ letter + "  : Loop#: " + i + "\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // This class implements Runnable and puts random non-alphanumeric characters into a queue
    private class PrintChar implements Runnable {

        private Random random = new Random();
        private char[] allowedChars = new char[] {'!', '@', '#', '$', '%', '&', '*','~','?','=','+'};

        @Override
        public void run() {
            // Loop for NUMBEROFRESULTS iterations
            for (int i = 0; i < NUMBEROFRESULTS; i++) {
                char asciiChar = allowedChars[random.nextInt(allowedChars.length)]; // Generate random ASCII character from the allowed set
                try {
                    // Put the item into the queue
                    queue.put("\t\t" + "ASCII Thread:  " + asciiChar + "  : Loop#: " + i + "\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // This class implements Runnable and puts random numbers 1-9 into a queue
    private class PrintNum implements Runnable {

        private Random random = new Random();

        @Override
        public void run() {
            // Loop for NUMBEROFRESULTS iterations
            for(int i=0;i<NUMBEROFRESULTS;i++) {
                int num = random.nextInt(10); // Generate random numbers 1-9
                try {
                    // Put the item into the queue
                    queue.put("\t\t\t\t" + "Number Thread:  " + num + "  : Loop#: " + i + "\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // This class takes items from the queue and appends them to the JTextArea for display
    private class QueueConsumer implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    // Take an item from the queue and append it to the JTextArea
                    outputArea.append(queue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

