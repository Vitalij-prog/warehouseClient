package controllers;

import entities.Supply;
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
import static sample.InputCheck.checkChoiceForSearchingProducts;

public class ProviderController extends MainController {

    @FXML
    private Label userNameLabel;
    @FXML
    private Label userRoleLabel;
    @FXML
    private Label suppliesAmountLabel;
    @FXML
    private Label productAmountLabel;
    @FXML
    private Label sumLabel;
    @FXML
    private Button checkProviderInfoButton;
    @FXML
    private Button changeProviderInfoButton;

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
    private Button showSuppliesButton;
    @FXML
    private Button newSupplyButton;
    @FXML
    private Button cancelSupplyButton;
    @FXML
    private TableView<Supply> suppliesTableView;
    @FXML
    private TableColumn<Supply, Integer> suppliesColId;
    @FXML
    private TableColumn<Supply, String> suppliesColProdName;
    @FXML
    private TableColumn<Supply, Integer> suppliesColAmount;
    @FXML
    private TableColumn<Supply, Double> suppliesColPrice;
    @FXML
    private TableColumn<Supply, String> suppliesColManufacturer;
    @FXML
    private TableColumn<Supply, Date> suppliesColDate;
    @FXML
    private TableColumn<Supply, Time> suppliesColTime;
    @FXML
    private TableColumn<Supply, String> suppliesColStatus;


    @FXML
    public void initialize() {

        seUserData();

        ObservableList<String>  productOptions = FXCollections.observableArrayList("номеру","названию", "количеству");
        searchProductsChoiceBox.setItems(productOptions);
        searchProductsChoiceBox.setValue("названию");

        suppliesAmountLabel.setVisible(false);
        productAmountLabel.setVisible(false);
        sumLabel.setVisible(false);

        checkProviderInfoButton.setOnAction(event -> {
            String answer = getProviderInfoById(ClientSocket.user.getId());
            String[] values = answer.split("/");
            suppliesAmountLabel.setText(values[0]);
            productAmountLabel.setText(values[1]);
            sumLabel.setText(values[2]);
            suppliesAmountLabel.setVisible(true);
            productAmountLabel.setVisible(true);
            sumLabel.setVisible(true);
        });

        changeProviderInfoButton.setOnAction(event -> {
            createNewStage("../views/user/updateInfo.fxml");
            seUserData();
        });

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

        searchProductsButton.setOnAction(event -> {
            productNotFoundLabel.setVisible(false);
            if(searchProductsTextField.getText().equals("")) {

                productNotFoundLabel.setText("поле не заполнено");
                productNotFoundLabel.setVisible(true);
            } else {

                if(checkChoiceForSearchingProducts( searchProductsChoiceBox.getValue(),searchProductsTextField.getText())) {
                    ArrayList<Product> list = null;
                    try {
                        list = searchProducts( searchProductsChoiceBox.getValue(), searchProductsTextField.getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ObservableList<Product> products = FXCollections.observableArrayList();

                    if (list.size() != 0) {
                        products.setAll(list);

                        productsColId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
                        productsColName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
                        productsColPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
                        productsColAmount.setCellValueFactory(new PropertyValueFactory<Product, Integer>("amount"));
                        productsColManufacturer.setCellValueFactory(new PropertyValueFactory<Product, String>("manufacturer"));
                        productsTableView.setItems(products);
                    } else {
                        productNotFoundLabel.setText("данные не найдены");
                        productNotFoundLabel.setVisible(true);
                    }
                } else {
                    productNotFoundLabel.setText("некорректный ввод");
                    productNotFoundLabel.setVisible(true);
                }
            }
        });

        showSuppliesButton.setOnAction(event -> {
            ArrayList<Supply> list = null;
            try {
                list = getListSuppliesByUserId(ClientSocket.user.getId());
                //getListProducts();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ObservableList<Supply> orders = FXCollections.observableArrayList();

            if(list != null) {
                orders.setAll(list);
            }
            suppliesColId.setCellValueFactory(new PropertyValueFactory<Supply, Integer>("id"));
            suppliesColProdName.setCellValueFactory(new PropertyValueFactory<Supply, String>("productName"));
            suppliesColPrice.setCellValueFactory(new PropertyValueFactory<Supply, Double>("productPrice"));
            suppliesColAmount.setCellValueFactory(new PropertyValueFactory<Supply, Integer>("productAmount"));
            suppliesColManufacturer.setCellValueFactory(new PropertyValueFactory<Supply, String>("manufacturerName"));
            suppliesColDate.setCellValueFactory(new PropertyValueFactory<Supply, Date>("date"));
            suppliesColTime.setCellValueFactory(new PropertyValueFactory<Supply, Time>("time"));
            suppliesColStatus.setCellValueFactory(new PropertyValueFactory<Supply, String>("status"));
            suppliesTableView.setItems(orders);
        });

        newSupplyButton.setOnAction(event -> {
            createNewStage("../views/supply/addSupply.fxml", newSupplyButton);
        });
        cancelSupplyButton.setOnAction(event -> {
            createNewStage("../views/supply/cancelSupply.fxml", cancelSupplyButton);
        });
    }

    public void seUserData() {
        userNameLabel.setText(ClientSocket.user.getUserName());
        userNameLabel.setVisible(true);
        userRoleLabel.setText(ClientSocket.user.getRole());
        userRoleLabel.setVisible(true);
    }

}
