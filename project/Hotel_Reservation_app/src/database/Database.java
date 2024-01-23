package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.models.BillInfo;
import application.models.BookingInfo;
import application.models.Guest;
import application.models.ReservationInfo;
import application.models.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {

    private static final String DATABASE_URL = "jdbc:sqlite:C:\\Users\\hp\\eclipse-workspace\\Hotel_Reservation_app\\src\\database\\testDB.db";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";
    private static final String AVAILABLE_ROOMS_QUERY = "SELECT Rooms.RoomID, Rooms.RoomNumber, RoomTypes.RoomType, RoomTypes.Capacity, RoomTypes.PricePerNight " +
            "FROM Rooms " +
            "JOIN RoomTypes ON Rooms.RoomTypeID = RoomTypes.RoomTypeID " +
            "WHERE RoomTypes.Capacity <= ? AND RoomTypes.RoomType IN (?, ?, ?, ?) " +
            "AND Rooms.RoomID NOT IN (SELECT ReservationRoom.RoomID FROM Reservations " +
            "JOIN ReservationRoom ON Reservations.ReservationID = ReservationRoom.ReservationID " +
            "JOIN Rooms ON ReservationRoom.RoomID = Rooms.RoomID " +
            "JOIN RoomTypes ON Rooms.RoomTypeID = RoomTypes.RoomTypeID " +
            "WHERE RoomTypes.RoomType IN (?, ?, ?, ?) AND " +
            "(? BETWEEN Reservations.CheckInDate AND Reservations.CheckOutDate) OR " +
            "(? BETWEEN Reservations.CheckInDate AND Reservations.CheckOutDate) " +
            "OR (Reservations.CheckInDate BETWEEN ? AND ?) OR (Reservations.CheckOutDate BETWEEN ? AND ?))";
    private static final String ADD_GUEST_QUERY = "INSERT INTO Guests (GuestID, FirstName, LastName, Address, Phone, Email) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String ADD_RESERVATION_QUERY= "INSERT INTO Reservations (ReservationID, GuestID, CheckInDate, CheckOutDate, BookingDate) VALUES (?, ?, ?, ?, ?)";
    private static final String MAX_GUEST_ID_QUERY = "SELECT MAX(GuestID) FROM Guests";
    private static final String MAX_RESERVATION_ID = "SELECT MAX(ReservationID) FROM Reservations";
    private static final String ADD_RESERVATION_ROOM_QUERY =  "INSERT INTO ReservationRoom (ReservationRoomID, ReservationID, RoomID) VALUES (?, ?, ?)";
    private static final String MAX_RESERVATION_ROOM_ID = "SELECT MAX(ReservationRoomID) FROM ReservationRoom";
    private static final String ADD_BILL_QUERY = "INSERT INTO Bills (ReservationID, Discount) VALUES (?, ?)";
    private static final String GET_ADMIN_QUERY = "SELECT AdminName FROM Admin WHERE Email = ? AND Password = ?";
    private static final String BOOKING_INFO_QUERY = "SELECT Reservations.ReservationID, " +
	        "Guests.FirstName || ' ' || Guests.LastName AS GuestName, " +
	        "Guests.Address, " +
	        "Guests.Phone, " +
	        "Guests.Email, " +
	        "Reservations.CheckInDate, " +
	        "Reservations.CheckOutDate, " +
	        "Reservations.BookingDate, " +
	        "RoomTypes.RoomType, " +
	        "Rooms.RoomNumber " +
	        "FROM Reservations " +
	        "JOIN Guests ON Reservations.GuestID = Guests.GuestID " +
	        "JOIN ReservationRoom ON Reservations.ReservationID = ReservationRoom.ReservationID " +
	        "JOIN Rooms ON ReservationRoom.RoomID = Rooms.RoomID " +
	        "JOIN RoomTypes ON Rooms.RoomTypeID = RoomTypes.RoomTypeID";
    private static final String FILTER_BOOKING_INFO_QUERY = "SELECT Reservations.ReservationID, " +
            "Guests.FirstName || ' ' || Guests.LastName AS GuestName, " +
            "Guests.Address, " +
            "Guests.Phone, " +
            "Guests.Email, " +
            "Reservations.CheckInDate, " +
            "Reservations.CheckOutDate, " +
            "Reservations.BookingDate, " +
            "RoomTypes.RoomType, " +
            "Rooms.RoomNumber " +
            "FROM Reservations " +
            "JOIN Guests ON Reservations.GuestID = Guests.GuestID " +
            "JOIN ReservationRoom ON Reservations.ReservationID = ReservationRoom.ReservationID " +
            "JOIN Rooms ON ReservationRoom.RoomID = Rooms.RoomID " +
            "JOIN RoomTypes ON Rooms.RoomTypeID = RoomTypes.RoomTypeID " + // Add a space here
            "WHERE (CheckInDate >= ? OR ? IS NULL) " +
            "AND (CheckOutDate <= ? OR ? IS NULL)";

    private static final String RESERVATION_INFO_QUERY = "SELECT Reservations.*, Guests.FirstName || ' ' || Guests.LastName AS GuestName, RoomTypes.RoomType, Rooms.RoomNumber, RoomTypes.PricePerNight " +
            "FROM Reservations " +
            "JOIN Guests ON Reservations.GuestID = Guests.GuestID " +
            "JOIN ReservationRoom ON Reservations.ReservationID = ReservationRoom.ReservationID " +
            "JOIN Rooms ON ReservationRoom.RoomID = Rooms.RoomID " +
            "JOIN RoomTypes ON Rooms.RoomTypeID = RoomTypes.RoomTypeID " +
            "WHERE Reservations.ReservationID = ?";
    private static final String BILL_INFO_QUERY = "SELECT * FROM Bills WHERE ReservationID = ?";
    private static final String UPDATE_BILL_DISCOUNT_QUERY = "UPDATE Bills SET Discount = ? WHERE ReservationID = ?";
    
    
    // Establishes a connection to the database
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
    }
    
    
    // Retrieves a list of available rooms from the database based on user input
    public ObservableList<Room> getAvailableRooms(int numberOfPersons, LocalDate checkInDate, LocalDate checkOutDate,
                                                       boolean includeSingle, boolean includeDouble, boolean includeDeluxe,
                                                       boolean includePenthouse) throws SQLException {
    ObservableList<Room> availableRooms = FXCollections.observableArrayList();

    try (Connection connection = connect()) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(AVAILABLE_ROOMS_QUERY)) {
            preparedStatement.setInt(1, numberOfPersons);
            setBooleanOrString(preparedStatement, 2, includeSingle, "Single");
            setBooleanOrString(preparedStatement, 3, includeDouble, "Double");
            setBooleanOrString(preparedStatement, 4, includeDeluxe, "Deluxe");
            setBooleanOrString(preparedStatement, 5, includePenthouse, "Penthouse");
            setBooleanOrString(preparedStatement, 6, includeSingle, "Single");
            setBooleanOrString(preparedStatement, 7, includeDouble, "Double");
            setBooleanOrString(preparedStatement, 8, includeDeluxe, "Deluxe");
            setBooleanOrString(preparedStatement, 9, includePenthouse, "Penthouse");
            preparedStatement.setObject(10, checkInDate);
            preparedStatement.setObject(11, checkOutDate);
            preparedStatement.setObject(12, checkInDate);
            preparedStatement.setObject(13, checkOutDate);
            preparedStatement.setObject(14, checkInDate);
            preparedStatement.setObject(15, checkOutDate);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int roomID = resultSet.getInt("RoomID");
                String roomNumber = resultSet.getString("RoomNumber");
                String roomType = resultSet.getString("RoomType");
                int capacity = resultSet.getInt("Capacity");
                double pricePerNight = resultSet.getDouble("PricePerNight");

                Room availableRoom = new Room(roomID, roomNumber, roomType, capacity, pricePerNight);
                availableRooms.add(availableRoom);
            }
        }
    }

    return availableRooms;
}

    // Helper method to set boolean or string in prepared statement
    private void setBooleanOrString(PreparedStatement preparedStatement, int index, boolean isBoolean, String value) throws SQLException {
        if (isBoolean) {
            preparedStatement.setString(index, value);
        } else {
            preparedStatement.setInt(index, 0);
        }
    }
    
    
 // Adds a new guest to the database and returns the generated guest ID
    public int addGuest(Guest guest) throws SQLException {
        int generatedGuestID = -1;

        try (Connection connection = connect()) {
            // Get the maximum guest ID
            int maxGuestID = getMaxGuestID(connection);

            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_GUEST_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setInt(1, maxGuestID + 1); // Increment the max ID for a new guest
                preparedStatement.setString(2, guest.getFirstName());
                preparedStatement.setString(3, guest.getLastName());
                preparedStatement.setString(4, guest.getAddress());
                preparedStatement.setString(5, guest.getPhone());
                preparedStatement.setString(6, guest.getEmail());

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        generatedGuestID = generatedKeys.getInt(1);
                        System.out.println("Guest added successfully with ID: " + generatedGuestID);
                    }
                } else {
                    System.out.println("Failed to add guest. No rows affected.");
                }
            }
        }

        return generatedGuestID;
    }

    // Adds a new reservation to the database and returns the generated reservation ID
    public int addReservation(int guestID, LocalDate checkInDate, LocalDate checkOutDate, LocalDate bookingDate) throws SQLException {
        int generatedReservationID = -1;

        try (Connection connection = connect()) {
            // Get the maximum reservation ID
            int maxReservationID = getMaxReservationID(connection);

            try (PreparedStatement preparedStatement = connection.prepareStatement(
            		ADD_RESERVATION_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setInt(1, maxReservationID + 1); // Increment the max ID for a new reservation
                preparedStatement.setInt(2, guestID);
                preparedStatement.setObject(3, checkInDate);
                preparedStatement.setObject(4, checkOutDate);
                preparedStatement.setObject(5, bookingDate);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        generatedReservationID = generatedKeys.getInt(1);
                    }
                } else {
                    System.out.println("Failed to add reservation. No rows affected.");
                }
            }
        }

        return generatedReservationID;
    }

    // Helper method to get the maximum guest ID
    private int getMaxGuestID(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(MAX_GUEST_ID_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        }
    }

    // Helper method to get the maximum reservation ID
    private int getMaxReservationID(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(MAX_RESERVATION_ID)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        }
    }

 // Adds a new reservation room to the database
    public void addReservationRoom(int reservationID, int roomID) throws SQLException {
        try (Connection connection = connect()) {
            // Get the maximum ReservationRoomID
            int maxReservationRoomID = getMaxReservationRoomID(connection);

            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_RESERVATION_ROOM_QUERY)) 
            {
            	preparedStatement.setInt(1, maxReservationRoomID + 1); // Increment the max ID for a new reservation room
                preparedStatement.setInt(2, reservationID);
                preparedStatement.setInt(3, roomID);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Reservation room added successfully!");
                } else {
                    System.out.println("Failed to add reservation room. No rows affected.");
                }
            }
        }
    }

    // Helper method to get the maximum ReservationRoomID
    private int getMaxReservationRoomID(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(MAX_RESERVATION_ROOM_ID)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        }
    }


 // Function to make a reservation
    public void makeReservation(Guest guest, List<Room> availableRooms, LocalDate checkInDate, LocalDate checkOutDate) {
        try {

            // Step 1: Add guest to the database and get the generated guest ID
            int guestID = addGuest(guest);
            System.out.println("Guest added successfully with ID: " + guestID);

            // Step 2: Add reservation to the database and get the generated reservation ID
            int reservationID = addReservation(guestID, checkInDate, checkOutDate, LocalDate.now());
            System.out.println("Reservation added successfully with ID: " + reservationID);

            // Step 3: Check if availableRooms is empty
            System.out.println("Number of available rooms: " + availableRooms.size());

            // Step 4: Add reservation rooms to the database
            for (Room availableRoom : availableRooms) {
                addReservationRoom(reservationID, availableRoom.getRoomID());
                System.out.println("Reservation room added for RoomID " + availableRoom.getRoomID() + " to ReservationID " + reservationID);
            }

         // Step 5: Add a record in the Bills table with 0 discount
            addBill(reservationID, 0.00);
            System.out.println("Bill added for ReservationID " + reservationID + " with 0 discount");

            // Step 6: Display success message or perform additional actions
            System.out.println("Reservation successfully made for guest " + guest.getFirstName() + " " + guest.getLastName());
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors or display an error message
        }
    }
    
 // Function to add a record in the Bills table
    private void addBill(int reservationID, double discount) throws SQLException {

    	try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_BILL_QUERY)) {

            preparedStatement.setInt(1, reservationID);
            preparedStatement.setDouble(2, discount);

            preparedStatement.executeUpdate();
        }
    }
    
    // Validates admin credentials and returns the admin name if valid
    public String validateAdmin(String email, String password) {
    	
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ADMIN_QUERY)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("AdminName");
            } else {
                return null; // Admin not found with provided credentials
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors or display an error message
            return null;
        }
    }
    
    public ObservableList<BookingInfo> loadBookingData() {
        ObservableList<BookingInfo> bookings = FXCollections.observableArrayList();
        
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(BOOKING_INFO_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                BookingInfo booking = new BookingInfo(
                        resultSet.getInt("ReservationID"),
                        resultSet.getString("GuestName"),
                        resultSet.getString("Address"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Email"),
                        resultSet.getString("CheckInDate"),
                        resultSet.getString("CheckOutDate"),
                        resultSet.getString("BookingDate"),
                        resultSet.getString("RoomType"),
                        resultSet.getString("RoomNumber")
                );
                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public ObservableList<BookingInfo> getBookings(LocalDate checkInDate, LocalDate checkOutDate) {
        ObservableList<BookingInfo> filteredBookings = FXCollections.observableArrayList();

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(FILTER_BOOKING_INFO_QUERY)) {

            preparedStatement.setObject(1, checkInDate);
            preparedStatement.setObject(2, checkInDate);
            preparedStatement.setObject(3, checkOutDate);
            preparedStatement.setObject(4, checkOutDate);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    BookingInfo booking = new BookingInfo(
                            resultSet.getInt("ReservationID"),
                            resultSet.getString("GuestName"),
                            resultSet.getString("Address"),
                            resultSet.getString("Phone"),
                            resultSet.getString("Email"),
                            resultSet.getString("CheckInDate"),
                            resultSet.getString("CheckOutDate"),
                            resultSet.getString("BookingDate"),
                            resultSet.getString("RoomType"),
                            resultSet.getString("RoomNumber")
                    );
                    filteredBookings.add(booking);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filteredBookings;
    }
 // Function to generate bill details and reservation details in a summary
    @SuppressWarnings("null")
	public String generateBillSummary(int reservationID) {
        StringBuilder summary = new StringBuilder();

        try {
            // Retrieve reservation details
            List<ReservationInfo> reservationInfoList = getReservationInfo(reservationID);

            // Retrieve bill details
            BillInfo billInfo = getBillInfo(reservationID);

            // Check if there are reservations for the given ID
            if (reservationInfoList.isEmpty()) {
                summary.append("No reservations found for Reservation ID ").append(reservationID);
                return summary.toString();
            }

            // Build the bill summary
            summary.append("Bill Summary for Reservation ID: ").append(reservationID).append("\n");
            summary.append("----------------------------------------").append("\n");

            
                summary.append("Guest Name: ").append(reservationInfoList.get(0).getGuestName()).append("\n");
                summary.append("Check-in Date: ").append(reservationInfoList.get(0).getCheckInDate()).append("\n");
                summary.append("Check-out Date: ").append(reservationInfoList.get(0).getCheckOutDate()).append("\n");
                summary.append("Booking Date: ").append(reservationInfoList.get(0).getBookingDate()).append("\n");
                summary.append("Room Types: ").append(formatRoomTypes(reservationInfoList)).append("\n");
            

            // Check if billInfo is not null before accessing its properties
            if (billInfo != null) {
                summary.append("Discount: ").append(billInfo.getDiscount()).append("\n");
                summary.append("Total Amount: ").append(calculateTotalAmount(reservationInfoList, billInfo.getDiscount())).append("\n");
            } else {
            	summary.append("Total Amount: ").append(calculateTotalAmount(reservationInfoList, 0.0)).append("\n");
                summary.append("No DISCOUNT information available for this reservation.").append("\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors or display an error message
        }

        return summary.toString();
    }



    // Helper function to format room types
    private String formatRoomTypes(List<ReservationInfo> reservationInfoList) {
        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (ReservationInfo reservationInfo : reservationInfoList) {
            String roomType = reservationInfo.getRoomType();
            roomTypeCount.put(roomType, roomTypeCount.getOrDefault(roomType, 0) + 1);
        }

        StringBuilder formattedRoomTypes = new StringBuilder();
        roomTypeCount.forEach((roomType, count) ->
                formattedRoomTypes.append(count).append(" ").append(roomType).append(" "));

        return formattedRoomTypes.toString().trim();
    }

    private List<ReservationInfo> getReservationInfo(int reservationID) throws SQLException {
        List<ReservationInfo> reservationInfoList = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(RESERVATION_INFO_QUERY)) {

            preparedStatement.setInt(1, reservationID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ReservationInfo reservationInfo = new ReservationInfo(
                            resultSet.getInt("ReservationID"),
                            resultSet.getString("GuestName"),
                            resultSet.getString("CheckInDate"),
                            resultSet.getString("CheckOutDate"),
                            resultSet.getString("BookingDate"),
                            resultSet.getString("RoomType"),
                            resultSet.getString("RoomNumber"),
                            resultSet.getDouble("PricePerNight")
                    );
                    reservationInfoList.add(reservationInfo);
                }
            }
        }

        return reservationInfoList;
    }

    // Function to retrieve bill details based on reservation ID
    private BillInfo getBillInfo(int reservationID) throws SQLException {

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(BILL_INFO_QUERY)) {

            preparedStatement.setInt(1, reservationID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new BillInfo(
                            resultSet.getDouble("Discount")
                    );
                }
            }
        }

        return null;
    }

    // Function to calculate total amount after applying discount
    private double calculateTotalAmount(List<ReservationInfo> reservationInfoList, double discount) {
    	double totalPrice=0.0;
    	 for (ReservationInfo reservationInfo : reservationInfoList) {
             totalPrice+= reservationInfo.getPricePerNight();
         }
    	 return totalPrice - (totalPrice * discount / 100.0);

    	
    }

 // Function to update discount in the Bills table
    public void updateDiscount(int reservationID, double newDiscount) {

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BILL_DISCOUNT_QUERY)) {

            preparedStatement.setDouble(1, newDiscount);
            preparedStatement.setInt(2, reservationID);

            preparedStatement.executeUpdate();
            System.out.println("Discount updated successfully for Reservation ID " + reservationID);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors or display an error message
        }
    }



}
