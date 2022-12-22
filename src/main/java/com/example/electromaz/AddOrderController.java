package com.example.electromaz;

import com.example.electromaz.Models.Order;
import com.example.electromaz.Utils.AlertUtils;
import com.example.electromaz.Utils.Config;
import com.example.electromaz.Utils.DBUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.atomic.AtomicReference;

public class AddOrderController {

    /**
     * Поля для ввода данных о заказе
     */
    @FXML
    private TextField orderIdProduct;
    @FXML
    private TextField orderIdUser;
    @FXML
    private TextField orderProductCount;
    @FXML
    private TextField orderFinalPrice;

    // Кнопка закрытия окна
    @FXML
    private Button exitOBtn;

    // Кнопка формирования штрих-кода
    @FXML
    private Button createBarcode;

    // Кнопка добавления заказа в базу данных
    @FXML
    private Button addOrder;

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

        createBarcode.setStyle(IDLE_BUTTON_STYLE);
        createBarcode.setOnMouseEntered(e -> createBarcode.setStyle(HOVERED_BUTTON_STYLE));
        createBarcode.setOnMouseExited(e -> createBarcode.setStyle(IDLE_BUTTON_STYLE));

        addOrder.setStyle(IDLE_BUTTON_STYLE);
        addOrder.setOnMouseEntered(e -> addOrder.setStyle(HOVERED_BUTTON_STYLE));
        addOrder.setOnMouseExited(e -> addOrder.setStyle(IDLE_BUTTON_STYLE));

        /**
         * События при нажатии на кнопки
         */
        // При нажатии на кнопку закрытия окна окно закрывается
        exitOBtn.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) exitOBtn.getScene().getWindow();
            stage.close();
        });
        // При нажатии на кнопку добавления заказа заказ добавляется, если поля заполнены верно
        // Иначе сообщение об ошибке
        addOrder.setOnMouseClicked(mouseEvent -> {
            if (
                    orderIdProduct.getText().equals("") ||
                            orderIdUser.getText().equals("") ||
                            orderProductCount.getText().equals("") ||
                            orderFinalPrice.getText().equals("")
            ) {
                AlertUtils.showAlert("Поля не могут быть пустыми при добавлении!", Alert.AlertType.ERROR);
            } else {

                DBUtils db = new DBUtils();
                db.addOrder(new Order(
                        orderIdProduct.getText(),
                        orderIdUser.getText(),
                        orderProductCount.getText(),
                        orderFinalPrice.getText()
                ));
                Stage stage = (Stage) exitOBtn.getScene().getWindow();
                stage.close();
            }
        });
        // При нажатии на кнопку формирования штрих-кода открывается окно с сформированным штрих-кодом, если поля
        // номер и количество товара заполнены, иначе вывод сообщения о том что данные поля должны быть заполнены
        createBarcode.setOnMouseClicked(mouseEvent -> {
            if (orderIdProduct.getText().equals("") || orderProductCount.getText().equals("")) {
                AlertUtils.showAlert("Для печати штрих-кода необходимо чтобы поля" +
                        "\nномер товара и количество были заполнены", Alert.AlertType.ERROR);
            } else {
                Parent root;
                try {
                    AtomicReference<Double> xOffset = new AtomicReference<>((double) 101);
                    AtomicReference<Double> yOffset = new AtomicReference<>((double) 101);
                    FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("barcode-view.fxml"));
                    root = loader.load();

                    BarcodeController bc = loader.getController();
                    bc.getData(new Order(
                            orderIdProduct.getText(),
                            orderIdUser.getText(),
                            orderProductCount.getText(),
                            orderFinalPrice.getText()
                    ));

                    Stage stage = new Stage();

                    stage.initStyle(StageStyle.DECORATED.UNDECORATED);

                    root.setOnMouseMoved(mouseEventRoot -> {
                        xOffset.set(mouseEvent.getSceneX());
                        yOffset.set(mouseEvent.getSceneY());
                    });

                    root.setOnMouseDragged(mouseEventRoot -> {
                        if (yOffset.get() < Config.draggedYZone) {
                            stage.setX(mouseEvent.getScreenX() - xOffset.get());
                            stage.setY(mouseEvent.getScreenY() - yOffset.get());
                        }
                    });
                    stage.setTitle("ШТРИХ-КОД");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
