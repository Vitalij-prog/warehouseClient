package controllers.order;

import entities.Manufacturer;
import entities.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import sample.ClientSocket;

import java.util.ArrayList;

import static sample.ClientSocket.del;
import static sample.ClientSocket.getListOrdersByUserId;

public class CancelOrderController {

    @FXML
    private ChoiceBox<Order> ordersChoiceBox;
    @FXML
    private Button cancelButton;
    @FXML
    private Label prodNameLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label orderPriceLabel;
    @FXML
    private Label successMessage;

    @FXML
    public void initialize() {
        loadProcessingOrders();

        ordersChoiceBox.setOnAction(event -> {
            Order order = ordersChoiceBox.getValue();
            prodNameLabel.setText(order.getProd_name());
            amountLabel.setText(Integer.toString(order.getAmount()));
            orderPriceLabel.setText(Double.toString(order.getPrice()));
        });

        cancelButton.setOnAction(event -> {
            successMessage.setVisible(false);
            String answer = del(ordersChoiceBox.getValue().getId(), "order");
            if(answer.equals("success")) {
                loadProcessingOrders();
                successMessage.setVisible(true);
            }
        });
    }

    private void loadProcessingOrders() {
        ArrayList<Order> list = getListOrdersByUserId(ClientSocket.user.getId());
        ordersChoiceBox.getItems().setAll(list);

    }


}
