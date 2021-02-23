package com.global.kinetic;

public class MyData {
    private String id;
    private String main;
    private String description;
    private String icon;
    public MyData(String id, String main, String description, String icon) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }
    public String getID() {
        return id;
    }
    public void setID(String id) {
        this.id = id;
    }
    public String getMain() {
        return main;
    }
    public void setMain(String name) {
        this.main = main;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String thumbnail) {
        this.icon = icon;
    }
}