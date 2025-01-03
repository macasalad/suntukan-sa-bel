/**	
    This class is the GameObject class. This is used for managing the objects in the game.
	
    @author Hannah Lei Baniqued (230716); Julie Ann Xyrene Lalimarmo (233429)
	@version 14 May 2024
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/

import java.awt.*;

public abstract class GameObject {
    protected double x, y, w, h;

    /**
	 * Constructor of the GameObject class with param x and y, respectively.
	 * @param x for the x-coordinate
	 * @param y for the y-coordinate
	 */
    public GameObject(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
	 * Constructor of the GameObject class with param x, y, w, and h, respctively.
	 * @param x for the x-coordinate
	 * @param y for the y-coordinate
	 * @param w for the width
	 * @param h for the height
	 */
    public GameObject(double x, double y, double w, double h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    /**
	 * Getter method for the x-xoordinate
	 * @return x
	 */
    public double getX(){
        return x;
    }

    /**
	 * Getter method for the y-coordinate
	 * @return y
	 */
    public double getY(){
        return y;
    }

    /**
	 * Getter meethod for the wdith
	 * @return w
	 */
    public double getW(){
        return w;
    }

    /**
	 * Getter method for the height
	 * @return h
	 */
    public double getH(){
        return h;
    }

    /**
	 * Getter method for the height
	 * @return h
	 */
    public void setX(double n){
        this.x = n;
    }

    /**
	 * Setter method for the y-coordinate
	 * @param n
	 */
    public void setY(double n){
        this.y = n;
    }

    /**
	 * Setter method for the width
	 * @param n
	 */
    public void setW(double n){
        this.w = n;
    }

    /**
	 * Setter method for the height
	 * @param n
	 */
    public void setH(double n){
        this.h = n;
    }

    /**
	 * Used to render the objects
	 * @param g2d
	 */
    abstract void draw(Graphics2D g2d);
}