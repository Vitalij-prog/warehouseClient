package controllers.user;

import entities.Product;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.ClientSocket;

import static sample.ClientSocket.*;
import static sample.InputCheck.isInteger;

public class AccessControlController {
    @FXML
    private TextField idTextField;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private Button searchButton;
    @FXML
    private Button updateButton;
    @FXML
    private Label infoMessage;
    @FXML
    private Label currentName;
    @FXML
    private Label currentStatus;
    @FXML
    private Label errorMessage;
    @FXML
    private Label successMessage;

    @FXML
    public void initialize() {

        ObservableList<String> userStatus = FXCollections.observableArrayList("active", "block");
        statusChoiceBox.setItems(userStatus);
        statusChoiceBox.setValue("active");

        searchButton.setOnAction(event -> {
            setMessage(false);
            successMessage.setVisible(false);
            String id = idTextField.getText();
            if (!id.equals("")) {
                if (isInteger(id)) {
                    User user = getUserById(Integer.parseInt(id));
                    if (user != null) {
                        setTextFields(user);
                        blockTextFields(false);
                    } else {
                        infoMessage.setText("пользователь не найден");
                        infoMessage.setVisible(true);
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

        updateButton.setOnAction(event -> {
            setMessage(false);
            if(ClientSocket.user.getUserName().equals(currentName.getText())) {
                setMessage(false);
                errorMessage.setVisible(true);
                blockTextFields(true);
                idTextField.setText("");
            } else {
                User user = getUpdateUser();
                String answer = setUserStatusById(user);
                if (answer.equals("success")) {
                    setMessage(false);
                    successMessage.setVisible(true);
                    blockTextFields(true);
                    idTextField.setText("");
                }
            }

        });
    }

    private void setMessage(boolean option) {
        infoMessage.setVisible(option);
        errorMessage.setVisible(option);
        currentName.setVisible(option);
        currentStatus.setVisible(option);
    }

    private void blockTextFields(boolean option) {
        idTextField.setDisable(!option);
        searchButton.setDisable(!option);
        statusChoiceBox.setDisable(option);
        updateButton.setDisable(option);
    }

    private void setTextFields(User us) {
        currentName.setText(us.getUserName());
        currentName.setVisible(true);
        currentStatus.setText(us.getStatus());
        currentStatus.setVisible(true);
    }

    private User getUpdateUser(){
        return new User(Integer.parseInt(idTextField.getText()),
                currentName.getText(),
                "", "", statusChoiceBox.getValue());
    }

}
