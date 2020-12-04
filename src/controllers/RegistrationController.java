package controllers;

import entities.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import static sample.ClientSocket.registrationByUser;
import static sample.MainController.*;

public class RegistrationController {
    @FXML
    private AnchorPane registrationPane;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordField1;
    @FXML
    private PasswordField passwordField2;
    @FXML
    private RadioButton clientRadioButton;
    @FXML
    private RadioButton providerRadioButton;

    @FXML
    private RadioButton selectedButton;

    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;
    @FXML
    private Label emptyFieldWarning;
    @FXML
    private Label notEqualPasswordsWarning;
    @FXML
    private Label userNameExistWarning;

    @FXML
    public void initialize() {

        ToggleGroup toggleGroup = new ToggleGroup();
        clientRadioButton.setToggleGroup(toggleGroup);
        clientRadioButton.setSelected(true);
        providerRadioButton.setToggleGroup(toggleGroup);

        toggleGroup.selectedToggleProperty().addListener((changed, oldValue, newValue) -> {

            selectedButton = (RadioButton) newValue;

        });


        registerButton.setOnAction(event -> {

            emptyFieldWarning.setVisible(false);
            notEqualPasswordsWarning.setVisible(false);
            userNameExistWarning.setVisible(false);

            String userName = userNameTextField.getText();
            String password1 = passwordField1.getText();
            String password2 = passwordField2.getText();
            String role = "";

            if(!userName.equals("") && !password1.equals("") && !password2.equals("")) {
                if(password1.equals(password2)) {
                    if(selectedButton.getText().equals("клиент")) {
                        role = "client";
                    } else {
                        role = "provider";
                    }
                    User user = new User(userName, password1, role);
                    String answer = registrationByUser(user);
                    if(answer.equals("")) {
                        userNameExistWarning.setVisible(true);
                    } else if(answer.equals("client")) {
                        display_page("../views/authorization.fxml", registerButton);
                    } else if(answer.equals("provider")) {
                        display_page("../views/authorization.fxml", registerButton);
                    }
                } else {
                    notEqualPasswordsWarning.setVisible(true);
                }
            } else {
                emptyFieldWarning.setVisible(true);
            }
        });

        loginButton.setOnAction(event -> {
            display_page("../views/authorization.fxml", loginButton);
        });
    }



}
