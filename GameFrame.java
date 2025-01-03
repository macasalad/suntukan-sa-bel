/**	
    This is the GameFrame class. This is used for managing the movements of the objects within the canvas.

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
import javax.swing.Timer;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.io.File;
import javax.sound.sampled.*;

public class GameFrame {
    private JFrame frame;
    private boolean isMovingLeft, isMovingRight, isPunching, isKicking, isIdle, isJumping;
    private GameCanvas gameCanvas;
    private Timer animationTimer;
    private Socket s;
    private ReadFromServer rfsRunnable;
    private WriteToServer wtsRunnable;
    private int playerID;
    private ArrayList<Platform> platforms; 
    private Platform pf1, pf2, pf3, pf4, pf5, pf6, pf7, pf8, pf9, pf10, pf11;
    protected File musicPath;

    /**
     * Constructor method for the GameFrame class
     */
    public GameFrame(){
        frame = new JFrame();
        gameCanvas = new GameCanvas(800, 600);
        pf1 = new Platform(51,135,411,36,"/res/Platforms/p1.png");
        pf2 = new Platform(551,76,37,127,"/res/Platforms/p2.png");
        pf3 = new Platform(682,175,118,26,"/res/Platforms/p3.png");
        pf4 = new Platform(267,279,245,41,"/res/Platforms/p4.png");
        pf5 = new Platform(611,321,191,43,"/res/Platforms/p5.png");
        pf6 = new Platform(0, 410, 248, 28, "/res/Platforms/p6.png");
        pf7 = new Platform(242, 520, 248, 41, "/res/Platforms/p7.png");
        pf8 = new Platform(511, 437,290, 29,"/res/Platforms/p8.png");
        pf9 = new Platform(87, 251, 42, 41, "/res/Platforns/p9.png");
        pf10 = new Platform(88, 291, 42, 41, "/res/Platforms/p9.png");
        pf11 = new Platform(128, 290, 42, 41, "/res/Platforms/p9.png");
        platforms = new ArrayList<>();
        addPlatforms();
        
    }

    /**
     * Sets up the GUI
     */
    public void setUpGUI(){
        frame.setTitle("Suntukan sa Bellarmine: Player #" + playerID);
        gameCanvas.setID(playerID);
        gameCanvas.setUpSprites();
        frame.add(gameCanvas);
        frame.pack();
        setUpKeyListeners();
        setUpAnimationTimer();
        setUpMouseListener();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /**
     * Sets up the animation timer
     */
    private void setUpAnimationTimer(){
        int delay = 50;
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                int speed = 10;
                if (isMovingLeft){
                    gameCanvas.getYou().moveLeft(speed);
                } else if (isMovingRight){
                    gameCanvas.getYou().moveRight(speed);
                } else if (isPunching){
                    gameCanvas.getYou().punch(speed);
                    if (gameCanvas.getYou().isAttacking(gameCanvas.getEnemy())){
                        gameCanvas.getYou().addScore();
                    } else if (gameCanvas.getEnemy().isAttacking(gameCanvas.getYou())){
                        gameCanvas.getEnemy().addScore();
                    }
                } else if (isKicking){
                    gameCanvas.getYou().kick(speed);
                    if (gameCanvas.getYou().isAttacking(gameCanvas.getEnemy())){
                        gameCanvas.getYou().addScore();
                    } else if (gameCanvas.getEnemy().isAttacking(gameCanvas.getYou())){
                        gameCanvas.getEnemy().addScore();
                    }
                } else if (isJumping){
                    if (isOnPlatform(gameCanvas.getYou(), platforms)){
                        gameCanvas.getYou().jump();
                    }
                } else{
                    gameCanvas.getYou().idle();
                }
                handlePlatformCollisions(platforms, gameCanvas.getYou());
                handleBorderCollision();
                gameCanvas.repaint();
            }
        };

        animationTimer = new Timer(delay, al);
        animationTimer.start();
    }

    /**
     * Adds the platforms to the arraylist
     */
    public void addPlatforms() {
        platforms.add(pf1);
        platforms.add(pf2);
        platforms.add(pf3);
        platforms.add(pf4);
        platforms.add(pf5);
        platforms.add(pf6);
        platforms.add(pf7);
        platforms.add(pf8);
        platforms.add(pf9);
        platforms.add(pf10);
        platforms.add(pf11);
    }

    /**
     * Checks is a player is colliding with a platform
     * @param p Platform
     * @param player Player
     * @return boolean collision
     */
    public boolean isColliding(Platform p, Player player) {
        double playerRight = player.x + player.getW();
        double playerBottom = player.y + player.getH();
        double platformRight = p.getX() + p.getW();
        double platformBottom = p.getY() + p.getH();

        boolean collision = !(playerRight <= p.getX() || 
                            player.x >= platformRight || 
                            playerBottom <= p.getY() ||
                            player.y >= platformBottom);

        return collision;
    }

    /**
     * Used to handle the border collisions
     */
    public void handleBorderCollision(){
        if (gameCanvas.getYou().getX() < 0){
            gameCanvas.getYou().setX(750);
        } else if (gameCanvas.getYou().getY() < 0){
            gameCanvas.getYou().setY(0);
        } else if (gameCanvas.getYou().getX() > 750){
            gameCanvas.getYou().setX(0);
        } else if (gameCanvas.getYou().getY() > 540){
            gameCanvas.getYou().setY(0);
        }
    }

    /**
     * Used to handle the border collisions
     */
    public ArrayList<Platform> Colliding(Player player, ArrayList<Platform> pForms){
        ArrayList<Platform> collidingPlatform = new ArrayList<Platform>();
        for(Platform a: pForms){
            if(isColliding(a, player)){
                collidingPlatform.add(a);
            }
        }
        return collidingPlatform; 
    }
    
    /**
     * Used to handle platform collisions
     * @param platforms
     * @param player
     */
    public void handlePlatformCollisions(ArrayList<Platform> platforms, Player player) {
        player.speedY += player.gravity;
        player.y += player.speedY;

        player.x += player.speedX;
        ArrayList<Platform> collidingPfs = Colliding(player, platforms);

        collidingPfs = Colliding(player, platforms);
        if (collidingPfs.size() > 0) {
            Platform collided = collidingPfs.get(0);
            if (player.speedX > 0) {
                player.setRight(collided.getLeft());
            } else if (player.speedX < 0) {
                player.setLeft(collided.getRight());
            }
            player.speedX = 0;
        }
        
        if (collidingPfs.size() > 0) {
            Platform collided = collidingPfs.get(0);
            if (player.speedY > 0) {
                player.setBottom(collided.getTop());
            } else if (player.speedY < 0) {
                player.setTop(collided.getBottom());
            }
            player.speedY = 0;
        }
    }

    /**
     * Used in settin gup the mouse listeners
     */
    private void setUpMouseListener(){
        MouseListener menu = new MouseListener() {
            public void mouseClicked(MouseEvent m) {
                double mx = m.getX();
                double my = m.getY();

                if (gameCanvas.getGameState() == 0){
                    if (mx >= 150 && mx <= 326 && my >= 383 && my <= 427){
                        gameCanvas.setGameState(1);
                    }
                    if (mx >= 150 && mx <= 326 && my >= 458 && my <= 506){
                        System.exit(0);
                    }
                }
    }
    public void mouseEntered(MouseEvent e) {    
    }  
    public void mouseExited(MouseEvent e) {   
    }  
    public void mousePressed(MouseEvent e) {    
    }  
    public void mouseReleased(MouseEvent e) {   
    }
    };
    gameCanvas.addMouseListener(menu);
    }

    /**
     * Used for checking if the player is on the platform
     * @param player
     * @param pfs
     * @return true or false
     */
    public boolean isOnPlatform(Player player, ArrayList<Platform> pfs){
        player.y += 5;
        ArrayList<Platform> collidingPfs = Colliding(player, pfs);
        player.y -= 5;
        if(collidingPfs.size() > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Used for setting up the key listeners
     */
    private void setUpKeyListeners(){
        KeyListener kl = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e){}

            @Override
            public void keyPressed(KeyEvent e){
                int key = e.getKeyCode();

                switch (key) {
                    case KeyEvent.VK_W:
                        isJumping = true;
                        break;
                    case KeyEvent.VK_A:
                        isMovingLeft = true;
                        break;
                    case KeyEvent.VK_D:
                        isMovingRight = true;
                        break;
                    case KeyEvent.VK_P:
                        isPunching = true;
                        break;
                    case KeyEvent.VK_K:
                        isKicking = true;
                        break;
                    default:
                        isIdle = false;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
            
                switch (key) {
                    case KeyEvent.VK_W:
                        isJumping = false;
                        break;
                    case KeyEvent.VK_A:
                        isMovingLeft = false;
                        break;
                    case KeyEvent.VK_D:
                        isMovingRight = false;
                        break;
                    case KeyEvent.VK_P:
                        isPunching = false;
                        break;
                    case KeyEvent.VK_K:
                        isKicking = false;
                        break;
                    default:
                        isIdle = true;
                        break;
                }
            };};
        gameCanvas.addKeyListener(kl);
        gameCanvas.setFocusable(true);
    }

    /**
     * Used to connect to server
     */
    public void connectToServer(){
        System.out.println("===== Client =====");
        try{
            Scanner scan = new Scanner(System.in);
            System.out.print("Insert IP Address: ");
            String ipAddress = scan.nextLine();
            System.out.print("Port: ");
            int portNumber = Integer.parseInt(scan.nextLine());
            s = new Socket(ipAddress, portNumber);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            playerID = in.readInt();

            System.out.printf("You are Player #%d.\n", playerID);

            if (playerID == 1){
                System.out.println("Waiting for other player...");
            }

            rfsRunnable = new ReadFromServer(in);
            wtsRunnable = new WriteToServer(out);
            rfsRunnable.waitForStartMessage();
        } catch (IOException e){
            System.out.println("IOException from connectToServer().");
        }
    }

    /**
     * Used to connect to server
     */
    private class ReadFromServer implements Runnable{
        private DataInputStream in;

        public ReadFromServer(DataInputStream in){
            this.in = in;
            System.out.println("RFS Runnable created.");
        }

        /**
         * Threads
         */
        public void run(){
            try{
                while (true){
                    double enemyX = in.readDouble();
                    double enemyY = in.readDouble();
                    int enemyScore = in.readInt();
                    int playerID = in.readInt();

                    if (gameCanvas.getEnemy() != null){
                        gameCanvas.getEnemy().setX(enemyX);
                        gameCanvas.getEnemy().setY(enemyY);
                        gameCanvas.getEnemy().setScore(enemyScore);
                        gameCanvas.setFrame(playerID);
                    }

                }
            } catch (IOException e){
                System.out.println("IOException from RFS run().");
            }
        }

        /**
         * Used to wait for the start message
         */
        public void waitForStartMessage(){
            try{
                String startMsg = in.readUTF();
                System.out.println("Message from server: " + startMsg);
                
                Thread readThread = new Thread(rfsRunnable);
                Thread writeThread = new Thread(wtsRunnable);
                readThread.start();
                writeThread.start();
            }catch (IOException ex){
                System.out.println("IOException from waitForStartMessage().");
            }
    }}

    /**
     * This is an inner class to write to server. It implements Runnable.
     */
    private class WriteToServer implements Runnable{
        private DataOutputStream out;

        public WriteToServer(DataOutputStream out){
            this.out = out;
            System.out.println("WTS Runnable created.");
        }

        /**
         * threads
         */
        public void run(){
            try{
                while (true) {
                    if (gameCanvas.getYou() != null){
                        out.writeDouble(gameCanvas.getYou().getX());
                        out.writeDouble(gameCanvas.getYou().getY());
                        out.writeInt(gameCanvas.getYou().getTotalScore());
                        out.flush();
                    }
                    
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        System.out.println("InterruptedException from WTS run().");
                    }
                }
            } catch (IOException e){
                System.out.println("IOException from WTS run().");
            }
        }
    }

    /**
     * This is responsible for the overall background music of the project.
     * It also loops the whole music.
     * 
     * @param location file name of the music file
     */
    public void playMusic(String location) {
        try {
            musicPath = new File(location);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } 
        catch (Exception e) {
            System.out.println(e);
        }
    }
}