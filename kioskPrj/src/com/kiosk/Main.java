package com.kiosk;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        //키오스크 시작
        new Kiosk().start(menu);
    }
}