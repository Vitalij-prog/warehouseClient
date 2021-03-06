package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    ClientSocket client;
    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/authorization.fxml"));
        Parent root = loader.load();
        window.getIcons().add(new Image("file:src/img/icon.png"));
        window.setTitle("Авторизация в системе");
        window.setScene(new Scene(root, 600, 400));
        window.show();

        client = new ClientSocket("127.0.0.1", 8000);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        client.exit();
        client.close();
        super.stop();
    }
}
