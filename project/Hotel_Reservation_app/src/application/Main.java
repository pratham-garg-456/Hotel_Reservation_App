// Project - 1 (Hotel Reservation App)
// Student Name: Pratham Garg
// Student Number : 117900217

package application;
import application.controllers.WelcomePageController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	private WelcomePageController welcomePageController;
    @Override
    public void start(Stage primaryStage) {
    	// Initialize and display the RoomSelectionKiosk view
		welcomePageController = new WelcomePageController(primaryStage);
		welcomePageController.initializeUI();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
