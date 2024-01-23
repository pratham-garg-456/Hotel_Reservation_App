package application.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdminMenuView extends BorderPane {
	
	private Button bookRoomButton;
    private Button billServiceButton;
    private Button currentBookingsButton;
    private Button availableRoomsButton;
    private Button exitButton;

    public AdminMenuView(String userID) {
        setupUI(userID);
    }

    private void setupUI(String userID) {
        Label headingLabel = new Label("Admin Menu");
        headingLabel.setStyle(
                "-fx-font-size: 24px; " +
                        "-fx-font-weight: bold; "
        );
        headingLabel.setPadding(new Insets(0, 10, 10, 10));

        // Create person icon and label with padding
        ImageView personIcon = new ImageView(new Image((getClass().getResourceAsStream("/application/resources/person.png"))));
        personIcon.setFitWidth(30); // Adjust the size as needed
        personIcon.setFitHeight(30); // Adjust the size as needed

        Label userIDLabel = new Label(userID);

        HBox headerBox = new HBox(10); // Set spacing between headingLabel and personBox
        
        headerBox.setAlignment(Pos.CENTER_RIGHT); // Align to the right
        headerBox.getChildren().addAll(personIcon, userIDLabel);
        headerBox.setPadding(new Insets(10, 10, 0, 10)); // Adjust padding as needed

        HBox headerHBox = new HBox(10);
        headerHBox.getChildren().addAll(headerBox);
        
        setTop(headerHBox);

        // Create buttons
        bookRoomButton = createStyledButton("Book a room");
        billServiceButton = createStyledButton("Bill service");
        currentBookingsButton = createStyledButton("Current bookings");
        availableRoomsButton = createStyledButton("Available rooms");
        exitButton = createStyledButton("Exit");

        // Create main menu layout with padding
        VBox mainMenuLayout = new VBox(20);
        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenuLayout.getChildren().addAll(
        		headingLabel,
                bookRoomButton,
                billServiceButton,
                currentBookingsButton,
                availableRoomsButton,
                exitButton
        );

        // Set the main menu to the center of the BorderPane
        setCenter(mainMenuLayout);
    }



    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-font-size: 16px; " +
                        "-fx-padding: 10px; " +
                        "-fx-min-width: 200px;"
        );
        return button;
    }

	public Button getBookRoomButton() {
		return bookRoomButton;
	}

	public Button getBillServiceButton() {
		return billServiceButton;
	}

	public Button getCurrentBookingsButton() {
		return currentBookingsButton;
	}

	public Button getAvailableRoomsButton() {
		return availableRoomsButton;
	}

	public Button getExitButton() {
		return exitButton;
	}
    
    
}

