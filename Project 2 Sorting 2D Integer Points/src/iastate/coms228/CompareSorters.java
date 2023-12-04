package edu.iastate.cs228.hw2;

/**
 *  
 * @author Christian Lapnow
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		// TODO 
		// 
		// Conducts multiple rounds of comparison of four sorting algorithms.  Within each round, 
		// set up scanning as follows: 
		// 
		//    a) If asked to scan random points, calls generateRandomPoints() to initialize an array 
		//       of random points. 
		// 
		//    b) Reassigns to the array scanners[] (declared below) the references to four new 
		//       RotationalPointScanner objects, which are created using four different values  
		//       of the Algorithm type:  SelectionSort, InsertionSort, MergeSort and QuickSort. 
		// 
		//
		
		
		System.out.println("(1) generate an array of random points OR (2) generate anarray from a file input OR (3) exit the program.");
		
		Scanner scnrIn = new Scanner(System.in);
		PointScanner[] scanners = new PointScanner[4];
		
		// Create the main loop
		while(true)
		{
			int userPick = scnrIn.nextInt();
			// You can seed this for testing
			Random rand = new Random();
				
				if (userPick == 1) 
				{
					
					// Option 1:  Random point selection					
					System.out.println("How many random numbers would you like to gernerate? ");
					int numRands = scnrIn.nextInt();
					
					// Generate the random points
					Point[] pointarr = generateRandomPoints(numRands, rand);
				
					// Initialize the array using randomly generated points
					scanners[0] = new PointScanner(pointarr, Algorithm.SelectionSort);
					scanners[1] = new PointScanner(pointarr, Algorithm.InsertionSort);
					scanners[2] = new PointScanner(pointarr, Algorithm.MergeSort);
					scanners[3] = new PointScanner(pointarr, Algorithm.QuickSort);
					
				} 
				else if (userPick == 2)
				{
					// Option 2:  Read points from file
					System.out.print("File name: ");
					String fileName = scnrIn.next();
					
					// Initialize the array using the file of points
					scanners[0] = new PointScanner(fileName, Algorithm.SelectionSort);
					scanners[1] = new PointScanner(fileName, Algorithm.InsertionSort);
					scanners[2] = new PointScanner(fileName, Algorithm.MergeSort);
					scanners[3] = new PointScanner(fileName, Algorithm.QuickSort);
				} 
				else 
				{
					System.exit(0);
			}
			
			// Iterate through the array scanners[]
			for (int i = 0; i < scanners.length; i++) 
			{
				// Have every scanner call the scan() method
				scanners[i].scan();
				
				// Optional write to file - which isn't working
//				scanners[i].writePointsToFile();
				
				// Have every scanner call the draw() method //////////////////**********************///////////////(((********
				//scanners[i].draw();
			}
			
			// Print the summary
			System.out.println("-------------------------------------------------------------");
			System.out.printf("%-14s %-8s %-4s \n", "algorithm", "size", "time (ns)");
			System.out.println("-------------------------------------------------------------");
			
			// Loop through each scanner to display stats
			for (int i = 0; i < scanners.length; i++) 
			{
				System.out.println(scanners[i].stats());
			}
			
			System.out.println("--------------------------------------");
			
		}
		
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		Point[] points = new Point[numPts];
		int x;
		int y;
		for(int i = 0; i < numPts; i++) 
		{
			x = rand.nextInt(101) - 50;
			y = rand.nextInt(101) - 50;
			
			Point randP = new Point(x,y);
			points[i] = randP;
		}
			return(points);
		}
	}
	
