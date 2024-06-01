package org.apkmem.aplikasimembership;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apkmem.aplikasimembership.util.SessionManager;

import java.io.IOException;

public class GUI extends Application{
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        if (SessionManager.getInstance().isLoggedIn()){
            primaryStage.setTitle("Menu Utama");
            primaryStage.setScene(new Scene(loadFXML("menu-utama")));
        }else {
            primaryStage.setTitle("Halaman Login");
            primaryStage.setScene(new Scene(loadFXML("halaman-signin")));
        }
        primaryStage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class
                .getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void setRoot(String fxml, String title, boolean isResizeable)
            throws IOException {
        primaryStage.getScene().setRoot(loadFXML(fxml));
        primaryStage.sizeToScene();
        primaryStage.setResizable(isResizeable);
        if(title !=null){
            primaryStage.setTitle(title);
        }
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
