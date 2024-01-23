package application.views;

import java.time.LocalDate;

import application.models.Room;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AvailableRoomsView extends BorderPane {

    private  TableView<Room> roomTableView;
   
    private  DatePicker checkInDatePicker;
    private  DatePicker checkOutDatePicker;
    private  CheckBox singleRoomCheckBox;
    private  CheckBox doubleRoomCheckBox;
    private  CheckBox deluxeRoomCheckBox;
    private  CheckBox penthouseRoomCheckBox;

    public AvailableRoomsView() {
       setupUI();
       setTop(createHeading());
       GridPane centerLayout = createRoomSlectionContent();
       setCenter(centerLayout);
 }
    
    private void setupUI() {
    	setPadding(new Insets(20));
    	setCenter(createRoomSlectionContent());
    }
    
    @SuppressWarnings({ "unchecked", "deprecation" })
    private GridPane createRoomSlectionContent() {

        GridPane layout;

        // Set up UI layout
        layout = new GridPane();
        layout.setPadding(new Insets(10));
        layout.setHgap(10);
        layout.setVgap(10);

        // Column constraints for consistent width
        ColumnConstraints col1 = new ColumnConstraints(165);
        col1.setHgrow(Priority.ALWAYS);
        ColumnConstraints col2 = new ColumnConstraints(165);
        col2.setHgrow(Priority.ALWAYS);
        layout.getColumnConstraints().addAll(col1, col2);
        
      
        // Initialize UI components
        roomTableView = new TableView<>();
        
        roomTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
     // Set up DatePicker with DayCellFactory to disallow past dates
        checkInDatePicker = new DatePicker();
        checkInDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });

        checkOutDatePicker = new DatePicker();
        checkOutDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
 
        singleRoomCheckBox = new CheckBox("Single Room");
        doubleRoomCheckBox = new CheckBox("Double Room");
        deluxeRoomCheckBox = new CheckBox("Deluxe Room");
        penthouseRoomCheckBox = new CheckBox("Penthouse Room");

        // Set up TableView columns for roomTableView
        TableColumn<Room, String> roomNumberColumn = new TableColumn<>("Room Number");
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        TableColumn<Room, String> roomTypeColumn = new TableColumn<>("Room Type");
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));

        TableColumn<Room, Double> priceColumn = new TableColumn<>("Price"); // New column for Price
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));

        TableColumn<Room, Integer> capacityColumn = new TableColumn<>("Capacity"); // New column for Quantity
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        roomTableView.getColumns().addAll(roomNumberColumn, roomTypeColumn, priceColumn, capacityColumn); // Add new columns
       

        layout.add(getStyledLabel("Date:"), 0, 0);
        layout.add(new Label("From:"), 0, 1);
        layout.add(checkInDatePicker, 1, 1);

        layout.add(new Label("To:"), 0, 2);
        layout.add(checkOutDatePicker, 1, 2);

        layout.add(getStyledLabel("Type Of Room:"), 0, 3);
        layout.add(singleRoomCheckBox, 0, 4);
        layout.add(doubleRoomCheckBox, 1, 4);
        layout.add(deluxeRoomCheckBox, 0, 5);
        layout.add(penthouseRoomCheckBox, 1, 5);

        // colspan of 2
        layout.add(roomTableView, 0, 6, 2, 1); // colspan of 2
        return layout;
    }
    
    private HBox createHeading() {
        HBox headingBox = new HBox();
        Text headingText = new Text("Available Rooms");
        headingText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headingBox.getChildren().add(headingText);
        headingBox.setAlignment(Pos.CENTER);
        headingBox.setPadding(new Insets(10, 0, 30, 0)); // Add padding
        return headingBox;
    }
    
    private Label getStyledLabel(String labelText) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        return label;
    }

   
	public TableView<Room> getRoomTableView() {
		return roomTableView;
	}

	public void setRoomTableView(ObservableList<Room> availableRooms) {
		roomTableView.setItems(availableRooms);
	}
	
	public DatePicker getCheckInDatePicker() {
		return checkInDatePicker;
	}

	public DatePicker getCheckOutDatePicker() {
		return checkOutDatePicker;
	}

	public CheckBox getSingleRoomCheckBox() {
		return singleRoomCheckBox;
	}

	public CheckBox getDoubleRoomCheckBox() {
		return doubleRoomCheckBox;
	}

	public CheckBox getDeluxeRoomCheckBox() {
		return deluxeRoomCheckBox;
	}

	public CheckBox getPenthouseRoomCheckBox() {
		return penthouseRoomCheckBox;
	}


}
