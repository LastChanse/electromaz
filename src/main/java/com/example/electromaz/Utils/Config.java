package com.example.electromaz.Utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Config {
    // Default
    public static String resourcesPath = "file:src/main/resources/com/example/electromaz/";
    // App
    public static String appName = "ЭлектроМаз";
    public static String appIconPath = "drawables/logo.png";
    public static Image appIcon = new Image(resourcesPath+appIconPath);
    public static int draggedYZone = 50; // SceneUtils
    // Database
    public static String DBUrl = "jdbc:mysql://localhost:3306/electromaz";
    public static String DBUser = "root";
    public static String DBPassword = "";
    // Session
    public static int timeSession = 5; // min
    public static int timeWarningSession = 3; // min
    public static int timeLockAuthAfterSession = 60; // sec
    // Drawables
    // Show&hide password images
    public static String invisiblePath = "drawables/hidden-eye.png";
    public static String visiblePath = "drawables/eye.png";
    // +constructor Show&hide image views
    public static ImageView invisible = new ImageView(new Image(resourcesPath+invisiblePath, 30, 30, true, true));
    public static ImageView visible = new ImageView(new Image(resourcesPath+visiblePath, 30, 30, true, true));
    // Captcha
    public static int captchaLength = 6;
    public static int countTryLogin = 2;
}
