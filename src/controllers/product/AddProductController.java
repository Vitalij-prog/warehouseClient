package controllers.product;

import entities.Manufacturer;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

import static sample.ClientSocket.addProduct;
import static sample.InputCheck.*;

import static sample.ClientSocket.getListManufacturers;


public class AddProductController {
    @FXML
    private TextField productName;
    @FXML
    private TextField productAmount;
    @FXML
    private TextField productPrice;
    @FXML
    private Button addButton;
    @FXML
    private ChoiceBox<Manufacturer> manufacturersChoiceBox;
    @FXML
    private Label incorrectInputLabel;
    @FXML
    private Label completeMessage;
    @FXML
    private Label productTypeLabel;

    @FXML
    public void initialize() {
        ArrayList<Manufacturer> list = getListManufacturers();

        //ObservableList<String> orderOptions = (ObservableList<String>) FXCollections.observableArrayList(list);
        manufacturersChoiceBox.getItems().setAll(list);
        manufacturersChoiceBox.setValue(list.get(0));
        //productTypeLabel.setText(manufacturersChoiceBox.getValue().getProductsType());

        manufacturersChoiceBox.setOnAction(event -> {
            Manufacturer man = manufacturersChoiceBox.getValue();
            productTypeLabel.setText(man.getProductsType());
        });

        addButton.setOnAction(event->{
            incorrectInputLabel.setVisible(false);
            completeMessage.setVisible(false);

            String name = productName.getText();
            String amount = productAmount.getText();
            String price = productPrice.getText();
            int manufactureId = manufacturersChoiceBox.getValue().getId();
            if(!name.equals("") && !amount.equals("") && !price.equals("")) {
                if(isCorrectString(name,LENGTH_PRODUCT_NAME) && isInteger(amount) && isDouble(price)) {

                    Product product = new Product(0, name, Integer.parseInt(amount), Double.parseDouble(price), manufactureId);
                    String answer = addProduct(product);
                    if (answer.equals("success")) {
                        completeMessage.setText("Товар успешно добавлен");
                        completeMessage.setVisible(true);
                    } else {
                        completeMessage.setText("Ошибка добавления");
                        completeMessage.setVisible(true);
                    }

                } else {
                    incorrectInputLabel.setText("некорректный ввод");
                    incorrectInputLabel.setVisible(true);
                }

            } else {
                incorrectInputLabel.setText("не все поля заполнены");
                incorrectInputLabel.setVisible(true);
            }
        });

    }



}
