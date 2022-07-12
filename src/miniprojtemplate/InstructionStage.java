package miniprojtemplate;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InstructionStage {
	public static final int WINDOW_HEIGHT = 500;
	public static final int WINDOW_WIDTH = 800;
	private Scene scene;
	private Stage stage;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;

	//images for the instructions/descriptions (modified sizes)
	public final static Image bg = new Image("images/bg2.jpg",InstructionStage.WINDOW_WIDTH,InstructionStage.WINDOW_HEIGHT,false,false);
	public final static Image FISH_IMAGE = new Image("images/alien.gif",Fish.FISH_WIDTH*1.2,Fish.FISH_WIDTH*1.2,false,false);
	public final static Image BOSS_IMAGE = new Image("images/boss.gif",Fish.FISH_WIDTH*2,Fish.FISH_WIDTH*1.5,false,false);
	public final static Image SHIP_IMAGE = new Image("images/gunner.gif",Ship.SHIP_WIDTH*1.75,Ship.SHIP_WIDTH*1.75,false,false);
	public final static Image PEARL_IMAGE = new Image("images/pearl.gif",(PowerUps.POWERUP_WIDTH-15)*0.75,PowerUps.POWERUP_WIDTH*0.75,false,false);
	public final static Image STAR_IMAGE = new Image("images/star.gif",PowerUps.POWERUP_WIDTH*0.75,PowerUps.POWERUP_WIDTH*0.75,false,false);
	public final static Image MULTISHOT_IMAGE = new Image("images/multishot.gif",PowerUps.POWERUP_WIDTH*0.75,PowerUps.POWERUP_WIDTH*0.75,false,false);
	public final static Image ARRKEYS_IMAGE = new Image("images/arrkeys.gif",PowerUps.POWERUP_WIDTH*2,PowerUps.POWERUP_WIDTH*1.75,false,false);
	public final static Image WASD_IMAGE = new Image("images/wasd.gif",PowerUps.POWERUP_WIDTH*2.75,PowerUps.POWERUP_WIDTH*2.5,false,false);
	public final static Image SPACEBTN_IMAGE = new Image("images/spaceBtn.gif",PowerUps.POWERUP_WIDTH*2,PowerUps.POWERUP_WIDTH*1.75,false,false);


	//the class constructor
	public InstructionStage() {
		this.root = new Group();
		this.scene = new Scene(root, InstructionStage.WINDOW_WIDTH,InstructionStage.WINDOW_HEIGHT,Color.CADETBLUE);
		this.canvas = new Canvas(InstructionStage.WINDOW_WIDTH,InstructionStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
	}

	//method to add the stage elements
	void setStage(Stage stage) {
		this.stage = stage;
		this.gc.drawImage(bg, 0, 0);
		this.root.getChildren().add(canvas);


		//Start of Instructions Layout
		Group spriteGrp = new Group();
		Rectangle rect1 = new Rectangle(10,10,600,300);
		rect1.setStroke(Color.BLACK);
		rect1.setStrokeWidth(5);
		rect1.setFill(Color.rgb(255, 255, 255,0.25));

		Rectangle rect2 = new Rectangle(620,10,170,480);
		rect2.setStroke(Color.BLACK);
		rect2.setStrokeWidth(5);
		rect2.setFill(Color.rgb(255, 255, 255,0.25));

		Rectangle rect3 = new Rectangle(10,320,600,170);
		rect3.setStroke(Color.BLACK);
		rect3.setStrokeWidth(5);
		rect3.setFill(Color.rgb(255, 255, 255,0.25));
		//End of Instructions Layout (i.e. adding shapes as overlay background for texts)

		this.root.getChildren().addAll(rect1, rect2, rect3);

		//Start of Adding In-Game Sprite Icons
		ImageView ship = new ImageView();
		ship.setImage(SHIP_IMAGE);ship.setLayoutX(30); ship.setLayoutY(50);

		ImageView fish = new ImageView();
		fish.setImage(FISH_IMAGE);fish.setLayoutX(35); fish.setLayoutY(150);

		ImageView boss = new ImageView();
		boss.setImage(BOSS_IMAGE);boss.setLayoutX(20); boss.setLayoutY(220);

		ImageView pearl = new ImageView();
		pearl.setImage(PEARL_IMAGE);pearl.setLayoutX(50); pearl.setLayoutY(350);

		ImageView star = new ImageView();
		star.setImage(STAR_IMAGE);star.setLayoutX(45); star.setLayoutY(395);

		ImageView multishot = new ImageView();
		multishot.setImage(MULTISHOT_IMAGE);multishot.setLayoutX(45); multishot.setLayoutY(440);

		ImageView arrKeys = new ImageView();
		arrKeys.setImage(ARRKEYS_IMAGE);arrKeys.setLayoutX(655); arrKeys.setLayoutY(40);

		ImageView wasdKeys = new ImageView();
		wasdKeys.setImage(WASD_IMAGE);wasdKeys.setLayoutX(635); wasdKeys.setLayoutY(130);

		ImageView spaceBtn = new ImageView();
		spaceBtn.setImage(SPACEBTN_IMAGE);spaceBtn.setLayoutX(655); spaceBtn.setLayoutY(330);


		Font subFont = Font.font("OCR A Extended",FontWeight.EXTRA_BOLD,23);//setting font for sub-headings
		Text sprites = new Text("The In-Game Sprites:"); sprites.setFont(subFont); sprites.setLayoutX(125); sprites.setLayoutY(35);
		sprites.setFill(Color.LAWNGREEN);

		Text controls = new Text("Controls"); controls.setFont(subFont); controls.setLayoutX(630); controls.setLayoutY(35);
		controls.setFill(Color.LAWNGREEN);

		Text powerUps = new Text("Power-Ups: Despawns after 5 seconds!"); powerUps.setFont(subFont); powerUps.setLayoutX(20); powerUps.setLayoutY(340);
		powerUps.setFill(Color.LAWNGREEN);

		Text orWord = new Text("OR"); orWord.setFont(subFont); orWord.setLayoutX(690); orWord.setLayoutY(145);
		orWord.setFill(Color.LAWNGREEN);

		spriteGrp.getChildren().addAll(ship, fish, boss, pearl, star, multishot, arrKeys, wasdKeys, spaceBtn); // Adding the sprite display for the user to see
		spriteGrp.getChildren().addAll(sprites, controls, powerUps, orWord); // Adding Labels for identification
		//End of Adding Game Sprite display

		// Start of Adding Game Descriptions
		Font descFont = Font.font("Trebuchet MS",FontWeight.NORMAL,17); //description font
		Text gunner = new Text("This is your Gunner. Use the controls and shoot the \nenemies coming from the right side of the screen!\n(HP/Damage: Random value between 100-150)");
		gunner.setFont(descFont); gunner.setLayoutX(130); gunner.setLayoutY(70); gunner.setTextAlignment(TextAlignment.JUSTIFY);
		gunner.setFill(Color.WHITE);

		Text normalFish = new Text("Shoot the mini-alien ships to gain points! Watch out though! \nThey fly over the screen from left to right and vice-versa. \n(Alien Ship Attack Damage: 30)");
		normalFish.setFont(descFont); normalFish.setLayoutX(130); normalFish.setLayoutY(150); normalFish.setTextAlignment(TextAlignment.JUSTIFY);
		normalFish.setFill(Color.WHITE);

		Text bossFish = new Text("This big alien ship shoots plasma rays! It's much bigger than\nthe normal ships and deals more damage to your Gunner!\n(HP: 3000; Boss Ship Attack Damage: 50;\nPlasma Ray Damage: 30; +5 points if defeated)");
		bossFish.setFont(descFont); bossFish.setLayoutX(130); bossFish.setLayoutY(230); bossFish.setTextAlignment(TextAlignment.JUSTIFY);
		bossFish.setFill(Color.WHITE);

		Text pearlTitle = new Text("Pearl - restores 50 HP to your Gunner!"); pearlTitle.setFont(descFont); pearlTitle.setLayoutX(130); pearlTitle.setLayoutY(380); pearlTitle.setTextAlignment(TextAlignment.JUSTIFY);
		pearlTitle.setFill(Color.WHITE);

		Text starTitle = new Text("Star - grants immortality for 3 seconds! Defeat the \nsmall fries while invulnerable!"); starTitle.setFont(descFont); starTitle.setLayoutX(130); starTitle.setLayoutY(415); starTitle.setTextAlignment(TextAlignment.CENTER);
		starTitle.setFill(Color.WHITE);

		Text multishotTitle = new Text("Multishot - grants 2 more additional bullet shots!\nOnly lasts for 5 seconds!"); multishotTitle.setFont(descFont); multishotTitle.setLayoutX(130); multishotTitle.setLayoutY(460); multishotTitle.setTextAlignment(TextAlignment.CENTER);
		multishotTitle.setFill(Color.WHITE);

		Text keysCtrls = new Text("Move your Gunner\nwith Arrow keys or\nWASD keys!"); keysCtrls.setFont(descFont); keysCtrls.setLayoutX(630); keysCtrls.setLayoutY(250); keysCtrls.setTextAlignment(TextAlignment.CENTER);
		keysCtrls.setFill(Color.WHITE);

		Text shootCtrl = new Text("Shoot your enemies\n by pressing the\n Spacebar!"); shootCtrl.setFont(descFont); shootCtrl.setLayoutX(630); shootCtrl.setLayoutY(430); shootCtrl.setTextAlignment(TextAlignment.CENTER);
		shootCtrl.setFill(Color.WHITE);
		// End of Adding Game Descriptions

		// Adding a back button
		Font btnFont = Font.font("Bahnschrift",FontWeight.EXTRA_BOLD,12);
		Button backGameBtn = new Button("Back");
		backGameBtn.setFont(btnFont);
		backGameBtn.setLayoutX(18);
		backGameBtn.setLayoutY(18);

		this.addEventHandler(backGameBtn);

		spriteGrp.getChildren().addAll(gunner, normalFish, bossFish,pearlTitle, starTitle, multishotTitle, keysCtrls, shootCtrl, backGameBtn);

		this.root.getChildren().add(spriteGrp);
		this.stage.setTitle("Instructions");
		this.stage.setScene(this.scene);

		this.stage.show();
	}

	private void addEventHandler(Button btn) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				switch(btn.getText()){

				case "Back":
					GameStartStage theGameStartStage = new GameStartStage();
					theGameStartStage.setStage(stage, 1);

				}
			}
		});
	}
}

