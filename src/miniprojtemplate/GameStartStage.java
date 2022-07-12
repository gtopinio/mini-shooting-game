package miniprojtemplate;

import miniprojtemplate.GameStage;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameStartStage {
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 500;
	private Scene scene;
	private Stage stage;
	private Group root;
//	private GraphicsContext gc;
//	private Canvas canvas;

	private static MediaPlayer mediaPlayer;
	public static final String MAIN_MUSIC = "bgm1.wav"; //Splash screen background music
	public static final String IN_GAME_MUSIC = "bgm2.wav"; // In-game background music

	public static final Image bg = new Image("images/bg1.png",GameStartStage.WINDOW_WIDTH*1.5,GameStartStage.WINDOW_HEIGHT*1.5,true,false);
	public static final Image MAIN_BG = new Image("images/gameStartBG.gif",GameStartStage.WINDOW_WIDTH*1.5,GameStartStage.WINDOW_HEIGHT*1.5,true,false);
	//the class constructor
	public GameStartStage() {
		this.root = new Group();
		this.scene = new Scene(root, GameStartStage.WINDOW_WIDTH,GameStartStage.WINDOW_HEIGHT,Color.CADETBLUE);
//		this.canvas = new Canvas(GameStartStage.WINDOW_WIDTH,GameStartStage.WINDOW_HEIGHT);
//		this.gc = canvas.getGraphicsContext2D();

//		Media bgm1 = new Media(new File(GameStartStage.MAIN_MUSIC).toURI().toString());
//		GameStartStage.mediaPlayer = new MediaPlayer(bgm1);
	}

	//method to add the stage elements
	public void setStage(Stage stage, int musicContinue) {
//		this.gc.drawImage(bg, -118, 0);
		this.stage = stage;
		ImageView mainBg = new ImageView();
		mainBg.setImage(GameStartStage.MAIN_BG); mainBg.setLayoutX(-115);

		//set stage elements here
		this.root.getChildren().addAll(mainBg);
//		Font theFont = Font.font("Harlow Solid Italic",FontWeight.BOLD,50);//set font type, style and size
		Font btnFont = Font.font("Bahnschrift",FontWeight.EXTRA_BOLD,18);

		//Previous Title Text for the Splashscreen
//		this.gc.setFont(theFont);

//		this.gc.setFill(Color.WHITE);										//set font color of text
//		this.gc.fillText("Super Dead Gunner", GameStartStage.WINDOW_WIDTH*0.2, GameStartStage.WINDOW_HEIGHT*0.2);

//		Text head = new Text(GameStartStage.WINDOW_WIDTH*0.1, GameStartStage.WINDOW_HEIGHT*0.2, "Super Dead Gunner");
//		head.setFont(theFont);
//		head.setFill(Color.WHITE);
//		head.setStroke(Color.BLACK);
//		head.setStrokeWidth(2);

		//Adding a background overlay for the buttons
		Rectangle rect1 = new Rectangle(170,380,150,175);
		rect1.setFill(Color.rgb(255, 255, 255,0.25));

		Button newGameBtn = new Button("New Game");
		newGameBtn.setFont(btnFont);
		newGameBtn.setLayoutX(187);
		newGameBtn.setLayoutY(400);

		Button instrctBtn = new Button("Instructions");
		instrctBtn.setFont(btnFont);
		instrctBtn.setLayoutX(183);
		instrctBtn.setLayoutY(450);

		Button abtBtn = new Button("About");
		abtBtn.setFont(btnFont);
		abtBtn.setLayoutX(205);
		abtBtn.setLayoutY(500);

		this.addEventHandler(newGameBtn);
		this.addEventHandler(instrctBtn);
		this.addEventHandler(abtBtn);

		newGameBtn.setTextFill(Color.DARKRED);
		instrctBtn.setTextFill(Color.DARKRED);
		abtBtn.setTextFill(Color.DARKRED);

		this.root.getChildren().addAll(rect1, newGameBtn, instrctBtn,abtBtn);
		this.stage.setTitle("Mini-Ship Shooting Game");
		this.stage.setScene(this.scene);
		this.stage.show();


		//Playing and looping the music (NES Shooter Music by SketchyLogic)
		//if musicContinue is not zero, it means that the GameStartStage music is still playing
		if(musicContinue == 0){
			GameStartStage.playMusic(GameStartStage.MAIN_MUSIC);
		}
	}

	private void addEventHandler(Button btn) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
//				System.exit(0);
				switch(btn.getText()){

				case "New Game":
					System.out.println("New Game");
					Stage newStage = new Stage();
					GameStage theGameStage = new GameStage();
					theGameStage.setStage(newStage);
					musicStop();
					playMusic(GameStartStage.IN_GAME_MUSIC);
					stage.close();
					break;
				case "Instructions":
					System.out.println("Instructions");
					Stage instructStage = new Stage();
					InstructionStage theInstructions = new InstructionStage();
					theInstructions.setStage(instructStage);
					stage.close();
					break;
				case "About":
					System.out.println("About");
					Stage aboutStage = new Stage();
					AboutStage aboutMe = new AboutStage();
					aboutMe.setStage(aboutStage);
					stage.close();
					break;

				}
			}
		});

	}

	//method to play music
	static void playMusic(String music){
		Media sound = new Media(new File(music).toURI().toString());
		GameStartStage.mediaPlayer = new MediaPlayer(sound);
		GameStartStage.mediaPlayer.setOnEndOfMedia(new Runnable(){
			public void run(){
				mediaPlayer.seek(Duration.ZERO);
			}
		});
		GameStartStage.mediaPlayer.play();
	}

	//method to stop the music
	static void musicStop(){
		GameStartStage.mediaPlayer.stop();
	}
}

