package controllers.order;

import entities.Order;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import sample.ClientSocket;
import sample.MainController;

import java.util.ArrayList;

import static sample.ClientSocket.*;

public class ProcessOrderController {
    @FXML
    private ChoiceBox<Order> ordersChoiceBox;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private Label clientNameLabel;
    @FXML
    private Label prodNameLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label amountOnWareHouseLabel;
    @FXML
    private Label orderPriceLabel;
    @FXML
    private Label successMessage;
    @FXML
    private Label warningLabel;
    @FXML
    private Button updateButton;

    private Order order;
    private Product product;

    @FXML
    public void initialize() {

        ObservableList<String> orderStatus = FXCollections.observableArrayList("confirmed", "cancel");
        statusChoiceBox.setItems(orderStatus);

        updateButton.setDisable(true);
        loadProcessingOrders();

        ordersChoiceBox.setOnAction(event -> {
            warningLabel.setVisible(false);
            this.order = ordersChoiceBox.getValue();
            clientNameLabel.setText(order.getUser_name());
            prodNameLabel.setText(order.getProd_name());
            amountLabel.setText(Integer.toString(order.getAmount()));
            orderPriceLabel.setText(Double.toString(order.getPrice()));
            product = getProductByName(order.getProd_name());
            amountOnWareHouseLabel.setText(Integer.toString(product.getAmount()));
            setVisibleInfo(true);
            updateButton.setDisable(false);
            successMessage.setVisible(false);
        });

        updateButton.setOnAction(event -> {
            warningLabel.setVisible(false);
            successMessage.setVisible(false);
            order.setStatus(statusChoiceBox.getValue());
            if(statusChoiceBox.getValue().equals("confirmed")) {
                if(order.getAmount() > product.getAmount()) {
                    warningLabel.setVisible(true);
                } else {
                    product.setAmount(product.getAmount() - order.getAmount());
                    String answer = ClientSocket.<Product>setById(product, "product");
                    if(answer.equals("success")) {
                        System.out.println("product amount updated");
                    }
                }
            }
            ClientSocket.order = this.order;
            MainController.createNewStage("/views/order/writeOrder.fxml", updateButton);

            String answer = setOrderById(order);
            if(answer.equals("success")) {
                loadProcessingOrders();
                successMessage.setVisible(true);
                setVisibleInfo(false);
                updateButton.setDisable(true);
            }
        });
    }

    private void loadProcessingOrders() {
        ArrayList<Order> list = getListOrdersByStatus("processing");
        if(list.size() == 0) {

            ordersChoiceBox.setDisable(true);
        } else {
            ordersChoiceBox.setDisable(false);
            ordersChoiceBox.getItems().setAll(list);
        }
    }

    private void setVisibleInfo(boolean option) {
        clientNameLabel.setVisible(option);
        prodNameLabel.setVisible(option);
        orderPriceLabel.setVisible(option);
        amountLabel.setVisible(option);
        amountOnWareHouseLabel.setVisible(option);
    }

}
