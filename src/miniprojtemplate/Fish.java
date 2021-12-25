package miniprojtemplate;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

public class Fish extends Sprite {
	public static final int MAX_FISH_SPEED = 5;
	public static final int MIN_FISH_SPEED = 1;
	public final static Image FISH_IMAGE = new Image("images/alien.gif",Fish.FISH_WIDTH,Fish.FISH_WIDTH,false,false);
	public final static Image BOSS_IMAGE = new Image("images/boss.gif",Fish.FISH_WIDTH*3.25,Fish.FISH_WIDTH*2.75,false,false);
	public final static int FISH_WIDTH=50;
	public final static int FISH_NORMAL_DMG = 30;
	public final static int FISH_BOSS_DMG = 50;
	public final static String NORMAL_FISH_TYPE = "normal fish";
	public final static String BOSS_FISH_TYPE = "boss fish";

	private boolean alive;
	private boolean moveRight; //attribute that will determine if a fish will initially move to the right
	private int speed;
	private int health;
	private String type;
	private ArrayList<Plasma> plasma; //attribute that is only active for the Boss Type fish


	Fish(int x, int y, String type){ // if type is 0, just a normal fish. Else if 1, it's a boss
		super(x,y);
		this.alive = true;
		switch(type){
		case Fish.NORMAL_FISH_TYPE: this.loadImage(Fish.FISH_IMAGE); this.type = Fish.NORMAL_FISH_TYPE; this.health = 1; break;
		case Fish.BOSS_FISH_TYPE: this.loadImage(Fish.BOSS_IMAGE); this.type = Fish.BOSS_FISH_TYPE;
				this.health = 3000; this.plasma = new ArrayList<Plasma>(); break;
		}

		/*
		 *TODO: Randomize speed of fish and moveRight's initial value
		 */
		Random r = new Random();
		this.speed = r.nextInt((MAX_FISH_SPEED-MIN_FISH_SPEED)+1)+MIN_FISH_SPEED;

		Random rand = new Random();
		int check = rand.nextInt((1-0)+1)+0; //Random toggle movement (either move left first or right first)
		if(check==0) this.moveRight = false;
		else this.moveRight = true;
	}

	//method that changes the x position of the fish
	void move(){
		/*
		 * TODO: 				If moveRight is true and if the fish hasn't reached the right boundary yet,
		 *    						move the fish to the right by changing the x position of the fish depending on its speed
		 *    					else if it has reached the boundary, change the moveRight value / move to the left
		 * 					 Else, if moveRight is false and if the fish hasn't reached the left boundary yet,
		 * 	 						move the fish to the left by changing the x position of the fish depending on its speed.
		 * 						else if it has reached the boundary, change the moveRight value / move to the right
		 */
		if(this.moveRight == true && this.x + this.speed < GameStage.WINDOW_WIDTH){
			this.x += this.speed;
		}
		else if(this.moveRight == true && this.x + this.speed >= GameStage.WINDOW_WIDTH){
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

	//method for the Boss' plasma ray attack
	void bossAttack(){
	//compute for the x and y initial position of the plasma rays
		int x = (int) (this.x + this.width-50);
		int y = (int) (this.y + this.height/2);
		/*
		 * TODO: Instantiate new plasma rays and add it to the plasma rays arraylist of Boss
		 */
		Plasma pTop = new Plasma(x,y,Plasma.PLASMA_TOP_TYPE); // the top plasma ray of the attack
		Plasma pBot = new Plasma(x,y,Plasma.PLASMA_BOT_TYPE);// the bottom plasma ray of the attack
		this.plasma.add(pTop); this.plasma.add(pBot);
	}

	//getters
	boolean isAlive() {
		return this.alive;
	}

	int getHealth(){
		return this.health;
	}

	String getType(){
		return this.type;
	}

	ArrayList<Plasma> getPlasma(){
		return this.plasma;
	}

	//setters
	void die(){
		this.alive = false;
	}

	void setHealth(int dmg){
		this.health += dmg;
	}


}
