package com.kiosk;

import java.util.Objects;

public class MenuItem {
    //속성
    private String name;
    private double price;
    private String description;

    //생성자
    public MenuItem(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    //Getter
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }

    //toString
    @Override
    public String toString() {
        return name +  "   |   W " + price + "   |   " + description;

    }

    //map 으로 key 비교할때 동등성 조건 필요
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(name, menuItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
