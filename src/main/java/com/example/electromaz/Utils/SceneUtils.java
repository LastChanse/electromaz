package com.example.electromaz.Utils;

import com.example.electromaz.LoginController;
import com.example.electromaz.MainController;
import com.example.electromaz.Models.User;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class SceneUtils {

    double xOffset = 0;
    double yOffset = 0;

    public void deleteDefaultMenu(Stage stage, Scene scene) {

        stage.initStyle(StageStyle.DECORATED.UNDECORATED);

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            }
        });

        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() - xOffset);
                stage.setY(mouseEvent.getScreenY() - yOffset);
            }
        });
    }

    public void changeScene(Scene scene, String fxmlFile, User user) {
        Parent root = new Parent() {};
        int width = 800;
        int height = 600;

        if (user != null) {
            width = 1280;
            try {
                FXMLLoader loader = new FXMLLoader(new URL(Config.resourcesPath+fxmlFile));
                root = loader.load();
                MainController mc = loader.getController();
                mc.setUser(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(new URL(Config.resourcesPath+"login-view.fxml"));
                root = loader.load();
                LoginController lc = loader.getController();
                lc.lock(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) scene.getWindow();
        scene = new Scene(root, width, height);
        scene.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });

        scene.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
        });
        stage.setMaxHeight(height);
        stage.setMaxWidth(width);
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.getIcons().add(Config.appIcon);
        stage.setTitle(Config.appName);
        stage.setScene(scene);
        stage.show();
    }
}
