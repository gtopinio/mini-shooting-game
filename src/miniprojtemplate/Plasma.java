package miniprojtemplate;

import javafx.scene.image.Image;

public class Plasma extends Sprite{
	private int strength;
	private String type;

	private final int PLASMA_SPEED = 2;
	public final static String PLASMA_TOP_TYPE = "top";
	public final static String PLASMA_BOT_TYPE = "bot";
	public final static Image PLASMA_TOP_IMAGE = new Image("images/plasmaTop.gif",Plasma.PLASMA_WIDTH,Plasma.PLASMA_WIDTH,false,false);
	public final static Image PLASMA_BOT_IMAGE = new Image("images/plasmaBot.gif",Plasma.PLASMA_WIDTH,Plasma.PLASMA_WIDTH,false,false);
	public final static int PLASMA_WIDTH = 40;

	public Plasma(int x, int y, String type){
		super(x,y);
		this.strength = 30;
		switch(type){
		case Plasma.PLASMA_TOP_TYPE:
			this.loadImage(Plasma.PLASMA_TOP_IMAGE);
			this.type = Plasma.PLASMA_TOP_TYPE;
			break;
		case Plasma.PLASMA_BOT_TYPE:
			this.loadImage(Plasma.PLASMA_BOT_IMAGE);
			this.type = Plasma.PLASMA_BOT_TYPE;
			break;
		}

	}


	//method that will move/change the x and y position of the plasma ray
	void move(String type){
		switch(type){
		case Plasma.PLASMA_TOP_TYPE:
			if(this.x - this.PLASMA_SPEED > 0 && this.y - this.PLASMA_SPEED > 0){
				this.x -= this.PLASMA_SPEED;
				this.y -= this.PLASMA_SPEED;
			}
			else{
				this.setVisible(false);
			}
			break;
		case Plasma.PLASMA_BOT_TYPE:
			if(this.x - this.PLASMA_SPEED > 0 && this.y + this.PLASMA_SPEED < GameStage.WINDOW_HEIGHT){
				this.x -= this.PLASMA_SPEED;
				this.y += this.PLASMA_SPEED;
			}
			else{
				this.setVisible(false);
			}
			break;
		}

	}
	//setter
	void setPlasmaStrength(int num){
		this.strength = num;
	}
	//getters
	int getPlasmaStrength(){
		return this.strength;
	}

	String getPlasmaType(){
		return this.type;
	}
}
