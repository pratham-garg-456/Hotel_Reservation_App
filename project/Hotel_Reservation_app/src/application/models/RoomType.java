package application.models;

import javafx.beans.property.*;

public class RoomType {
    private final IntegerProperty roomTypeID;
    private final StringProperty roomType;
    private final IntegerProperty capacity;
    private final DoubleProperty pricePerNight;

    public RoomType(int roomTypeID, String roomType, int capacity, double pricePerNight) {
        this.roomTypeID = new SimpleIntegerProperty(roomTypeID);
        this.roomType = new SimpleStringProperty(roomType);
        this.capacity = new SimpleIntegerProperty(capacity);
        this.pricePerNight = new SimpleDoubleProperty(pricePerNight);
    }

    // Getters
    public int getRoomTypeID() {
        return roomTypeID.get();
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
    public IntegerProperty roomTypeIDProperty() {
        return roomTypeID;
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
}
