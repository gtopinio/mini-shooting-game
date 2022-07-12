/***********************************************************
* This is a Java program that simulates a Mini-Shooting game.
* Mini-Project CMSC 22
*
* @author Mark Genesis C. Topinio
* @created_date 2021-11-22 13:45
*
***********************************************************/
package user;

import javafx.application.Application;
import javafx.stage.Stage;
import miniprojtemplate.GameStartStage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage){
		GameStartStage theGameStartStage = new GameStartStage();
		theGameStartStage.setStage(stage, 0);
	}

}

/***********************************************************
* Mini-Project References:
*
* CMSC 22 - Base Code (Institute of Computer Science, CAS, UPLB)
*
* Super Dead Gunner - Theme Idea, Art design, and other Game Assets:
* 	Artist: Emcee Flesher (https://opengameart.org/users/emcee-flesher)
*
* Game Stage Background:
*	Shared by: charligodd
*	Link: https://wall.alphacoders.com/big.php?i=885542
*
* Instructions Stage Background:
*	https://www.freepik.com/free-photos-vectors/galaxy
*
* Game Implementations:
* 	Positioning ImageView elements:
*	https://stackoverflow.com/questions/42939530/setx-and-sety-not-working-when-trying-to-position-images
*
* Game Music: NES Shooter Music Pack
*	Author: SketchyLogic
*	Link: https://opengameart.org/content/nes-shooter-music-5-tracks-3-jingles
*
* Game Over Music (Losing Theme):
*	Author: KLY
*	Link: https://opengameart.org/content/kl-peach-game-over-iii
*
* Generating random integers within a specific range:
*	https://stackoverflow.com/a/363732/15416780
*	Author: TJ_Fischer
*
* How to play sounds with JavaFX:
*	https://stackoverflow.com/a/29529478/15416780
*	Author: anaik
*
* How to loop sound in JavaFX:
*	https://stackoverflow.com/a/23500512/15416780
*
* Other Game Asset sources:
*	Free Icons Library
*	PngFind
*	jtgp-arts (Gameover GIF)
***********************************************************/


