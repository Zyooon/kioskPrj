package com.kiosk;

public class MenuItem {
    //menu 정보 관리 (DB 같은 곳)

    private String menuName;
    private double menuPrice;
    private String menuDescription;

    public MenuItem(String menuName){
        this.menuName = menuName;
    }

    public MenuItem(String menuName, double menuPrice, String menuDescription) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuDescription = menuDescription;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public double getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(double menuPrice) {
        this.menuPrice = menuPrice;
    }

    @Override
    public String toString() {
        return menuName +  "   |   W " + menuPrice + "   |   " + menuDescription;

    }
}
