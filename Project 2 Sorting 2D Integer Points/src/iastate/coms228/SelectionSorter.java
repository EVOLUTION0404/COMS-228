package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException; 
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;
import java.util.Comparator;

/**
 *  
 * @author Christian Lapnow
 *
 */

/**
 * 
 * This class implements selection sort.   
 *
 */

public class SelectionSorter extends AbstractSorter 
{
    public SelectionSorter(Point[] pts) 
    {
        super(pts);
        algorithm = "Selection Sort";
    }

    @Override
    public void sort() 
    {
        // Implement the selection sort algorithm using pointComparator
        for (int i = 0; i < points.length - 1; i++) 
        {
            int minIndex = i;
            for (int j = i + 1; j < points.length; j++) 
            {
                if (pointComparator.compare(points[j], points[minIndex]) < 0) 
                {
                    minIndex = j;
                }
            }
            // Swap points[i] and points[minIndex]
            Point temp = points[i];
            points[i] = points[minIndex];
            points[minIndex] = temp;
        }
    }
}