/*Filter.java
* Module 5 Assignment 
* Name: Brittany Kyncl
* Date: 4.7.23
* Course: CSD420
* Program that read words from txt file and display all non-duplicate words in ascending order, then descending order with use of TreeSet
*/
import java.util.*;
import java.util.function.Function;
import java.io.*;

// Filter class with methods for reading elements from a text file and returning elements in an ArrayList
// and for filtering out duplicates/sorting collection and printing collection
public class Filter {
    public static void main (String[] args) {
        // filename containing words
        String wordfile = "collection_of_words.txt";
        
        // Test to run on word text file (String Values)
        StringTesting.test(wordfile);

        // Uncomment to run optional test on filter functionality with text file containing double values
        // filename containing numbers
        // String numberfile = "collection_of_numbers.txt";
        // // Optional Test to run on number text file (Double Values)
        // NumberTesting.test(numberfile);
    }

    // Remove duplicates and Sort elements in ArrayList in natural ascending order by returning elements in TreeSet
    public static <E extends Comparable<? super E>> TreeSet<E> sortAsc(ArrayList<E> list) {
        // Create a new TreeSet and pass the ArrayList to it as a constructor parameter
        TreeSet<E> sortedAsc = new TreeSet<>(list);
        return sortedAsc;     
    }
    // Remove duplicates and Sort elements in ArrayList in descending order by returning elements in TreeSet
    public static <E extends Comparable<? super E>> TreeSet<E> sortDesc(ArrayList<E> list) {
        // Create a new TreeSet with a reverse order comparator and pass the ArrayList to it as a constructor parameter
        TreeSet<E> sortedDesc = new TreeSet<>(Collections.reverseOrder());
        sortedDesc.addAll(list);
        return sortedDesc;
    }
    // Print all elements from the iterable collection
    public static  <E> void printList(Iterable<E> collection) {
        // Use the forEach method of the Iterable interface to print each element of collection
        collection.forEach(System.out::println);
    }
    // Reads elements from text file and converts each element to specified type using converter function returning an ArrayList 
    public static <E> ArrayList<E> readFromFile(String filename, Function<String, E> converter) throws FileNotFoundException {
        // Create a new ArrayList to hold the elements read from the file
        ArrayList<E> elements = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNext()) {
                // Convert the element to the specified type using the supplied converter function
                E  element = converter.apply(scanner.next().toLowerCase());
                elements.add(element);
            }
        }
        return elements;
    }
}
// Class for testing filter methods with data from text file containing words
class StringTesting {
    public static void test(String filename) {
        // Testing for file containing string values
        try {
            // Read words from file and assign to arraylist
            ArrayList<String> words = Filter.readFromFile(filename, s -> s);
            // Remove duplicates from list
            TreeSet<String> sortedAsc= Filter.sortAsc(words);           // Sort unique list ascending
            TreeSet<String> sortedDesc = Filter.sortDesc(words);        // Sort unique list descending

            // Print unique list in ascending order
            System.out.println("------ Unique items sorted in ascending order ------");
            Filter.printList(sortedAsc);
            System.out.println("------------------ End ------------------");

            // Print unique list in descending order
            System.out.println("------ Unique items sorted in descending order ------");
            Filter.printList(sortedDesc);
            System.out.println("------------------ End ------------------");

            // Test code to ensure the program functions correctly
            System.out.println("------------------ Testing ------------------");
            System.out.println("Number of words in file: " + words.size());
            System.out.println("Number of unique words: " + sortedAsc.size());
            System.out.println("First word in ascending order: " + sortedAsc.first());
            System.out.println("Last word in ascending order: " + sortedAsc.last());
            System.out.println("First word in descending order: " + sortedDesc.first());
            System.out.println("Last word in descending order: " + sortedDesc.last());
            System.out.println("------------------ End Testing ------------------");

        } catch (FileNotFoundException e) {
            System.out.println("Could not find file " + filename);
            return;
        }
    }
}
// Class for testing filter methods with data from text file containing numbers
class NumberTesting {
    public static void test(String filename) {
        // Testing for text file containing double values
        try {
            // Read numbers from file and assign to arraylist
            ArrayList<Double> numbers = Filter.readFromFile(filename, Double::parseDouble);
            // Remove duplicates from list
            TreeSet<Double> sortedAsc= Filter.sortAsc(numbers);             // Sort unique list ascending
            TreeSet<Double> sortedDesc = Filter.sortDesc(numbers);          // Sort unique list descending

            // Print unique list in ascending order
            System.out.println("------ Unique items sorted in ascending order ------");
            Filter.printList(sortedAsc);
            System.out.println("------------------ End ------------------");

            // Print unique list in descending order
            System.out.println("------ Unique items sorted in descending order ------");
            Filter.printList(sortedDesc);
            System.out.println("------------------ End ------------------");

            // Test code to ensure the program functions correctly
            System.out.println("------------------ Testing ------------------");
            System.out.println("Number of numbers in file: " + numbers.size());
            System.out.println("Number of unique numbers: " + sortedAsc.size());
            System.out.println("First number in ascending order: " + sortedAsc.first());
            System.out.println("Last number in ascending order: " + sortedAsc.last());
            System.out.println("First number in descending order: " + sortedDesc.first());
            System.out.println("Last number in descending order: " + sortedDesc.last());
            System.out.println("------------------ End Testing ------------------");

        } catch (FileNotFoundException e) {
            System.out.println("Could not find file " + filename);
            return;
        }
    }
}
