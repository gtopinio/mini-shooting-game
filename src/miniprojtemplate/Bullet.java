package miniprojtemplate;

import javafx.scene.image.Image;

public class Bullet extends Sprite {
	private int strength;

	private final int BULLET_SPEED = 5;
	public final static Image BULLET_IMAGE = new Image("images/blast.png",Bullet.BULLET_WIDTH+30,Bullet.BULLET_WIDTH,false,false);
	public final static int BULLET_WIDTH = 10;

	public Bullet(int x, int y){
		super(x,y);
		this.strength = 0;
		this.loadImage(Bullet.BULLET_IMAGE);
	}


	//method that will move/change the x position of the bullet
	void move(){
		/*
		 * TODO: Change the x position of the bullet depending on the bullet speed.
		 * 					If the x position has reached the right boundary of the screen,
		 * 						set the bullet's visibility to false.
		 */
		if(this.x + this.BULLET_SPEED < GameStage.WINDOW_WIDTH){
			this.x += this.BULLET_SPEED;
		}
		else{
			this.setVisible(false);
		}
	}

	void setBulletStrength(int num){
		this.strength = num;
	}

	int getBulletStrength(){
		return this.strength;
	}
}