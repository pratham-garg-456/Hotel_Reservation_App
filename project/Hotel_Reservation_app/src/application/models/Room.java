package application.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Room {
    private final IntegerProperty roomID;
    private final StringProperty roomNumber;
    private final StringProperty roomType;
    private final IntegerProperty capacity;
    private final DoubleProperty pricePerNight;

    public Room(int roomID, String roomNumber, String roomType, int capacity, double pricePerNight) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomNumber = new SimpleStringProperty(roomNumber);
        this.roomType = new SimpleStringProperty(roomType);
        this.capacity = new SimpleIntegerProperty(capacity);
        this.pricePerNight = new SimpleDoubleProperty(pricePerNight);
    }
    public Room(int roomID, String roomNumber, String roomType) {
       this(roomID,roomNumber,roomType,0,0.0);
    }
    // Getters
    public int getRoomID() {
        return roomID.get();
    }

    public String getRoomNumber() {
        return roomNumber.get();
    }

    public String getRoomType() {
        return roomType.get();
    }

    public int getCapacity() {
        return capacity.get();
    }

    public double getPricePerNight() {
        return pricePerNight.get();
    }

    // IntegerProperty, StringProperty, DoubleProperty getters
    public IntegerProperty roomIDProperty() {
        return roomID;
    }

    public StringProperty roomNumberProperty() {
        return roomNumber;
    }

    public StringProperty roomTypeProperty() {
        return roomType;
    }

    public IntegerProperty capacityProperty() {
        return capacity;
    }

    public DoubleProperty pricePerNightProperty() {
        return pricePerNight;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Room otherRoom = (Room) obj;
        return Objects.equals(this.getRoomNumber(), otherRoom.getRoomNumber());
    }
}
