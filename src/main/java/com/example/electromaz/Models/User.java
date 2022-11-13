package com.example.electromaz.Models;

public class User {
    private String login;
    private String password;
    private String role;
    private String fio;

    public User() {}

    public User(String login,String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, String role, String fio) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.fio = fio;
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

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getFio() {
        return fio;
    }
    public void setFio(String fio) { this.fio = fio; }
}