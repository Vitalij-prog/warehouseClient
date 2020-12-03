package controllers.supply;

import entities.Product;
import entities.Supply;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import sample.ClientSocket;

import java.util.ArrayList;

import static sample.ClientSocket.*;

public class ProcessSupplyController {
    @FXML
    private ChoiceBox<Supply> suppliesChoiceBox;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private Label providerNameLabel;
    @FXML
    private Label prodNameLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label manufacturerLabel;
    @FXML
    private Label successMessage;

    @FXML
    private Button updateButton;

    private Supply supply;

    @FXML
    public void initialize() {

        ObservableList<String> orderStatus = FXCollections.observableArrayList("confirmed", "cancel");
        statusChoiceBox.setItems(orderStatus);
        statusChoiceBox.setValue("confirmed");

        updateButton.setDisable(true);
        loadProcessingSupplies();

        suppliesChoiceBox.setOnAction(event -> {
            this.supply = suppliesChoiceBox.getValue();
            providerNameLabel.setText(supply.getProviderName());
            prodNameLabel.setText(supply.getProductName());
            manufacturerLabel.setText(supply.getManufacturerName());
            amountLabel.setText(Integer.toString(supply.getProductAmount()));
            setVisibleInfo(true);
            updateButton.setDisable(false);
        });

        updateButton.setOnAction(event -> {
            successMessage.setVisible(false);
            supply.setStatus(statusChoiceBox.getValue());
            if(statusChoiceBox.getValue().equals("confirmed")) {
                Product product = new Product(0, supply.getProductName(),
                        supply.getProductAmount(),
                        supply.getProductPrice(),
                        supply.getManufacturerId());
                String answer = add(product, "product", "addFromSupply");
                if(answer.equals("success")) {
                    System.out.println("add supply successfully");
                }

            }
            String answer = ClientSocket.<Supply>setById(supply, "supply");
            if(answer.equals("success")) {
                loadProcessingSupplies();
                successMessage.setVisible(true);
                setVisibleInfo(false);
                updateButton.setDisable(true);
            }
        });
    }

    private void loadProcessingSupplies() {
        ArrayList<Supply> list = ClientSocket.<Supply>getListByStatus("processing", "supply",  "getListByStatus");
        if(list.size() == 0) {
            suppliesChoiceBox.setDisable(true);
        } else {
            suppliesChoiceBox.setDisable(false);
            suppliesChoiceBox.getItems().setAll(list);
        }

    }

    private void setVisibleInfo(boolean option) {
        providerNameLabel.setVisible(option);
        prodNameLabel.setVisible(option);
        manufacturerLabel.setVisible(option);
        amountLabel.setVisible(option);
    }

}
