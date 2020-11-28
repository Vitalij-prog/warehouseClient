package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static sample.ClientSocket.del;
import static sample.InputCheck.isInteger;

public class DelProductController {
    @FXML
    private TextField id;
    @FXML
    private Button delButton;
    @FXML
    private Label delMessage;
    @FXML
    private Label notFoundMessage;

    @FXML
    public void initialize() {
        delButton.setOnAction(event -> {
            notFoundMessage.setVisible(false);
            delMessage.setVisible(false);

            String id = this.id.getText();
            if(!id.equals("")) {
                if(isInteger(id)) {
                    String answer = del(Integer.parseInt(id), "product");
                    if(answer.equals("success")) {
                        delMessage.setVisible(true);
                    } else {
                        notFoundMessage.setText("товар не найден");
                        notFoundMessage.setVisible(true);
                    }
                } else {
                    notFoundMessage.setText("некорректный ввод");
                    notFoundMessage.setVisible(true);
                }
            } else {
                notFoundMessage.setText("поле не заполнено");
                notFoundMessage.setVisible(true);
            }
        });
    }

}
