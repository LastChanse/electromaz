package com.example.electromaz;

import com.example.electromaz.Utils.AlertUtils;
import com.example.electromaz.Utils.Config;
import com.example.electromaz.Utils.DBUtils;
import com.example.electromaz.Utils.SceneUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.w3c.dom.events.Event;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class LoginController {
    public boolean lock = false; // Переменная блокировки ввода
    public int countTryLogin = 0; // Количество попыток входа

    @FXML
    private ImageView logo; // Логотип

    @FXML
    private TextField loginField; // Поле ввода логина

    @FXML
    private Button authBtn; // Кнопка авторизации

    @FXML
    private PasswordField passwordField; // Поле ввода пароля (пароль скрыт)

    @FXML
    private TextField passwordTextField; // Поле ввода пароля (пароль открыт)

    @FXML
    private ToggleButton toggleBtn; // Переключатель видимости пароля

    @FXML
    private Button exitBtn; // Кнопка выхода из аккаунта

    @FXML
    private Label errorText; // Текст ошибки

    @FXML
    private Label errorMsgLogin; // Текст ошибки поля ввода логина

    @FXML
    private Label errorMsgPassword; // Текст ошибки поля ввода пароля

    @FXML
    private LoginController selfRoot; // Ссылка на самого себя

    /**
     * getSelfController -- функция получения ссылки контроллера авторизации на самого себя
     * @param selfRoot -- ссылка на контроллер авторизации
     */
    public void getSelfController(LoginController selfRoot) {
        this.selfRoot = selfRoot;
    }

    /**
     * initialize -- функция запускающаяся при запуске контроллера
     */
    @FXML
    public void initialize() {
        // Загружаем кантинки
        // Для логотипа
        logo.setImage(Config.appIcon);

        // Задаём стартовое изображение переключателя
        toggleBtn.setGraphic(Config.visible);

        // Скрываем ошибки полей ввода
        errorMsgLogin.setVisible(false);
        errorMsgPassword.setVisible(false);
    }

    /**
     * onTypedPas -- функция копирующая текст из поля ввода со скрытым паролем в поле ввода с открытым паролем.
     * Срабатывает при введении данных в поле со скрытым паролем
     */
    @FXML
    private void onTypedPas() {
        passwordTextField.setText(passwordField.getText());
    }

    /**
     * onTypedTextPas -- функция копирующая текст из поля ввода с открытым паролем в поле ввода со скрытым паролем.
     * Срабатывает при введении данных в поле с открытым паролем
     */
    @FXML
    private void onTypedTextPas() {
        passwordField.setText(passwordTextField.getText());
    }

    /**
     * toggleButtonShowOtHide -- функция переключания видимости поля ввода пароля и изображения переключателя видимости.
     * Срабатывает при нажатии на переключатель видимости.
     */
    @FXML
    private void toggleButtonShowOtHide() {
        if (toggleBtn.isSelected()) {
            toggleBtn.setGraphic(Config.invisible);
            passwordField.setVisible(false);
            passwordTextField.setVisible(true);
        } else {
            toggleBtn.setGraphic(Config.visible);
            passwordField.setVisible(true);
            passwordTextField.setVisible(false);
        }
    }

    /**
     * auth -- функция авторизующая пользователя.
     * Срабатывает по нажатию на кнопку авторизации
     */
    @FXML
    private void auth() {
        if (loginField.getText().isEmpty()) {
            errorMsgLogin.setVisible(true);
        } else {
            errorMsgLogin.setVisible(false);
        }
        if (passwordField.getText().isEmpty()) {
            errorMsgPassword.setVisible(true);
        } else {
            errorMsgPassword.setVisible(false);
        }

        if (!(loginField.getText().isEmpty() || passwordField.getText().isEmpty())) {
            DBUtils.logInUser(authBtn.getScene(), loginField.getText(), passwordField.getText());
        } else {
            countTryLogin++;
            if (countTryLogin >= Config.countTryLogin) {
                countTryLogin = 0;
                new SceneUtils().openCaptchaScene(selfRoot);
            }
        }
    }

    /**
     * exit -- функция завершения программы.
     * Срабатывает по нажатию на кнопку закрытия окна
     */
    @FXML
    private void exit() {
        System.exit(0);
    }

    /**
     * lock -- функция блокировки ввода
     * @param on -- если true то блокировать
     * @param timeSec -- время блокировки в секундах
     */
    @FXML
    public void lock(boolean on, int timeSec) {
        lock = on;
        if (on) {
            lockAll(true);
            startTimer(timeSec);
        }
    }

    /**
     * startTimer -- функция запуска таймера блокировки
     * @param timeSeconds -- время действия таймера в секундах
     */
    private void startTimer(int timeSeconds) {
        double[] timeMin = {0}; //Чтобы внутри события был доступен, делаем в виде массива.
        int[] timeSec = {timeSeconds};
        if (timeSeconds >= 60) {
            timeMin[0] = timeSeconds / 60;
            timeMin[0]--;
            timeSec[0] = timeSeconds - ((int) timeMin[0]) * 60;
        }

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1000), //1000 мс = 1 сек
                        ae -> {
                            --timeSec[0];

                            if (timeSec[0] < 10) {
                                errorText.setText("Время блокировки: " + (int) timeMin[0] + ":0" + timeSec[0]);
                            } else {
                                errorText.setText("Время блокировки: " + (int) timeMin[0] + ":" + timeSec[0]);
                            }

                            if ((timeMin[0] == 0) && (timeSec[0] == 0)) {
                                errorText.setText("");
                                lockAll(false);
                            }
                            if (timeSec[0] == 0) {
                                if (timeMin[0] >= 0) {
                                    timeSec[0] = 60;
                                    timeMin[0]--;
                                }
                            }
                        }
                )
        );

        timeline.setCycleCount((int) timeSeconds); // Ограничим число повторений
        timeline.play(); //Запускаем
    }

    /**
     * lockAll -- функция блокировки элементов
     * @param lock -- если true то блокировать
     */
    private void lockAll(boolean lock) {
        loginField.setDisable(lock);
        passwordField.setDisable(lock);
        passwordTextField.setDisable(lock);
        toggleBtn.setDisable(lock);
        authBtn.setDisable(lock);
    }

    /**
     * Анимации при выделении и наведении
     */
    @FXML
    private void activateLoginField() {
        loginField.setStyle("-fx-background-color: #00000000; -fx-border-color: #558eda; -fx-border-width: 0px 0px 2px 0px;");
        passwordField.setStyle("-fx-background-color: #00000000; -fx-border-color: #aaa; -fx-border-width: 0px 0px 2px 0px;");
        passwordTextField.setStyle("-fx-background-color: #00000000; -fx-border-color: #aaa; -fx-border-width: 0px 0px 2px 0px;");
        errorMsgLogin.setTextFill(Color.rgb(255, 0, 0, 1));
        errorMsgPassword.setTextFill(Color.rgb(255, 0, 0, 0.6));
    }


    @FXML
    private void activatePasswordField() {
        loginField.setStyle("-fx-background-color: #00000000; -fx-border-color: #aaa; -fx-border-width: 0px 0px 2px 0px;");
        passwordField.setStyle("-fx-background-color: #00000000; -fx-border-color: #558eda; -fx-border-width: 0px 0px 2px 0px;");
        passwordTextField.setStyle("-fx-background-color: #00000000; -fx-border-color: #558eda; -fx-border-width: 0px 0px 2px 0px;");
        errorMsgLogin.setTextFill(Color.rgb(255, 0, 0, 0.6));
        errorMsgPassword.setTextFill(Color.rgb(255, 0, 0, 1));
    }

    @FXML
    private void activatePasswordTextField() {
        loginField.setStyle("-fx-background-color: #00000000; -fx-border-color: #aaa; -fx-border-width: 0px 0px 2px 0px;");
        passwordField.setStyle("-fx-background-color: #00000000; -fx-border-color: #558eda; -fx-border-width: 0px 0px 2px 0px;");
        passwordTextField.setStyle("-fx-background-color: #00000000; -fx-border-color: #558eda; -fx-border-width: 0px 0px 2px 0px;");
        errorMsgLogin.setTextFill(Color.rgb(255, 0, 0, 0.6));
        errorMsgPassword.setTextFill(Color.rgb(255, 0, 0, 1));
    }

    @FXML
    private void selectedAuth() {
        authBtn.setStyle("-fx-background-color: #457eca; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 10, 0, 3, 5);");
    }

    @FXML
    private void unselectedAuth() {
        authBtn.setStyle("-fx-background-color: #4070d8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 10, 0, 3, 5);");
    }

    @FXML
    private void pressedAuth() {
        authBtn.setStyle("-fx-background-color: #3c7acd; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 3, 5);");
    }

    @FXML
    private void releasedAuth() {
        authBtn.setStyle("-fx-background-color: #457eca; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 10, 0, 3, 5);");
    }

    @FXML
    private void selectedToggleBtn() {
        toggleBtn.setStyle("-fx-background-color: #aaaa;");
    }

    @FXML
    private void unselectedToggleBtn() {
        toggleBtn.setStyle("-fx-background-color: #0000;");
    }

    @FXML
    private void selectedExitBtn() {
        exitBtn.setStyle("-fx-background-color: #aaaa;");
    }

    @FXML
    private void unselectedExitBtn() {
        exitBtn.setStyle("-fx-background-color: #0000;");
    }
}