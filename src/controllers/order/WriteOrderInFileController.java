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
    int i = 0;

    @FXML
    public void initialize() {

        orderIdLabel.setText(Integer.toString(ClientSocket.order.getId()));
        orderIdLabel.setVisible(true);

        writeOrderButton.setOnAction(event -> {
            try {
                if(i == 0) {
                    //OutputStreamWriter outWriter = new OutputStreamWriter(new FileOutputStream(new File("./src/files/orders.txt")), StandardCharsets.UTF_8);
                    //FileOutputStream f = new FileOutputStream(new File("./src/files/orders.txt"),);
                    //ObjectOutputStream o = new ObjectOutputStream(outWriter);
                    //BufferedWriter bw = new BufferedWriter(outWriter);

                    //bw.append(String.valueOf(ClientSocket.order.getId())).append("/").append(ClientSocket.order.getUser_name()).append("/").append(ClientSocket.order.getProd_name()).append("/").append(String.valueOf(ClientSocket.order.getAmount())).append("/").append(String.valueOf(ClientSocket.order.getPrice())).append("/").append(String.valueOf(ClientSocket.order.getDate())).append("\r\n");
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
                    //outWriter.close();
                    //bw.close();
                }
            } catch(IOException e) {
                System.out.println(e);
            }
        });
    }

}
