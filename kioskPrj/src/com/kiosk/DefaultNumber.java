package com.kiosk;

//입력값 및 에러 숫자 관리
public enum DefaultNumber {
    ERROR(-1),
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int number;

    DefaultNumber(int number){
        this.number = number;
    }
    public int getValue(){
        return number;
    }

}
