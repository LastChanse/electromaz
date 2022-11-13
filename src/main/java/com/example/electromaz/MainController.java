package com.example.electromaz;

import com.example.electromaz.Models.User;
import com.example.electromaz.Utils.Config;
import com.example.electromaz.Utils.SceneUtils;
import com.example.electromaz.Utils.SessionUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button exitBtn;

    @FXML
    public Label userInfo;

    @FXML
    public Label sessionInfo;

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

    @FXML
    private GridPane pgProducts;

    @FXML
    private GridPane pgOrders;

    @FXML
    private GridPane pgAppInfo;

    public void setUser(User user) {
        userInfo.setText(user.getFio()+"\nДолжность: "+user.getRole());
        SessionUtils.startSession(sessionInfo);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    }

    @FXML
    private void handleClicks(ActionEvent event) {
        if (event.getSource() == logOutBtn) {
            new SceneUtils().changeScene(exitBtn.getScene(), "login-view.fxml", null);
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

    @FXML
    private void exit() {
        System.exit(0);
    }
}
