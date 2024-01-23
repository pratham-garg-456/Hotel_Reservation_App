package application.controllers;

import java.util.Optional;

import application.views.AdminLoginView;
import database.Database;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AdminLoginController {

    private AdminLoginView m_view;
    private Stage m_stage; // Add a reference to the main stage
    private  Database jdbcDao;

    public AdminLoginController() {
        m_view = new AdminLoginView();
        m_stage = new Stage();
        jdbcDao = new Database();
    }

    public void initializeUI() {
    	
    	m_stage.setTitle("Hotel Reservation System");
        Scene scene = new Scene(m_view, 300, 300);
        m_stage.setResizable(false);
        m_stage.getIcons().add(new Image(getClass().getResourceAsStream("/application/resources/hotel.png")));
        m_stage.setScene(scene);
        setUpActions();
        m_stage.show();
       
    }

    private void setUpActions(){
    	 // Set up event handling for the submit button
        m_view.getSubmitButton().setOnAction(e -> { 
        	login();           
        });
    }
    private void login(){
    	
        String emailId = m_view.getEmailTextField().getText();
        String password = m_view.getPasswordField().getText();

        // Validate form inputs
        if (emailId.isBlank()) {
            showAlert( "Form Error!", "Please enter your email id");
            return;
        }

        if (password.isBlank()) {
            showAlert("Form Error!", "Please enter a password");
            return;
        }

        // Validate user credentials with the database
        
        String adminName = jdbcDao.validateAdmin(emailId, password);

        // Show appropriate message based on validation result
        if (adminName==null) {
        	showAlert("Validation Error", "Please enter correct Email and Password");
        } else {        	
            // Open the Cart view when login is successful
        	showConfirmation( "Login Successful!", "Welcome "+adminName, adminName);
        }
    }

    private static void openAdminMenu(String userId) {
        // Initialize and display the Cart view
        AdminMenuController cartController = new AdminMenuController();
        cartController.initializeUI(userId);
    }

    private void showAlert(String title,String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmation(String title, String message, String userId) {
        // Display a confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        // Show the alert and wait for user response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User clicked OK and the alert type is CONFIRMATION, open the Cart view
            m_stage.close();
            openAdminMenu(userId);
        }
    }
}
