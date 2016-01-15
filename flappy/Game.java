package flappy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	/***************************************** Variables ********************************************************/
	static JLabel label; // Variable declaration
	JLabel score, displayScore, highScore;
	JLabel gameOver;
	JLabel title;
	JLabel help1, help2;
	
	int xHighScore = 0;
	static int xScore = 0;
	int xSpeed = 0;

	public static int timer = 20;

	static boolean alive;
	static boolean click;

	static final int PIC_WIDTH = 61;
	static final int PIC_HEIGHT = 44;

	Timer tm = new Timer(timer, this);
	Bird bird = new Bird();

	static int pipeCounter = 1;
	Pipe[] pipe = new Pipe[4];

	/***************************************** Constructor ********************************************************/
	public Game() {
		setPreferredSize(new Dimension(Window.WIDTH, Window.HEIGHT)); // set preferred size
		
		addKeyListener(this); // adds key listener to this
		setFocusable(true); // sets focus to game
		setLayout(null); // layout manager setting to null

		alive = true; // alive and click true
		click = true;

		initLabels(); // initialize labels

		for (int i = 0; i < 4; i++) { // initialize pipe array
			pipe[i] = new Pipe(380, 70, pipeCounter, bird.getX());
			pipe[i].newPipeLocation();
			pipeCounter++;
		}

		setBackground(Color.cyan);
	}

	/***************************************** Initialize labels ********************************************************/
	public void initLabels() { // Here to keep less cluttered.

		ImageIcon birdIcon = new ImageIcon(getClass().getResource("resources/flappy.png")); // Flappy bird icon initialize
		label = new JLabel(birdIcon);
		label.setBounds(0, 0, PIC_WIDTH, PIC_HEIGHT);
		label.setLocation((1000 - PIC_WIDTH) / 2, 30 + (600 - PIC_HEIGHT) / 2);
		add(label);
		label.setVisible(true);

		score = new JLabel("0"); // Score label initialize
		score.setBounds(15, 10, 60, 60);
		score.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		add(score);
		score.setVisible(false);

		title = new JLabel("Flappy Bird"); // Title label initialize
		title.setBounds(100, 50, 800, 200);
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 140));
		title.setForeground(Color.white);
		add(title);
		title.setVisible(true);

		help1 = new JLabel("1. Press the escape key to start the game or to restart at any point. P to Pause."); // help1																			// initialize
		help1.setBounds(100, 425, 800, 30);
		help1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
		help1.setForeground(Color.white);
		add(help1);
		help1.setVisible(true);

		help2 = new JLabel("2. Press space to flap your wings and avoid the green pipes."); // help 2
		help2.setBounds(100, 475, 800, 30);
		help2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
		help2.setForeground(Color.white);
		add(help2);
		help2.setVisible(true);
		
		gameOver = new JLabel("GAME OVER"); // gameover label initialize
		gameOver.setBounds(100, 50, 800, 200);
		gameOver.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 115));
		gameOver.setForeground(Color.black);
		add(gameOver);
		gameOver.setVisible(false);
		
		displayScore = new JLabel("Score: 0"); // gameover score label initialize
		displayScore.setBounds(100, 475, 800, 40);
		displayScore.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
		displayScore.setForeground(Color.black);
		add(displayScore);
		displayScore.setVisible(false);
		
		highScore = new JLabel("Highscore: 0"); // highscore label initialize
		highScore.setBounds(100, 425, 800, 40);
		highScore.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
		highScore.setForeground(Color.black);
		add(highScore);
		highScore.setVisible(false);

	}

	/***************************************** Paint Component ********************************************************/
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Paint of superclass
		
		if (alive) xSpeed = 4;		//If alive, horizontal speed of 4
		else xSpeed = 0;				//if dead horizontal speed of 4

		for (int i = 0; i < 4; i++) { // Paint pipe array
			pipe[i].pipePaint(g, xSpeed);
		}
	}

	/***************************************** Hit Box checking ********************************************************/
	public void checkBounds() {
		if (bird.getY() < -50) { // If at top > Make max bounds
			bird.setPosVel(-50, 100); // Set to highest position vertical
										// position allowed then with velocity
										// to simulate bounce
		} else if ((bird.getY() + 44) >= Window.HEIGHT) { // If hit bottom then
															// DEAD
			bird.setPosVel((Window.HEIGHT - PIC_HEIGHT),
					(int) (bird.getVelY() / 2)); // Sets position to bottom,
													// velocity decreasing (more
													// smooth)
			alive = false;
			click = false;
		}

		for (int i = 0; i < 4; i++) { // Check hitboxes with pipes
			pipe[i].checkBounds(bird.getY());
		}
	}

	/***************************************** Update score label ********************************************************/
	public void addScore() {
		if (!score.getText().equals(""+xScore)){		//only set text if not same
			score.setText("" + xScore);
		}
	}

	/***************************************** Game initialization class/game reset class ********************************************************/
	public void gameStart() {
		score.setVisible(true);							//setting labels
		score.setText("0");
		
		title.setVisible(false);
		help1.setVisible(false);
		help2.setVisible(false);
		
		gameOver.setVisible(false);
		displayScore.setVisible(false);
		highScore.setVisible(false);

		for (int i = 0; i < 4; i++) {					//reset pipes
			pipe[i].p_x = pipe[i].p_InitialGap
					+ ((i + 1) * pipe[i].p_GapBetween);
			pipe[i].newPipeLocation();
			pipe[i].scored = false;
		}

		alive = true;									//reset game
		click = true;

		bird.setPosVel(300, 0);							//reset bird

		xScore = 0;										//reset score

		tm.start();										//start timer
	}
	
	/***************************************** GAME OVER CLASS********************************************************/
	public void gameOver() {
		if (xScore > xHighScore) //Check highscore then replace if higher
			xHighScore = xScore;
		
		displayScore.setText("Score: "+xScore);			//set score label text
		highScore.setText("Highscore: "+xHighScore);	//set highscore label text
		
		gameOver.setVisible(true);						//label configurations 
		displayScore.setVisible(true);
		highScore.setVisible(true);
		score.setVisible(false);
		
	}

	/***************************************** Key press action ********************************************************/
	@Override
	public void keyPressed(KeyEvent e) {
		if (click) {							//if click is allowed
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {		//if space was clicked
				bird.m_velocityY = -375;					//set bird velocity
				click = false;								//set click to false
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {			//if escape then restart game
			gameStart();
		}
		if(alive){											//if alive
			if(e.getKeyCode() == KeyEvent.VK_P){				//if p clicked
				if (tm.isRunning()){								//if timer running
					tm.stop();}											//stop - pause
				else{												//not running
					tm.start();}										//start - unpause
			}
		}	
	}

	/***************************************** action performed/ called by timer ********************************************************/
	@Override
	public void actionPerformed(ActionEvent e) {
		checkBounds();
		addScore();

		if (e.getSource() == tm) { // if source is the timer
			if (alive) { // if alive then calc position and set location
				bird.calcVelocity();
				bird.calcPositionY();
				label.setLocation(bird.getX(), bird.getY());
			} else if ((!alive) && (bird.getY() < 600)) { // if dead but not on ground then calc position and set location until on ground
				bird.calcVelocity();
				bird.calcPositionY();
				label.setLocation(bird.getX(), bird.getY());
				if((bird.getY() >= 550)  && (bird.getVelY() < 40) ){		//if on ground and slowed down
					gameOver();
					score.setVisible(false);
					tm.stop();
				}
			}
			repaint(); // repaint
		}
	}

	/***************************************** key released action ********************************************************/
	@Override
	public void keyReleased(KeyEvent e) {			
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {			//if space released
			if (alive)											//if alive
				click = true;										//set click to true (disables holding of spacebar, mouse typed wasn't working)
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
