package com.kiosk;

public class MenuItem {
    //menu 정보 관리 (DB 같은 곳)

    private int menuCode;
    private String menuName;
    private double menuPrice;
    private String menuDescription;

    public MenuItem(int menuCode, String menuName, double menuPrice, String menuDescription) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuDescription = menuDescription;
    }

    public int getMenuCode() {return menuCode;}
    public String getMenuName() {
        return menuName;
    }
    public double getMenuPrice() {
        return menuPrice;
    }


    @Override
    public String toString() {
        return menuName +  "   |   W " + menuPrice + "   |   " + menuDescription;

    }
}
