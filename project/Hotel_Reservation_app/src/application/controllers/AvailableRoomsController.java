

package application.controllers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import application.models.Room;
import application.views.AvailableRoomsView;
import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AvailableRoomsController {
    private AvailableRoomsView m_view;
    private Database m_database;
    private Stage m_stage;
    private ObservableList<Room> availableRoomsInDB ;

    public AvailableRoomsController() {
        this.m_view = new AvailableRoomsView();
        this.m_database = new Database();
        m_stage = new Stage();
        availableRoomsInDB = FXCollections.observableArrayList();        
    }
    
    private void setUpActions() {
    	addFieldChangeListeners();
    }

	private void addFieldChangeListeners() {

		m_view.getCheckInDatePicker().valueProperty().addListener(c->{
    		updateAvailableRooms();
    	});
    	
    	m_view.getCheckOutDatePicker().valueProperty().addListener(c->{
    		updateAvailableRooms();
    	});
    	
    	m_view.getDeluxeRoomCheckBox().selectedProperty().addListener(c->{
    		updateAvailableRooms();
    	});
    	m_view.getDoubleRoomCheckBox().selectedProperty().addListener(c->{
    		updateAvailableRooms();
    	});
    	m_view.getSingleRoomCheckBox().selectedProperty().addListener(c->{
    		updateAvailableRooms();
    	});
    	m_view.getPenthouseRoomCheckBox().selectedProperty().addListener(c->{
    		updateAvailableRooms();
    	});
    	
    }
    
    private void updateAvailableRooms() {
        try {
            // Get user input
            //int numberOfPersons = m_view.getNumberOfPersonsSpinner().getValue();
            LocalDate checkInDate = m_view.getCheckInDatePicker().getValue();
            LocalDate checkOutDate = m_view.getCheckOutDatePicker().getValue();

            // Determine selected room types
            boolean includeSingle = m_view.getSingleRoomCheckBox().isSelected();
            boolean includeDouble = m_view.getDoubleRoomCheckBox().isSelected();
            boolean includeDeluxe = m_view.getDeluxeRoomCheckBox().isSelected();
            boolean includePenthouse = m_view.getPenthouseRoomCheckBox().isSelected();


            // Query the database for available rooms
            availableRoomsInDB = getAvailableRooms(5, checkInDate, checkOutDate,
                    includeSingle, includeDouble, includeDeluxe, includePenthouse);
            m_view.setRoomTableView(availableRoomsInDB);
  	
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database error
        }
    }

    
    private ObservableList<Room> getAvailableRooms(int numberOfPersons, LocalDate checkInDate, LocalDate checkOutDate,
            boolean includeSingle, boolean includeDouble, boolean includeDeluxe,
            boolean includePenthouse) throws SQLException 
    {
		// Perform the database query to get available rooms based on the entered criteria
    	 List<Room> availableRoomsList = m_database.getAvailableRooms(numberOfPersons, checkInDate, checkOutDate,
		includeSingle, includeDouble, includeDeluxe, includePenthouse);
    	// Convert the List to an ObservableList
    	
		return FXCollections.observableArrayList(availableRoomsList);
    }
    


    
    public void initializeUI() {
    	
    	// Set up the scene and stage
    	m_stage.setTitle("Available Rooms");
        Scene scene = new Scene(m_view, 400, 700);
        m_stage.setResizable(false);
        m_stage.getIcons().add(new Image(getClass().getResourceAsStream("/application/resources/hotel.png")));
        m_stage.setScene(scene);
        setUpActions();
        m_stage.show();

    }

}

