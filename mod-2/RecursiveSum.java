/*RecursiveSum.java
* Module 2 Assignment 
* Name: Brittany Kyncl
* Date: 3.17.23
* Course: CSD420
* Program using recursive method to return the sum of the following type inputs
* m(i) = 1/2 + 2/3 + 3/4 + 4/5 + 5/6 … i/(i + 1)
* Using three different input finishing values
* m(4) m(9) m(14)
*/
public class RecursiveSum {
	//recursive method to compute series
	public static double sumSeries(double i) {
		//base case: when i=1, return the first term of the series
        if (i==1) {
            return 0.5;
        } 
		//recursive case: sum the current term and the previous term in the series
		else {
            return i / (i + 1) + sumSeries(i - 1);
        }
    }
    public static void main(String[] args) {
        // Test the program with specified input values
        System.out.println("\n------Test 1------");
        System.out.println(sumSeries(4)); // Output: 2.716666666666667
        System.out.println(sumSeries(9)); // Output: 7.071031746031746
        System.out.println(sumSeries(14)); // Output: 11.681771006771008

		// Testing m(i) for i=1,...14
        System.out.println("\n------Test 2------");
		for(int i=1; i<=14; i++) {
            // Print the index of the current term and the value of the sum of the series up to that term
			System.out.println(i + ": " + sumSeries(i));
		}
    }
}