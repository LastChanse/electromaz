package com.example.electromaz;

import com.example.electromaz.Utils.Config;
import com.example.electromaz.Utils.DBUtils;
import com.example.electromaz.Utils.SceneUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.w3c.dom.events.Event;

public class LoginController {
    public boolean lock = false;

    @FXML
    private HBox hbox;

    @FXML
    private ImageView logo;

    @FXML
    private TextField loginField;

    @FXML
    private Button authBtn;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private ToggleButton toggleBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Label errorText;

    @FXML
    private Label errorMsgLogin;

    @FXML
    private Label errorMsgPassword;

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

    @FXML
    private void onTypedPas() {
        passwordTextField.setText(passwordField.getText());
    }

    @FXML
    private void onTypedTextPas() {
        passwordField.setText(passwordTextField.getText());
    }

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
        }
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    public void lock(boolean on) {
        lock = on;
        if (on) {
            lockAll(true);
            startTimer();
        }
    }

    private void startTimer() {
        int[] timeMin = {Config.timeLockAuthAfterSession}; //Чтобы внутри события был доступен, делаем в виде массива.
        int[] timeSec = {60};
        timeMin[0]--;
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1000), //1000 мс = 1 сек
                        ae -> {
                            --timeSec[0];

                            if (timeSec[0] < 10) {
                                errorText.setText("Время блокировки: " + timeMin[0] + ":0" + timeSec[0]);
                            } else {
                                errorText.setText("Время блокировки: " + timeMin[0] + ":" + timeSec[0]);
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

        timeline.setCycleCount(Config.timeLockAuthAfterSession * 60); // Ограничим число повторений
        timeline.play(); //Запускаем
    }

    private void lockAll(boolean lock) {
        loginField.setDisable(lock);
        passwordField.setDisable(lock);
        passwordTextField.setDisable(lock);
        toggleBtn.setDisable(lock);
        authBtn.setDisable(lock);
    }

    // Actions

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