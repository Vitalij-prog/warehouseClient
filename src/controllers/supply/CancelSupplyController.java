package controllers.supply;

import entities.Order;
import entities.Supply;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import sample.ClientSocket;

import java.util.ArrayList;

import static sample.ClientSocket.*;

public class CancelSupplyController {
    @FXML
    private ChoiceBox<Supply> suppliesChoiceBox;
    @FXML
    private Button cancelButton;
    @FXML
    private Label prodNameLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label manufacturerLabel;
    @FXML
    private Label successMessage;

    @FXML
    public void initialize() {
        loadProcessingOrders();

        suppliesChoiceBox.setOnAction(event -> {
            Supply supply = suppliesChoiceBox.getValue();
            if(supply == null) {
                prodNameLabel.setText("");
                amountLabel.setText("");
                manufacturerLabel.setText("");
            } else {
                prodNameLabel.setText(supply.getProductName());
                amountLabel.setText(Integer.toString(supply.getProductAmount()));
                manufacturerLabel.setText(supply.getManufacturerName());
            }
        });

        cancelButton.setOnAction(event -> {
            successMessage.setVisible(false);
            String answer = del(suppliesChoiceBox.getValue().getId(), "supply");
            if(answer.equals("success")) {
                loadProcessingOrders();
                successMessage.setVisible(true);
            }
        });
    }

    private void loadProcessingOrders() {
        ArrayList<Supply> list = getListByUserIdAndStatus("supply",ClientSocket.user.getId(), "processing");
        if(list.size() == 0) {
            suppliesChoiceBox.setDisable(true);
        } else {
            suppliesChoiceBox.setDisable(false);
            suppliesChoiceBox.getItems().setAll(list);
        }
    }
}
