package com.example.electromaz;

import com.example.electromaz.Models.Order;
import com.example.electromaz.Models.Product;
import com.example.electromaz.Models.User;
import com.example.electromaz.Utils.DBUtils;
import com.example.electromaz.Utils.SceneUtils;
import com.example.electromaz.Utils.SessionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController {

    /**
     * Верхняя панель
     */
    @FXML
    private Button exitBtn;
    @FXML
    public Label userInfo;
    @FXML
    public Label sessionInfo;

    /**
     * Левая панель навигации
     */
    @FXML
    private Button logOutBtn;
    @FXML
    private Button goToProductsBtn;
    @FXML
    private Button goToOrdersBtn;
    @FXML
    private Button goToAppInfoBtn;
    @FXML
    private Label pageTitle;

    /**
     * Страницы контента
     */
    @FXML
    private GridPane pgProducts;
    @FXML
    private GridPane pgOrders;
    @FXML
    private GridPane pgAppInfo;

    /**
     * Страница заказов
     */
    @FXML
    private Button orderAddBtn;
    @FXML
    private Button orderSearchBtn;
    @FXML
    private TextField orderSearchField;
    @FXML
    private TableView<Order> tableOrders;
    @FXML
    private TableColumn<Order, String> orderProductCol;
    @FXML
    private TableColumn<Order, String> orderUserCol;
    @FXML
    private TableColumn<Order, String> orderCountCol;
    @FXML
    private TableColumn<Order, String> orderFinalPriceCol;
    @FXML
    ObservableList<Order> ordersList = FXCollections.observableArrayList();

    /**
     * Страница товаров
     */
    @FXML
    private Button productAddBtn;
    @FXML
    private Button productSearchBtn;
    @FXML
    private TextField productSearchField;
    @FXML
    private TableView<Product> tableProducts;
    @FXML
    private TableColumn<Product, String> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, String> productPriceCol;
    @FXML
    private TableColumn<Product, String> productDescriptionCol;
    @FXML
    ObservableList<Product> productsList = FXCollections.observableArrayList();

    /**
     * setUser -- функция получения данных пользователя
     * @param user -- данные пользователя
     */
    public void setUser(User user) {
        userInfo.setText(user.getFio()+"\nНомер: "+user.getId());
        SessionUtils.startSession(sessionInfo);
    }

    /**
     * initialize -- функция запускающаяся при запуске контроллера
     */
    @FXML
    public void initialize() {
        /**
         * Animations
         */
        final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
        final String HOVERED_BUTTON_STYLE = "-fx-background-color: #aaaa;";

        exitBtn.setStyle(IDLE_BUTTON_STYLE);
        exitBtn.setOnMouseEntered(e -> exitBtn.setStyle(HOVERED_BUTTON_STYLE));
        exitBtn.setOnMouseExited(e -> exitBtn.setStyle(IDLE_BUTTON_STYLE));

        logOutBtn.setStyle(IDLE_BUTTON_STYLE);
        logOutBtn.setOnMouseEntered(e -> logOutBtn.setStyle(HOVERED_BUTTON_STYLE));
        logOutBtn.setOnMouseExited(e -> logOutBtn.setStyle(IDLE_BUTTON_STYLE));

        goToProductsBtn.setStyle(IDLE_BUTTON_STYLE);
        goToProductsBtn.setOnMouseEntered(e -> goToProductsBtn.setStyle(HOVERED_BUTTON_STYLE));
        goToProductsBtn.setOnMouseExited(e -> goToProductsBtn.setStyle(IDLE_BUTTON_STYLE));

        goToOrdersBtn.setStyle(IDLE_BUTTON_STYLE);
        goToOrdersBtn.setOnMouseEntered(e -> goToOrdersBtn.setStyle(HOVERED_BUTTON_STYLE));
        goToOrdersBtn.setOnMouseExited(e -> goToOrdersBtn.setStyle(IDLE_BUTTON_STYLE));

        goToAppInfoBtn.setStyle(IDLE_BUTTON_STYLE);
        goToAppInfoBtn.setOnMouseEntered(e -> goToAppInfoBtn.setStyle(HOVERED_BUTTON_STYLE));
        goToAppInfoBtn.setOnMouseExited(e -> goToAppInfoBtn.setStyle(IDLE_BUTTON_STYLE));

        /**
         * Default
         */
        refreshTableOrdersList();
        refreshTableProductsList();
    }

    /**
     * Функции для страницы Заказов
     */
    private void refreshTableOrdersList() {
        ordersList = new DBUtils().getOrdersList();
        orderProductCol.setCellValueFactory(new PropertyValueFactory<>("product"));
        orderUserCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        orderCountCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        orderFinalPriceCol.setCellValueFactory(new PropertyValueFactory<>("finalprice"));
        tableOrders.setItems(ordersList);
    }

    private void refreshTableOrdersList(String searchProduct) {
        ordersList = new DBUtils().getOrdersList(searchProduct);
        orderProductCol.setCellValueFactory(new PropertyValueFactory<>("product"));
        orderUserCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        orderCountCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        orderFinalPriceCol.setCellValueFactory(new PropertyValueFactory<>("finalprice"));
        tableOrders.setItems(ordersList);
    }

    /**
     * Функции для страницы товаров
     */
    private void refreshTableProductsList() {
        productsList = new DBUtils().getProductsList();
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableProducts.setItems(productsList);
    }

    private void refreshTableProductsList(String searchName) {
        productsList = new DBUtils().getProductsList(searchName);
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableProducts.setItems(productsList);
    }

    /**
     * События при нажатии левой кнопкой маши
     */

    /**
     * События панели навигации
     */
    @FXML
    private void handleClicks(ActionEvent event) {
        if (event.getSource() == logOutBtn) {
            new SceneUtils().changeSceneToLogin(exitBtn.getScene(), "login-view.fxml");
        }
        if (event.getSource() == goToProductsBtn) {
            pageTitle.setText("Товары");
            pgProducts.toFront();
        }
        if (event.getSource() == goToOrdersBtn) {
            pageTitle.setText("Заказы");
            pgOrders.toFront();
        }
        if (event.getSource() == goToAppInfoBtn) {
            pageTitle.setText("О приложении");
            pgAppInfo.toFront();
        }
    }

    /**
     * События поисковых кнопок
     */

    @FXML
    private void handleClicksSearch(ActionEvent event) {
        if (event.getSource() == orderSearchBtn) {
            refreshTableOrdersList(orderSearchField.getText());
        }
        if (event.getSource() == productSearchBtn) {
            refreshTableProductsList(productSearchField.getText());
        }
    }

    /**
     * События кнопок добавления
     */

    @FXML
    private void handleClicksAdd(ActionEvent event) {
        if (event.getSource() == orderAddBtn) {
            new SceneUtils().openWindowScene("add-order-view.fxml");
        }
        if (event.getSource() == productAddBtn) {
            new SceneUtils().openWindowScene("add-product-view.fxml");
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
}