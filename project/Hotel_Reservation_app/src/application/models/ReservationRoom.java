package application.models;

import javafx.beans.property.*;

public class ReservationRoom {
    private final IntegerProperty reservationRoomID;
    private final IntegerProperty reservationID;
    private final IntegerProperty roomID;

    public ReservationRoom(int reservationRoomID, int reservationID, int roomID) {
        this.reservationRoomID = new SimpleIntegerProperty(reservationRoomID);
        this.reservationID = new SimpleIntegerProperty(reservationID);
        this.roomID = new SimpleIntegerProperty(roomID);
    }

    // Getters
    public int getReservationRoomID() {
        return reservationRoomID.get();
    }

    public int getReservationID() {
        return reservationID.get();
    }

    public int getRoomID() {
        return roomID.get();
    }

    // IntegerProperty getters
    public IntegerProperty reservationRoomIDProperty() {
        return reservationRoomID;
    }

    public IntegerProperty reservationIDProperty() {
        return reservationID;
    }

    public IntegerProperty roomIDProperty() {
        return roomID;
    }
}
