package flappy;

public class Bird {
	/*****************************************Variables********************************************************/
	private int m_x; // x and y coordinates upper left
	private static int m_y;

	static int m_velocityX; // Find good speed
	double m_velocityY = 0;

	private static int m_accelerationY = 980;
	
	/*****************************************Constructor********************************************************/
	public Bird() {
		m_x = 200;
		m_y = 300;
	}
	/*****************************************Set y pos and velocity********************************************************/
	public void setPosVel(int yLocation, int yVelocity){
		m_y = yLocation;
		m_velocityY = yVelocity;
	}

	/*****************************************get x, y, velocity Y********************************************************/

	public int getX() {return m_x;}						//returns x (kind of pointless) x is always 200, makes it easier to switch
	public int getY() {return m_y;}						//Returns Y
	public double getVelY() {return m_velocityY;}		//Returns velocity
	
	/*****************************************Calc velocity********************************************************/
	public void calcVelocity() {
		m_velocityY += m_accelerationY * (Game.timer / 1000.0);			//Calcs velocity used for displacement
	}
	/*****************************************Calc Displacement********************************************************/
	public void calcPositionY() {
		int distance = 0;
		
		distance = (int)((m_velocityY * (Game.timer / 1000.0))+ ((1 / 2) * (m_accelerationY) * ((Game.timer / 1000.0) * (Game.timer / 1000.0))));
		m_y += distance;		//Adds displacement to y val
	}
}
