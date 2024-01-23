package application.models;

//ReservationInfo class
public class ReservationInfo {
 private int reservationID;
 private String guestName;
 private String checkInDate;
 private String checkOutDate;
 private String bookingDate;
 private String roomType;
 private String roomNumber;
 private double pricePerNight;

 public ReservationInfo(int reservationID, String guestName, String checkInDate, String checkOutDate,
                        String bookingDate, String roomType, String roomNumber, double pricePerNight) {
     this.reservationID = reservationID;
     this.guestName = guestName;
     this.checkInDate = checkInDate;
     this.checkOutDate = checkOutDate;
     this.bookingDate = bookingDate;
     this.roomType = roomType;
     this.roomNumber = roomNumber;
     this.pricePerNight = pricePerNight;
 }

 // Getter methods
 public int getReservationID() {
     return reservationID;
 }

 public String getGuestName() {
     return guestName;
 }

 public String getCheckInDate() {
     return checkInDate;
 }

 public String getCheckOutDate() {
     return checkOutDate;
 }

 public String getBookingDate() {
     return bookingDate;
 }

 public String getRoomType() {
     return roomType;
 }

 public String getRoomNumber() {
     return roomNumber;
 }

 public double getPricePerNight() {
     return pricePerNight;
 }
}