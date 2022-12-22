package com.example.electromaz.Models;

public class Order {
    private String product;
    private String user;
    private String count;
    private String finalprice;

    public Order(String product, String user, String count, String finalprice) {
        this.product = product;
        this.user = user;
        this.count = count;
        this.finalprice = finalprice;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getFinalprice() {
        return finalprice;
    }

    public void setFinalprice(String finalprice) {
        this.finalprice = finalprice;
    }
}
