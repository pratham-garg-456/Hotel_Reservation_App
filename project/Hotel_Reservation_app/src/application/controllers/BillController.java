package application.controllers;

import application.views.BillView;
import database.Database;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BillController {
    private BillView m_view;
    private Database m_database;
    private Stage m_stage;
    
    public BillController() {
    	m_view = new BillView();
    	m_database = new Database();
    	m_stage = new Stage();    	
    }
    
    private void setUpActions() {
    	m_view.getShowBillButton().setOnAction(event -> {
    	    String reservationText = m_view.getReservationTextField().getText();

    	    if (!reservationText.isEmpty()) {
    	        int reservationNumber = Integer.parseInt(reservationText);
    	        String billSummary = m_database.generateBillSummary(reservationNumber);

    	        if (!billSummary.isEmpty()) {
    	            m_view.getBillTextArea().setText(billSummary);
    	            m_view.getDiscountTextField().setDisable(false); // Enable discount field
    	        } else {
    	            showAlert("Please Show the bill before applying a discount.");
    	        }
    	    } else {
    	        showAlert("Please enter a reservation number.");
    	    }
    	});


        m_view.getUpdateDiscountButton().setOnAction(e -> {
            

            // Check if BillTextArea is not empty
            if (!m_view.getBillTextArea().getText().isEmpty()) {
            	int reservationNumber = Integer.parseInt(m_view.getReservationTextField().getText());
                try {
                    double discount = Double.parseDouble(m_view.getDiscountTextField().getText());

                    // Validate discount range (0 to 25)
                    if (discount < 0 || discount > 25) {
                        showAlert("Discount should be between 0 and 25.");
                    } else {
                        m_database.updateDiscount(reservationNumber, discount);
                        m_view.getBillTextArea().setText(m_database.generateBillSummary(reservationNumber));
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Invalid discount amount. Please enter a valid number.");
                }
            } else {
                showAlert("Please select the bill before applying a discount.");
            }
        });
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    
   public void initializeUI() {
    	
    	// Set up the scene and stage
    	m_stage.setTitle("Bill Details");
        Scene scene = new Scene(m_view, 500, 500);
        m_stage.setResizable(false);
        m_stage.getIcons().add(new Image(getClass().getResourceAsStream("/application/resources/hotel.png")));
        m_stage.setScene(scene);
        setUpActions();
        m_stage.show();

    }
    
}
