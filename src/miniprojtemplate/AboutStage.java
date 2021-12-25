package miniprojtemplate;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AboutStage {
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 800;
	private Scene scene;
	private Stage stage;
	private Group root;
	private ImageView bgAbout;

	public final static Image bg = new Image("images/bg3.gif",AboutStage.WINDOW_WIDTH,AboutStage.WINDOW_HEIGHT,false,false);
	public final static Image PROFILE_IMAGE = new Image("images/profilePic.png",Ship.SHIP_WIDTH*4.25,Ship.SHIP_WIDTH*4.25,false,false);

	//the class constructor
	public AboutStage() {
		this.root = new Group();
		this.scene = new Scene(root, AboutStage.WINDOW_WIDTH,AboutStage.WINDOW_HEIGHT,Color.CADETBLUE);
		this.bgAbout = new ImageView();
		bgAbout.setImage(bg);
	}

	//method to add the stage elements
	void setStage(Stage stage) {
		this.stage = stage;
		this.root.getChildren().add(this.bgAbout);

		Group descGroup = new Group();
		//Start of Instructions Layout (i.e. adding shapes as overlay background for texts)
		Rectangle rect1 = new Rectangle(10,10,780,300);
		rect1.setStroke(Color.BLACK);
		rect1.setStrokeWidth(5);
		rect1.setFill(Color.rgb(255, 255, 255,0.25)); //Making the shape a bit transparent


		Rectangle rect2 = new Rectangle(10,320,780,270);
		rect2.setStroke(Color.BLACK);
		rect2.setStrokeWidth(5);
		rect2.setFill(Color.rgb(255, 255, 255,0.25)); //Making the shape a bit transparent
		//End of Instructions Layout

		this.root.getChildren().addAll(rect1, rect2);

		// Title and Descriptions texts
		Font headFont = Font.font("Corbel",FontWeight.BOLD,50);
//		Font subFont = Font.font("Tw Cen MT",FontWeight.BOLD,40);
		Font subDetailsFont = Font.font("Trebuchet MS",FontWeight.BOLD,20);
		Font descFont = Font.font("Trebuchet MS",FontWeight.BOLD,18);
		Font referenceFont = Font.font("Tw Cen MT",FontWeight.NORMAL,15);

		Text aboutTitle = new Text("About the Developer"); aboutTitle.setLayoutX(20); aboutTitle.setLayoutY(60);
		aboutTitle.setFont(headFont); aboutTitle.setStroke(Color.BLACK); aboutTitle.setFill(Color.AQUA); aboutTitle.setStrokeWidth(1.5);

//		Text name = new Text("Mark Genesis C. Topinio"); name.setLayoutX(370); name.setLayoutY(300); name.setFill(Color.WHITE);
//		name.setFont(subFont); name.setStroke(Color.BLACK);

		Text subDetails = new Text("Name: Mark Genesis C. Topinio\nAge: 21\nResidence: Cabatuan, Isabela, Philippines\nEmail: mctopinio@up.edu.ph");
		subDetails.setLayoutX(30); subDetails.setLayoutY(100); subDetails.setFill(Color.WHITE);
		subDetails.setFont(subDetailsFont); subDetails.setFill(Color.LIGHTGREEN); subDetails.setStroke(Color.BLACK); subDetails.setStrokeWidth(0.3);

		Text aboutDev = new Text("Hello! I'm Mark Genesis C. Topinio.\nI'm a BS Computer Science student from "
				+ "the University of the Philippines Los Baños."
				+ "\nMy favorite hobbies are playing the piano, competitive online chess,"
				+ "\nand Genshin Impact. I also listen to a lot of Lo-Fi music and Alan Watts' philosophies.");
		aboutDev.setLayoutX(30); aboutDev.setLayoutY(210); aboutDev.setFill(Color.PALEGOLDENROD);aboutDev.setFont(descFont);

		Text referencesTitle = new Text("Mini-Project References:"); referencesTitle.setLayoutX(20); referencesTitle.setLayoutY(365);
		referencesTitle.setFont(headFont); referencesTitle.setStroke(Color.BLACK); referencesTitle.setFill(Color.AQUA); referencesTitle.setStrokeWidth(1.5);

		Text references1 = new Text("\n"
				+ "\tCMSC 22 - Base Code (Institute of Computer Science, CAS, UPLB)\n"
				+ "\tSuper Dead Gunner - Theme Idea, Art design, and other Game Assets:\n"
				+ "\t\t(Artist) Emcee Flesher \n\t\t(https://opengameart.org/users/emcee-flesher)\n"
				+ "\tGame Stage Background:\n"
				+ "\t\tShared by: charligodd\n"
				+ "\t\tLink: https://wall.alphacoders.com/big.php?i=885542\n"
				+ "\tInstructions Stage Background:\n"
				+ "\t\thttps://www.freepik.com/free-photos-vectors/galaxy\n"
				+ "\tGame Music: NES Shooter Music Pack\n"
				+ "\t\tAuthor: SketchyLogic\n"
				+ "\t\tLink: https://bit.ly/3q6aJLB\n");
		references1.setLayoutX(5); references1.setLayoutY(375); references1.setFill(Color.WHITE);references1.setFont(referenceFont);

		Text references2 = new Text("\n"
				+ "\tGame Over Music (Losing Theme):\n"
				+ "\t\tAuthor: KLY\n"
				+ "\t\tLink: https://opengameart.org/content/\n\t\tkl-peach-game-over-iii\n"
				+ "\tHow to play sounds with JavaFX:\n"
				+ "\t\tLink: https://stackoverflow.com/a\n\t\t/29529478/15416780\n"
				+ "\t\tAuthor: anaik\n"
				+ "\tHow to loop sound in JavaFX:\n"
				+ "\t\tLink: https://stackoverflow.com/a\n\t\t/23500512/15416780");
		references2.setLayoutX(440); references2.setLayoutY(375); references2.setFill(Color.WHITE);references2.setFont(referenceFont);

		descGroup.getChildren().addAll(aboutTitle,referencesTitle, aboutDev, references1, references2, subDetails);
		this.root.getChildren().add(descGroup);
		//End of Title and Descriptions texts

		//Images (e.g. User Profile Picture)
		ImageView profilePic = new ImageView(); profilePic.setLayoutX(510); profilePic.setLayoutY(10);
		profilePic.setImage(PROFILE_IMAGE);

		descGroup.getChildren().addAll(profilePic);

		this.stage.setTitle("About the Developer");
		this.stage.setScene(this.scene);

		this.stage.show();
	}



}