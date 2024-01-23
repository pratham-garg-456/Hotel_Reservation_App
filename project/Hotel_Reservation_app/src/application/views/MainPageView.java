package application.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPageView {

    private BorderPane borderPane;
    private Label welcomeLabel;
    private Button kioskButton;
    private Button adminButton;

    // Method to display the main page
    public void displayMainPage(Stage primaryStage) {

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

    // Method to create the layout of the main page
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
        welcomeLabel = new Label("Continue as..");
        welcomeLabel.setStyle("-fx-font-size: 24pt; -fx-text-fill: white;");

        // Create a horizontal box for buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        // Kiosk button
        kioskButton = new Button("Kiosk");
        kioskButton.setStyle("-fx-font-size: 18pt; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        // Add event handler for kiosk button click if needed

        // Admin button
        adminButton = new Button("Admin");
        adminButton.setStyle("-fx-font-size: 18pt; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        // Add event handler for admin button click if needed

        // Add buttons to the button box
        buttonBox.getChildren().addAll(kioskButton, adminButton);
        centerBox.getChildren().addAll(welcomeLabel, buttonBox);

        // Add center content to BorderPane
        borderPane.setCenter(centerBox);

        return borderPane;
    }

    // Getter for the kiosk button
    public Button getKioskButton() {
        return kioskButton;
    }

    // Getter for the admin button
    public Button getAdminButton() {
        return adminButton;
    }
}
