package edu.iastate.cs228.hw2;

import java.io.BufferedWriter;
import java.io.File;

/**
 * 
 * @author Christian Lapnow
 *
 */

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * 
 * This class sorts all the points in an array by polar angle with respect to a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
	
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[]. Set outputFileName. 
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		points = new Point[pts.length];
		sortingAlgorithm = algo;
		for (int i = 0; i < pts.length; i++) {
			points[i] = pts[i];
		}
		// First argument check.
		
		}
	
	/**
	 * This constructor reads points from a file. Set outputFileName. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		// Load the file
		sortingAlgorithm = algo;
		int x;
		int y;
		File file = new File(inputFileName);
		Scanner scnr = new Scanner(file);
		
		// Create a temporary array to house the integers from the file
		ArrayList<Point> tempArr = new ArrayList<Point>();
		
		// Load the integers from the file.  Will not load a file with improper values (float, character, etc.)
		while (scnr.hasNextInt()) 
		{
			x = scnr.nextInt();
			y = scnr.nextInt();
			tempArr.add(new Point(x,y));
		}
		points = new Point[tempArr.size()];
		
		
		for (int i = 0; i < tempArr.size(); i++) 
		{
			// Create the new Point object with sequential values in temp
			points[i] = tempArr.get(i);
			
		
		}
		
		scnr.close();
	}

	
	/**
	 * Carry out three rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates. 
	 *     d) Sort points[] again by the polar angle with respect to medianCoordinatePoint.
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting. Copy the sorting result back onto the array points[] by calling 
	 * the method getPoints() in AbstractSorter. 
	 *      
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		AbstractSorter aSorter = null;
		long startTime;
		
		// Create an object for the supertype AbstractSorter to reference
		if (sortingAlgorithm == Algorithm.SelectionSort) 
		{
			aSorter = new SelectionSorter(this.points);
		} 
		else if (sortingAlgorithm == Algorithm.InsertionSort) 
		{
			aSorter = new InsertionSorter(this.points);
		} 
		else if (sortingAlgorithm == Algorithm.MergeSort) 
		{
			aSorter = new MergeSorter(this.points);
		} 
		else 
		{
			aSorter = new QuickSorter(this.points);
		}
		
		
		
		// for each of the three rounds of sorting, have aSorter do the following: 
		// Call setComparator() with an argument of 0, 1, or 2.  In case it is 2, must have made
			// the call to setReferencePoint(medianCoordinatePoint) already.
		
		int xMedian = 0;
		int yMedian = 0;
		
		
		// Get the start time
		startTime = System.nanoTime();
					
		for (int i = 0; i < 2; i++) 
		{
			// First, set the comparator
			aSorter.setComparator(i);
			aSorter.sort();
			
			// Start the sort
			// 0. sort x-cord
			// 1. sort y-cord
			// 2. sort for PolarAngle
			if (i == 0 || i == 1) 
			{
				
				aSorter.sort();
			}
			
			// Get the median values to create the medianCoordinatePoint
			if (i == 0) 
			{
				xMedian = aSorter.getMedian().getX();
			}
			
			if (i == 1) 
			{
				yMedian = aSorter.getMedian().getY();
			}
				medianCoordinatePoint = new Point(xMedian, yMedian);
				
			}
			
			scanTime = System.nanoTime() - startTime;
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		if(sortingAlgorithm == Algorithm.MergeSort || sortingAlgorithm == Algorithm.QuickSort) {
			return sortingAlgorithm + "      " + points.length + "  " + scanTime;
		}
		else {		
			return sortingAlgorithm + "  " + points.length + "  " + scanTime;
			}
	}
	
	
	/**
	 * Write points[] after a call to scan().  When printed, the points will appear 
	 * in order of polar angle with respect to medianCoordinatePoint with every point occupying a separate 
	 * line.  The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		
		return "MCP: (" + medianCoordinatePoint.getX() + "," + medianCoordinatePoint.getY() + ")";
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * @throws FileNotFoundException, IOException 
	 */
	public void writePointsToFile() throws FileNotFoundException, IOException
	{
		BufferedWriter wrtr = new BufferedWriter(new FileWriter("Points.txt"));
		wrtr.write(this.toString());
		wrtr.close();
	}	

	
	/**
	 * This method is called after each scan for visually check whether the result is correct.  You  
	 * just need to generate a list of points and a list of segments, depending on the value of 
	 * sortByAngle, as detailed in Section 4.1. Then create a Plot object to call the method myFrame().  
	 */
}