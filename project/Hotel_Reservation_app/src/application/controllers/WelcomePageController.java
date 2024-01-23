package application.controllers;

import application.views.WelcomePageView;
import javafx.stage.Stage;

public class WelcomePageController {
	
	private WelcomePageView m_welcomePage;
	private Stage m_stage;
	
	public WelcomePageController(Stage stage) {
		m_welcomePage = new WelcomePageView();
		m_stage = stage;
	}
	
	public void initializeUI() {		
		m_welcomePage.displayWelcomePage(m_stage);
		setButtonActions();
	}
	
	private void setButtonActions() {
		// Define action for the continue button
		m_welcomePage.getContinueButton().setOnAction(e -> showMainPageView(m_stage));
	}
	
	private void showMainPageView(Stage primaryStage) {
		// Create a controller for the main page
		MainPageController mainPageController = new MainPageController(primaryStage);
		// Initialize the UI for the main page
		mainPageController.initializeUI();
	}
}
