package application.views;

import application.models.BookingInfo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CurrentBookingView extends BorderPane {

    private TableView<BookingInfo> tableView;
    private Label bookingCountLabel;
    private DatePicker checkInDatePicker;
    private DatePicker checkOutDatePicker;
    private Button filterButton;
    private Button clearFilterButton;
    private Label dateLabel;

    public CurrentBookingView() {
        setupUI();
        setupTable();
    }

    private void setupUI() {
        setPadding(new Insets(20));

        // Create Header
        HBox header = new HBox();
        Label headerLabel = new Label("Current Bookings");
        headerLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        header.getChildren().add(headerLabel);
        header.setPadding(new Insets(10, 0, 30, 0));
        header.setAlignment(javafx.geometry.Pos.CENTER);
        setTop(header);

        // Create Filter Section
        GridPane filterGrid = new GridPane();
        filterGrid.setHgap(10);
        filterGrid.setVgap(10);
        filterGrid.setPadding(new Insets(10, 0, 30, 0));

        bookingCountLabel = getStyledLabel("Number of Bookings: ",false);
        checkInDatePicker = new DatePicker();
        checkOutDatePicker = new DatePicker();
        filterButton = new Button("Filter Bookings");
        clearFilterButton = new Button("Clear Filters");
        
        dateLabel = getStyledLabel("Date: ",false);

        HBox hb1 = new HBox(10);
        hb1.getChildren().addAll(bookingCountLabel);
        hb1.setAlignment(Pos.CENTER);
        filterGrid.add(hb1, 0, 0, 4, 1);
        filterGrid.add(dateLabel,0,1);
//        filterGrid.add(new Label ("from: "), 1, 1);
        filterGrid.add(checkInDatePicker, 1, 1);
        filterGrid.add(new Label("to: "), 2, 1);
        filterGrid.add(checkOutDatePicker, 3, 1);
        
        HBox hb = new HBox(10);
        hb.getChildren().addAll(filterButton, clearFilterButton);
        hb.setAlignment(Pos.CENTER);
        hb.setPadding(new Insets(5, 0, 5, 0));
        filterGrid.add(hb, 0, 3,4,1);
        
        HBox fg = new HBox(10);
        fg.getChildren().addAll(filterGrid);
        fg.setAlignment(Pos.CENTER);
        setCenter(fg);

        // Initialize the TableView
        tableView = new TableView<>();
        setBottom(tableView);
        setPadding(new Insets(10, 20, 30, 20));
    }

    @SuppressWarnings("unchecked")
	private void setupTable() {
        // Define columns for the TableView
        TableColumn<BookingInfo, Integer> reservationIdCol = new TableColumn<>("Reservation ID");
        reservationIdCol.setCellValueFactory(new PropertyValueFactory<>("reservationID"));

        TableColumn<BookingInfo, String> guestNameCol = new TableColumn<>("Guest Name");
        guestNameCol.setCellValueFactory(new PropertyValueFactory<>("guestName"));

        TableColumn<BookingInfo, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<BookingInfo, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<BookingInfo, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<BookingInfo, String> checkInDateCol = new TableColumn<>("Check-In Date");
        checkInDateCol.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));

        TableColumn<BookingInfo, String> checkOutDateCol = new TableColumn<>("Check-Out Date");
        checkOutDateCol.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));

        TableColumn<BookingInfo, String> bookingDateCol = new TableColumn<>("Booking Date");
        bookingDateCol.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));

        TableColumn<BookingInfo, String> roomTypeCol = new TableColumn<>("Room Type");
        roomTypeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));

        TableColumn<BookingInfo, String> roomNumberCol = new TableColumn<>("Room Number");
        roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        // Add columns to the TableView
        tableView.getColumns().addAll(
                reservationIdCol, guestNameCol, addressCol, phoneCol, emailCol,
                checkInDateCol, checkOutDateCol, bookingDateCol, roomTypeCol, roomNumberCol
        );
    }
    
    private Label getStyledLabel(String labelText, boolean required) {
        Label label = new Label(labelText + (required ? " *" : ""));
        label.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        return label;
    }

	public TableView<BookingInfo> getTableView() {
		return tableView;
	}

	public Label getBookingCountLabel() {
		return bookingCountLabel;
	}

	public DatePicker getCheckInDatePicker() {
		return checkInDatePicker;
	}

	public DatePicker getCheckOutDatePicker() {
		return checkOutDatePicker;
	}

	public Button getFilterButton() {
		return filterButton;
	}

	public Button getClearFilterButton() {
		return clearFilterButton;
	}
    
    


}
