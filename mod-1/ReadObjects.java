/*ReadObjects.java
* Module 1 Assignment 
* Name: Brittany Kyncl
* Date: 3.17.23
* Course: CSD420
* Program reads and displays data from brittdatafile.dat
*/
import java.util.*;
import java.io.*;

public class ReadObjects {
	
	public static void main(String[] args) {
        // Read the ('fileName' brittdatafile.dat) file and display the data
        String fileName = "brittdatafile.dat";
		try {
			FileInputStream fileInputStream = new FileInputStream(fileName);
			DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            
            // Read five integers from the file and store them in an array then print
			int[] readIntegers = new int[5];
			for (int i=0; i<readIntegers.length; i++) {
				readIntegers[i] = dataInputStream.readInt();
			}
            System.out.println("Integers: " + Arrays.toString(readIntegers));

            // Read a long integer from the file, which represents date then print
			long readDate = dataInputStream.readLong();
			System.out.println("Date: " + new Date(readDate));

            // Read five doubles from the file and store them in an array then print
			double[] readDoubles = new double[5];
			for (int i=0; i<readDoubles.length; i++) {
				readDoubles[i] = dataInputStream.readDouble();
			}
            System.out.println("Doubles: " + Arrays.toString(readDoubles));

			dataInputStream.close();
            fileInputStream.close();

		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
    }
}