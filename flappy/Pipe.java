package flappy;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Pipe{
	
	/*****************************************Variables********************************************************/
	public final int p_WidthTop = 100, p_WidthBottom = 80;									//Final ints
	public final int p_HeightTop = 60;
	public final int p_GapBetween = 340, p_GapHeight = 115, p_InitialGap = 700;
	
	private int birdWidth = 61, birdHeight = 44, birdX = 200;								//Private ints associated with bird
	
	boolean scored = false;												//Rest of variables
	
	int randMax, randMin;
	int xSpeed = 5;
	public int p_x, p_y;
	
	Random rand = new Random();		//used for random numbers
	
	/*****************************************Constructor********************************************************/
	public Pipe(int max, int min, int counter, int xBird){				//overloaded constructor
		randMax = max;
		randMin = min;
		
		birdWidth = Game.PIC_WIDTH;
		birdHeight = Game.PIC_HEIGHT;
		birdX = xBird;
		
		p_x = p_InitialGap + (counter * p_GapBetween);
		
		newPipeLocation();				//calls new pipe location
	}
	
	/*****************************************Random num gen********************************************************/
	public void newPipeLocation(){
		 p_y = rand.nextInt((randMax - randMin) + 1) + randMin;			//chooses point y from random number gen
	}
	
	/*****************************************Paint********************************************************/
	public void pipePaint(Graphics g, int speed){
		xSpeed = speed;				//Setting speed
		pipeMove(xSpeed);			//Moves pipes xSpeed amount to the left
		
		g.setColor(Color.GREEN);	//Paint in green - Pipe Shaft
		
		g.fillRect(p_x+10, 0, p_WidthBottom, p_y);
		g.fillRect(p_x+10, (p_y+p_GapHeight), p_WidthBottom, (600-(p_y+p_GapHeight)));
		
		g.setColor(Color.green.darker());			//Paint in dark green - Pipe Entrance
		g.fillRect(p_x, p_y-p_HeightTop, p_WidthTop, p_HeightTop);
		g.fillRect(p_x, p_y+p_GapHeight, p_WidthTop, p_HeightTop);
		
	}
	
	/*****************************************Check bounds********************************************************/
	public void checkBounds(int yPos){
		int[][] pipeBounds = new int[2][2];			//[0][0] is for top left corner X VAL, [0][1] is for top left corner Y VAL, [1][0] for bottom right X VAL etc...
		
		pipeBounds[0][0] = p_x+5;						//top left x coord, +5 for hitbox leway
		pipeBounds[0][1] = p_y;						//top left y coord
		pipeBounds[1][0] = (p_x)+p_WidthTop-5;		//bottom right x coord, +5 for hitbox leway
		pipeBounds[1][1] = p_y + p_GapHeight;		//bottom right y coord
		
		if ((birdX + birdWidth >= pipeBounds[0][0]) && (birdX < pipeBounds[1][0])){		//If within pipe
			if(!scored){						//If score has not been counted for this pipe
				scored = true;						//score has now been counted	
				Game.xScore++;						//increment score
			}
			if(yPos+5 < pipeBounds[0][1]){		//Higher than top of pipe then dead
				Game.click = false;					//+5 for slight hitbox leway
				Game.alive = false;
			}
			else if(yPos - 5 + birdHeight > pipeBounds[1][1]){		//lower than bottom pipe then dead
				Game.click = false;					//-5 for slight hitbox leway
				Game.alive = false;
			}
		}
	}
	
	/*****************************************Move Pipe********************************************************/
	public void pipeMove(int xMove){
		p_x -= xMove;							//X point of pipe minus xMove, moves pipe to left
		if(p_x == -100){						//If pipe is full offscreen then reset
			p_x = 1260;								//Start off screen
			newPipeLocation();						//New y Val
			scored = false;							//Able to score once passed again
		}
	}
}
