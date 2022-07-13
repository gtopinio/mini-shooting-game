package miniprojtemplate;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/*
 * The GameTimer is a subclass of the AnimationTimer class. It must override the handle method.
 */

public class GameTimer extends AnimationTimer{

	private GraphicsContext gc;
	private Scene theScene;
	private Ship myShip;
	//separate timers for different elements of the game
	private long startSpawn;
	private long gameTimerStart;
	private long bossSpawn;
	private long powerUpSpawn;
	private long startImmortal;
	private long startBossAttack;
	private long startPlasmaShoot;
	private long startMultishot;
	private long powerUpDespawn;

	private ArrayList<Fish> fishes;
	private ArrayList<PowerUps> powerUps;

	private final static double SPAWN_FISH_DELAY = 5.0;
	private final static double SPAWN_BOSS_DELAY = 30.0;
	private final static double SPAWN_POWERUPS_DELAY = 10.0;
	private final static double IMMORTALITY_DELAY = 3.0;
	private final static double MULTISHOT_DELAY = 5.0;
	private final static double BOSS_ATTACK_DELAY = 1.0;
	private final static double PLASMA_SHOOT_DELAY = 1.0;
	private final static double POWERUP_DESPAWN_TIME = 5.0;

	public static final int MAX_NUM_FISHES = 7;
	public static final int MAX_NUM_POWERUPS = 1;

	private static int min; // for showing the minute mark for the timer
	private static int sec; // for showing the second mark for the timer
	private static boolean killTick; // toggle switch when shooting a NORMAL fish
	private static boolean bossHit; // toggle switch when a BOSS fish takes damage from bullets
	private static boolean shootTick; // toggle switch when spawning a bullet when the Ship shoots

	public static final Image bg = new Image("images/gameStageBG.png",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	public static final Image heart = new Image("images/heart.gif",30,30,false,false); // heart icon for the Ship's strength/HP
	public final static Image boss = new Image("images/boss.gif",Fish.FISH_WIDTH*0.7,Fish.FISH_WIDTH*0.45,false,false); // boss icon for boss health

	GameTimer(GraphicsContext gc, Scene theScene){
		this.gc = gc;
		this.theScene = theScene;

		//separate timers for different elements of the game
		this.startSpawn = System.nanoTime();
		this.gameTimerStart = System.nanoTime();
		this.bossSpawn = System.nanoTime();
		this.powerUpSpawn = System.nanoTime();
		this.startImmortal = System.nanoTime();
		this.startBossAttack = System.nanoTime();
		this.startMultishot = System.nanoTime();
		this.startPlasmaShoot = System.nanoTime();
		this.powerUpDespawn = System.nanoTime();

		this.myShip = new Ship("Alpha Zero",150,250);
		//instantiate the ArrayList of Fish
		this.fishes = new ArrayList<Fish>();
		this.powerUps = new ArrayList<PowerUps>();

		//call the spawnFishes method
		this.spawnFishes(0);
		//call method to handle mouse click event
		this.handleKeyPressEvent();
	}

	@Override
	public void handle(long currentNanoTime) {
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc.drawImage(bg, 0, 0);

		//These switches reset at every frame, making sure that the handle() doesn't increment the ship's kill count
		GameTimer.killTick = false;
		GameTimer.bossHit = false;
		GameTimer.shootTick = false;

		long currentSec = TimeUnit.NANOSECONDS.toSeconds(currentNanoTime);
		long gameStartSec = TimeUnit.NANOSECONDS.toSeconds(this.gameTimerStart);
		int passedTime = (int) (currentSec - gameStartSec);

		this.checkBulletCollisions(this.myShip);
		this.checkFishCollisions(this.myShip);
		this.checkPowerUpCollision(this.myShip);
		this.checkPlasmaCollisions(this.myShip);

		double spawnElapsedTime = (currentNanoTime - this.startSpawn) / 1000000000.0;
		double bossSpawnTime = (currentNanoTime - this.bossSpawn) / 1000000000.0;
		double powerUpSpawnTime = (currentNanoTime - this.powerUpSpawn) / 1000000000.0;
		double immortalElapsedTime = (currentNanoTime - this.startImmortal) / 1000000000.0;
		double bossAttackElapsedTime = (currentNanoTime - this.startBossAttack) / 1000000000.0;
		double multishotElapsedTime = (currentNanoTime - this.startMultishot) / 1000000000.0;
		double plasmaSpawnTime = (currentNanoTime - this.startPlasmaShoot) / 1000000000.0;
		double powerUpDespawnTime = (currentNanoTime - this.powerUpDespawn) / 1000000000.0;

		this.myShip.move();
		/*
		 * TODO: Call the moveBullets and moveFishes methods (and other moving Sprites)
		 */
		this.moveFishes();
		this.moveBullets();
		this.movePowerUp();
		this.movePlasma();

		if(spawnElapsedTime > GameTimer.SPAWN_FISH_DELAY){ //Fishes respawn every 5 seconds
			this.spawnFishes(3);
			this.startSpawn = System.nanoTime();
		}

		if(powerUpDespawnTime > GameTimer.POWERUP_DESPAWN_TIME){ //Power-Ups despawn every 5 seconds
			this.despawnPowerUp();
		}

		if(powerUpSpawnTime > GameTimer.SPAWN_POWERUPS_DELAY){ //Power-Ups respawn every 10 seconds
			this.spawnPowerUp();
			this.powerUpSpawn = System.nanoTime();
			this.powerUpDespawn = System.nanoTime();
		}

		if(bossSpawnTime > GameTimer.SPAWN_BOSS_DELAY){ //Boss spawns every 30 seconds or half a minute
			this.spawnFishes(1); // '1' phase for summoning the Boss
			this.bossSpawn = System.nanoTime();
			this.startPlasmaShoot = System.nanoTime();
		}

		if(immortalElapsedTime > GameTimer.IMMORTALITY_DELAY && this.myShip.isImmortal() == true){ // Acquired immortality only lasts 3 seconds
			this.myShip.setShipImmortal(false);
			this.startImmortal = System.nanoTime();
		}

		if(multishotElapsedTime > GameTimer.MULTISHOT_DELAY && this.myShip.isMultiActive() == true){ // Acquired multishot only lasts 5 seconds
			this.myShip.setMultishot(false);
			this.startMultishot = System.nanoTime();
		}

		this.checkBossCollision(myShip, bossAttackElapsedTime);
		if(bossAttackElapsedTime > GameTimer.BOSS_ATTACK_DELAY){ //Boss' attack has a cool down of 1 second
			this.startBossAttack = System.nanoTime();
		}

		for(int i=0; i<fishes.size(); i++){
			if(fishes.get(i).getType() == Fish.BOSS_FISH_TYPE){
				if(plasmaSpawnTime > GameTimer.PLASMA_SHOOT_DELAY){ //If the Boss is spawned, it shoots plasma rays every 1 second
					fishes.get(i).bossAttack();
					this.startPlasmaShoot = System.nanoTime();
				}
			}
		}

		//rendering status bar
		this.setTimer(passedTime);
		this.showScore(this.myShip);
		this.showHealth(this.myShip);
		for(int i=0; i<fishes.size(); i++){
			if(fishes.get(i).getType() == Fish.BOSS_FISH_TYPE){
					this.showBossHealth(fishes.get(i).getHealth());
			}
		}

		//render the ship
		this.myShip.render(this.gc);

		/*
		 * TODO: Call the renderFishes and renderBullets methods
		 */
		this.renderFishes();
		this.renderBullets();
		this.renderPowerUp();
		this.renderPlasma();

		//Check if the game is over (i.e. Ship's strength<=0 or the timer hits 60 seconds)
		this.checkGameOver(currentSec, gameStartSec, this.myShip);

		//Checking in-game status via terminal
		System.out.println("==================================");
		System.out.println("Fishes: " + fishes.size());
		System.out.println("Bullets: " + this.myShip.getBullets().size());
		System.out.println("Immortal: " + this.myShip.isImmortal());
		System.out.println("Bullet dmg: " + this.myShip.getInitStrength());
		System.out.println("Ship strength: " + this.myShip.getStrength());
		System.out.println("Multishot: " + this.myShip.isMultiActive());
		for(Fish f: fishes){
			if(f.getType()==Fish.BOSS_FISH_TYPE){
				System.out.println("Boss HP: "+ f.getHealth());
				System.out.println("Plasma Rays: " + f.getPlasma().size());
			}
		}
	}

	//method to check the Ship's strength and determines which GameOver Window theme to show the player.
	//If the ship survives until 60 seconds, the player wins and shows the Winning theme for the GameOver window.
	//Otherwise, the player gets the Losing theme for the GameOver window.
	private void checkGameOver(long currentSec, long startSec, Ship myShip){
		if(currentSec != startSec && (currentSec-startSec)%60==0){
			if(myShip.getStrength() > 0 || myShip.isAlive() == true){
				this.stop();
				this.setGameOver(1,myShip);
			}
			else if(myShip.getStrength() <= 0 || myShip.isAlive() == false){
				this.stop();
				this.setGameOver(0,myShip);
			}
		}
	}

	//method to update the timer at every frame
	private void setTimer(int t){
		Font theFont = Font.font("OCR A Extended",FontWeight.BOLD,30);
		this.gc.setFont(theFont);
		if(t == 0){
			min = 1; sec = 0;
			this.gc.setFill(Color.CYAN);
			this.gc.fillText("TIME:"+min+":"+sec+""+0, GameStage.WINDOW_WIDTH-565, 30);
		}
		else if(t > 50){
			min = 0; sec = 60-t;
			this.gc.setFill(Color.CRIMSON);
			this.gc.fillText("TIME:"+min+":"+0+""+sec, GameStage.WINDOW_WIDTH-565, 30);
		}
		else{
			min = 0; sec = 60-t;
			this.gc.setFill(Color.CYAN);
			this.gc.fillText("TIME:"+min+":"+sec, GameStage.WINDOW_WIDTH-565, 30);
		}
	}

	//method to show the ship's current kill count
	private void showScore(Ship myShip){
		Font theFont = Font.font("OCR A Extended",FontWeight.BOLD,30);
		this.gc.setFont(theFont);

		this.gc.setFill(Color.MEDIUMSPRINGGREEN);
		this.gc.fillText("SCORE:"+myShip.getShipKillCount(), GameStage.WINDOW_WIDTH-383, 30);
	}

	//method to show the ship's current strength(HP)
	private void showHealth(Ship myShip){

		Font theFont = Font.font("OCR A Extended",FontWeight.BOLD,30);
		this.gc.setFont(theFont);

		this.gc.setFill(Color.CHARTREUSE);
		this.gc.fillText("STRENGTH:"+myShip.getStrength(), GameStage.WINDOW_WIDTH-225, 30);

		if(myShip.isImmortal()==true){ //If Ship is immortal, notify user by setting the health bar's color to YELLOW
			this.gc.setFill(Color.YELLOW);
		}else{
			this.gc.setFill(Color.LAWNGREEN); //normal health bar's color is GREEN
		}
		double healthStatus = (double) myShip.getStrength()/(double) myShip.getInitStrength(); //percentage of the Ship's current strength
		if(healthStatus >= 1.0){
			this.gc.fillRect(20, 10,200,10); // max health bar width
		}else{
			this.gc.fillRect(20, 10,200*healthStatus,10);
		}
		this.gc.drawImage(heart, 0, 0);

	}

	//method to show the boss' current health
	private void showBossHealth(int bossHealth){

		double initBossHealth = 3000.0;

		this.gc.setFill(Color.RED);

		double healthStatus = (double) bossHealth / initBossHealth;
		if(healthStatus >= 1.0){
			this.gc.fillRect(20, 40, 200, 10);
		} else {
			this.gc.fillRect(20, 40,200*healthStatus,10);
		}
		this.gc.drawImage(boss, 3, 35);
	}

	//method to check Bullet collisions
	private void checkBulletCollisions(Ship myShip){
		ArrayList<Bullet> bullets = myShip.getBullets();
		for(int i=0; i < bullets.size(); i++){
			for(int j=0; j < fishes.size(); j++){
				if(bullets.get(i).collidesWith(fishes.get(j)) && fishes.get(j).getType() == Fish.NORMAL_FISH_TYPE){
					fishes.get(j).die(); //The normal fish die if collided with the Ship's bullet
					bullets.get(i).setVisible(false);
					if(killTick == false){ //Makes sure not to execute the 'increaseShipKillCount()' at every second
						myShip.increaseShipKillCount();
						killTick = true;
					}
				}
				else if(bullets.get(i).collidesWith(fishes.get(j)) && fishes.get(j).getType() == Fish.BOSS_FISH_TYPE && bossHit == false && shootTick == false){
					fishes.get(j).setHealth(-(bullets.get(i).getBulletStrength()));
					if(fishes.get(j).getHealth()<=0){ //If the Boss' health is not greater than 0, set visibility on both Boss and Bullet to false
						fishes.get(j).die();
						bullets.get(i).setVisible(false);

						myShip.increaseShipKillCount(5); //When a Boss die, points gained is five instead of 1
					}
					else{
						bullets.get(i).setVisible(false);
					}
				} bossHit = true; //this doesn't allow the ship to have multiple shots on the boss for the current time
			}
		}
	}

	//method to check Plasma collisions from the Boss Fish
	private void checkPlasmaCollisions(Ship myShip){
		for(int i=0; i<fishes.size(); i++){
			if(fishes.get(i).getType() == Fish.BOSS_FISH_TYPE){
				ArrayList<Plasma> plasma = fishes.get(i).getPlasma();
				for(int j=0; j<plasma.size(); j++){
					if(plasma.get(j).collidesWith(myShip) && myShip.isImmortal() == false){
						myShip.setShipStrength(-(plasma.get(i).getPlasmaStrength()));
						plasma.get(j).setVisible(false);
					}
					else if(plasma.get(j).collidesWith(myShip) && myShip.isImmortal() == true){
						plasma.get(j).setVisible(false);
					}
				}
			}
		}
	}

	//method to check Fish collisions
	private void checkFishCollisions(Ship myShip){
		for(Fish f: fishes){
			if(myShip.collidesWith(f) && f.getType()==Fish.NORMAL_FISH_TYPE && myShip.isImmortal() == false){
				myShip.setShipStrength(-(Fish.FISH_NORMAL_DMG));
				f.die();
				f.setVisible(false);
			}
			else if(myShip.collidesWith(f) && f.getType()==Fish.NORMAL_FISH_TYPE && myShip.isImmortal() == true){
				f.die();
				f.setVisible(false);
				myShip.increaseShipKillCount();
			}
			if(myShip.getStrength()<=0) {myShip.die(); this.stop(); this.setGameOver(0,myShip); }
			this.checkBulletCollisions(myShip);
		}
	}

	//method to check Boss collisions to the Ship.
	//This method makes sure that the  Boss' attack has a cool down of 1 second
	private void checkBossCollision(Ship myShip, double bossAttackElapsedTime){
		for(Fish f: fishes){
			if(f.getType()==Fish.BOSS_FISH_TYPE && myShip.collidesWith(f) && myShip.isImmortal() == false
					&& bossAttackElapsedTime > GameTimer.BOSS_ATTACK_DELAY){
				myShip.setShipStrength(-(Fish.FISH_BOSS_DMG));
				this.startBossAttack = System.nanoTime();
			}
			else if(f.getType()==Fish.BOSS_FISH_TYPE && myShip.collidesWith(f) && myShip.isImmortal() == true
					&& bossAttackElapsedTime > GameTimer.BOSS_ATTACK_DELAY){
				this.startBossAttack = System.nanoTime();
			}
			if(myShip.getStrength()<=0) {myShip.die();}
		}
	}

	//method to check PowerUp collisions
	private void checkPowerUpCollision(Ship myShip){
		for(PowerUps p: powerUps){
			if(myShip.collidesWith(p) && p.getType() == PowerUps.PEARL_TYPE){
				myShip.setShipStrength(PowerUps.PEARL_BUFF);
				p.die();
				p.setVisible(false);
			}
			else if(myShip.collidesWith(p) && p.getType() == PowerUps.STAR_TYPE){
				this.startImmortal = System.nanoTime();
				myShip.setShipImmortal(true);
				p.die();
				p.setVisible(false);
			}
			else if(myShip.collidesWith(p) && p.getType() == PowerUps.MULTISHOT_TYPE){
				this.startMultishot = System.nanoTime();
				myShip.setMultishot(true);
				p.die();
				p.setVisible(false);
			}
		}
	}

	//method that will render/draw the fishes to the canvas
	private void renderFishes() {
		for (Fish f : this.fishes){
			f.render(this.gc);
		}
	}

	//method that will render/draw the bullets to the canvas
	private void renderBullets() {
		/*
		 *TODO: Loop through the bullets arraylist of myShip
		 *				and render each bullet to the canvas
		 */
		for (Bullet b: this.myShip.getBullets()){
			b.render(this.gc);
		}
	}

	private void renderPlasma() {

		for (Fish f: this.fishes){
			if(f.getType()==Fish.BOSS_FISH_TYPE){
				ArrayList<Plasma> p = f.getPlasma();
				for(int i=0; i<p.size(); i++){
					p.get(i).render(this.gc);
				}
			}
		}
	}

	private void renderPowerUp() {
		for (PowerUps p: powerUps){
			p.render(this.gc);
		}
	}

	//method that will spawn/instantiate the fishes at a random y location from the start of the game
	//Phase 0 = initial 7 fishes at the start of the game
	//Phase 1 = summoning the Boss
	//any other phase = respawning 3 fishe
	private void spawnFishes(int phase){
		Random r = new Random();
		if(phase==0){ //If phase is 0, we spawn the initial 7 fishes first
		for(int i=0;i<GameTimer.MAX_NUM_FISHES;i++){
			//making sure that fishes won't spawn at the left hand side of the screen.
//			int x = r.nextInt((GameStage.WINDOW_WIDTH-GameStage.WINDOW_WIDTH/2)+1)+GameStage.WINDOW_WIDTH/2;
			int x = GameStage.WINDOW_WIDTH; // only spawns at the left most part of the screen at first spawn
			int y = r.nextInt(((GameStage.WINDOW_HEIGHT-70)-10)+1)+10;
			/*
			 *TODO: Add a new object Fish to the fishes arraylist
			 */
			Fish f = new Fish(x,y,Fish.NORMAL_FISH_TYPE);
			fishes.add(f);
			}
		}
		else if(phase==1){//Summoning the boss
			int x = r.nextInt((GameStage.WINDOW_WIDTH-GameStage.WINDOW_WIDTH/2)+1)+GameStage.WINDOW_WIDTH/2;
			int y = r.nextInt(((GameStage.WINDOW_HEIGHT-70)-10)+1)+10;
			Fish f = new Fish(x,y,Fish.BOSS_FISH_TYPE);
			fishes.add(f);
		}
		else{//Respawning 3 fishes every 5 seconds
		for(int i=0;i<GameTimer.MAX_NUM_FISHES-4;i++){
			//making sure that fishes won't spawn at the left hand side of the screen.
			int x = r.nextInt((GameStage.WINDOW_WIDTH-GameStage.WINDOW_WIDTH/2)+1)+GameStage.WINDOW_WIDTH/2;
			int y = r.nextInt(((GameStage.WINDOW_HEIGHT-70)-10)+1)+10;
			Fish f = new Fish(x,y,Fish.NORMAL_FISH_TYPE);
			fishes.add(f);
			}
		}
	}

	//method to summon a PowerUp
	private void spawnPowerUp(){
		Random r = new Random();
		for(int i=0;i<GameTimer.MAX_NUM_POWERUPS;i++){
			//making sure that powerUp won't spawn at the right hand side of the screen.
			int x = r.nextInt((GameStage.WINDOW_WIDTH/2-10)+1)+10;
			int y = r.nextInt(((GameStage.WINDOW_HEIGHT-50)-10)+1)+10;
			// Random PowerUp: 1 = Star; 0 = Pearl; 2 = Multishot;
			int z = r.nextInt((2-0)+1)+0;
			PowerUps p;
			if(z==1) p = new PowerUps(x,y,PowerUps.STAR_TYPE);
			else if(z==2) p = new PowerUps(x,y,PowerUps.MULTISHOT_TYPE);
			else p = new PowerUps(x,y,PowerUps.PEARL_TYPE);
			powerUps.add(p);
		}
	}

	//method to despawn the PowerUp
	private void despawnPowerUp(){
		  for(PowerUps p: powerUps){
			p.die();
			p.setVisible(false);
		  }
	}

	//method that will move the bullets shot by a ship
	private void moveBullets(){
		//create a local arraylist of Bullets for the bullets 'shot' by the ship
		ArrayList<Bullet> bList = this.myShip.getBullets();

		//Loop through the bullet list and check whether a bullet is still visible.
		for(int i = 0; i < bList.size(); i++){
			Bullet b = bList.get(i);
			/*
			 * TODO:  If a bullet is visible, move the bullet, else, remove the bullet from the bullet array list.
			 */
			if(b.getVisible()==true){
				b.move();
			}
			else{
				bList.remove(i);
			}
		}
	}

	private void movePlasma(){
		for(int i=0; i<this.fishes.size(); i++){
			if(fishes.get(i).getType() == Fish.BOSS_FISH_TYPE){
				ArrayList<Plasma> plasmaRays = fishes.get(i).getPlasma();

				for(int j=0; j<plasmaRays.size(); j++){
					Plasma p = plasmaRays.get(j);
					if(p.getVisible()==true && p.getPlasmaType() == Plasma.PLASMA_TOP_TYPE){
						p.move(Plasma.PLASMA_TOP_TYPE);
					}
					else if(p.getVisible()==true && p.getPlasmaType() == Plasma.PLASMA_BOT_TYPE){
						p.move(Plasma.PLASMA_BOT_TYPE);
					}
					else{
						plasmaRays.remove(j);
					}
				}
			}
		}
	}

	//method that will move the fishes
	private void moveFishes(){
		//Loop through the fishes arraylist
		for(int i = 0; i < this.fishes.size(); i++){
			Fish f = this.fishes.get(i);
			/*
			 * TODO:  *If a fish is alive, move the fish. Else, remove the fish from the fishes arraylist.
			 */
			if(f.isAlive()==true)f.move();
			else fishes.remove(i);
		}
	}

	//method that will move the power up
	private void movePowerUp(){
		for(int i = 0; i < this.powerUps.size(); i++){
			PowerUps p = this.powerUps.get(i);
			if(p.isAlive()==true){
				p.move();
			}
			else powerUps.remove(i);
		}
	}


	//method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                moveMyShip(code);
			}
		});

		this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
		            public void handle(KeyEvent e){
		            	KeyCode code = e.getCode();
		                stopMyShip(code);
		                myShipShoot(code);
		            }
		        });
    }

	//method that will move the ship depending on the key pressed
	private void moveMyShip(KeyCode ke) {
		if(ke==KeyCode.UP || ke==KeyCode.W) this.myShip.setDY(-3);

		if(ke==KeyCode.LEFT || ke==KeyCode.A) this.myShip.setDX(-3);

		if(ke==KeyCode.DOWN || ke==KeyCode.S) this.myShip.setDY(3);

		if(ke==KeyCode.RIGHT || ke==KeyCode.D) this.myShip.setDX(3);

   	}

	//method that will stop the ship's movement; set the ship's DX and DY to 0.
	private void stopMyShip(KeyCode ke){
		this.myShip.setDX(0);
		this.myShip.setDY(0);
		if(ke==KeyCode.SPACE) this.myShipShoot(ke);
	}

	//method to trigger the Ship's ability to shoot
	private void myShipShoot(KeyCode ke){
		if(ke==KeyCode.SPACE && shootTick == false) this.myShip.shoot(); shootTick = true; //Making sure that only one Bullet instance is spawned per shoot() method
	}

	// method to make the transition to another window that tells the player if he/she wins or not.
	// If num is 1, the player won. If 0, the player lost.
	private void setGameOver(int num, Ship myShip){
		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();

		transition.setOnFinished(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
//				Stage newStage = new Stage();
				GameOverStage gameover = new GameOverStage();
				gameover.setProperties(GameStage.getStage(),num, myShip.getShipKillCount());
			}
		});
	}

}
