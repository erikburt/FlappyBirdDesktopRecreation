package flappy;

import javax.swing.JFrame;

public class Window extends JFrame{						//Start of class
	private static final long serialVersionUID = 1L;

	static int WIDTH = 1000, HEIGHT = 600;				//Height and Width of Frame/Window

	public static void main(String[] args) {
		Game game = new Game();							//instantiate game

		JFrame window = new JFrame("Flappy Bird");				//New JFrame
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//set characteristics of the new frame/window
		window.setResizable(false);
		window.add(game);											//add game to window
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

}
