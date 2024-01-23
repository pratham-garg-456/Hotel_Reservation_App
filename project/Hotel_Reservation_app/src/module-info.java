module Hotel_Reservation_app {
	requires javafx.controls;
	requires java.sql;
	requires javafx.base;
	requires org.xerial.sqlitejdbc;
	
	opens application to javafx.graphics, javafx.fxml;
	opens application.models to javafx.base;  // Add this line
}
