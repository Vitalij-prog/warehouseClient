package controllers.order;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.ClientSocket;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class WriteOrderInFileController {
    @FXML
    private Label orderIdLabel;
    @FXML
    private Button writeOrderButton;
    @FXML
    private Label successLabel;
    int i = 0;

    @FXML
    public void initialize() {

        orderIdLabel.setText(Integer.toString(ClientSocket.order.getId()));
        orderIdLabel.setVisible(true);

        writeOrderButton.setOnAction(event -> {
            try {
                if(i == 0) {
                    i++;
                    FileWriter fw = new FileWriter("./src/files/orders.txt", true);
                    String str = ClientSocket.order.getId() + "/" +
                            ClientSocket.order.getUser_name() + "/" +
                            ClientSocket.order.getProd_name() + "/" +
                            ClientSocket.order.getAmount() + "/" +
                            ClientSocket.order.getPrice() + "/" +
                            ClientSocket.order.getDate() + "\r\n";
                    fw.write(str);
                    fw.flush();
                    fw.close();
                    successLabel.setVisible(true);
                    writeOrderButton.setDisable(true);
                }
            } catch(IOException e) {
                System.out.println(e);
            }
        });
    }

}
