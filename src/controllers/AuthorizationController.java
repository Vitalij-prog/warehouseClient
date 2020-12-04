package controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.ClientSocket;
import sample.MainController;

import java.io.IOException;

import static sample.ClientSocket.authorization;

public class AuthorizationController {
    @FXML
    private Button logInButton;

    @FXML
    private Button signUpButton;

    /*@FXML
    private Button signUpButton;*/

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Label labelWrong;

    @FXML
    public void logIn(ActionEvent event) {

        String loginText = loginField.getText();
        String passwordText = passwordField.getText();

        if(!loginText.equals("") && !passwordText.equals(""))
            loginUser(loginText,passwordText);
        else {
            labelWrong.setText("         не все поля заполнены");
            labelWrong.setVisible(true);
        }


    }

    private void loginUser(String loginText, String passText) {

        labelWrong.setVisible(false);
        User user = null;
        try{
            user = authorization(loginText, passText);
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(user != null) {
            if(!user.getStatus().equals("block")) {
                System.out.println("Ответ :" + user.getRole());
                ClientSocket.user = user;
                switch (user.getRole()) {
                    case "client":
                        ClientSocket.userName = loginText;
                        MainController.display_page("../views/client.fxml", logInButton, loginText);
                        break;
                    case "provider":
                        MainController.display_page("../views/provider.fxml", logInButton, loginText);
                        break;
                    case "admin":
                        ClientSocket.userName = loginText;
                        MainController.display_page("../views/admin.fxml", logInButton, loginText);
                        break;
                }
            } else {
                labelWrong.setText("вам временно ограничен вход в систему");
                labelWrong.setVisible(true);
            }
        } else {
            labelWrong.setText("неверное имя пользователя или пароль");
            labelWrong.setVisible(true);
        }

    }

    @FXML
    public void signUp(ActionEvent event) {

        signUpButton.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/registration.fxml"));

        try{
            loader.load();
        }catch (IOException e) {
            e.printStackTrace();
        }

        Parent main = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(main));
        stage.showAndWait();

    }
}
