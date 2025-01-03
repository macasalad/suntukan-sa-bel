/**	
    This is the GameServer Class. This is used to connect to the AWS server.

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

import java.io.*;
import java.net.*;

public class GameServer {
    private ServerSocket ss;
    private int numPlayers, maxPlayers;
    private Socket p1Socket, p2Socket;
    public ReadFromClient p1ReadRunnable, p2ReadRunnable;
    private WriteToClient p1WriteRunnable, p2WriteRunnable;
    private double p1x, p1y, p2x, p2y;
    private int p1Score, p2Score;
    private final int WINNING_SCORE = 100;

    /**
     * Constructor for the GameServer class.
     */
    public GameServer(){
        System.out.println("===== Game Server =====");
        System.out.println("Server is running.");
        try{
            ss = new ServerSocket(5000);
        } catch (IOException e){
            System.out.println("IOException from GameServer constructor.");
        }
        numPlayers = 0;
        maxPlayers = 2;
        p1x = 150;
        p1y = 77;
        p2x = 725;
        p2y = 90;
        p1Score = p2Score = 0;
    }

    /**
     * Constructor for the GameServer class.
     */
    public void acceptConnections(){
        System.out.println("Waiting for connections...");
        try{
            while (numPlayers < maxPlayers){
                Socket s = ss.accept();
                DataInputStream in  = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                
                numPlayers++;
                out.writeInt(numPlayers);
                System.out.printf("Player #%d has connected.\n", numPlayers);
                
                ReadFromClient rfc = new ReadFromClient(numPlayers, in);
                WriteToClient wtc = new WriteToClient(numPlayers, out);

                if (numPlayers == 1){
                    p1Socket = s; 
                    p1ReadRunnable = rfc; 
                    p1WriteRunnable = wtc;
                } else {
                    p2Socket = s;
                    p2ReadRunnable = rfc;
                    p2WriteRunnable = wtc;

                    p1WriteRunnable.sendStartMessage();
                    p2WriteRunnable.sendStartMessage();

                    Thread readThread1 = new Thread(p1ReadRunnable);
                    Thread readThread2 = new Thread(p2ReadRunnable);
                    readThread1.start();
                    readThread2.start();
                    
                    Thread writeThread1 = new Thread(p1WriteRunnable);
                    Thread writeThread2 = new Thread(p2WriteRunnable);
                    writeThread1.start();
                    writeThread2.start();
                }
            }
            System.out.println("No longer accepting connections.");
        } catch (IOException e){
            System.out.println("IOException from acceptConnections().");
        }
    }

    /**
     * An inner class that allows the server to read from clients
     */
    private class ReadFromClient implements Runnable{
        private int playerID;
        private DataInputStream in;

        /**
         * Writes to client that the RFC was created
         * @param playerID
         * @param in
         */
        public ReadFromClient(int playerID, DataInputStream in){
            this.playerID = playerID;
            this.in = in;
            System.out.printf("RFC for Player #%d created.\n", playerID);
        }

        /**
         * threads
         */
        public void run(){
            try{
                while (true){
                    if (playerID == 1){
                        p1x = in.readDouble();
                        p1y = in.readDouble();
                        p1Score = in.readInt();
                    }
                    else{
                        p2x = in.readDouble();
                        p2y = in.readDouble();
                        p2Score = in.readInt();
                    }
                    
                }
            } catch (IOException e){
                System.out.println("IOException from RFC run().");
            }
        }
    }

    /**
     * An inner class that allows the server to write to clients
     */
    private class WriteToClient implements Runnable{
        private int playerID;
        private DataOutputStream out;

        /**
         * Writes to client that the WTC was created
         * @param playerID
         * @param out
         */
        public WriteToClient(int playerID, DataOutputStream out){
            this.playerID = playerID;
            this.out = out;
            
            System.out.printf("WTC for Player #%d created.\n", playerID);
        }

        /**
         * threads
         */
        public void run(){
            try{
                while (true){
                    if (playerID == 1){
                        out.writeDouble(p2x);
                        out.writeDouble(p2y);
                        out.writeInt(p2Score);
                        out.writeInt(getWinner());
                        out.flush();
                    }
                    else{
                        out.writeDouble(p1x);
                        out.writeDouble(p1y);
                        out.writeInt(p1Score);
                        out.writeInt(getWinner());
                        out.flush();
                    }
                    try{
                        Thread.sleep(25);
                    } catch (InterruptedException e){
                        System.out.println("InterruptedException from WTC run().");
                    }
                }
            } catch (IOException e){
                System.out.println("IOException from WTC run().");
            }
        }

        /**
         * threads
         */
        public void sendStartMessage(){
            try{
                out.writeUTF("We now have two players. The game will now start.");
            }catch (IOException ex){
                System.out.println("IOException from sendStartMessage().");
            }
        }
    }

    /**
     * Checks if there is a winner already
     * @return n
     */
    private int getWinner(){
        int n = 0;
        if (p1Score >= WINNING_SCORE){
            n = 1;
        } else if (p2Score >= WINNING_SCORE){
            n = 2;
        }
        return n;
    }

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}