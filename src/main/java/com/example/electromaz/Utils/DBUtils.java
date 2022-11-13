package com.example.electromaz.Utils;

import com.example.electromaz.Models.User;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.sql.*;

public class DBUtils {

    public static void logInUser(Scene scene, String login, String password) {
        User user = new User();
        String statusId = new String();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(Config.DBUrl, Config.DBUser, Config.DBPassword);
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE name=?");
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                AlertUtils.showAlert("Предоставленные учетные данные неверны", Alert.AlertType.ERROR);
            } else {
                while (resultSet.next()) {
                    user.setLogin(resultSet.getString("name"));
                    user.setPassword(resultSet.getString("Password"));
                    user.setFio(resultSet.getString("FIO"));
                    statusId = resultSet.getString("user_role");
                    if (user.getPassword().equals(password)) {
                        try {
                            PreparedStatement prSt = connection.prepareStatement("SELECT * FROM user_roles WHERE id_user_roles=?");
                            prSt.setString(1,statusId);

                            ResultSet resSet =  prSt.executeQuery();

                            resSet.next();

                            user.setRole(resSet.getString("name"));

                        } catch (SQLException e) {
                            AlertUtils.showAlert("Не удалось получить данные о должности из базы данных", Alert.AlertType.ERROR);
                            throw new RuntimeException(e);
                        }
                        SceneUtils sc = new SceneUtils();
                        sc.changeScene(scene, "main-view.fxml", user);
                    } else {
                        AlertUtils.showAlert("Предоставленные учетные данные неверны", Alert.AlertType.ERROR);
                    }
                }
            }
        } catch (SQLException e) {
            AlertUtils.showAlert("Ошибка подключения к базе данных", Alert.AlertType.ERROR);
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
