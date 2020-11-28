package controllers.manufacturer;

import entities.Manufacturer;
import entities.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static sample.ClientSocket.*;
import static sample.InputCheck.*;
import static sample.InputCheck.isDouble;

public class EditManufacturerController {
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField prodTypeTextField;
    @FXML
    private Label notFoundLabel;
    @FXML
    private Label incorrectNameLabel;
    @FXML
    private Label incorrectProdTypeLabel;
    @FXML
    private Label successMessage;
    @FXML
    private Button findButton;
    @FXML
    private Button updateButton;


    @FXML
    public void initialize() {

       findButton.setOnAction(event -> {
        setMessage(false);

        String id = this.idTextField.getText();
            if(!id.equals("")) {
                if(isInteger(id)) {
                    Manufacturer manufacturer = getManufacturerById(Integer.parseInt(id));
                    if(manufacturer != null) {
                        setMessage(false);
                        blockTextFields(false);
                        setTextFields(manufacturer);
                    } else {
                        notFoundLabel.setText("данные не найдены");
                        notFoundLabel.setVisible(true);
                    }
                } else {
                    notFoundLabel.setText("некорректный ввод");
                    notFoundLabel.setVisible(true);
                }
            } else {
                notFoundLabel.setText("поле не заполнено");
                notFoundLabel.setVisible(true);
            }
        });

        updateButton.setOnAction(event -> {
            setMessage(false);
            if(dataEmptyCheck()) {
                if(dataCorrectCheck()) {
                    Manufacturer manufacturer = getUpdateManufacturer();
                    String answer = setManufacturerById(manufacturer);
                    if(answer.equals("success")) {
                        successMessage.setText("данные успешно обновлены");
                        successMessage.setVisible(true);
                    } else {
                        successMessage.setText("ошибка обновления");
                        successMessage.setVisible(true);
                    }
                }
            }
        });
    }

    private void setMessage(boolean option) {
        notFoundLabel.setVisible(option);
        incorrectNameLabel.setVisible(option);
        incorrectProdTypeLabel.setVisible(option);
        successMessage.setVisible(option);
    }

    private void blockTextFields(boolean option) {
        idTextField.setDisable(!option);
        findButton.setDisable(!option);
        nameTextField.setDisable(option);
        prodTypeTextField.setDisable(option);
        updateButton.setDisable(option);
    }

    private void setTextFields(Manufacturer mr) {
        nameTextField.setText(mr.getName());
        prodTypeTextField.setText(mr.getProductsType());

    }

    private Manufacturer getUpdateManufacturer(){
        return new Manufacturer(Integer.parseInt(idTextField.getText()),
                nameTextField.getText(),
                prodTypeTextField.getText());
    }

    private boolean dataCorrectCheck() {
        if(!isCorrectString(nameTextField.getText(), LENGTH_PRODUCT_NAME)) {
            incorrectNameLabel.setText("некорректные ввод");
            incorrectNameLabel.setVisible(true);
            return false;
        }
        if(!isCorrectString(prodTypeTextField.getText(), LENGTH_PRODUCT_NAME)) {
            incorrectProdTypeLabel.setText("некорректные ввод");
            incorrectProdTypeLabel.setVisible(true);
            return false;
        }
        return true;
    }

    private boolean dataEmptyCheck() {
        if(nameTextField.getText().equals("")) {
            incorrectNameLabel.setText("поле не заполнено");
            incorrectNameLabel.setVisible(true);
            return false;
        }
        if(prodTypeTextField.getText().equals("")) {
            incorrectProdTypeLabel.setText("поле не заполнено");
            incorrectProdTypeLabel.setVisible(true);
            return false;
        }
        return true;
    }


}
