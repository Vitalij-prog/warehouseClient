package controllers.user;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.ClientSocket;

import static sample.ClientSocket.del;
import static sample.InputCheck.isInteger;

public class DelUserController {
    @FXML
    private TextField idTextField;
    @FXML
    private Button delButton;
    @FXML
    private Label infoMessage;
    @FXML
    private Label errorMessage;
    @FXML
    private Label successMessage;

    @FXML
    public void initialize() {
        delButton.setOnAction(event -> {
            infoMessage.setVisible(false);
            errorMessage.setVisible(false);
            successMessage.setVisible(false);

            String id = idTextField.getText();
            if(!id.equals("")) {
                if(isInteger(id)) {
                    if(Integer.parseInt(id) == ClientSocket.user.getId()) {
                        errorMessage.setVisible(true);
                    } else {
                        String answer = del(Integer.parseInt(id), "user");
                        if(answer.equals("success")) {
                            successMessage.setVisible(true);
                        } else {
                            infoMessage.setText("пользователь не найден");
                            infoMessage.setVisible(true);
                        }
                    }
                } else {
                    infoMessage.setText("некорректный ввод");
                    infoMessage.setVisible(true);
                }
            } else {
                infoMessage.setText("поле не заполнено");
                infoMessage.setVisible(true);
            }
        });
    }

}
