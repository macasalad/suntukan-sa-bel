/**	
    This is the player class. This extends the GameObject class and is responsible for the movements of the player.

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
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Player extends GameObject{
    private double initialX, initialY;
    private int playerID;
    private BufferedImage[] p1Walk, p1Punch, p1Kick, p1Idle;
    private BufferedImage[] p2Walk, p2Punch, p2Kick, p2Idle;
    private BufferedImage[] p1Frames, p2Frames;
    private int currentFrameIndex1, currentFrameIndex2;
    private int totalScore;
    private double w, h;
    public int gravity = 3;
    public int speedY = 0;
    public int speedX = 0;

    /**
     * Constructor of the player class
     * @param x for the x-coordinate
     * @param y for the y-coordinate
     * @param playerID for the player ID
     */
    public Player(double x, double y, int playerID){
        super(x, y);
        initialX = x;
        initialY = y;
        this.playerID = playerID;
        currentFrameIndex1 = 0;
        currentFrameIndex2 = 0;
        totalScore = 0;
        w = 45;
        h = 55;

        getImages();
        p1Frames = p1Idle;
        p2Frames = p2Idle;  
    }

    /**
     * Responsible for getting the sprite images from the resourse folder
     */
    public void getImages(){
        try{
            if (playerID == 1){
                p1Walk = new BufferedImage[2];
                p1Walk[0] = ImageIO.read(new File("res/p1_walk_1.png"));
                p1Walk[1] = ImageIO.read(new File("res/p1_walk_2.png"));

                p1Punch = new BufferedImage[2];
                p1Punch[0] = ImageIO.read(new File("res/p1_punch_1.png"));
                p1Punch[1] = ImageIO.read(new File("res/p1_punch_2.png"));

                p1Kick = new BufferedImage[2];
                p1Kick[0] = ImageIO.read(new File("res/p1_kick_1.png"));
                p1Kick[1] = ImageIO.read(new File("res/p1_kick_2.png"));

                p1Idle = new BufferedImage[2];
                p1Idle[0] = ImageIO.read(new File("res/p1_idle_1.png"));
                p1Idle[1] = ImageIO.read(new File("res/p1_idle_2.png"));
            } else{
                p2Walk = new BufferedImage[2];
                p2Walk[0] = ImageIO.read(new File("res/p2_walk_1.png"));
                p2Walk[1] = ImageIO.read(new File("res/p2_walk_2.png"));

                p2Punch = new BufferedImage[2];
                p2Punch[0] = ImageIO.read(new File("res/p2_punch_1.png"));
                p2Punch[1] = ImageIO.read(new File("res/p2_punch_2.png"));

                p2Kick = new BufferedImage[2];
                p2Kick[0] = ImageIO.read(new File("res/p2_kick_1.png"));
                p2Kick[1] = ImageIO.read(new File("res/p2_kick_2.png"));

                p2Idle = new BufferedImage[2];
                p2Idle[0] = ImageIO.read(new File("res/p2_idle_1.png"));
                p2Idle[1] = ImageIO.read(new File("res/p2_idle_2.png"));
            }
        } catch (IOException e) {
            System.out.println("IOException from getImages().");
        }
    }

    /**
     * Renders the sprite images
     */
    @Override
    public void draw(Graphics2D g2d){
        if (playerID == 1){
            g2d.drawImage(p1Frames[currentFrameIndex1], (int) x, (int) y, (int) w, (int) h, null);
            currentFrameIndex1 = (currentFrameIndex1 + 1) % 2;
        } else{
            g2d.drawImage(p2Frames[currentFrameIndex2], (int) x, (int) y, (int) w, (int) h, null);
            currentFrameIndex2 = (currentFrameIndex2 + 1) % 2;
        }
    }

    /**
     * Used for moving left
     * @param n change in the x-coordinate
     */
    public void moveLeft(double n){
        if (playerID == 1){
            p1Frames = p1Walk;
        } else{
            p2Frames = p2Walk;
        }
        x -= 2*n;
        w = 50;
    }

    /**
     * Used for moving right
     * @param n change in the x-coordinate
     */
    public void moveRight(double n){
        if (playerID == 1){
            p1Frames = p1Walk;
        } else{
            p2Frames = p2Walk;
        }
        x += 2*n;
        w = 50;
    }

    /**
     * Used for the punch movement
     * @param n change in the x-coordinate
     */
    public void punch(double n){
        if (playerID == 1){
            p1Frames = p1Punch;
            x += n;
        } else{
            p2Frames = p2Punch;
            x -= n;}
        
        w = 75;
    }

    /**
     * Used for the kick movement
     * @param n change in the x-coordinate
     */
    public void kick(double n){
        if (playerID == 1){
            p1Frames = p1Kick;
            x += n;
        } else{
            p2Frames = p2Kick;
            x -= n;
        }
        w = 75;
    }

    /**
     * Used for the idle mode of the player
     */
    public void idle(){
        if (playerID == 1){
            p1Frames = p1Idle;
        } else{
            p2Frames = p2Idle;
        }
        w = 50;
    }

    /**
     * Used for the jump movement
     */
    public void jump(){
        if (playerID == 1){
            p1Frames = p1Idle;
        } else{
            p2Frames = p2Idle;
        }
        w = 50;
        y -= 90;
    }

    /**
     * Used for setting the bottom of an object as the top of another
     * @param bottom new y-coordinate
     */
    public void setTop(double bottom) {
        y = bottom;
    }

    /**
     * Used for setting the top of an object as the bottom of another
     * @param top y-coordinate
     */
    public void setBottom(double top) {
        y = top-getH();
    }

    /**
     * used for setting the left of an object as the right of another
     * @param left x-coordinate
     */
    public void setRight(double left) {
        x = left-getW();
    }

    /**
     * Used for setting the right of an object as the left of another
     * @param right x-coordinate
     */
    public void setLeft(double right) {
        x = right;
    }

    /**
     * Used for setting the right of an object as the left of another
     * @param right x-coordinate
     */
    public int getTotalScore(){
        return totalScore;
    }

    /**
     * Used for setting the value of the totalScore
     * @param n = totalScore
     */
    public void setScore(int n){
        totalScore = n;
    }

    /**
     * Used to increment the score
     */
    public void addScore(){
        totalScore++;
    }

    /**
     * Used to get gravity
     * @return gravity
     */
    public int returnGravity(){
        return gravity;
    }

    /**
     * Used to get the width
     */
    public double getW(){
        return w;
    }

    /**
     * Used to get the height
     */
    public double getH(){
        return h;
    }

    /**
     * Used to set the width
     */
    public void setW(double n){
        w = n;
    }

    /**
     * Used to set the height
     */
    public void setH(double n){
        h = n;
    }

    /**
     * Used to set the value of x
     */
    public void setX(double n){
        x = n;
    }

    /**
     * Used to set the value of y
     */
    public void setY(double n){
        y = n;
    }

    /**
     * Boolean checker to check if a player is attacking an enemy
     * @param enemy
     * @return true or false
     */
    public boolean isAttacking(Player enemy){
        return !(x + w <= enemy.getX() || x >= enemy.getX() + enemy.getW());    
    }
}