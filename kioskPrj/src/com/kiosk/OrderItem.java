package com.kiosk;

public class OrderItem {
    private MenuItem menuItem;
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
    public OrderItem(MenuItem menuItem,int quantity) {
        this.quantity = quantity;
        this.menuItem = menuItem;
    }


    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
