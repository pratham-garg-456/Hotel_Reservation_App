package application.controllers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import application.models.Room;
import application.views.RoomSelectionKioskView;
import database.Database;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class RoomSelectionKioskController {

    private RoomSelectionKioskView m_view;
    private Database m_database;
    private Stage m_stage;
    private ObservableList<Room> availableRoomsInDB;
    private ObservableList<Room> selectedRooms;
   
	private Socket m_clientSocket;
    private DataOutputStream dout;
    @SuppressWarnings("unused")
	private DataInputStream din;

    public RoomSelectionKioskController(Socket clientSocket) {
        m_view = new RoomSelectionKioskView();
        m_database = new Database();
        m_clientSocket = clientSocket;

        try {
            dout = new DataOutputStream(m_clientSocket.getOutputStream());
            din = new DataInputStream(m_clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        m_stage = new Stage();
        selectedRooms = FXCollections.observableArrayList();
        availableRoomsInDB = FXCollections.observableArrayList();
    }

    public void initializeUI() {
        m_stage.setTitle("Hotel Reservation System");
        Scene scene = new Scene(m_view, 800, 600);
        m_stage.setResizable(false);
        m_stage.getIcons().add(new Image(getClass().getResourceAsStream("/application/resources/hotel.png")));
        m_stage.setScene(scene);
        setUpActions();
        m_stage.show();
    }

    private void setUpActions() {
        m_view.getTotalPriceLabel().textProperty().bind(Bindings.createStringBinding(() -> {
            double totalPrice = calculateTotalPrice();
            return String.format("Total Price: $%.2f", totalPrice);
        }, m_view.getRoomTableView2().getItems()));

        addFieldChangeListeners();

        m_view.getSendRB().setOnAction(e -> {
            Room roomSelected = m_view.getRoomTableView().getSelectionModel().getSelectedItem();
            if (roomSelected != null) {
                selectedRooms.add(roomSelected);
                m_view.setRoomTableView2(selectedRooms);
                updateAvailableRooms();
            }
        });

        m_view.getSendLB().setOnAction(e -> {
            Room roomSelected = m_view.getRoomTableView2().getSelectionModel().getSelectedItem();
            if (roomSelected != null) {
                selectedRooms.remove(roomSelected);
                updateAvailableRooms();
            }
        });
    }

    private void addFieldChangeListeners() {
        m_view.getRoomTableView2().itemsProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateTotalPriceLabel();
            }
        });

        m_view.getNumberOfPersonsSpinner().valueProperty().addListener(c -> {
            updateAvailableRooms();
        });

        m_view.getCheckInDatePicker().valueProperty().addListener(c -> {
            updateAvailableRooms();
        });

        m_view.getCheckOutDatePicker().valueProperty().addListener(c -> {
        	
            updateAvailableRooms();
            
        });

        m_view.getDeluxeRoomCheckBox().selectedProperty().addListener(c -> {
            updateAvailableRooms();
        });

        m_view.getDoubleRoomCheckBox().selectedProperty().addListener(c -> {
            updateAvailableRooms();
        });

        m_view.getSingleRoomCheckBox().selectedProperty().addListener(c -> {
            updateAvailableRooms();
        });

        m_view.getPenthouseRoomCheckBox().selectedProperty().addListener(c -> {
            updateAvailableRooms();
        });

        m_view.getContinueButton().setOnAction(event -> {
            if (validateFields() && validateTotalCapacity()) {
                try {
                    sendReservationDetails();
                    GuestController guestController = new GuestController();
                    LocalDate checkInDate = m_view.getCheckInDatePicker().getValue();
                    LocalDate checkOutDate = m_view.getCheckOutDatePicker().getValue();
                    guestController.initializeUI(selectedRooms, checkInDate, checkOutDate);
                    updateAvailableRooms();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    private void sendReservationDetails() throws IOException {
        String reservationDetails = buildReservationDetails();
        dout.writeUTF(reservationDetails);
    }
    
    private String buildReservationDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Reservation Details:\n");

        // Add selected rooms
        for (Room room : selectedRooms) {
            details.append("Room Number: ").append(room.getRoomNumber()).append(", ");
            details.append("Room Type: ").append(room.getRoomType()).append("\n");
        }

        // Add check-in and check-out dates
        details.append("Check-In Date: ").append(m_view.getCheckInDatePicker().getValue()).append("\n");
        details.append("Check-Out Date: ").append(m_view.getCheckOutDatePicker().getValue()).append("\n");

        // Add more details as needed

        return details.toString();
    }
    
    private boolean validateFields() {
        LocalDate checkInDate = m_view.getCheckInDatePicker().getValue();
        LocalDate checkOutDate = m_view.getCheckOutDatePicker().getValue();
        int numberOfPersons = m_view.getNumberOfPersonsSpinner().getValue();

        if (checkInDate == null || checkOutDate == null || numberOfPersons <= 0 || selectedRooms.isEmpty()) {
            showAlert("Please fill in all the required fields.");
            return false;
        }

        if (checkInDate.isAfter(checkOutDate)) {
            showAlert("Check-in date cannot be after the check-out date.");
            return false;
        }

        return true;
    }

    private boolean validateTotalCapacity() {
        int totalCapacity = calculateTotalCapacity();
        int numberOfPersons = m_view.getNumberOfPersonsSpinner().getValue();

        if (totalCapacity < numberOfPersons) {
            showAlert("Total capacity of selected rooms is less than the entered number of persons. Please select more rooms.");
            return false;
        }

        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void updateAvailableRooms() {
        try {
            int numberOfPersons = m_view.getNumberOfPersonsSpinner().getValue();
            LocalDate checkInDate = m_view.getCheckInDatePicker().getValue();
            LocalDate checkOutDate = m_view.getCheckOutDatePicker().getValue();

            boolean includeSingle = m_view.getSingleRoomCheckBox().isSelected();
            boolean includeDouble = m_view.getDoubleRoomCheckBox().isSelected();
            boolean includeDeluxe = m_view.getDeluxeRoomCheckBox().isSelected();
            boolean includePenthouse = m_view.getPenthouseRoomCheckBox().isSelected();

            availableRoomsInDB = getAvailableRooms(numberOfPersons, checkInDate, checkOutDate,
                    includeSingle, includeDouble, includeDeluxe, includePenthouse);

            if (!selectedRooms.isEmpty()) {
                availableRoomsInDB.removeAll(selectedRooms);
            }
            m_view.setRoomTableView(availableRoomsInDB);

            Room selectedRoom = m_view.getRoomTableView().getSelectionModel().getSelectedItem();
            int totalCapacity = calculateTotalCapacity();
            if (selectedRoom != null) {
                m_view.getSendRB().setDisable((totalCapacity >= numberOfPersons) || (numberOfPersons - totalCapacity < selectedRoom.getCapacity()));
            } else {
                m_view.getSendRB().setDisable(totalCapacity >= numberOfPersons);
            }

            m_view.getRoomTableView().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    updateSendButtonState(newSelection, m_view.getNumberOfPersonsSpinner().getValue());
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Room> getAvailableRooms(int numberOfPersons, LocalDate checkInDate, LocalDate checkOutDate,
            boolean includeSingle, boolean includeDouble, boolean includeDeluxe,
            boolean includePenthouse) throws SQLException {
        List<Room> availableRoomsList = m_database.getAvailableRooms(numberOfPersons, checkInDate, checkOutDate,
                includeSingle, includeDouble, includeDeluxe, includePenthouse);
        return FXCollections.observableArrayList(availableRoomsList);
    }

    private int calculateTotalCapacity() {
        int totalCapacity = 0;
        for (Room room : selectedRooms) {
            totalCapacity += room.getCapacity();
        }
        return totalCapacity;
    }

    private double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Room room : selectedRooms) {
            totalPrice += room.getPricePerNight();
        }
        return totalPrice;
    }

    private void updateSendButtonState(Room selectedRoom, int numberEntered) {
        boolean exceedTheCapacity = selectedRoom.getCapacity() > (numberEntered - calculateTotalCapacity());
        int totalCapacity = calculateTotalCapacity();
        m_view.getSendRB().setDisable(exceedTheCapacity || totalCapacity >= m_view.getNumberOfPersonsSpinner().getValue());
    }

    private void updateTotalPriceLabel() {
        DoubleBinding totalPriceBinding = Bindings.createDoubleBinding(() -> {
            return calculateTotalPrice();
        }, selectedRooms);

        m_view.getTotalPriceLabel().textProperty().bind(Bindings.format("Total Price: $%.2f", totalPriceBinding));
    }
}
