package com.maruti.models;

public class Settings {
    private int userId;
    private String appPassword;
    private char darkMode = 'N', pushNotification = 'Y', appLock = 'N';

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getAppPassword() { return appPassword; }
    public void setAppPassword(String appPassword) { this.appPassword = appPassword; }
    public char getDarkMode() { return darkMode; }
    public void setDarkMode(char darkMode) { this.darkMode = darkMode; }
    public char getPushNotification() { return pushNotification; }
    public void setPushNotification(char pushNotification) { this.pushNotification = pushNotification; }
    public char getAppLock() { return appLock; }
    public void setAppLock(char appLock) { this.appLock = appLock; }
}
