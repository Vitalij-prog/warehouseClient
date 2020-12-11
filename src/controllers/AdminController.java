package controllers;

import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Modality;
import sample.ClientSocket;
import sample.Main;
import sample.MainController;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import static sample.ClientSocket.*;
//import static sample.ClientSocket.addUser;
import static sample.InputCheck.*;


public class AdminController extends MainController {
    @FXML
    private Label adminNameLabel;
    @FXML
    private Label adminRoleLabel;

    @FXML
    private Button changeUserDataButton;

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
    private Button orderSearchingButton;
    @FXML
    private ChoiceBox<String> orderSearchingChoiceBox;
    @FXML
    private TextField orderSearchTextField;
    @FXML
    private Label orderInfoLabel;

    //-----------------------------------------------------
    @FXML
    private Button showSuppliesButton;
    @FXML
    private Button delSuppliesButton;
    @FXML
    private Button processingSuppliesButton;
    @FXML
    private TableView<Supply> suppliesTableView;
    @FXML
    private TableColumn<Supply,Integer> supplyColId;
    @FXML
    private TableColumn<Supply, String> supplyColUserName;
    @FXML
    private TableColumn<Supply, String> supplyColProdName;
    @FXML
    private TableColumn<Supply, Double> supplyColProdPrice;
    @FXML
    private TableColumn<Supply,  Integer> supplyColProdAmount;
    @FXML
    private TableColumn<Supply, String> supplyColManufacturer;
    @FXML
    private TableColumn<Supply, Date> supplyColDate;
    @FXML
    private TableColumn<Supply, Time> supplyColTime;
    @FXML
    private TableColumn<Supply, String> supplyColStatus;
    @FXML
    private Button searchSuppliesButton;
    @FXML
    private ChoiceBox<String> searchSuppliesChoiceBox;
    @FXML
    private TextField searchSuppliesTextField;
    @FXML
    private Label searchSuppliesLabel;

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
    
    //--------------------------------------------------------
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private Button viewLineChartButton;

    @FXML
    private PieChart pieChart;
    @FXML
    private Button showPieChartButton;

    //--------------------------------------------------------
    @FXML
    void initialize() {

        setUserData();

        changeUserDataButton.setOnAction(event -> {
            MainController.createNewStage("../views/user/updateInfo.fxml", changeUserDataButton);
            setUserData();
        });

        ObservableList<String>  orderOptions = FXCollections.observableArrayList("номеру", "имени пользователя","названию товара", "дате заказа");
        orderSearchingChoiceBox.setItems(orderOptions);
        orderSearchingChoiceBox.setValue("номеру");

        ObservableList<String>  suppliesOptions = FXCollections.observableArrayList("номеру", "имени пользователя","названию товара", "дате поставки");
        searchSuppliesChoiceBox.setItems(suppliesOptions);
        searchSuppliesChoiceBox.setValue("номеру");

        ObservableList<String>  productOptions = FXCollections.observableArrayList("номеру","названию", "количеству", "производителю");
        choiceBoxForProducts.setItems(productOptions);
        choiceBoxForProducts.setValue("названию");

        ObservableList<String>  userOptions = FXCollections.observableArrayList("номеру", "имени", "роли", "статусу");
        choiceForUsersSearching.setItems(userOptions);
        choiceForUsersSearching.setValue("имени");

        ObservableList<String>  userRole = FXCollections.observableArrayList("admin", "client", "provider");
        choiceBoxUserRole.setItems(userRole);
        choiceBoxUserRole.setValue("client");

        //orderSearchingChoiceBox.setOnAction(event -> orderSearchingChoiceBox.setValue(orderSearchingChoiceBox.getValue()));

        buttonShowProd.setOnAction(event -> {
            ArrayList<Product> list = null;
            try {
                list = getList("product");
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
            loader.setLocation(getClass().getResource("../views/product/addProduct.fxml"));

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
            createNewStage("../views/product/editProduct.fxml", buttonEditProd);
        });

        buttonDeleteProd.setOnAction(event -> {
           createNewStage("../views/product/delProduct.fxml",buttonDeleteProd);
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
                        list = search("product", choiceBoxForProducts.getValue(), textForProductsSearching.getText());
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
            createNewStage("../views/manufacturer/addManufacturer.fxml", addManufacturersButton);
        });

        delManufacturersButton.setOnAction(event -> {
            createNewStage("../views/manufacturer/delManufacturer.fxml", delManufacturersButton);
        });
        editManufacturersButton.setOnAction(event -> {
            createNewStage("../views/manufacturer/editManufacturer.fxml", editManufacturersButton);
        });
        //------------------------------------------------------------
        deleteUserButton.setOnAction(event -> {
            createNewStage("../views/user/delUser.fxml", deleteUserButton);
        });

        blockUserButton.setOnAction(event-> {
            createNewStage("../views/user/accessControl.fxml", blockUserButton);
        });
        //-------------------------------------------Orders-----------------
        buttonShowOrders.setOnAction(event -> {
            ArrayList<Order> list = null;

            list = getListOrders();

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

        buttonOrderProcessing.setOnAction(event -> {
            MainController.createNewStage("../views/order/processOrder.fxml", buttonOrderProcessing);
        });

        orderSearchingButton.setOnAction(event -> {
            orderInfoLabel.setVisible(false);
            if(orderSearchTextField.getText().equals("")) {
                orderInfoLabel.setText("поле на заполнено");
                orderInfoLabel.setVisible(true);
            } else {
                if(checkChoiceForSearchingOrders(orderSearchingChoiceBox.getValue(),orderSearchTextField.getText())) {

                    ArrayList<Order> list = null;
                    try {
                        list = ClientSocket.<Order>search("order", orderSearchingChoiceBox.getValue(), orderSearchTextField.getText());
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
                        orderInfoLabel.setText("данные не найдены");
                        orderInfoLabel.setVisible(true);
                    }
                } else {
                    orderInfoLabel.setText("некорректный ввод");
                    orderInfoLabel.setVisible(true);
                }
            }
        });

        //--------------------------------------Supplies----------------------

        showSuppliesButton.setOnAction(event -> {
            ArrayList<Supply> list = null;

            list = getListSupplies();

            ObservableList<Supply> supplies = FXCollections.observableArrayList();

            if(list != null) {
                supplies.setAll(list);
            }

            supplyColId.setCellValueFactory(new PropertyValueFactory<Supply, Integer>("id"));
            supplyColUserName.setCellValueFactory(new PropertyValueFactory<Supply, String>("providerName"));
            supplyColProdName.setCellValueFactory(new PropertyValueFactory<Supply, String>("productName"));
            supplyColProdPrice.setCellValueFactory(new PropertyValueFactory<Supply, Double>("productPrice"));
            supplyColProdAmount.setCellValueFactory(new PropertyValueFactory<Supply, Integer>("productAmount"));
            supplyColManufacturer.setCellValueFactory(new PropertyValueFactory<Supply, String>("manufacturerName"));
            supplyColDate.setCellValueFactory(new PropertyValueFactory<Supply, Date>("date"));
            supplyColTime.setCellValueFactory(new PropertyValueFactory<Supply, Time>("time"));
            supplyColStatus.setCellValueFactory(new PropertyValueFactory<Supply, String>("status"));

            suppliesTableView.setItems(supplies);
        });

        processingSuppliesButton.setOnAction(event -> {
            createNewStage("../views/supply/processSupply.fxml", processingSuppliesButton);
        });

        searchSuppliesButton.setOnAction(event -> {
            searchSuppliesLabel.setVisible(false);
            if(searchSuppliesTextField.getText().equals("")) {
                searchSuppliesLabel.setText("поле на заполнено");
                searchSuppliesLabel.setVisible(true);
            } else {
                if( checkChoiceForSearchingSupplies(searchSuppliesChoiceBox.getValue(), searchSuppliesTextField.getText())) {

                    ArrayList<Supply> list = null;
                    try {
                        list = ClientSocket.<Supply>search("supply", searchSuppliesChoiceBox.getValue(), searchSuppliesTextField.getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ObservableList<Supply> supplies = FXCollections.observableArrayList();

                    if (list.size() != 0) {
                        supplies.setAll(list);

                        supplyColId.setCellValueFactory(new PropertyValueFactory<Supply, Integer>("id"));
                        supplyColUserName.setCellValueFactory(new PropertyValueFactory<Supply, String>("providerName"));
                        supplyColProdName.setCellValueFactory(new PropertyValueFactory<Supply, String>("productName"));
                        supplyColProdPrice.setCellValueFactory(new PropertyValueFactory<Supply, Double>("productPrice"));
                        supplyColProdAmount.setCellValueFactory(new PropertyValueFactory<Supply, Integer>("productAmount"));
                        supplyColManufacturer.setCellValueFactory(new PropertyValueFactory<Supply, String>("manufacturerName"));
                        supplyColDate.setCellValueFactory(new PropertyValueFactory<Supply, Date>("date"));
                        supplyColTime.setCellValueFactory(new PropertyValueFactory<Supply, Time>("time"));
                        supplyColStatus.setCellValueFactory(new PropertyValueFactory<Supply, String>("status"));

                        suppliesTableView.setItems(supplies);
                    } else {
                        searchSuppliesLabel.setText("данные не найдены");
                        searchSuppliesLabel.setVisible(true);
                    }
                } else {
                    searchSuppliesLabel.setText("некорректный ввод");
                    searchSuppliesLabel.setVisible(true);
                }
            }
        });
        
        viewLineChartButton.setOnAction(event -> {

            ObservableList<XYChart.Series<Integer,Integer>> seriesList = FXCollections.observableArrayList();

            xAxis.setLabel("число");
            yAxis.setLabel("количество");
            ArrayList<Order> orders = getOrdersLast10Days();
            ArrayList<Supply> supplies = getSuppliesLastDays();

            //Preparing the data points for the line
            XYChart.Series<String, Number> series1 = new XYChart.Series<>();
            for (Order order : orders) {
               series1.getData().add(new XYChart.Data<>(order.getProd_name(), order.getAmount()));
            }
            XYChart.Series<String, Number> series2 = new XYChart.Series<>();
            for (Supply supply : supplies) {
                series2.getData().add(new XYChart.Data<>(supply.getProductName(), supply.getProductAmount()));
            }

            /*series.getData().add(new XYChart.Data<>("OnePlus X", 80));
            series.getData().add(new XYChart.Data<>("OnePlus One", 123));
            series.getData().add(new XYChart.Data<>("OnePlus 2", 110));*/
            //Setting the name to the line (series)
            series1.setName("Заказы");
            series2.setName("Поставки");
            //Setting the data to Line chart
            lineChart.setTitle("Операции на складе за последние 10 дней");
            lineChart.setAnimated(false);
            lineChart.getData().add(series1);
            lineChart.getData().add(series2);


        });

        showPieChartButton.setOnAction(event -> {

            ArrayList<Product> products = getInfoProducts();
            ObservableList<PieChart.Data> valueList = FXCollections.observableArrayList();

            for (Product product : products) {
                valueList.add(new PieChart.Data(product.getType(), product.getAmount()));
            }

            pieChart.setData(valueList);
            pieChart.setTitle("Продукция на складе по категориям");
        });

    }

    public void setUserData() {
        adminNameLabel.setText(user.getUserName());
        adminNameLabel.setVisible(true);
        adminRoleLabel.setText(user.getRole());
        adminRoleLabel.setVisible(true);
    }
}
