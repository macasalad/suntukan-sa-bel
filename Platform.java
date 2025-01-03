/**	
    This is the Platform class. This is responsible for managing the platform objects. It extends the GameObject class.

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

import java.awt.image.BufferedImage;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class Platform extends GameObject {

    public double x, y, width, height;
    public String filePath;
    public BufferedImage platformImage;

    /**
     * Constructor method for the Platform class
     * @param x x-coordinate
     * @param y y - coordinate
     * @param width
     * @param height
     * @param filePath
     */
    public Platform (double x, double y, double width, double height, String filePath) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.filePath = filePath;
    }

    /**
     * Used to read and set the platform images
     */
    public void setPlatformImage() {
        try {
            platformImage = ImageIO.read(new File(filePath));
        } catch (IOException ex) {
            System.out.println("IOError from setPlatformImage");
        }
    }

    /**
     * Used to get the top of the platform
     * @return top y-coordinate of the platform
     */
    public double getTop() {
        return y;
    }

    /**
     * Used to get the bottom of the platform
     * @return bottom y-coordinate of the platform
     */
    public double getBottom() {
        return y+height;
    }

    /**
     * Used to get the bottom of the platform
     * @return bottom y-coordinate of the platform
     */
    public double getLeft() {
        return x;
    }

    /**
     * Used to get the right of the platform
     * @return right x-coordinate of the platform
     */
    public double getRight() {
        return x+width;
    }

    /**
     * Used to get the right of the platform
     * @return right x-coordinate of the platform
     */
    @Override
    public void draw(Graphics2D g2d){
        BufferedImage pImage = platformImage;
        g2d.drawImage(pImage, (int) x, (int) y, (int)w, (int)h, null);
    }
}
