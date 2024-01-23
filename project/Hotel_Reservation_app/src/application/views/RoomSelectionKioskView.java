package application.views;

import java.time.LocalDate;

import application.models.Room;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class RoomSelectionKioskView extends BorderPane {

    private TableView<Room> roomTableView;
    private TableView<Room> roomTableView2;
    private DatePicker checkInDatePicker;
    private DatePicker checkOutDatePicker;
    private Spinner<Integer> numberOfPersonsSpinner;
    private CheckBox singleRoomCheckBox;
    private CheckBox doubleRoomCheckBox;
    private CheckBox deluxeRoomCheckBox;
    private CheckBox penthouseRoomCheckBox;
    private Button sendRB;
    private Button sendLB;
    private Label totalPriceLabel;
    private Button continueButton;

    public RoomSelectionKioskView() {
        setupUI();
    }

    private void setupUI() {
        setPadding(new Insets(20));
        setTop(createHeading());
        setCenter(createRoomSelectionContent());
        continueButton = new Button("Continue");
        setBottom(continueButton);
        setMargin(continueButton, new Insets(10, 0, 10, 0));
        setAlignment(continueButton, Pos.CENTER);
        continueButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    private GridPane createRoomSelectionContent() {
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10));
        layout.setHgap(10);
        layout.setVgap(10);

        ColumnConstraints col1 = new ColumnConstraints(165);
        ColumnConstraints col2 = new ColumnConstraints(160);
        ColumnConstraints col3 = new ColumnConstraints(50);
        col2.setHgrow(Priority.ALWAYS);
        ColumnConstraints col4 = new ColumnConstraints(165);
        ColumnConstraints col5 = new ColumnConstraints(160);
        layout.getColumnConstraints().addAll(col1, col2, col3, col4, col5);

        roomTableView = new TableView<>();
        roomTableView2 = new TableView<>();
        roomTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        roomTableView2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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
        numberOfPersonsSpinner = new Spinner<>(1, 10, 1);
        singleRoomCheckBox = new CheckBox("Single Room");
        doubleRoomCheckBox = new CheckBox("Double Room");
        deluxeRoomCheckBox = new CheckBox("Deluxe Room");
        penthouseRoomCheckBox = new CheckBox("Penthouse Room");

        // Columns for TableView
        TableColumn<Room, String> roomNumberColumn = new TableColumn<>("Room Number");
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        TableColumn<Room, String> roomTypeColumn = new TableColumn<>("Room Type");
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));

        TableColumn<Room, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));

        TableColumn<Room, Integer> capacityColumn = new TableColumn<>("Capacity");
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        roomTableView.getColumns().addAll(roomNumberColumn, roomTypeColumn, priceColumn, capacityColumn);

        TableColumn<Room, String> roomNumberColumn2 = new TableColumn<>("Room Number");
        roomNumberColumn2.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        TableColumn<Room, String> roomTypeColumn2 = new TableColumn<>("Room Type");
        roomTypeColumn2.setCellValueFactory(new PropertyValueFactory<>("roomType"));

        TableColumn<Room, Double> priceColumn2 = new TableColumn<>("Price");
        priceColumn2.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));

        TableColumn<Room, Integer> capacityColumn2 = new TableColumn<>("Capacity");
        capacityColumn2.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        roomTableView2.getColumns().addAll(roomNumberColumn2, roomTypeColumn2, priceColumn2, capacityColumn2);

        sendRB = new Button(" > ");
        sendLB = new Button(" < ");

        VBox vb = new VBox(10);
        vb.getChildren().addAll(sendRB, sendLB);
        vb.setAlignment(Pos.CENTER);

        totalPriceLabel = new Label();
        totalPriceLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        layout.add(getStyledLabel("Number of People:", true), 0, 0);
        layout.add(numberOfPersonsSpinner, 1, 0);

        layout.add(getStyledLabel("Check-in Date:", true), 0, 1);
        layout.add(checkInDatePicker, 1, 1);

        layout.add(getStyledLabel("Check-out Date:", true), 0, 2);
        layout.add(checkOutDatePicker, 1, 2);

        layout.add(getStyledLabel("Type Of Room:",true), 3, 0);
        layout.add(singleRoomCheckBox, 3, 1);
        layout.add(doubleRoomCheckBox, 4, 1);
        layout.add(deluxeRoomCheckBox, 3, 2);
        layout.add(penthouseRoomCheckBox, 4, 2);

        layout.add(getStyledLabel("Available Rooms:", false), 0, 5, 2, 1);
        layout.add(roomTableView, 0, 6, 2, 1);
        layout.add(vb, 2, 6);
        layout.add(getStyledLabel("Selected Rooms:", true), 3, 5, 2, 1);
        layout.add(roomTableView2, 3, 6, 2, 1);
        layout.add(totalPriceLabel, 3, 7, 2, 1);

        return layout;
    }
    
    private HBox createHeading() {
        HBox headingBox = new HBox();
        Text headingText = new Text("Self Checkout Kiosk");
        headingText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headingBox.getChildren().add(headingText);
        headingBox.setAlignment(Pos.CENTER);
        headingBox.setPadding(new Insets(10, 0, 30, 0)); // Add padding
        return headingBox;
    }

    private Label getStyledLabel(String labelText, boolean required) {
        Label label = new Label(labelText + (required ? " *" : ""));
        label.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        return label;
    }

    // Getter methods...

    public TableView<Room> getRoomTableView() {
        return roomTableView;
    }

    public void setRoomTableView(ObservableList<Room> availableRooms) {
        roomTableView.setItems(availableRooms);
    }

    public TableView<Room> getRoomTableView2() {
        return roomTableView2;
    }

    public void setRoomTableView2(ObservableList<Room> selectedRooms) {
        roomTableView2.setItems(selectedRooms);
    }

    public DatePicker getCheckInDatePicker() {
        return checkInDatePicker;
    }

    public DatePicker getCheckOutDatePicker() {
        return checkOutDatePicker;
    }

    public Spinner<Integer> getNumberOfPersonsSpinner() {
        return numberOfPersonsSpinner;
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

    public Button getSendRB() {
        return sendRB;
    }

    public Button getSendLB() {
        return sendLB;
    }

    public Label getTotalPriceLabel() {
        return totalPriceLabel;
    }

    public Button getContinueButton() {
        return continueButton;
    }
}
