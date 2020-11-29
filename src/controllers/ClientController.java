package controllers;

import entities.Order;
import entities.Product;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.sql.Time;

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


}
