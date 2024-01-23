package application.views;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class GuestInformationView extends GridPane {

    private TextField firstNameField;
    private TextField lastNameField;
    private TextField addressField;
    private TextField phoneField;
    private TextField emailField;

    private Label titleLabel;
    private Label firstNameLabel;
    private Label lastNameLabel;
    private Label addressLabel;
    private Label phoneLabel;
    private Label emailLabel;

    private Button submitButton;

    public GuestInformationView() {
        initializeView();
    }

    private void initializeView() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        // Set up column constraints for centering
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setMaxWidth(150); // Set a maximum width for col2

        this.getColumnConstraints().addAll(col1, col2);

        // Set up title
        titleLabel = new Label("User/Guest Registration");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        GridPane.setHalignment(titleLabel, HPos.CENTER);
        this.add(titleLabel, 0, 0, 2, 1);

        // Set up labels and form fields
        firstNameLabel = createLabel("First Name:",true);
        firstNameField = createTextField();
        lastNameLabel = createLabel("Last Name:",true);
        lastNameField = createTextField();
        addressLabel = createLabel("Address:",true);
        addressField = createTextField();
        phoneLabel = createLabel("Phone:",true);
        phoneField = createTextField();
        emailLabel = createLabel("Email:",true);
        emailField = createTextField();

        

        // Set up submit button
        submitButton = new Button("Checkout");
        //submitButton.setOnAction(e -> handleSubmit());
        GridPane.setHalignment(submitButton, HPos.CENTER);

        // Add components to the grid
        this.add(firstNameLabel, 0, 3);
        this.add(firstNameField, 1, 3);
        this.add(lastNameLabel, 0, 4);
        this.add(lastNameField, 1, 4);
        this.add(addressLabel, 0, 5);
        this.add(addressField, 1, 5);
        this.add(phoneLabel, 0, 6);
        this.add(phoneField, 1, 6);
        this.add(emailLabel, 0, 7);
        this.add(emailField, 1, 7);
        this.add(submitButton, 0, 8, 2, 1);

        // Center the form in the grid
        this.setAlignment(Pos.CENTER);
    }

    private Label createLabel(String text,boolean required) {
        Label label = new Label(text+ (required ? " *" : ""));
        label.setStyle("-fx-font-weight: bold;");
        return label;
    }

    private TextField createTextField() {
        TextField textField = new TextField();
        return textField;
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getLastName() {
        return lastNameField.getText();
    }

    public String getAddress() {
        return addressField.getText();
    }

    public String getPhone() {
        return phoneField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

	public Button getSubmitButton() {
		return submitButton;
	}

	public TextField getEmailField() {
		return emailField;
	}
    
    
}
