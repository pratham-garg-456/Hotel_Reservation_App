package application.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class BillView extends GridPane {
	private Label heading;
    private Label reservationLabel;
    private TextField reservationTextField;
    private Button showBillButton;
    private TextArea billTextArea;
    private Label discountLabel;
    private TextField discountTextField;
    private Button updateDiscountButton; // Added button for updating discount

    public BillView() {
        initializeView();
    }

    public void initializeView() {
        // Create UI components
        heading = new Label("View/Update Bill");
        heading.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-alignment: center;"); // Set style for heading
        reservationLabel = new Label("Enter Reservation Number:");
        reservationTextField = new TextField();
        showBillButton = new Button("Show Bill");
        billTextArea = new TextArea();
        discountLabel = new Label("Change Discount:");
        discountTextField = new TextField();
        updateDiscountButton = new Button("Update Discount"); // Added button for updating discount

        // Column constraints for consistent width
        ColumnConstraints col1 = new ColumnConstraints(165);
        col1.setHgrow(Priority.ALWAYS);
        ColumnConstraints col2 = new ColumnConstraints(165);
        col2.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(col1, col2);

        setVgap(20); // Set vertical gap between rows

        HBox buttonBox = new HBox(10, showBillButton, updateDiscountButton);
        buttonBox.setAlignment(Pos.CENTER);
        HBox headingBox = new HBox(10,heading);
        headingBox.setAlignment(Pos.CENTER);
        add(headingBox, 0, 0, 2, 1); // Span the heading across two columns
        add(reservationLabel, 0, 1);
        add(reservationTextField, 1, 1);
        add(discountLabel, 0, 2);
        add(discountTextField, 1, 2);
        add(buttonBox, 0, 3, 2, 1);
        add(billTextArea, 0, 4, 2, 5);

        setAlignment(Pos.CENTER);
        setPadding(new Insets(20, 20, 20, 20));
    }


    public Label getReservationLabel() {
        return reservationLabel;
    }

    public TextField getReservationTextField() {
        return reservationTextField;
    }

    public Button getShowBillButton() {
        return showBillButton;
    }

    public TextArea getBillTextArea() {
        return billTextArea;
    }

    public Label getDiscountLabel() {
        return discountLabel;
    }

    public TextField getDiscountTextField() {
        return discountTextField;
    }

    public Button getUpdateDiscountButton() {
        return updateDiscountButton;
    }
}
