package application.controllers;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Optional;

import application.views.MainPageView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class MainPageController {
	
	private MainPageView m_mainPage;
	private Stage m_stage;
	
	// Constructor
	MainPageController(Stage stage){
		m_stage = stage;
		m_mainPage = new MainPageView();
	}
	
	// Method to initialize the UI
	public void initializeUI() {
		m_mainPage.displayMainPage(m_stage);
		setButtonActions();
	}
	
	// Method to set button actions
	private void setButtonActions() {
		m_mainPage.getKioskButton().setOnAction(e -> showAlertAndOpenKiosk());
		m_mainPage.getAdminButton().setOnAction(e -> showAlertAndOpenAdminLogin());
	}
	
	// Method to show an alert and open the kiosk window
	private void showAlertAndOpenKiosk() {
		showAlert(Alert.AlertType.CONFIRMATION, m_stage, "Welcome Page!", "Welcome to Marriott self-booking kiosk")
			.ifPresent(result -> {
				if (result == ButtonType.OK) {
					System.out.println("I am in kisok confiramtion)");
					openKioskWindow();
					m_stage.close();
					
				}
			});
	}


	// Method to show an alert and open the admin login window
	private void showAlertAndOpenAdminLogin() {
		showAlert(Alert.AlertType.CONFIRMATION, m_stage, "Welcome Page!", "Welcome to Marriott Admin Login")
			.ifPresent(result -> {
				if (result == ButtonType.OK) {
					m_stage.close();
					openAdminLogin();
				}
			});
	}
	
	// Method to show an alert
	private Optional<ButtonType> showAlert(Alert.AlertType alertType, javafx.stage.Window owner, String title, String message) {
		// Create and configure the alert
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);

		// Set the owner window for the alert
		alert.initOwner(owner);

		// Show the alert and wait for user response
		return alert.showAndWait();
	}
	
	// Method to open the admin login window
	private void openAdminLogin() {
		AdminLoginController alc = new AdminLoginController();
		alc.initializeUI();
	}
	
	// Method to open the room selection window
	private void openKioskWindow() {
		RoomSelectionKioskController avc;
		try {
			avc = new RoomSelectionKioskController(new Socket("localhost", 5000));
			avc.initializeUI();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
