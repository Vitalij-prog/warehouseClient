package controllers.order;

import entities.Order;
import entities.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.ClientSocket;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import static sample.ClientSocket.*;
import static sample.InputCheck.isInteger;

public class AddOrderController {

    @FXML
    private TextField prodNameTextField;
    @FXML
    private TextField prodAmountTextField;

    @FXML
    private Button searchProdButton;
    @FXML
    private Button chooseAmountButton;
    @FXML
    private Button confirmOrderButton;
    @FXML
    private Button cancelOrderButton;

    @FXML
    private Label infoProdMessage;
    @FXML
    private Label infoAmountMessage;
    @FXML
    private Label infoProdPrice;
    @FXML
    private Label infoProdAmount;
    @FXML
    private Label infoOrderPrice;
    @FXML
    private Label successOrderMessage;
    Product product;
    @FXML
    public void initialize() {
       setDisable(true);
       setVisible(false);

       searchProdButton.setOnAction(event -> {
           infoProdMessage.setVisible(false);
           String name = prodNameTextField.getText();

           if(!name.equals("")) {
               Product product = null;
               product = getProductByName(name);
               if(product != null) {
                   this.product = product;
                   infoProdPrice.setText(Double.toString(product.getPrice()));
                   infoProdAmount.setText(Integer.toString(product.getAmount()));
                   infoProdPrice.setVisible(true);
                   infoProdAmount.setVisible(true);
                   prodAmountTextField.setDisable(false);
                   chooseAmountButton.setDisable(false);
                   prodNameTextField.setDisable(true);
                   searchProdButton.setDisable(true);
               } else {
                   infoProdMessage.setText("товар не найден");
                   infoProdMessage.setVisible(true);
               }

           } else {
               infoProdMessage.setText("поле не заполнено");
               infoProdMessage.setVisible(true);
           }
       });

       chooseAmountButton.setOnAction(event -> {
           infoAmountMessage.setVisible(false);
           String amount = prodAmountTextField.getText();
           if(!amount.equals("")) {
               if(isInteger(amount)) {
                   if(Integer.parseInt(amount) <= Integer.parseInt(infoProdAmount.getText())) {
                       infoOrderPrice.setText(Double.toString(countOrderPrice()));
                       infoOrderPrice.setVisible(true);
                       prodAmountTextField.setDisable(true);
                       chooseAmountButton.setDisable(true);
                       confirmOrderButton.setDisable(false);
                   } else {
                       infoAmountMessage.setText("недостатачно на складе");
                       infoAmountMessage.setVisible(true);
                   }
               } else {
                   infoAmountMessage.setText("некорректный ввод");
                   infoAmountMessage.setVisible(true);
               }
           } else {
               infoAmountMessage.setText("поле не заполнено");
               infoAmountMessage.setVisible(true);
           }
       });

       confirmOrderButton.setOnAction(event -> {
           Date date = new Date(Calendar.getInstance().getTime().getTime());
           Time time = new Time(Calendar.getInstance().getTime().getTime());
           int user_id = ClientSocket.user.getId();
           int prod_id = this.product.getId();
           int amount = Integer.parseInt(prodAmountTextField.getText());
           double order_price = Double.parseDouble(infoOrderPrice.getText());
           Order order = new Order(user_id, prod_id, amount, order_price, date, time);
           String answer = addOrder(order);
           if(answer.equals("success")) {
               restart();
               successOrderMessage.setVisible(true);
           }
       });

       cancelOrderButton.setOnAction(event -> {
           restart();
       });
    }

    public void restart() {
        infoOrderPrice.setVisible(false);
        infoAmountMessage.setVisible(false);
        infoProdAmount.setVisible(false);
        infoProdPrice.setVisible(false);
        infoProdMessage.setVisible(false);

        prodAmountTextField.setText("");
        prodAmountTextField.setDisable(true);

        prodNameTextField.setText("");
        prodNameTextField.setDisable(false);

        searchProdButton.setDisable(false);
        chooseAmountButton.setDisable(true);
        confirmOrderButton.setDisable(true);


    }

    public Double countOrderPrice() {
        return Integer.parseInt(prodAmountTextField.getText()) * Double.parseDouble(infoProdPrice.getText());
    }

    public void setDisable(boolean option) {
        prodAmountTextField.setDisable(option);
        chooseAmountButton.setDisable(option);
        confirmOrderButton.setDisable(option);
    }

    public void setVisible(boolean option) {
        infoProdPrice.setVisible(option);
        infoProdAmount.setVisible(option);
        infoOrderPrice.setVisible(option);
    }

}
