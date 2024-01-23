package application.controllers;

import application.models.BookingInfo;
import application.views.CurrentBookingView;
import database.Database;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CurrentBookingController {
	private CurrentBookingView m_view;
	private Stage m_stage;	
	private Database m_database;
	private ObservableList<BookingInfo> bookings;
	
	public CurrentBookingController() {
		m_database = new Database();
        m_stage = new Stage();
        m_view = new CurrentBookingView();
	}


	private void setUpActions() {
		
		bookings = m_database.loadBookingData();
		// Set the data in the TableView
		m_view.getTableView().setItems(bookings);
		// Update the label with the number of current bookings
		m_view.getBookingCountLabel().setText("Number of Current Bookings: " + bookings.size());
		
		m_view.getFilterButton().setOnAction(event -> filterBookings());
		m_view.getClearFilterButton().setOnAction(e -> {
            m_view.getCheckInDatePicker().setValue(null);
            m_view.getCheckOutDatePicker().setValue(null);
            filterBookings();
        });

	}
	
	private void filterBookings() {
		bookings = m_database.getBookings(m_view.getCheckInDatePicker().getValue(),m_view.getCheckOutDatePicker().getValue());
		// Set the data in the TableView
		m_view.getTableView().setItems(bookings);
		// Update the label with the number of current bookings
		m_view.getBookingCountLabel().setText("Number of Current Bookings: " + bookings.size());
		
	}

	public void initializeUI() {
		
		// Set up the scene and stage
		m_stage.setTitle("Current Bookings");
	    Scene scene = new Scene(m_view, 900, 700);
	    m_stage.setResizable(false);
	    m_stage.getIcons().add(new Image(getClass().getResourceAsStream("/application/resources/hotel.png")));
	    m_stage.setScene(scene);
	    setUpActions();
	    m_stage.show();
	
	}
}
