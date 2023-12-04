 package edu.iastate.cs228.hw2;

/**
 *  
 * @author Christian Lapnow
 *
 */

public class Point implements Comparable<Point>
{
	private int x; 
	private int y;
	
	public static boolean xORy;  // compare x coordinates if xORy == true and y coordinates otherwise 
	                             // To set its value, use Point.xORy = true or false. 
	
	public Point()  // default constructor
	{
		// x and y get default value 0
	}
	
	public Point(int x, int y)
	{
		this.x = x;  
		this.y = y;   
	}
	
	/**
	 * Copy constructor
	 * @param p and existing Point object
	 */
	public Point(Point p) // copy constructor
	{ 
		x = p.getX();
		y = p.getY();
	}

	/**
	 * Accessor method to return the x-coordinate
	 * @return the x-coordinate of the Point
	 */
	public int getX()   
	{
		return x;
	}

	/**
	 * Accessor method to return the y-coordinate
	 * @return the y-coordinate of the Point
	 */
	public int getY()
	{
		return y;
	}
	
	/** 
	 * Set the value of the static instance variable xORy. 
	 * @param xORy
	 */
	public static void setXorY(boolean xORy)
	{
		// TODO 
		Point.xORy = xORy;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		// Check obj is not null and that obj and this instance have the same class
		if (obj == null || obj.getClass() != this.getClass())
		{
			return false;
		}
    
		// Cast obj into a Point and assign it to a new Point other
		// Compare the x- and y-coordinates
		Point other = (Point) obj;
		return x == other.x && y == other.y;   
	}

	/**
	 * Compare this point with a second point q depending on the value of the static variable xORy 
	 * @param 	q 
	 * @return  -1  if (xORy == true && (this.x < q.x || (this.x == q.x && this.y < q.y))) 
	 *                || (xORy == false && (this.y < q.y || (this.y == q.y && this.x < q.x)))
	 * 		    0   if this.x == q.x && this.y == q.y)  
	 * 			1	otherwise 
	 */
	public int compareTo(Point q)
	{
		if ((xORy == true && (this.x < q.x || (this.x == q.x && this.y < q.y))) || (xORy == false && (this.y < q.y || (this.y == q.y && this.x < q.x)))) 
		{
			return (-1);
		} 
		
		if (this.x == q.x && this.y == q.y) {
			return 0;
		}
		
		return 1;
	}
	
	
	/**
	 * Output a point in the standard form (x, y). 
	 */
	@Override
    public String toString() 
	{
		// TODO 
		return ("(" + x + ", " + y + ")");
	}
}