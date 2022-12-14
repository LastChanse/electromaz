package com.example.electromaz.Utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class SessionUtils {
    /**
     * startSession -- функция начинающая отсчёт времени сессии и заносящая оставшееся время в текстовое поле
     * ссылка на которое передаётся в функцию
     * @param sessionInfo -- ссылка на текстовое поле
     */
    public static void startSession(Label sessionInfo) {
        int[] timeMin = {Config.timeSession}; //Чтобы внутри события был доступен, делаем в виде массива.
        int[] timeSec = {60};
        sessionInfo.setText("Время сеанса: " + timeMin[0]+":00");
        timeMin[0]--;
        Timeline timeline = new Timeline (
                new KeyFrame(
                        Duration.millis(1000), //1000 мс = 1 сек
                        ae -> {
                            --timeSec[0];
                            if (timeSec[0] < 10) {
                                sessionInfo.setText("Время сеанса: " + timeMin[0]+":0"+timeSec[0]);
                            } else {
                                sessionInfo.setText("Время сеанса: " + timeMin[0]+":"+timeSec[0]);
                            }
                            if ((timeMin[0] == Config.timeWarningSession) && (timeSec[0] == 0)) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);

                                alert.setTitle("Внимание");
                                alert.setHeaderText("Время сеанса истечёт через "+Config.timeWarningSession+" минут");

                                alert.show();
                            }

                            if ((timeMin[0] <= 0) && (timeSec[0] <= 0)) {
                                finishSession(sessionInfo);
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

        timeline.setCycleCount(Config.timeSession * 60); // Ограничим число повторений
        timeline.play(); //Запускаем
    }

    /**
     * finishSession -- функция завершающая отсчёт времени сессии и сообщающая об этом, также возвращает пользователя
     * на страницу авторизации
     * @param sessionInfo -- ссылка на текстовое поле
     */
    private static void finishSession(Label sessionInfo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Сеанс окончен");
        alert.setHeaderText("Время сеанса закончилось");

        alert.show();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    new SceneUtils().changeSceneToLogin(sessionInfo.getScene(), "login-view.fxml");
                });
                timer.cancel();
            }
        }, 1000, 1000);
    }
}
