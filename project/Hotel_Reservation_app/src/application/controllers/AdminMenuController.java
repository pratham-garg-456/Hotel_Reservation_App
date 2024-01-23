package application.controllers;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import application.views.AdminMenuView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AdminMenuController {
	
	private AdminMenuView m_view;
	private Stage m_stage; 
	
	public AdminMenuController() {		
		m_stage = new Stage();
	}
	
	 private void setUpActions() {		  
		m_view.getBookRoomButton().setOnAction(e->handleBookARoomBtn());	
		m_view.getCurrentBookingsButton().setOnAction(e->handleCurrentBookingBtn());
		m_view.getAvailableRoomsButton().setOnAction(e->handleAvailableRoomsBtn());
		m_view.getBillServiceButton().setOnAction(e->handleBillServiceBtn());
		m_view.getExitButton().setOnAction(e->handleExitBtn());
	}
	 
	private void handleBookARoomBtn() {
		 Socket clientSocket = null;
		try {
			clientSocket = new Socket("localhost", 5000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RoomSelectionKioskController avc = new RoomSelectionKioskController(clientSocket);
    	avc.initializeUI();
	}
	private void handleCurrentBookingBtn() {
		CurrentBookingController cbc = new CurrentBookingController();
    	cbc.initializeUI();
	}
	private void handleAvailableRoomsBtn() {
		AvailableRoomsController arc = new AvailableRoomsController();
    	arc.initializeUI();
	}
	private void handleBillServiceBtn() {
		BillController bc = new BillController();
    	bc.initializeUI();
	}
	private void handleExitBtn() {
		m_stage.close();
	}
	
	public void initializeUI(String userID) {
		m_stage.setTitle("Admin Menu");
		m_view = new AdminMenuView(userID);
		Scene scene = new Scene(m_view,500,500);
		m_stage.setResizable(false);
        m_stage.getIcons().add(new Image(getClass().getResourceAsStream("/application/resources/hotel.png")));
        m_stage.setScene(scene);
        setUpActions();
        m_stage.show();
	}
}
