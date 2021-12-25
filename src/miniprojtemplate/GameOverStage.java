package miniprojtemplate;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class GameOverStage {
	private Group root;
	private Scene scene;
	private Stage stage;
	private GraphicsContext gc;
	private Canvas canvas;
	private MediaPlayer mediaPlayer;

	public static final String LOSE_MUSIC = "lose.wav";
	public static final String WIN_MUSIC = "win.wav";

	public final static Image WIN_IMAGE = new Image("images/win.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT-200,false,false);
	public final static Image LOSE_IMAGE = new Image("images/lose.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT-200,false,false);

	GameOverStage(){
		this.root = new Group();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();

	}

	public void setProperties(Stage stage,int num, int killCount){
		this.stage = stage;
		Font theFont = Font.font("OCR A Extended",FontWeight.BOLD,30);//set font type, style and size
		Font btnFont = Font.font("OCR A Extended",FontWeight.NORMAL,20);
		this.gc.setFont(theFont);
		ImageView view = new ImageView();
		GameStartStage.musicStop();
		switch(num){
		case 0: // Case if the player loses the game, this is the losing theme for the game over window
			this.gc.setFill(Color.BLACK);
			this.gc.fillRect(0,0,GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
			view.setImage(LOSE_IMAGE);
			this.root.getChildren().addAll(this.canvas,view);

			this.gc.setFill(Color.CRIMSON);										//set font color of text
			this.gc.fillText("You lost!\nScore: "+killCount, GameStage.WINDOW_WIDTH-480, GameStage.WINDOW_HEIGHT-160);
			this.gc.setTextAlign(TextAlignment.JUSTIFY);
			this.playMusic(LOSE_MUSIC);
			break;
		case 1: // Case if the player wins the game, this is the winning theme for the game over window
			this.gc.setFill(Color.BLACK);
			this.gc.fillRect(0,0,GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
			view.setImage(WIN_IMAGE);
			this.root.getChildren().addAll(this.canvas,view);

			this.gc.setFill(Color.LIMEGREEN);										//set font color of text
			this.gc.fillText("You won!\nScore: "+killCount, GameStage.WINDOW_WIDTH-480, GameStage.WINDOW_HEIGHT-160);
			this.gc.setTextAlign(TextAlignment.JUSTIFY);
			this.playMusic(WIN_MUSIC);
			break;
		}
		Button exitbtn = new Button("Exit Game"); exitbtn.setLayoutX(337); exitbtn.setLayoutY(400);
		Button menubtn = new Button("Back to Menu"); menubtn.setLayoutX(317); menubtn.setLayoutY(445);
		exitbtn.setFont(btnFont); menubtn.setFont(btnFont); exitbtn.setTextFill(Color.DARKRED); menubtn.setTextFill(Color.DARKRED);
		this.addEventHandler(exitbtn); this.addEventHandler(menubtn);
		root.getChildren().addAll(exitbtn,menubtn);

		this.stage.setTitle("Game Over!");
		this.stage.setScene(this.scene);
		this.stage.show();
	}

	private void addEventHandler(Button btn) {
		Stage gameOverStage = this.stage;
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				switch(btn.getText()){
				case "Exit Game":
					System.exit(0);
					break;
				case "Back to Menu":
					GameStartStage theGameStartStage = new GameStartStage();
					theGameStartStage.setStage(gameOverStage);
					break;
				}

			}
		});
	}

	//method to play music
	private void playMusic(String music){
		Media sound = new Media(new File(music).toURI().toString());
		this.mediaPlayer = new MediaPlayer(sound);
		this.mediaPlayer.play();
	}

//	//method to stop the music
//	private void musicStop(){
//		this.mediaPlayer.stop();
//	}
}
