package com.example.electromaz;

import com.example.electromaz.Utils.Config;
import com.example.electromaz.Utils.captcha.CaptchaGenerator;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CaptchaController {
    LoginController root = new LoginController(); // контроллер авторизации

    @FXML
    private Canvas canvas; // холст на котором будет рисоваться капча

    @FXML
    private TextField textInputField; // поле ввода капчи

    CaptchaGenerator cg; // генератор капчи
    String captchaText = ""; // текст капчи по умолчанию

    /**
     * getParentController -- получает контроллер авторизации
     * @param root -- контроллер авторизации
     */
    public void getParentController(LoginController root) {
        this.root = root;
    }

    /**
     * initialize -- функция запускающаяся при запуске контроллера
     */
    @FXML
    public void initialize() {
        cg = new CaptchaGenerator(canvas); // Генерация капчи и рисование её на холсте
        captchaText = cg.generate(Config.captchaLength); // получение текста сгенерированной капчи
    }

    /**
     * check -- функция проверки капчи, если капча введена верно, то окно с капчей закрывается,
     * если капча введена неверно, то вызывается функция lock в контроллере авторизации
     * для блокировки ввода на 10 секунды
     */
    @FXML
    private void check() {
        if (!captchaText.equals(textInputField.getText())) {
            root.lock(true, 10);
        }
        Stage stage = (Stage) textInputField.getScene().getWindow();
        stage.close();
    }

    /**
     * reset -- функция повторной генерации капчи
     */
    @FXML
    private void reset() {
        captchaText = cg.generate(Config.captchaLength);
    }
}
