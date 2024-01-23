package application.controllers;

import java.time.LocalDate;
import java.util.Optional;

import application.models.Guest;
import application.models.Room;
import application.views.GuestInformationView;
import database.Database;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GuestController {
    
    private Database m_database;
    private Stage m_stage;
    private GuestInformationView m_view;
    private Scene m_scene;

    public GuestController() {
        m_database = new Database();
        m_stage = new Stage();
        m_view = new GuestInformationView();
    }

    public void initializeUI(ObservableList<Room> SelectedRoom, LocalDate checkInDate, LocalDate checkOutDate) {
        m_stage.setTitle("Hotel Reservation System");
        m_scene = new Scene(m_view, 400, 400);
        m_stage.initModality(Modality.APPLICATION_MODAL);
        m_stage.setResizable(false);
        m_stage.getIcons().add(new Image(getClass().getResourceAsStream("/application/resources/hotel.png")));
        m_stage.setScene(m_scene);
        setUpActions(SelectedRoom, checkInDate, checkOutDate);
        m_stage.show();
    }

    private void setUpActions(ObservableList<Room> SelectedRoom, LocalDate checkInDate, LocalDate checkOutDate) {
        m_view.getSubmitButton().setOnAction(e -> handleSubmit(SelectedRoom, checkInDate, checkOutDate));
        // Add validators
        m_view.getEmailField().textProperty().addListener((observable, oldValue, newValue) -> validateEmail(newValue));
    }

    private void handleSubmit(ObservableList<Room> SelectedRoom, LocalDate checkInDate, LocalDate checkOutDate) {
        javafx.stage.Window owner = m_scene.getWindow();

        if (isValid()) {
        	 // After successful form submission, show the confirmation alert
            if (showConfirmationAlert(owner, "Submission Confirmation", "Guest information submitted successfully. Do you want to proceed?")) {
                // User clicked "OK" in the confirmation alert
                Guest guest = new Guest( m_view.getFirstName(), m_view.getLastName(), m_view.getAddress(), m_view.getPhone(), m_view.getEmail());
                m_database.makeReservation(guest, SelectedRoom, checkInDate, checkOutDate);
                SelectedRoom.clear();
                m_stage.close();
            } else {
                
            }

            return;
        }
       
    }

    private boolean isValid() {
       

        if (!isValidName(m_view.getFirstName())) {
            showAlert(Alert.AlertType.ERROR, m_scene.getWindow(), "Form Error!", "Please enter a valid first name.");
            return false;
        }
        if (m_view.getFirstName().isEmpty()) {
            showAlert(Alert.AlertType.ERROR,  m_scene.getWindow(), "Form Error!", "Please enter your first name.");
            return false;
        }

        if (!isValidName(m_view.getLastName())) {
            showAlert(Alert.AlertType.ERROR, m_scene.getWindow(), "Form Error!", "Please enter a valid last name.");
            return false;
        }

        if (m_view.getLastName().isEmpty()) {
            showAlert(Alert.AlertType.ERROR,  m_scene.getWindow(), "Form Error!", "Please enter your last name.");
            return false;
        }
        
        if (m_view.getAddress().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, m_scene.getWindow(), "Form Error!", "Please enter your address.");
            return false;
        }

        if (!isValidPhoneNumber(m_view.getPhone())) {
            showAlert(Alert.AlertType.ERROR, m_scene.getWindow(), "Form Error!", "Please enter a valid 10-digit phone number.");
            return false;
        }
        if (m_view.getPhone().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, m_scene.getWindow(), "Form Error!", "Please enter your phone number.");
            return false;
        }

        if (!validateEmail(m_view.getEmail())) {
            showAlert(Alert.AlertType.ERROR, m_scene.getWindow(), "Form Error!", "Please enter a valid email address.");
            return false;
        }
        if (m_view.getEmail().isEmpty()) {
            showAlert(Alert.AlertType.ERROR,  m_scene.getWindow(), "Form Error!", "Please enter your email address.");
            return false;
        }
        

        return true;
    }
    
    private boolean validateEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }


    private boolean isValidName(String name) {
        // Assuming names should contain only alphabetic characters
        return name.matches("[a-zA-Z]+");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Assuming phone number should be a 10-digit number
        return phoneNumber.matches("\\d{10}");
    }

    private void showAlert(Alert.AlertType alertType, javafx.stage.Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.initOwner(owner);

        alert.showAndWait();
    }

    private boolean showConfirmationAlert(javafx.stage.Window owner, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.initOwner(owner);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
