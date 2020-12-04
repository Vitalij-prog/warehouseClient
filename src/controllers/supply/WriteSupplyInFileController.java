package controllers.supply;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.ClientSocket;

import java.io.FileWriter;
import java.io.IOException;

public class WriteSupplyInFileController {
    @FXML
    private Label supplyIdLabel;
    @FXML
    private Button writeSupplyButton;
    @FXML
    private Label  successLabel;
    int i = 0;

    @FXML
    public void initialize() {

        supplyIdLabel.setText(Integer.toString(ClientSocket.supply.getId()));
        supplyIdLabel.setVisible(true);

        writeSupplyButton.setOnAction(event -> {
            try {
                if(i == 0) {
                    i++;
                    FileWriter fw = new FileWriter("./src/files/supplies.txt", true);
                    String str = ClientSocket.supply.getId() + "/" +
                            ClientSocket.supply.getProviderName() + "/" +
                            ClientSocket.supply.getProductName() + "/" +
                            ClientSocket.supply.getProductAmount() + "/" +
                            ClientSocket.supply.getProductPrice() + "/" +
                            ClientSocket.supply.getDate() + "\r\n";
                    fw.write(str);
                    fw.flush();
                    fw.close();
                    successLabel.setVisible(true);
                    writeSupplyButton.setDisable(true);
                }
            } catch(IOException e) {
                System.out.println(e);
            }
        });
    }
}
