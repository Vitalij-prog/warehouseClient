package controllers;

import entities.Order;
import entities.Product;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Modality;
import sample.MainController;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import static sample.ClientSocket.*;
//import static sample.ClientSocket.addUser;
import static sample.InputCheck.*;


public class AdminController {
    @FXML
    private Button buttonShowProd;

    @FXML
    private Button buttonEditProd;

    @FXML
    private TableView<Product> prodTableView;
    @FXML
    private TableColumn<Product,Integer> prodCol_1;
    @FXML
    private TableColumn<Product, String> prodCol_2;
    @FXML
    private TableColumn<Product, Double> prodCol_3;
    @FXML
    private TableColumn<Product, Integer> prodCol_4;

    @FXML
    private Button buttonAddProd;
    @FXML
    private Button buttonDeleteProd;
    @FXML
    private Button buttonSearchProducts;
    @FXML
    private ChoiceBox<String> choiceBoxForProducts;
    @FXML
    private Label labelSearchProductsInfo;
    @FXML
    private TextField textForProductsSearching;
    //-----------------------------------------------------
    @FXML
    private Button buttonShowOrders;
    @FXML
    private Button buttonOrderProcessing;
    @FXML
    private TableView<Order> ordersTableView;
    @FXML
    private TableColumn<Order,Integer> col_id;
    @FXML
    private TableColumn<Order, String> col_prodName;
    @FXML
    private TableColumn<Order, String> col_userName;
    @FXML
    private TableColumn<Order, Double> col_price;
    @FXML
    private TableColumn<Order,  Integer> col_amount;
    @FXML
    private TableColumn<Order, Date> col_date;
    @FXML
    private TableColumn<Order, Time> col_time;
    @FXML
    private TableColumn<Order, String> col_status;
    @FXML
    private Button buttonSearch;
    @FXML
    private ChoiceBox<String> choiceBoxForSearching;
    @FXML
    private TextField dataSearching;
    @FXML
    private Label labelInfo;
    @FXML
    private Label labelNotFound;
    //-----------------------------------------------------
    @FXML
    private Button buttonShowUsers;
    @FXML
    private Button buttonAddUser;
    @FXML
    private Button buttonDeleteUser;
    @FXML
    private TextField textUserId;
    @FXML
    private TextField textUserLogin;
    @FXML
    private TextField textUserPassword;
    @FXML
    private Label labelExist;
    @FXML
    private Label labelAddUserSuccess;
    @FXML
    private Label labelUserDeleted;
    @FXML
    private Label labelUserIdNotFound;
    @FXML
    private ChoiceBox<String> choiceBoxUserRole;
    @FXML
    private TableView<User> tableUsers;
    @FXML
    private TableColumn<User,Integer> col_userId;
    @FXML
    private TableColumn<User, String> col_usName;
    @FXML
    private TableColumn<User, String> col_userRole;



    @FXML
    void initialize() {
        ObservableList<String>  orderOptions = FXCollections.observableArrayList("product_name", "user_name","price", "date");
        choiceBoxForSearching.setItems(orderOptions);
        choiceBoxForSearching.setValue("product_name");

        ObservableList<String>  productOptions = FXCollections.observableArrayList("product_name", "product_price", "amount");
        choiceBoxForProducts.setItems(productOptions);
        choiceBoxForProducts.setValue("product_name");

        ObservableList<String>  userRole = FXCollections.observableArrayList("user", "admin");
        choiceBoxUserRole.setItems(userRole);
        choiceBoxUserRole.setValue("user");

        choiceBoxForSearching.setOnAction(event -> choiceBoxForSearching.setValue(choiceBoxForSearching.getValue()));

        buttonSearch.setOnAction(event -> {
            labelInfo.setVisible(false);
            labelNotFound.setVisible(false);
            if(dataSearching.getText().equals("")) {
                labelNotFound.setVisible(false);
                labelInfo.setText("the empty field");
                labelInfo.setVisible(true);
            } else {
                labelNotFound.setVisible(false);
                labelInfo.setVisible(false);
                if(checkChoiceForSearchingOrders(choiceBoxForSearching.getValue(),dataSearching.getText())) {

                    ArrayList<Order> list = null;
                    try {
                        list = getListOrders(choiceBoxForSearching.getValue(), dataSearching.getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ObservableList<Order> orders = FXCollections.observableArrayList();

                    if (list.size() != 0) {
                        orders.setAll(list);

                        col_id.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
                        col_prodName.setCellValueFactory(new PropertyValueFactory<Order, String>("prod_name"));
                        col_userName.setCellValueFactory(new PropertyValueFactory<Order, String>("user_name"));
                        col_price.setCellValueFactory(new PropertyValueFactory<Order, Double>("price"));
                        col_amount.setCellValueFactory(new PropertyValueFactory<Order, Integer>("amount"));
                        col_date.setCellValueFactory(new PropertyValueFactory<Order, Date>("date"));
                        col_time.setCellValueFactory(new PropertyValueFactory<Order, Time>("time"));
                        col_status.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));

                        ordersTableView.setItems(orders);
                    } else {
                        labelNotFound.setText("data not found");
                        labelNotFound.setVisible(true);
                    }
                } else {
                    labelInfo.setText("incorrect input");
                    labelInfo.setVisible(true);
                }
            }
        });

        buttonShowProd.setOnAction(event -> {
            ArrayList<Product> list = null;
            try {
                list = getListProducts();
                //getListProducts();
            } catch (Exception e) {
                e.printStackTrace();
            }

            ObservableList<Product> products = FXCollections.observableArrayList();

            if(list != null) {

                products.setAll(list);

            }
            prodCol_1.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
            prodCol_2.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
            prodCol_3.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
            prodCol_4.setCellValueFactory(new PropertyValueFactory<Product, Integer>("amount"));
            prodTableView.setItems(products);
        });

        buttonAddProd.setOnAction(event -> {
            Stage primaryStage = (Stage) buttonAddProd.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addProduct.fxml"));

            try{
                loader.load();
            }catch (IOException e) {
                e.printStackTrace();
            }

            Parent main = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(main));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            stage.show();
        });

        buttonEditProd.setOnAction(event -> {
            MainController.createNewStage("editProduct.fxml");
        });

        buttonDeleteProd.setOnAction(event -> {
            MainController.createNewStage("delProduct.fxml");
        });

        buttonOrderProcessing.setOnAction(event-> {
            MainController.createNewStage("order_processing.fxml");
        });

        buttonShowOrders.setOnAction(event -> {
            ArrayList<Order> list = null;
            try {
                list = getListOrders();
                //getListProducts();
            } catch (Exception e) {
                e.printStackTrace();
            }

            ObservableList<Order> orders = FXCollections.observableArrayList();

            if(list != null) {
                orders.setAll(list);
            }

            col_id.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
            col_prodName.setCellValueFactory(new PropertyValueFactory<Order, String>("prod_name"));
            col_userName.setCellValueFactory(new PropertyValueFactory<Order, String>("user_name"));
            col_price.setCellValueFactory(new PropertyValueFactory<Order, Double>("price"));
            col_amount.setCellValueFactory(new PropertyValueFactory<Order, Integer>("amount"));
            col_date.setCellValueFactory(new PropertyValueFactory<Order, Date>("date"));
            col_time.setCellValueFactory(new PropertyValueFactory<Order, Time>("time"));
            col_status.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));

            ordersTableView.setItems(orders);
        });

        buttonSearchProducts.setOnAction(event -> {
            if(textForProductsSearching.getText().equals("")) {

                labelSearchProductsInfo.setText("the empty field");
                labelSearchProductsInfo.setVisible(true);
            } else {
                labelSearchProductsInfo.setVisible(false);
                if(checkChoiceForSearchingProducts(choiceBoxForProducts.getValue(),textForProductsSearching.getText())) {
                    ArrayList<Product> list = null;
                    try {
                        list = getListProducts(choiceBoxForProducts.getValue(), textForProductsSearching.getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ObservableList<Product> products = FXCollections.observableArrayList();

                    if (list.size() != 0) {
                        products.setAll(list);

                        prodCol_1.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
                        prodCol_2.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
                        prodCol_3.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
                        prodCol_4.setCellValueFactory(new PropertyValueFactory<Product, Integer>("amount"));
                        prodTableView.setItems(products);
                    } else {
                        labelSearchProductsInfo.setText("data not found");
                        labelSearchProductsInfo.setVisible(true);
                    }
                } else {
                    labelSearchProductsInfo.setText("incorrect input");
                    labelSearchProductsInfo.setVisible(true);
                }
            }
        });

        buttonShowUsers.setOnAction(event -> {
            ArrayList<User> list = null;
            try {
                list = getListUsers();
            } catch (Exception e) {
                e.printStackTrace();
            }

            ObservableList<User> users = FXCollections.observableArrayList();

            if(list.size() != 0) {
                users.setAll(list);

                col_userId.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
                col_usName.setCellValueFactory(new PropertyValueFactory<User, String>("user_name"));
                col_userRole.setCellValueFactory(new PropertyValueFactory<User, String>("role"));

                tableUsers.setItems(users);
            }
        });

        buttonDeleteUser.setOnAction(event -> {
            String answer = "";
            labelUserDeleted.setVisible(false);
            if(textUserId.getText().equals("")) {
                labelUserIdNotFound.setText("the field is empty");
                labelUserIdNotFound.setVisible(true);
            } else {
                labelUserIdNotFound.setVisible(false);
                if(isInteger(textUserId.getText())) {

                    try {
                        answer = deleteUser(textUserId.getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (answer.equals("success")) {
                        labelUserDeleted.setVisible(true);
                    } else {
                        labelUserIdNotFound.setText("id not found");
                        labelUserIdNotFound.setVisible(true);
                    }
                } else {
                    labelUserIdNotFound.setText("incorrect input");
                    labelUserIdNotFound.setVisible(true);
                }
            }
        });

        buttonAddUser.setOnAction(event -> {
            String answer = "";
            labelExist.setVisible(false);
            labelAddUserSuccess.setVisible(false);
            if(textUserLogin.getText().equals("") || textUserPassword.getText().equals("")) {
                labelExist.setText("there is empty field");
                labelExist.setVisible(true);
            } else {
                labelExist.setVisible(false);
                if(isCorrectString(textUserLogin.getText(), LENGTH_USER_NAME) && isCorrectString(textUserPassword.getText(), LENGTH_USER_PASSWORD)) {

                    String login = textUserLogin.getText();
                    String password = textUserPassword.getText();
                    String role = choiceBoxUserRole.getValue();
                    User user = new User(0, login, password, role, "");
                    try {
                        answer = addUser(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (answer.equals("success")) {
                        labelAddUserSuccess.setVisible(true);
                    } else {
                        labelExist.setText("this login already exist");
                        labelExist.setVisible(true);
                    }
                } else {
                    labelExist.setText("incorrect input");
                    labelExist.setVisible(true);
                }
            }

        });
    }




}
