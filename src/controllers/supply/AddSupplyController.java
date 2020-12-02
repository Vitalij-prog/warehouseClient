package controllers.supply;

import entities.Manufacturer;
import entities.Supply;
import entities.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.ClientSocket;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

import static sample.ClientSocket.*;
import static sample.InputCheck.isDouble;
import static sample.InputCheck.isInteger;

public class AddSupplyController {
    @FXML
    private TextField prodNameTextField;
    @FXML
    private TextField prodAmountTextField;
    @FXML
    private TextField prodPriceTextField;
    @FXML
    private ChoiceBox<Manufacturer> manufacturersChoiceBox;
    @FXML
    private Button searchProdButton;
    @FXML
    private Button confirmSupplyButton;
    @FXML
    private Button cancelSupplyButton;

    @FXML
    private Label infoProdNameMessage;
    @FXML
    private Label infoProdAmountMessage;
    @FXML
    private Label infoProdPriceMessage;

    @FXML
    private Label successSupplyMessage;
    Product product;

    @FXML
    public void initialize() {
        restart();
        ArrayList<Manufacturer> list = getListManufacturers();
        manufacturersChoiceBox.getItems().setAll(list);
        //manufacturersChoiceBox.setValue(list.get(0));


        searchProdButton.setOnAction(event -> {
            infoProdNameMessage.setVisible(false);
            String name = prodNameTextField.getText();

            if (!name.equals("")) {
                Product product = null;
                product = getProductByName(name);
                if (product != null) {
                    this.product = product;
                    prodPriceTextField.setText(Double.toString(product.getPrice()));
                    Manufacturer manufacturer = getManufacturerById(product.getManufacturerId());
                    manufacturersChoiceBox.setValue(manufacturer);
                    prodNameTextField.setDisable(true);
                    searchProdButton.setDisable(true);
                } else {
                    manufacturersChoiceBox.setDisable(false);
                    prodNameTextField.setDisable(true);
                    searchProdButton.setDisable(true);
                    prodPriceTextField.setDisable(false);
                }
            } else {
                infoProdNameMessage.setText("поле не заполнено");
                infoProdNameMessage.setVisible(true);
            }
        });


        manufacturersChoiceBox.setOnAction(event -> {
            if (this.product == null) {
                prodPriceTextField.setDisable(false);
            }
            prodAmountTextField.setDisable(false);
            confirmSupplyButton.setDisable(false);
        });

            confirmSupplyButton.setOnAction(event -> {
            infoProdPriceMessage.setVisible(false);
            infoProdAmountMessage.setVisible(false);

            String price = prodPriceTextField.getText();
            String amount = prodAmountTextField.getText();
            if (!amount.equals("") && !price.equals("")) {
                if (isInteger(amount) && isDouble(price)) {
                    Date date = new Date(Calendar.getInstance().getTime().getTime());
                    Time time = new Time(Calendar.getInstance().getTime().getTime());
                    Supply supply = new Supply(user.getId(),
                            prodNameTextField.getText(),
                            Double.parseDouble(prodPriceTextField.getText()),
                            Integer.parseInt(prodAmountTextField.getText()),
                            manufacturersChoiceBox.getValue().getId(),
                            date, time);
                    String answer = addSupply(supply);
                    if (answer.equals("success")) {
                        successSupplyMessage.setVisible(true);
                    }
                } else {
                    if (!isDouble(price)) {
                        infoProdPriceMessage.setText("некорректный ввод");
                        infoProdPriceMessage.setVisible(true);
                    } else {
                        infoProdAmountMessage.setText("некорректный ввод");
                        infoProdAmountMessage.setVisible(true);
                    }
                }
            } else {
                if (price.equals("")) {
                    infoProdPriceMessage.setText("поле не заполнено");
                } else {
                    infoProdAmountMessage.setText("поле не заполнено");
                }
            }
        });


        cancelSupplyButton.setOnAction(event -> {
            restart();
        });
    }

    public void restart() {

        prodAmountTextField.setText("");
        prodAmountTextField.setDisable(true);

        prodPriceTextField.setText("");
        prodPriceTextField.setDisable(true);

        prodNameTextField.setText("");
        prodNameTextField.setDisable(false);

        searchProdButton.setDisable(false);

        confirmSupplyButton.setDisable(true);
        successSupplyMessage.setVisible(false);

        manufacturersChoiceBox.setDisable(true);

    }

    private int searchManufacturer(int id) {
        ArrayList<Manufacturer> list = getListManufacturers();
        int man_id = 0;
        for (Manufacturer man: list) {
            if(man.getId() == id) {
                break;
            }
            man_id++;
        }
        return  man_id;
    }

}


