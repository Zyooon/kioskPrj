package com.kiosk;

import java.util.function.Function;

//할인 정보 enum
public enum DiscountType {
    PATRIOT(1,"1. 국가유공자 : 10%", (num) -> num * 0.9),
    SOLDIER(2,"2. 군인      : 5%", (num) -> num * 0.95),
    STUDENT(3, "3. 학생      : 3%", (num) -> num * 0.97),
    NORMAL(4,"4. 일반      : 0%", (num)-> num);

    // 필드
    private final int typeNumber;
    private final String typeName;
    private final Function<Double, Double> discountApply;

    // 생성자
    DiscountType(int typeNumber, String typeName, Function<Double, Double> discountApply) {
        this.typeNumber = typeNumber;
        this.typeName = typeName;
        this.discountApply = discountApply;
    }

    // 연산 값 리턴
    public double getDiscountedPrice(double num) {
        return discountApply.apply(num);
    }

    public String getTypeName() {return typeName;}

    public int getTypeNumber(){
        return typeNumber;
}

}
