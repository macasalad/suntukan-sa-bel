/**	
    This is the GameCanvas class. This is used for making the canvas for the game. It extends JComponent.

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

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

public class GameCanvas extends JComponent {
    private Player you, enemy;
    private int yourID;
    private int w, h;
    public boolean doesEnemyExist;
    private int gameState;
    private BufferedImage menu, bg, frame;
    public int winnerID;

    /**
    * Constructor method for the GameCanvas class.
    * @param w width
    * @param h height
    */
    public GameCanvas(int w, int h) {
        this.w = w;
        this.h = h;
        gameState = 0;
        setPreferredSize(new Dimension(w, h));
        loadImages();
    }

    /**
     * Used to load the images needed for the background of the game.
     */
    private void loadImages() {
        try {
            menu = ImageIO.read(new File("res/menu.png"));
            bg = ImageIO.read(new File("res/bg.png"));
            frame = ImageIO.read(new File("res/blank.png"));
        } catch (IOException e) {
            System.out.println("IOException while loading images.");
        }
    }

    /**
     * Renders the images
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.setRenderingHints(rh);

        if (gameState == 0) {
            drawMenu(g2d);
        }
        else if (gameState == 1){
            drawGame(g2d);
        }
        g2d.drawImage(frame, 0, 0, 800, 600, null);
    }

    /**
     * Renders the Menu
     * @param g2d
     */
    private void drawMenu(Graphics2D g2d) {
        g2d.drawImage(menu, 0, 0, w, h, null);
    }

    /**
     * Renders the players
     * @param g2d
     */
    private void drawGame(Graphics2D g2d) {
        g2d.drawImage(bg, 0, 0, w, h, null);
        if (you != null && enemy != null) {
            you.draw(g2d);
            enemy.draw(g2d);
            g2d.setFont(new Font("Monospaced", Font.BOLD, 25));
            g2d.setColor(new Color(10, 10, 10));
            g2d.drawString("You: " + you.getTotalScore(), 25, 50);
            g2d.drawString("Enemy: " + enemy.getTotalScore(), 650, 50);
        }
    }

    /**
     * Draws the images for the final game state.
     * @param g2d
     */
    public void setFrame(int n){
        try{
            if (n == 1){
                frame = ImageIO.read(new File("res/p1_winner.png"));
            } else if (n == 2){
                frame = ImageIO.read(new File("res/p2_winner.png"));
            }
        } catch (IOException e){
            System.out.println("IOException from setFrame().");
        }
    }

    /**
     * Used in setting the user ID
     * @param id
     */
    public void setID(int id) {
        yourID = id;
    }

    /**
     * Used in setting up the sprites
     */
    public void setUpSprites() {
        if (yourID == 1) {
            you = new Player(150, 77, 1);
            enemy = new Player(725, 90, 2);
        } else {
            enemy = new Player(150, 77, 1);
            you = new Player(725, 90, 2);
        }
    }

    /**
     * Sets the game state
     * @param n
     */
    public void setGameState(int n) {
        gameState = n;
        repaint();
    }

    /**
     * Used to get the current game state
     * @return gameState
     */
    public int getGameState() {
        return gameState;
    }

    /**
     * Used to get the player
     * @return Player you
     */
    public Player getYou() {
        return you;
    }

    /**
     * Used to get the enemy
     * @return Player enemy
     */
    public Player getEnemy() {
        return enemy;
    }
}