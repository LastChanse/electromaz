package com.example.electromaz.Models;

public class User {
    private String id;
    private String login;
    private String password;
    private String fio;

    public User() {}

    public User(String login,String password) {
        this.login = login;
        this.password = password;
    }

    public User(String id, String login, String password, String fio) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.fio = fio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFio() {
        return fio;
    }
    public void setFio(String fio) { this.fio = fio; }
}