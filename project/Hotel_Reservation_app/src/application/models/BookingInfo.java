package application.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BookingInfo {

    private SimpleIntegerProperty reservationID;
    private SimpleStringProperty guestName;
    private SimpleStringProperty address;
    private SimpleStringProperty phone;
    private SimpleStringProperty email;
    private SimpleStringProperty checkInDate;
    private SimpleStringProperty checkOutDate;
    private SimpleStringProperty bookingDate;
    private SimpleStringProperty roomType;
    private SimpleStringProperty roomNumber;

    public BookingInfo(int reservationID, String guestName, String address, String phone, String email,
                       String checkInDate, String checkOutDate, String bookingDate, String roomType, String roomNumber) {
        this.reservationID = new SimpleIntegerProperty(reservationID);
        this.guestName = new SimpleStringProperty(guestName);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.checkInDate = new SimpleStringProperty(checkInDate);
        this.checkOutDate = new SimpleStringProperty(checkOutDate);
        this.bookingDate = new SimpleStringProperty(bookingDate);
        this.roomType = new SimpleStringProperty(roomType);
        this.roomNumber = new SimpleStringProperty(roomNumber);
    }

    // Getter methods for properties
    public SimpleIntegerProperty reservationIDProperty() {
        return reservationID;
    }

    public SimpleStringProperty guestNameProperty() {
        return guestName;
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public SimpleStringProperty checkInDateProperty() {
        return checkInDate;
    }

    public SimpleStringProperty checkOutDateProperty() {
        return checkOutDate;
    }

    public SimpleStringProperty bookingDateProperty() {
        return bookingDate;
    }

    public SimpleStringProperty roomTypeProperty() {
        return roomType;
    }

    public SimpleStringProperty roomNumberProperty() {
        return roomNumber;
    }
}
