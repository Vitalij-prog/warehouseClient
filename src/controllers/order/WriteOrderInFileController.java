package controllers.order;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.ClientSocket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class WriteOrderInFileController {
    @FXML
    private Label orderIdLabel;
    @FXML
    private Button writeOrderButton;
    int i = 0;

    @FXML
    public void initialize() {

        orderIdLabel.setText(Integer.toString(ClientSocket.order.getId()));
        orderIdLabel.setVisible(true);

        writeOrderButton.setOnAction(event -> {
            try {
                if(i == 0) {
                    FileOutputStream f = new FileOutputStream(new File("./src/files/orders.txt"));
                    ObjectOutputStream o = new ObjectOutputStream(f);
                    o.writeObject(ClientSocket.order.getId() + "/" +
                            ClientSocket.order.getUser_name() + "/" +
                            ClientSocket.order.getProd_name() + "/" +
                            ClientSocket.order.getAmount() + "/" +
                            ClientSocket.order.getPrice() + "/" +
                            ClientSocket.order.getDate() + "/n");
                    i++;
                    o.close();
                    f.close();
                }
            } catch(IOException e) {
                System.out.println(e);
            } finally {
            }
        });
    }

}
