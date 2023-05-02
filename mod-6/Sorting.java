/*Sorting.java
* Module 6 Assignment 
* Name: Brittany Kyncl
* Date: 4.7.23
* Course: CSD420
* Implementation of bubble sort algorithm with generic types and two overloaded methods:
* one using comparable interface for sorting the other using the comparator interface for sorting
* The bubble sort method is tested on arrays of different data types (Integer, Double, String, Char, and GeometricObject)
*/
import java.util.*;


// Sorting class contains two overloaded bubblesort methods
public class Sorting {
    // Method for sorting an array implementing bubblesort algorithm using comparable interface
    public static <E extends Comparable<E>> void bubbleSort(E[] list) {
        // iterate through the array and swap elements if they are in the wrond order
        for (int i = 0; i < list.length-1; i++) {
            for (int j = 0; j < list.length-i-1; j++) {
                if (list[j].compareTo(list[j+1]) > 0) {
                // swap list[j] and list[j+1]
                E temp = list[j];
                list[j] = list[j+1];
                list[j+1] = temp;
                }
            }
        }
    }

    // Method for sorting an array implementing bubblesort algorithm using comparator interface
    public static <E> void bubbleSort(E[] list, Comparator<? super E> comparator) {
        int i = 0;  // counter for number of iterations over array
        // flag to keep track of swaps made during iteration
        // if no swaps, then array sorted and loop exit early
        boolean swapped = true; 

        // Iterate through the array and swap elements if they are in the wrong order
        while (swapped) {
            swapped = false;
            for (int j = 0; j < list.length - i - 1; j++) {
                if (comparator.compare(list[j], list[j + 1]) > 0) {
                    E temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                    swapped = true;
                }
            }
            i++;
        } 
    }
    public static void main(String[] args) {
        // create integer array
        Integer[] intArray = {5, 3, 8, 2, 1, 9, 4};
        // create double array
        Double[] doubleArray = {5.5, 3.2, 8.0, 2.6, 1.12, 9.50, 4.405};
        // create string array
        String[] stringArray = {"Green", "apple", "blueberry", "Pineapple", "choreography", "hat", "drum"};
        // create character array
        Character[] charArray = {'j', 'A', 'c', 'r', 'b', '@', '?', 'G', '&'};
        // create geometric object array
        GeometricObject[] objArray = {new Rectangle(3,4), new Circle(6),new Circle(5), new Rectangle(2,3)};

        // Print unsorted intarray then sorted intarray
        System.out.println("Sorted Integer array using Comparable:");
        System.out.print("[Before sort] =  ");
        Arrays.stream(intArray).forEach(i -> System.out.print(i + " "));
        // sort integer array using Comparable
        bubbleSort(intArray);
        System.out.print("\n[After sort] = ");
        Arrays.stream(intArray).forEach(i -> System.out.print(i + " "));
        System.out.println("\n");

        // Print unsorted doublearray then sorted doublearray
        System.out.println("Sorted Double array using Comparable:");
        System.out.print("[Before sort] =  ");
        Arrays.stream(doubleArray).forEach(i -> System.out.print(i + " "));
        // sort double array using Comparable
        bubbleSort(doubleArray);
        System.out.print("\n[After sort] = ");
        Arrays.stream(doubleArray).forEach(i -> System.out.print(i + " "));
        System.out.println("\n");
        
        // Print unsorted stringArray then sorted stringArray
        System.out.println("Sorted String array alphabetically using Comparator:");
        System.out.print("[Before sort] =  ");
        Arrays.stream(stringArray).forEach(i -> System.out.print(i + " "));
        // sort string array using alphabetical comparator
        bubbleSort(stringArray, (s1, s2) -> s1.compareToIgnoreCase(s2));
        System.out.print("\n[After sort] = ");
        Arrays.stream(stringArray).forEach(i -> System.out.print(i + " "));
        System.out.println("\n");

        // Print unsorted stringArray then sorted stringArray
        System.out.println("Sorted String array by length using Comparator:");
        System.out.print("[Before sort] =  ");
        Arrays.stream(stringArray).forEach(i -> System.out.print(i + " "));
        // sort string array using string length comparator
        bubbleSort(stringArray, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
        System.out.print("\n[After sort] = ");
        Arrays.stream(stringArray).forEach(i -> System.out.print(i + " "));
        System.out.println("\n");

        // Print unsorted chararray then sorted chararray
        System.out.println("Sorted Character array using Comparable:");
        System.out.print("[Before sort] =  ");
        Arrays.stream(charArray).forEach(i -> System.out.print(i + " "));
        // sort character array using Comparable
        bubbleSort(charArray);
        System.out.print("\n[After sort] = ");
        Arrays.stream(charArray).forEach(i -> System.out.print(i + " "));
        System.out.println("\n");

        // Print unsorted geometric object array then sorted geometric object array
        System.out.println("Sorted object array by area using Comparable:");
        System.out.print("[Before sort] =  ");
        Arrays.stream(objArray).forEach(i -> System.out.println(i));
        // sort geometric object array using area Comparable (implementation provided in GeometricObject)
        bubbleSort(objArray);
        System.out.print("\n[After sort] = ");
        Arrays.stream(objArray).forEach(i -> System.out.println(i));
        System.out.println("\n");
    }
}
