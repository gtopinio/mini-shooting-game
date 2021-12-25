package miniprojtemplate;

import java.util.Random;

import javafx.scene.image.Image;

public class PowerUps extends Sprite{
	public final static Image PEARL_IMAGE = new Image("images/pearl.gif",PowerUps.POWERUP_WIDTH-15,PowerUps.POWERUP_WIDTH,false,false);
	public final static Image STAR_IMAGE = new Image("images/star.gif",PowerUps.POWERUP_WIDTH,PowerUps.POWERUP_WIDTH,false,false);
	public final static Image MULTISHOT_IMAGE = new Image("images/multishot.gif",PowerUps.POWERUP_WIDTH-10,PowerUps.POWERUP_WIDTH-5,false,false);

	public final static String PEARL_TYPE = "pearl";
	public final static String STAR_TYPE = "star";
	public final static String MULTISHOT_TYPE = "multi";

	public final static int POWERUP_WIDTH = 50;
	public final static int  PEARL_BUFF = 50;
	public final static int MAX_POWERUP_SPEED = 1;

	private boolean alive;
	private boolean moveRight;
	private int speed;
	private String type;


	public PowerUps(int x, int y, String type){
		super(x,y);
		this.alive = true;
		switch(type){
		case PowerUps.PEARL_TYPE: this.loadImage(PowerUps.PEARL_IMAGE); this.type = PowerUps.PEARL_TYPE; break;
		case PowerUps.STAR_TYPE: this.loadImage(PowerUps.STAR_IMAGE); this.type = PowerUps.STAR_TYPE; break;
		case PowerUps.MULTISHOT_TYPE: this.loadImage(PowerUps.MULTISHOT_IMAGE); this.type = PowerUps.MULTISHOT_TYPE; break;
		}
		this.speed = PowerUps.MAX_POWERUP_SPEED;

		Random rand = new Random();
		int check = rand.nextInt((1-0)+1)+0; //Random toggle movement (either move left first or right first)
		if(check==0) this.moveRight = false;
		else this.moveRight = true;
	}

	void move(){ //Same movements from the Fish class, but on the left side on the screen only
		if(this.moveRight == true && this.x + this.speed < GameStage.WINDOW_WIDTH/2){
			this.x += this.speed;
		}
		else if(this.moveRight == true && this.x + this.speed >= GameStage.WINDOW_WIDTH/2){
			this.moveRight = false;
			this.x -= this.speed;
		}
		else if(this.moveRight == false && this.x - this.speed > 0){
			this.x -= this.speed;
		}
		else if(this.moveRight == false && this.x - this.speed <= 0){
			this.moveRight = true;
			this.x += this.speed;
		}
	}

	//getter
	boolean isAlive() {
		return this.alive;
	}

	String getType(){
		return this.type;
	}

	//setter
	void die(){
		this.alive = false;
	}

}
