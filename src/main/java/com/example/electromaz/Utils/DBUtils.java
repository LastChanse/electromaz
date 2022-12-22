package com.example.electromaz.Utils;

import com.example.electromaz.Models.Order;
import com.example.electromaz.Models.Product;
import com.example.electromaz.Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.sql.*;

public class DBUtils {
    /**
     * logInUser -- функция авторизации пользователя, в случае успеха открывает главную страницу, в случае провала
     * сообщает об ошибке
     * @param scene -- сцена с котоорой запускается вход
     * @param login -- логин пользователя
     * @param password -- пароль пользователя
     */
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
                    user.setId(resultSet.getString("id_user"));
                    user.setLogin(resultSet.getString("name"));
                    user.setPassword(resultSet.getString("Password"));
                    user.setFio(resultSet.getString("FIO"));
                    if (user.getPassword().equals(password)) {
                        new SceneUtils().changeSceneToMain(scene, "main-view.fxml", user);
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

    /**
     * addOrder -- функция добавления заказа в базу данных
     * @param order -- заказ
     */
    public void addOrder(Order order) {

        try {
            Connection connection = DriverManager.getConnection(Config.DBUrl, Config.DBUser, Config.DBPassword);
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO `orders` (`id_product`, `id_user`, `count`, `finalprice`) VALUES ('"+order.getProduct()+"','" +
                    order.getUser()+"','"+
                    order.getCount()+"','"+
                    order.getFinalprice()+"')");
        } catch (SQLException e) {
            AlertUtils.showAlert(e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * getOrdersList -- функция получения списка всех заказов из базы данных
     * @return -- возвращает список заказов
     */
    public ObservableList<Order> getOrdersList() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<Order> ordersList = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(Config.DBUrl, Config.DBUser, Config.DBPassword);
            preparedStatement = connection.prepareStatement("SELECT P.name as prod, U.FIO as user, O.count, O.finalprice " +
                    "FROM orders O, product P, users U " +
                    "WHERE O.id_product=P.id_product AND O.id_user=U.id_user GROUP BY id_order");

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                AlertUtils.showAlert("Предоставленные данные неверны", Alert.AlertType.ERROR);
            } else {
                while (resultSet.next()) {
                    ordersList.add(new Order(
                            resultSet.getString("prod"),
                            resultSet.getString("user"),
                            resultSet.getString("count"),
                            resultSet.getString("finalprice")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ordersList;
    }

    /**
     * getOrdersList -- функция получения списка всех заказов, соответствующих поисковому запросу из базы данных
     * @param searchProduct -- поисковой запрос
     * @return -- возвращает список заказов
     */
    public ObservableList<Order> getOrdersList(String searchProduct) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<Order> ordersList = FXCollections.observableArrayList();
        ObservableList<Order> filteredItems = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(Config.DBUrl, Config.DBUser, Config.DBPassword);
            preparedStatement = connection.prepareStatement("SELECT P.name as prod, U.FIO as user, O.count, O.finalprice " +
                    "FROM orders O, product P, users U " +
                    "WHERE O.id_product=P.id_product AND O.id_user=U.id_user GROUP BY id_order");

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                AlertUtils.showAlert("Предоставленные данные неверны", Alert.AlertType.ERROR);
            } else {
                while (resultSet.next()) {
                    ordersList.add(new Order(
                            resultSet.getString("prod"),
                            resultSet.getString("user"),
                            resultSet.getString("count"),
                            resultSet.getString("finalprice")
                    ));
                }
            }
            if (!ordersList.isEmpty() && !(ordersList == null)) {
                for (Order li : ordersList)
                    if (li.getProduct().contains(searchProduct))
                        filteredItems.add(li);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filteredItems;
    }

    /**
     * getProductsList -- функция получения списка всех товаров из базы данных
     * @return -- возвращает список товаров
     */
    public ObservableList<Product> getProductsList() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<Product> productsList = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(Config.DBUrl, Config.DBUser, Config.DBPassword);
            preparedStatement = connection.prepareStatement("SELECT id_product, name, price, description FROM product");

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                AlertUtils.showAlert("Предоставленные данные неверны", Alert.AlertType.ERROR);
            } else {
                while (resultSet.next()) {
                    productsList.add(new Product(
                            resultSet.getString("id_product"),
                            resultSet.getString("name"),
                            resultSet.getString("price"),
                            resultSet.getString("description")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productsList;
    }

    /**
     * getOrdersList -- функция получения списка всех товаров, соответствующих поисковому запросу из базы данных
     * @param searchName -- поисковой запрос
     * @return -- возвращает список товаров
     */
    public ObservableList<Product> getProductsList(String searchName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<Product> productsList = FXCollections.observableArrayList();
        ObservableList<Product> filteredItems = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(Config.DBUrl, Config.DBUser, Config.DBPassword);
            preparedStatement = connection.prepareStatement("SELECT id_product, name, price, description FROM product");

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                AlertUtils.showAlert("Предоставленные данные неверны", Alert.AlertType.ERROR);
            } else {
                while (resultSet.next()) {
                    productsList.add(new Product(
                            resultSet.getString("id_product"),
                            resultSet.getString("name"),
                            resultSet.getString("price"),
                            resultSet.getString("description")
                    ));
                }
            }
            if (!productsList.isEmpty() && !(productsList == null)) {
                for (Product li : productsList)
                    if (li.getName().contains(searchName))
                        filteredItems.add(li);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filteredItems;
    }

    /**
     * addProduct -- функция добавления товара в базу данных
     * @param product -- товар
     */
    public void addProduct(Product product) {
        try {
            Connection connection = DriverManager.getConnection(Config.DBUrl, Config.DBUser, Config.DBPassword);
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO `product` (`id_product`, `name`, `price`, `description`) VALUES ('"+product.getId()+"','" +
                    product.getName()+"','"+
                    product.getPrice()+"','"+
                    product.getDescription()+"')");
        } catch (SQLException e) {
            AlertUtils.showAlert(e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}
