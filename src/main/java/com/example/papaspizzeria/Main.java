package com.example.papaspizzeria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application{
    private static Stage primaryStage;

   @Override
   public void start(Stage stage) throws Exception{
        primaryStage = stage;
        Parent MainMenuRoot = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        Scene scene = new Scene(MainMenuRoot, 500, 400);

        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setRoot(String fxml) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource(fxml));
        primaryStage.getScene().setRoot(root);
    }
}
