/*StoreObjects.java
* Module 1 Assignment 
* Name: Brittany Kyncl
* Date: 3.17.23
* Course: CSD420
* Program stores array of 5 random ints, date obj for current time, and array 4 random doubles to brittdatafile.dat
*/
import java.io.*;
import java.util.*;

public class StoreObjects {
	public static void main(String[] args) {

		// Create an array of 5 random integers
		int[] randInts = new int[5];
		// Create an array of 5 random doubles
		double[] randDoubles = new double[5];

		// Generate random integers and doubles using Random class
		Random rand = new Random();
		for (int i=0; i<randInts.length; i++) {
			randInts[i] = rand.nextInt(50);
			randDoubles[i] = rand.nextDouble() * 50;
		}

		// Create a Date object instance using the current date
		Date currentDate = new Date();

		// Write the data to 'fileName' brittdatafile.dat
		String fileName = "brittdatafile.dat";
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(fileName,false);
			DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
			
			// Write the array of 5 random integers to the file
			for (int i=0; i<randInts.length; i++) {
				dataOutputStream.writeInt(randInts[i]);
			}

			// Write the current date to the file as a long integer
			dataOutputStream.writeLong(currentDate.getTime());

			// Write the array of 5 random doubles to the file
			for (int i=0; i<randDoubles.length; i++) {
				dataOutputStream.writeDouble(randDoubles[i]);
			}
			dataOutputStream.close();
			fileOutputStream.close();

		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
}