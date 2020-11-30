package controllers;

import entities.Order;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.ClientSocket;
import sample.MainController;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import static sample.ClientSocket.*;

public class ClientController {
    @FXML
    private Button showProductsButton;
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, Integer> productsColId;
    @FXML
    private TableColumn<Product, String> productsColName;
    @FXML
    private TableColumn<Product, Double> productsColPrice;
    @FXML
    private TableColumn<Product, Integer> productsColAmount;
    @FXML
    private TableColumn<Product, String> productsColManufacturer;
    @FXML
    private ChoiceBox<String> searchProductsChoiceBox;
    @FXML
    private Button searchProductsButton;
    @FXML
    private Label productNotFoundLabel;
    @FXML
    private TextField searchProductsTextField;


    @FXML
    private Button showOrdersButton;
    @FXML
    private Button newOrderButton;
    @FXML
    private Button cancelOrderButton;
    @FXML
    private TableView<Order> ordersTableView;
    @FXML
    private TableColumn<Order, Integer> ordersColId;
    @FXML
    private TableColumn<Order, String> ordersColProdName;
    @FXML
    private TableColumn<Order, Integer> ordersColAmount;
    @FXML
    private TableColumn<Order, Double> ordersColPrice;
    @FXML
    private TableColumn<Order, Date> ordersColDate;
    @FXML
    private TableColumn<Order, Time> ordersColTime;
    @FXML
    private TableColumn<Order, String> ordersColStatus;


    @FXML
    public void initialize() {

        showProductsButton.setOnAction(event -> {
            ArrayList<Product> list = null;
            try {
                list = getListProducts();
            } catch (Exception e) {
                e.printStackTrace();
            }

            ObservableList<Product> products = FXCollections.observableArrayList();

            if(list != null) {
                products.setAll(list);
            }
            productsColId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
            productsColName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
            productsColPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
            productsColAmount.setCellValueFactory(new PropertyValueFactory<Product, Integer>("amount"));
            productsColManufacturer.setCellValueFactory(new PropertyValueFactory<Product, String>("manufacturer"));
            productsTableView.setItems(products);
        });

        showOrdersButton.setOnAction(event -> {
            ArrayList<Order> list = null;
            try {
                list = getListOrdersByUserId(ClientSocket.user.getId());
                //getListProducts();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ObservableList<Order> orders = FXCollections.observableArrayList();

            if(list != null) {
                orders.setAll(list);
            }
            ordersColId.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
            ordersColProdName.setCellValueFactory(new PropertyValueFactory<Order, String>("prod_name"));
            //col_userName.setCellValueFactory(new PropertyValueFactory<Order, String>("user_name"));
            ordersColPrice.setCellValueFactory(new PropertyValueFactory<Order, Double>("price"));
            ordersColAmount.setCellValueFactory(new PropertyValueFactory<Order, Integer>("amount"));
            ordersColDate.setCellValueFactory(new PropertyValueFactory<Order, Date>("date"));
            ordersColTime.setCellValueFactory(new PropertyValueFactory<Order, Time>("time"));
            ordersColStatus.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));
            ordersTableView.setItems(orders);
        });

        newOrderButton.setOnAction(event -> {
            MainController.createNewStage("../views/order/addOrder.fxml", newOrderButton);
        });
        cancelOrderButton.setOnAction(event -> {
            MainController.createNewStage("../views/order/cancelOrder.fxml", cancelOrderButton);
        });

    }


}
