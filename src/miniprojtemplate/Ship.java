package miniprojtemplate;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;



public class Ship extends Sprite{
	private String name;
	private int strength;
	private int initStrength;
	private int killCount;
	private boolean alive;
	private boolean immortal;
	private boolean multishot;

	private ArrayList<Bullet> bullets;
	public final static Image SHIP_IMAGE = new Image("images/gunner.gif",Ship.SHIP_WIDTH*1.35,Ship.SHIP_WIDTH*1.35,false,false);
	public final static int SHIP_WIDTH = 50;
	public final static int MAX_SHIP_STRENGTH = 150;
	public final static int MIN_SHIP_STRENGTH = 100;

	public Ship(String name, int x, int y){
		super(x,y);
		this.name = name;
		Random r = new Random();
		this.strength = r.nextInt((MAX_SHIP_STRENGTH-MIN_SHIP_STRENGTH)+1)+MIN_SHIP_STRENGTH; //Random strength between 100 to 150
		this.initStrength = this.strength;
		this.alive = true;
		this.immortal = false;
		this.multishot = false;
		this.killCount = 0;
		this.bullets = new ArrayList<Bullet>();
		this.loadImage(Ship.SHIP_IMAGE);
	}

	//method called if spacebar is pressed
	void shoot(){
		//compute for the x and y initial position of the bullet
		int x = (int) (this.x + this.width+20);
		int y = (int) (this.y + this.height/2);
		/*
		 * TODO: Instantiate a new bullet and add it to the bullets arraylist of ship
		 */
		if(this.multishot == false){
			Bullet b = new Bullet(x,y);
			b.setBulletStrength(this.getInitStrength());
			this.bullets.add(b);
		}
		else{ // Setting up additional bullets if multishot attribute is active
			int top = y - 35, bot = y + 35;
			Bullet b = new Bullet(x,y);
			Bullet bTop = new Bullet(x,top); // the top bullet
			Bullet bBot = new Bullet(x, bot); // the bottom bullet
			b.setBulletStrength(this.getInitStrength());
			bTop.setBulletStrength(this.getInitStrength());
			bBot.setBulletStrength(this.getInitStrength());
			this.bullets.add(b); this.bullets.add(bTop); this.bullets.add(bBot);
		}
    }

	//method called if up/down/left/right arrow key is pressed.
	void move() {
		/*
		 *TODO: 		Only change the x and y position of the ship if the current x,y position
		 *				is within the gamestage width and height so that the ship won't exit the screen
		 */
		if(this.x+this.dx > 0 && this.x+this.dx <= GameStage.WINDOW_WIDTH-30 &&
				this.y+this.dy > 0 && this.y+this.dy <= GameStage.WINDOW_HEIGHT-50){
			this.x += this.dx;
			this.y += this.dy;
		}
	}

	//getters
	boolean isAlive(){
		if(this.alive) return true;
		return false;
	}
	String getName(){
		return this.name;
	}

	int getStrength(){
		return this.strength;
	}

	int getInitStrength(){
		return this.initStrength;
	}

	boolean isImmortal(){
		if(this.immortal) return true;
		return false;
	}

	boolean isMultiActive(){
		if(this.multishot) return true;
		return false;
	}

	//method that will get the bullets 'shot' by the ship
	ArrayList<Bullet> getBullets(){
		return this.bullets;
	}

	int getShipKillCount(){
		return this.killCount;
	}

	//setters
	void die(){
    	this.alive = false;
    }

	void increaseShipKillCount(){
		this.killCount++;
	}

	void increaseShipKillCount(int num){
		this.killCount = this.killCount+num;
	}

	void setShipStrength(int num){
		this.strength += num;
	}

	void setShipImmortal(boolean val){
		this.immortal = val;
	}

	void setMultishot(boolean val){
		this.multishot = val;
	}

}
