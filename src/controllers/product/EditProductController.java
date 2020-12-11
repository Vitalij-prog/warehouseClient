package controllers.product;

import entities.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static sample.ClientSocket.*;
import static sample.InputCheck.*;

public class EditProductController {
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField amount;
    @FXML
    private TextField price;
    @FXML
    private Button findButton;
    @FXML
    private Button updateButton;
    @FXML
    private Label updateMessage;
    @FXML
    private Label findMessage;
    @FXML
    private Label incorrectNameMessage;
    @FXML
    private Label incorrectAmountMessage;
    @FXML
    private Label incorrectPriceMessage;

    @FXML
    public void initialize() {
        findButton.setOnAction(event -> {
            setMessage(false);

            String id = this.id.getText();
            if(!id.equals("")) {
                if(isInteger(id)) {
                    Product product = getProductById(Integer.parseInt(id));
                    if(product != null) {
                        setMessage(false);
                        blockTextFields(false);
                        setextFields(product);
                    } else {
                        findMessage.setText("товар не найден");
                        findMessage.setVisible(true);
                    }
                } else {
                    findMessage.setText("некорректный ввод");
                    findMessage.setVisible(true);
                }
            } else {
                findMessage.setText("поле не заполнено");
                findMessage.setVisible(true);
            }
        });

        updateButton.setOnAction(event -> {
            setMessage(false);
            if(dataEmptyCheck()) {
                if(dataCorrectCheck()) {
                    Product product = getUpdateProduct();
                    String answer = setProductById(product);
                    if(answer.equals("success")) {
                        updateMessage.setText("данные успешно обновлены");
                        updateMessage.setVisible(true);
                    } else {
                        updateMessage.setText("ошибка обновления");
                        updateMessage.setVisible(true);
                    }
                }
            }
        });
    }

    private void setMessage(boolean option) {
        findMessage.setVisible(option);
        incorrectNameMessage.setVisible(option);
        incorrectAmountMessage.setVisible(option);
        incorrectPriceMessage.setVisible(option);
    }

    private void blockTextFields(boolean option) {
        name.setDisable(option);
        amount.setDisable(option);
        price.setDisable(option);
        id.setDisable(!option);
        updateButton.setDisable(option);
    }

    private void setextFields(Product pr) {
        name.setText(pr.getName());
        amount.setText(Integer.toString(pr.getAmount()));
        price.setText(Double.toString(pr.getPrice()));
    }

    private Product getUpdateProduct(){
        return new Product(Integer.parseInt(id.getText()),
                name.getText(),
                Integer.parseInt(amount.getText()),
                Double.parseDouble(price.getText()),
                0);
    }

    private boolean dataCorrectCheck() {
        if(!isCorrectString(name.getText(), LENGTH_PRODUCT_NAME)) {
            incorrectNameMessage.setText("некорректные данные");
            incorrectNameMessage.setVisible(true);
            return false;
        }
        if(!isInteger(amount.getText())) {
            incorrectAmountMessage.setText("некорректные данные");
            incorrectAmountMessage.setVisible(true);
            return false;
        }
        if(!isDouble(price.getText())) {
            incorrectPriceMessage.setText("некорректные данные");
            incorrectPriceMessage.setVisible(true);
            return false;
        }
        return true;
    }

    private boolean dataEmptyCheck() {
        if(name.getText().equals("")) {
            incorrectNameMessage.setText("поле не заполнено");
            incorrectNameMessage.setVisible(true);
            return false;
        }
        if(amount.getText().equals("")) {
            incorrectAmountMessage.setText("поле не заполнено");
            incorrectAmountMessage.setVisible(true);
            return false;
        }
        if(price.getText().equals("")) {
            incorrectPriceMessage.setText("поле не заполнено");
            incorrectPriceMessage.setVisible(true);
            return false;
        }
        return true;
    }
}
