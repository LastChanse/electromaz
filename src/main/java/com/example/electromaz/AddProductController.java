package com.example.electromaz;

import com.example.electromaz.Models.Order;
import com.example.electromaz.Models.Product;
import com.example.electromaz.Utils.AlertUtils;
import com.example.electromaz.Utils.Config;
import com.example.electromaz.Utils.DBUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.atomic.AtomicReference;

public class AddProductController {

    /**
     * Поля ввода данных о товаре
     */
    @FXML
    private TextField productId;
    @FXML
    private TextField productName;
    @FXML
    private TextField productPrice;
    @FXML
    private TextArea productDescription;

    // Кнопа закрытия окна
    @FXML
    private Button exitOBtn;

    // Кнопка добавления товара в базу данных
    @FXML
    private Button addProduct;

    /**
     * initialize -- функция запускающаяся при запуске контроллера
     */
    @FXML
    public void initialize() {
        /**
         * Анимации при наведении на кнопки
         */
        final String IDLE_BUTTON_STYLE = "-fx-background-color: #558eda;";
        final String HOVERED_BUTTON_STYLE = "-fx-background-color: #558edaaa;";

        addProduct.setStyle(IDLE_BUTTON_STYLE);
        addProduct.setOnMouseEntered(e -> addProduct.setStyle(HOVERED_BUTTON_STYLE));
        addProduct.setOnMouseExited(e -> addProduct.setStyle(IDLE_BUTTON_STYLE));

        /**
         * События при нажатии на кнопки
         */
        // При нажатии на кнопку закрытия окна окно закрывается
        exitOBtn.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) exitOBtn.getScene().getWindow();
            stage.close();
        });
        // При нажатии на кнопку добавления товара товары добавляется, если поля заполнены верно
        // Иначе сообщение об ошибке
        addProduct.setOnMouseClicked(mouseEvent -> {
            if (
                    productId.getText().equals("") ||
                            productName.getText().equals("") ||
                            productPrice.getText().equals("") ||
                            productDescription.getText().equals("")
            ) {
                AlertUtils.showAlert("Поля не могут быть пустыми при добавлении!", Alert.AlertType.ERROR);
            } else {

                DBUtils db = new DBUtils();
                db.addProduct(new Product(
                        productId.getText(),
                        productName.getText(),
                        productPrice.getText(),
                        productDescription.getText()
                ));
                Stage stage = (Stage) exitOBtn.getScene().getWindow();
                stage.close();
            }
        });
    }
}
