package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.ClientSocket;

import java.io.IOException;

public abstract class MainController {
    public static void display_page(String page, Button btn, String userName) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(MainController.class.getResource(page));
        try {
            Parent root = loader.load();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("file:src/img/icon.png"));
            /*if(loader.getController() instanceof ClientMenu) {
                ClientMenu clientMenu = loader.getController();
                clientMenu.setUserData(userName);
                ClientSocket.userName = userName;
            }*/

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void display_page(String page, Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(MainController.class.getResource(page));
        try {
            Parent root = loader.load();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("file:src/img/icon.png"));
            /*if(loader.getController() instanceof ClientMenu) {
                ClientMenu clientMenu = loader.getController();
                clientMenu.setUserData(userName);
                ClientSocket.userName = userName;
            }*/

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createNewStage(String path) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainController.class.getResource(path));

        try{
            loader.load();
        }catch (IOException e) {
            e.printStackTrace();
        }

        Parent main = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(main));
        stage.getIcons().add(new Image("file:src/img/icon.png"));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();

    }

    public static void createNewStage(String path, Button parentButton) {
        Stage primaryStage = (Stage) parentButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainController.class.getResource(path));

        try{
            loader.load();
        }catch (IOException e) {
            e.printStackTrace();
        }

        Parent main = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(main));
        stage.getIcons().add(new Image("file:src/img/icon.png"));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.showAndWait();
    }

}
