/**	
    This is the GameStarter class. It contains the main method for starting the class
    from the player side.

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

public class GameStarter{
    public static void main(String[] args) {
        GameFrame gf = new GameFrame();
        gf.connectToServer();
		gf.playMusic("bgm.wav");
        gf.setUpGUI();
    }
}