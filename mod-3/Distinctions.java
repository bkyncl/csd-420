/*Distinctions.java
* Module 3 Assignment 
* Name: Brittany Kyncl
* Date: 3.24.23
* Course: CSD420
* Program to return ArrayList contianing only distinct elements from original list
*/
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Distinctions {
    // Method to return new ArrayList containing no duplicates from parsed list
    public static <E> ArrayList<E> removeDuplicates(ArrayList<E> list) {
        // Traditional loop based approach to remove duplicates 
        // Empty ArrayList for new distinct list
        ArrayList<E> distinctList = new ArrayList<>();
        
        // Traverse parsed list and only populate to distinctList non-duplicates
        for (E element : list) {
            if (!distinctList.contains(element)) {
                distinctList.add(element);
            }
        }
        // Return distinct list
        return distinctList;

        // Alternative option using the distinct method of stream to remove duplicates
        // return list.stream().distinct().collect(Collectors.toCollection(ArrayList::new));

        // Alternative option using LinkedHashSet to both preserve order and remove duplicates
        // return new ArrayList<>(new LinkedHashSet<>(list));
    }
    public static void main(String[] args) {
        // Create empty ArrayList for original non-distinct ArrayList
        ArrayList<Integer> originalList = new ArrayList<>();
        
        // Fill the original ArrayList with 50 random values from 1 to 20
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            originalList.add(random.nextInt(20) + 1);
        }
        
        System.out.println("Original List: " + originalList);
        
        // Call the removeDuplicates method to remove duplicates and store distinct array output
        ArrayList<Integer> uniqueList = removeDuplicates(originalList);
        
        System.out.println("Unique List: " + uniqueList);
        System.out.println();
        
        // Testing removeDuplicates function with varying data types
        // Create empty String ArrayList for original non-distinct String ArrayList
        ArrayList<String> originalStringList = new ArrayList<>();
        originalStringList.add("blue");
        originalStringList.add("blue");
        originalStringList.add("pink");
        originalStringList.add("pink");
        originalStringList.add("red");
        originalStringList.add("red");

        System.out.println("Original String List: " + originalStringList);
        
        // Call the removeDuplicates method to remove duplicates and store distinct array output
        ArrayList<String> uniqueStringList = removeDuplicates(originalStringList);
        
        System.out.println("Unique String List: " + uniqueStringList);
        System.out.println();

        // Create empty Double ArrayList for original non-distinct ArrayList
        ArrayList<Double> originalDoubleList = new ArrayList<>();
        
        // Fill the original ArrayList with 50 random double values from 1 to 5
        for (int i = 0; i < 50; i++) {
            double randomDouble = random.nextDouble(5) + 1;
            // Use BigDecimal to round the number to one decimal place
            randomDouble = BigDecimal.valueOf(randomDouble)
                                       .setScale(1, RoundingMode.HALF_UP)
                                       .doubleValue();
            originalDoubleList.add(randomDouble);
        }
        System.out.println("Original Double List: " + originalDoubleList);
        
        // Call the removeDuplicates method to remove duplicates and store distinct array output
        ArrayList<Double> uniqueDoubleList = removeDuplicates(originalDoubleList);
        
        System.out.println("Unique Double List: " + uniqueDoubleList  );
        System.out.println();
    }
    
}
