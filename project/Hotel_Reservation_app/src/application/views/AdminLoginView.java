package application.views;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AdminLoginView extends BorderPane {
    private Label headingLabel;
    private Label emailLabel;

    private PasswordField passwordField;
    private TextField emailTextField;

    private Button submitButton;

    public AdminLoginView() {
        setupUI();
    }

    private void setupUI() {
        setPadding(new Insets(20));
        setCenter(createLoginContent());
    }

    private GridPane createLoginContent() {
        GridPane loginContent = new GridPane();
        loginContent.setAlignment(Pos.CENTER);
        loginContent.setHgap(10);
        loginContent.setVgap(10);
        loginContent.setPadding(new Insets(20));

        // Big heading on top
        headingLabel = new Label("Admin Login");
        headingLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        GridPane.setHalignment(headingLabel, HPos.CENTER);
        loginContent.add(headingLabel, 0, 0, 2, 1); // span 2 columns

        // Labels and TextFields in a GridPane
        emailLabel = new Label("Email Id:");
        emailTextField = new TextField();

        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();

        // Button in an HBox
        submitButton = new Button("Submit");

        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(submitButton);

        // Adding components to the GridPane with padding
        loginContent.add(emailLabel, 0, 1);
        loginContent.add(emailTextField, 1, 1);

        loginContent.add(passwordLabel, 0, 2);
        loginContent.add(passwordField, 1, 2);

        loginContent.add(buttonsBox, 0, 3, 2, 1); // span 2 columns for the button

        // Apply some styling
        loginContent.setStyle("-fx-background-color: #f4f4f4;");

        // Apply styling to the button
        submitButton.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white;");

        return loginContent;
    }

    public Label getHeadingLabel() {
        return headingLabel;
    }

    public Label getEmailLabel() {
        return emailLabel;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public Button getSubmitButton() {
        return submitButton;
    }
}
