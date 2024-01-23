package application.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WelcomePageView {
	
	private BorderPane borderPane;
	private Label welcomeLabel;
	private Button continueButton;

    // Method to display the welcome page
    public void displayWelcomePage(Stage primaryStage) {
    	
        // Create the layout
        BorderPane layout = createLayout();
        Scene scene = new Scene(layout, 800, 600);

        // Set up the stage
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        // Set the application icon
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/application/resources/hotel.png")));
        primaryStage.setTitle("Hotel Reservation System");
        primaryStage.show();
    }

    // Method to create the layout of the welcome page
    public BorderPane createLayout() {
        borderPane = new BorderPane();

        // Set background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/application/resources/background2.jpg"));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.fitWidthProperty().bind(borderPane.widthProperty());
        backgroundImageView.fitHeightProperty().bind(borderPane.heightProperty());
        borderPane.getChildren().add(backgroundImageView);

        // Create a vertical box for center alignment
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);

        // Welcome label
        welcomeLabel = new Label("Welcome to Marriott Hotels");
        welcomeLabel.setStyle("-fx-font-size: 24pt; -fx-text-fill: white;");

        // Continue button
        continueButton = new Button("Continue");
        continueButton.setStyle("-fx-font-size: 18pt; -fx-background-color: #4CAF50; -fx-text-fill: white;");

        centerBox.getChildren().addAll(welcomeLabel, continueButton);
        borderPane.setCenter(centerBox);

        return borderPane;
    }

    // Getter for the continue button
	public Button getContinueButton() {
		return continueButton;
	}
    
}
