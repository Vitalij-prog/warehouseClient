package controllers;

import entities.Manufacturer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddProductController {
    @FXML
    private TextField productName;
    @FXML
    private TextField productAmount;
    @FXML
    private TextField productPrice;
    @FXML
    private Button addButton;
    @FXML
    private ChoiceBox<Manufacturer> manufacturersChoiceBox;
    @FXML
    private Label incorrectInputLabel;
    @FXML
    private Label completeMessage;
    @FXML
    private Label productTypeLabel;



}
