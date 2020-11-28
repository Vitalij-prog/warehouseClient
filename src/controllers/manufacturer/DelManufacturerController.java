package controllers.manufacturer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static sample.ClientSocket.del;
import static sample.InputCheck.isInteger;

public class DelManufacturerController {
    @FXML
    private TextField idTextField;
    @FXML
    private Label warningLabel;
    @FXML
    private Label successMessage;
    @FXML
    private Button delButton;

    @FXML
    public void initialize() {

        delButton.setOnAction(event -> {
            warningLabel.setVisible(false);
            successMessage.setVisible(false);
            String id = idTextField.getText();
            if(!id.equals("")) {
                if(isInteger(id)) {
                    String answer = del(Integer.parseInt(id), "manufacturer");
                    if(answer.equals("success")) {
                        successMessage.setVisible(true);
                    } else {
                        warningLabel.setText("данные не найдены");
                        warningLabel.setVisible(true);
                    }
                } else {
                    warningLabel.setText("некорректный ввод");
                    warningLabel.setVisible(true);
                }
            } else {
                warningLabel.setText("поле не заполнено");
                warningLabel.setVisible(true);
            }
        });
    }

}
