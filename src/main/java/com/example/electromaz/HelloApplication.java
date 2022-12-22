package com.example.electromaz;

import com.example.electromaz.Utils.Config;
import com.example.electromaz.Utils.SceneUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Создание загрузчика окна авторизации
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        // Сохранение в котроллер авторизации ссылки на самого себя
        LoginController lc = fxmlLoader.getController();
        lc.getSelfController(lc);
        // Удаление стандартного меню окна
        SceneUtils su = new SceneUtils();
        su.deleteDefaultMenu(stage, scene);
        // Задание ограничений размеров, название и иконку она
        stage.setMaxHeight(600);
        stage.setMaxWidth(800);
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.getIcons().add(Config.appIcon);
        stage.setTitle(Config.appName);
        stage.setScene(scene);
        stage.show(); // Отобразить окно на экране
    }

    public static void main(String[] args) {
        launch();
    }
}