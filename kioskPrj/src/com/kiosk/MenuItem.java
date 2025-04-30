package com.kiosk;

import java.util.Objects;

public class MenuItem {
    //menu 정보 관리 (DB 같은 곳)
    private String menuName;
    private double menuPrice;
    private String menuDescription;

    public MenuItem(String menuName, double menuPrice, String menuDescription) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuDescription = menuDescription;
    }

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

    //map 으로 key 비교할때 동등성 조건 필요
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(menuName, menuItem.menuName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(menuName);
    }
}
