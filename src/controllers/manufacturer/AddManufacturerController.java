package controllers.manufacturer;

import entities.Manufacturer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static sample.ClientSocket.addManufacturer;
import static sample.InputCheck.LENGTH_PRODUCT_NAME;
import static sample.InputCheck.isCorrectString;

public class AddManufacturerController {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField prodTypeTextField;
    @FXML
    private Button addButton;
    @FXML
    private Label takenNameLabel;
    @FXML
    private Label emptyFieldLabel;
    @FXML
    private Label addSuccessLabel;

    @FXML
    public void initialize() {
        addButton.setOnAction(event -> {
            takenNameLabel.setVisible(false);
            emptyFieldLabel.setVisible(false);
            addSuccessLabel.setVisible(false);

            String name = nameTextField.getText();
            String prodType = prodTypeTextField.getText();
            if(!name.equals("") && !prodType.equals("")) {
                if(isCorrectString(name, LENGTH_PRODUCT_NAME) && isCorrectString(prodType, LENGTH_PRODUCT_NAME)) {
                    Manufacturer manufacturer = new Manufacturer(name, prodType);
                    String answer = addManufacturer(manufacturer);
                    if(answer.equals("success")) {
                        addSuccessLabel.setText("данные успешно добавлены");
                        addSuccessLabel.setVisible(true);
                    } else {
                        takenNameLabel.setVisible(true);
                    }
                } else {
                    emptyFieldLabel.setText("некрректные данные");
                    emptyFieldLabel.setVisible(true);
                }
            } else {
                emptyFieldLabel.setText("не все поля заполнены");
                emptyFieldLabel.setVisible(true);
            }
        });
    }

}
