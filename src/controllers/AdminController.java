package controllers;

import entities.Manufacturer;
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
    private TableColumn<Product, String> prodCol_5;
    @FXML
    private TableColumn<Product, String> prodCol_6;

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
    private Button showUsersButton;
    @FXML
    private Button addUserButton;
    @FXML
    private Button deleteUserButton;
    @FXML
    private Button searchUsersButton;
    @FXML
    private Button blockUserButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField dataUserSearchTextField;
    @FXML
    private Label takenNameMessage;
    @FXML
    private Label addUserSuccessMessage;
    @FXML
    private Label userNotFoundMessage;
    @FXML
    private Label emptyFieldsMessage;

    @FXML
    private ChoiceBox<String> choiceForUsersSearching;
    @FXML
    private ChoiceBox<String> choiceBoxUserRole;
    @FXML
    private TableView<User> usersTableView;
    @FXML
    private TableColumn<User,Integer> usersColId;
    @FXML
    private TableColumn<User, String>  usersColName;
    @FXML
    private TableColumn<User, String> usersColRole;
    @FXML
    private TableColumn<User, String> usersColStatus;
    //-----------------------------------------------------
    @FXML
    private TableView<Manufacturer> manufacturersTableView;
    @FXML
    private TableColumn<Manufacturer, Integer> manufacturersColId;
    @FXML
    private TableColumn<Manufacturer, String> manufacturersColName;
    @FXML
    private TableColumn<Manufacturer, String> manufacturersColProdType;

    @FXML
    private Button viewManufacturersButton;
    @FXML
    private Button addManufacturersButton;
    @FXML
    private Button editManufacturersButton;
    @FXML
    private Button delManufacturersButton;

    @FXML
    void initialize() {
        ObservableList<String>  orderOptions = FXCollections.observableArrayList("номер", "имя пользователя","название товара", "дата заказа");
        choiceBoxForSearching.setItems(orderOptions);
        choiceBoxForSearching.setValue("product_name");

        ObservableList<String>  productOptions = FXCollections.observableArrayList("номеру","названию", "количеству", "производителю");
        choiceBoxForProducts.setItems(productOptions);
        choiceBoxForProducts.setValue("названию");

        ObservableList<String>  userOptions = FXCollections.observableArrayList("номеру", "имени", "роли", "статусу");
        choiceForUsersSearching.setItems(userOptions);
        choiceForUsersSearching.setValue("имени");

        ObservableList<String>  userRole = FXCollections.observableArrayList("admin", "client", "provider");
        choiceBoxUserRole.setItems(userRole);
        choiceBoxUserRole.setValue("client");

        choiceBoxForSearching.setOnAction(event -> choiceBoxForSearching.setValue(choiceBoxForSearching.getValue()));

        buttonSearch.setOnAction(event -> {
            labelInfo.setVisible(false);
            labelNotFound.setVisible(false);
            if(dataSearching.getText().equals("")) {
                labelNotFound.setVisible(false);
                labelInfo.setText("поле на заполнено");
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
            prodCol_5.setCellValueFactory(new PropertyValueFactory<Product, String>("type"));
            prodCol_6.setCellValueFactory(new PropertyValueFactory<Product, String>("manufacturer"));
            prodTableView.setItems(products);
        });

        buttonAddProd.setOnAction(event -> {
            Stage primaryStage = (Stage) buttonAddProd.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/addProduct.fxml"));

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
            stage.showAndWait();
        });

        buttonEditProd.setOnAction(event -> {
            MainController.createNewStage("../views/editProduct.fxml");
        });

        buttonDeleteProd.setOnAction(event -> {
            MainController.createNewStage("../views/delProduct.fxml");
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

                labelSearchProductsInfo.setText("поле не заполнено");
                labelSearchProductsInfo.setVisible(true);
            } else {
                labelSearchProductsInfo.setVisible(false);
                if(checkChoiceForSearchingProducts(choiceBoxForProducts.getValue(),textForProductsSearching.getText())) {
                    ArrayList<Product> list = null;
                    try {
                        list = searchProducts(choiceBoxForProducts.getValue(), textForProductsSearching.getText());
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
                        prodCol_5.setCellValueFactory(new PropertyValueFactory<Product, String>("type"));
                        prodCol_6.setCellValueFactory(new PropertyValueFactory<Product, String>("manufacturer"));
                        prodTableView.setItems(products);
                    } else {
                        labelSearchProductsInfo.setText("данные не найдены");
                        labelSearchProductsInfo.setVisible(true);
                    }
                } else {
                    labelSearchProductsInfo.setText("некорректный ввод");
                    labelSearchProductsInfo.setVisible(true);
                }
            }
        });
        //--------------------------------------------------------------
        showUsersButton.setOnAction(event -> {
            ArrayList<User> list = null;
            try {
                list = getListUsers();
            } catch (Exception e) {
                e.printStackTrace();
            }

            ObservableList<User> users = FXCollections.observableArrayList();

            if(list.size() != 0) {
                users.setAll(list);

                usersColId.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
                usersColName.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
                usersColRole.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
                usersColStatus.setCellValueFactory(new PropertyValueFactory<User,String>("status"));
                usersTableView.setItems(users);
            }
        });

        deleteUserButton.setOnAction(event -> {
            String answer = "";
            /*labelUserDeleted.setVisible(false);
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
            }*/
        });

        addUserButton.setOnAction(event -> {
            String answer = "";
            takenNameMessage.setVisible(false);
            addUserSuccessMessage.setVisible(false);
            emptyFieldsMessage.setVisible(false);
            if(nameTextField.getText().equals("") || passwordTextField.getText().equals("")) {
                emptyFieldsMessage.setText("поля не заполнены");
                emptyFieldsMessage.setVisible(true);
            } else {
                if(isCorrectString(nameTextField.getText(), LENGTH_USER_NAME) && isCorrectString(passwordTextField.getText(), LENGTH_USER_PASSWORD)) {

                    String login = nameTextField.getText();
                    String password = passwordTextField.getText();
                    String role = choiceBoxUserRole.getValue();
                    User user = new User(0, login, password, role, "");
                    try {
                        answer = addUser(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (answer.equals("success")) {
                        addUserSuccessMessage.setVisible(true);
                    } else {
                        takenNameMessage.setText("это имя уже занято");
                        takenNameMessage.setVisible(true);
                    }
                } else {
                    takenNameMessage.setText("некорректный ввод");
                    takenNameMessage.setVisible(true);
                }
            }
        });
        searchUsersButton.setOnAction(event -> {
            userNotFoundMessage.setVisible(false);
            if(dataUserSearchTextField.getText().equals("")) {

                userNotFoundMessage.setText("поле не заполнено");
                userNotFoundMessage.setVisible(true);
            } else {
                if(checkChoiceForSearchingUsers(choiceForUsersSearching.getValue(),dataUserSearchTextField.getText())) {
                    ArrayList<User> list = null;
                    try {
                        list = searchUsers(choiceForUsersSearching.getValue(), dataUserSearchTextField.getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ObservableList<User> users = FXCollections.observableArrayList();

                    if (list.size() != 0) {
                        users.setAll(list);
                        usersColId.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
                        usersColName.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
                        usersColRole.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
                        usersColStatus.setCellValueFactory(new PropertyValueFactory<User,String>("status"));
                        usersTableView.setItems(users);
                    } else {
                        userNotFoundMessage.setText("данные не найдены");
                        userNotFoundMessage.setVisible(true);
                    }
                } else {
                    userNotFoundMessage.setText("некорректный ввод");
                    userNotFoundMessage.setVisible(true);
                }
            }
        });

        //--------------------------------------------------------------
        viewManufacturersButton.setOnAction(event -> {
            ArrayList<Manufacturer> list = null;

            list = getListManufacturers();
            ObservableList<Manufacturer> manufacturers = FXCollections.observableArrayList();

            if(list.size() != 0) {
                manufacturers.setAll(list);

                manufacturersColId.setCellValueFactory(new PropertyValueFactory<Manufacturer, Integer>("id"));
                manufacturersColName.setCellValueFactory(new PropertyValueFactory<Manufacturer, String>("name"));
                manufacturersColProdType.setCellValueFactory(new PropertyValueFactory<Manufacturer, String>("productsType"));

                manufacturersTableView.setItems(manufacturers);
            }
        });

        addManufacturersButton.setOnAction(event -> {
            MainController.createNewStage("../views/manufacturer/addManufacturer.fxml");
        });

        delManufacturersButton.setOnAction(event -> {
            MainController.createNewStage("../views/manufacturer/delManufacturer.fxml");
        });
        editManufacturersButton.setOnAction(event -> {
            MainController.createNewStage("../views/manufacturer/editManufacturer.fxml");
        });
    }
}
