package com.example.electromaz.Utils;

import com.example.electromaz.CaptchaController;
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
import java.util.concurrent.atomic.AtomicReference;

public class SceneUtils {

    // Переменные для хранения координат точки где было произведено нажатие мыши
    double xOffset = 0;
    double yOffset = 0;

    /**
     * Удаляет рамки и меню окна по умолчанию
     * @param stage -- сцена в виде объекта Stage
     * @param scene -- сцена в виде объекта Scene
     */
    public void deleteDefaultMenu(Stage stage, Scene scene) {

        stage.initStyle(StageStyle.DECORATED.UNDECORATED);

        // При нажатии на окно, координаты, точки где было произведено нажатие мыши, сохраняются в переменные
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            }
        });
        // При движении мыши с зажатой левой кнопкой мыши на окне, выполняется изменение положения окна путём задания
        // в качестве координат окна разность текущих координат окна относительно координат мыши
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (yOffset < Config.draggedYZone) {
                    stage.setX(mouseEvent.getScreenX() - xOffset);
                    stage.setY(mouseEvent.getScreenY() - yOffset);
                }
            }
        });
    }

    /**
     * openCaptchaScene -- открывает новое окно капчи и передаётся контроллер логина
     * @param loginController -- контроллер логина
     */
    public void openCaptchaScene(LoginController loginController) {
        Parent root;
        try {
            AtomicReference<Double> xOffset = new AtomicReference<>((double) 101);
            AtomicReference<Double> yOffset = new AtomicReference<>((double) 101);
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("captcha-view.fxml"));
            root = loader.load();
            CaptchaController cc = loader.getController();
            cc.getParentController(loginController);

            Stage stage = new Stage();

            stage.initStyle(StageStyle.DECORATED.UNDECORATED);

            root.setOnMouseMoved(mouseEvent -> {
                xOffset.set(mouseEvent.getSceneX());
                yOffset.set(mouseEvent.getSceneY());
            });

            root.setOnMouseDragged(mouseEvent -> {
                if (yOffset.get() < Config.draggedYZone) {
                    stage.setX(mouseEvent.getScreenX() - xOffset.get());
                    stage.setY(mouseEvent.getScreenY() - yOffset.get());
                }
            });
            stage.setTitle("Капча");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * openWindowScene -- открывает новое окно в соответствии с переданным названием файла шалона
     * @param fxmlFile -- название файла шаблона
     */
    public void openWindowScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(new URL(Config.resourcesPath + fxmlFile));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.initStyle(StageStyle.DECORATED.UNDECORATED);

            scene.setOnMousePressed(mouseEvent -> {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            });

            scene.setOnMouseDragged(mouseEvent -> {
                stage.setX(mouseEvent.getScreenX() - xOffset);
                stage.setY(mouseEvent.getScreenY() - yOffset);
            });
            stage.getIcons().add(Config.appIcon);
            stage.setTitle(Config.appName);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Меняет текущую сцену на сцену авторизации
     * @param scene -- текущая сцена
     * @param fxmlFile -- название файла шаблона сцены авторизации
     */
    public void changeSceneToLogin(Scene scene, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(new URL(Config.resourcesPath + fxmlFile));
            Parent root = loader.load();
            LoginController lc = loader.getController();
            lc.getSelfController(lc);
            lc.lock(true, Config.timeLockAuthAfterSession);
            Stage stage = (Stage) scene.getWindow();
            scene = new Scene(root);
            scene.setOnMousePressed(mouseEvent -> {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            });

            scene.setOnMouseDragged(mouseEvent -> {
                stage.setX(mouseEvent.getScreenX() - xOffset);
                stage.setY(mouseEvent.getScreenY() - yOffset);
            });
            stage.getIcons().add(Config.appIcon);
            stage.setTitle(Config.appName);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Меняет текущую сцену на сцену главной страницы
     * @param scene -- текущая сцена
     * @param fxmlFile -- название файла шаблона сцены главной страницы
     * @param user -- данные пользователя
     */
    public void changeSceneToMain(Scene scene, String fxmlFile, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(new URL(Config.resourcesPath + fxmlFile));
            Parent root = loader.load();
            MainController mc = loader.getController();
            mc.setUser(user);
            Stage stage = (Stage) scene.getWindow();
            scene = new Scene(root);
            scene.setOnMousePressed(mouseEvent -> {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            });

            scene.setOnMouseDragged(mouseEvent -> {
                stage.setX(mouseEvent.getScreenX() - xOffset);
                stage.setY(mouseEvent.getScreenY() - yOffset);
            });
            stage.getIcons().add(Config.appIcon);
            stage.setTitle(Config.appName);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
