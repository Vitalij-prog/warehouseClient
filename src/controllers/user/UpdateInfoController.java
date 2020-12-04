package controllers.user;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.ClientSocket;
import sample.MainController;

import java.io.IOException;

import static sample.ClientSocket.setUser;
import static sample.MainController.display_page;

public class UpdateInfoController {

    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField currentPasswordTextField;

    @FXML
    private TextField oldPasswordTextField;
    @FXML
    private TextField newPasswordTextField;
    @FXML
    private TextField repeatPasswordTextField;

    @FXML
    private Button updateNameButton;
    @FXML
    private Button updatePasswordButton;

    @FXML
    private Label successUpdateNameLabel;
    @FXML
    private Label successUpdatePasswordLabel;
    @FXML
    private Label emptyFields1Label;
    @FXML
    private Label emptyFields2Label;

    @FXML
    private Label takenNameLabel;

    @FXML
    private Label wrongPasswordLabel;

    @FXML
    public void initialize() {

        updateNameButton.setOnAction(event -> {
            emptyFields1Label.setVisible(false);
            takenNameLabel.setVisible(false);

            String name = userNameTextField.getText();
            String password = currentPasswordTextField.getText();

            if(!name.equals("") && !password.equals("")) {
                if(password.equals(ClientSocket.user.getPassword())) {
                    User user = new User(ClientSocket.user.getId(),
                            name, password,
                            ClientSocket.user.getRole(),
                            ClientSocket.user.getStatus());
                    String answer = setUser(user, "updateName");
                    if(answer.equals("success")) {
                        successUpdateNameLabel.setVisible(true);
                        ClientSocket.user = user;

                       // display_page("../views/authorization.fxml", updateNameButton);
                    } else {
                        takenNameLabel.setVisible(true);
                    }
                } else {
                    emptyFields1Label.setText("неверный пароль");
                    emptyFields1Label.setVisible(true);
                }
            } else {
                emptyFields1Label.setText("не все поля заполнены");
                emptyFields1Label.setVisible(true);
            }
        });

        updatePasswordButton.setOnAction(event -> {
            emptyFields2Label.setVisible(false);
            wrongPasswordLabel.setVisible(false);

            String oldPassword = oldPasswordTextField.getText();
            String newPassword = newPasswordTextField.getText();
            String repeatPassword = repeatPasswordTextField.getText();

            if(!oldPassword.equals("") && !newPassword.equals("") && !repeatPassword.equals("")) {
                if(oldPassword.equals(ClientSocket.user.getPassword())) {
                    if(newPassword.equals(repeatPassword)) {
                        User user = new User(ClientSocket.user.getId(),
                                ClientSocket.user.getUserName(),
                                newPassword,
                                ClientSocket.user.getRole(),
                                ClientSocket.user.getStatus());
                        String answer = setUser(user, "updateName");
                        if(answer.equals("success")) {
                            successUpdatePasswordLabel.setVisible(true);
                            ClientSocket.user = user;
                        }
                    } else {
                        emptyFields2Label.setText("пароли не совпадают");
                        emptyFields2Label.setVisible(true);
                    }
                } else {
                    wrongPasswordLabel.setVisible(true);
                }
            } else {
                emptyFields2Label.setText("не все поля заполнены");
                emptyFields2Label.setVisible(true);
            }
        });
    }

}
