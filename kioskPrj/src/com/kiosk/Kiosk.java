package com.kiosk;

import java.util.*;
import java.util.function.Function;

public class Kiosk {
    int selectNum;
    double totalPrice;
    
    //숫자 관리
    private enum Number{
        ZERO(0),
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5);

        private final int number;

        Number(int number){
            this.number = number;
        }
        public int getNum(){
            return number;
        }
    }

    //할인 정보 enum
    private enum DiscountType{
        PATRIOT(1,"1. 국가유공자 : 10%", (num) -> num * 0.9),
        SOLDIER(2,"2. 군인      : 5%", (num) -> num * 0.95),
        STUDENT(3, "3. 학생      : 3%", (num) -> num * 0.97),
        NORMAL(4,"4. 일반      : 0%", (num)-> num);

        // 필드
        private final int typeNumber;
        private final String typeName;
        private final Function<Double, Double> discount;

        // 생성자
        DiscountType(int typeNumber, String typeName, Function<Double, Double> discount) {
            this.typeNumber = typeNumber;
            this.typeName = typeName;
            this.discount = discount;
        }

        // 연산 값 리턴
        public double discountApply(double num) {
            return discount.apply(num);
        }

        public int getTypeNumber(){
            return typeNumber;
        }

    }

    //키오스크 실행
    public void start(Menu menu){
        Scanner sc = new Scanner(System.in);

        Map<Integer, OrderItem> orderItemMap = new HashMap();

        //카테고리 출력
        boolean mapIsEmpty = orderItemMap.isEmpty();
        menu.showCategory(mapIsEmpty);

        //map.isEmpty 일 경우 에러체크 (구현필요)
        if(mapIsEmpty){
            selectNum = inputNumber(sc, Number.ZERO.getNum(), Number.THREE.getNum());
        }else {
            selectNum = inputNumber(sc, Number.ZERO.getNum(), Number.FIVE.getNum());
        }

        //메뉴 선택 후 장바구니 넣기
        if(selectNum < 4) {
            //메뉴 보여주는 곳
            List<MenuItem> muenList = menu.showAndGetMenuList(selectNum);

            //입력
            selectNum = 2;

            //메뉴 선택
            MenuItem selectMenu = selectMenu(muenList, selectNum);

            //입력
            selectNum = 1;

            //장바구니 추가
            orderItemMap = putOrderList(orderItemMap, selectMenu);


        }else if(selectNum == 4){
            totalPrice = getTotalPrice(orderItemMap);
            showOrderList(orderItemMap, totalPrice);
            selectNum = 1;
        } else if (selectNum == 5) {
            orderItemMap.clear();
        }



        //할인정보 출력
        showDiscountInfo();
        selectNum = 3;
        if(selectNum < 4){
            totalPrice = getDiscountPrice(totalPrice,selectNum);
        }

        System.out.println("총 가격 : " + totalPrice);

    }

    //번호 입력받는 부분
    private int inputNumber(Scanner sc, int minNum, int maxNum){

        try{
            selectNum = sc.nextInt();
            sc.nextLine();

            if(selectNum <= maxNum && selectNum >= minNum){
                return selectNum;
            }else {
                System.out.println("숫자를 제대로 입력해 주세요. (" + minNum +" ~ " + maxNum + ")");
                return -1;
            }

        } catch (Exception e) {
            System.out.println("숫자를 제대로 입력해 주세요. (" + minNum +" ~ " + maxNum + ")");
            return -1;
        }
    }

    //메뉴 확인 후 선택
    private MenuItem selectMenu(List<MenuItem> menuList, int selectNum){

        MenuItem selectMenu = menuList.get(selectNum-1);

        System.out.println("선택한 메뉴 : " + menuList.get(selectNum-1).toString());

        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인     2. 취소\n");

        return selectMenu;



    }

    //선택한 메뉴 장바구니에 넣기
    private Map<Integer, OrderItem> putOrderList(Map<Integer, OrderItem> orderItemMap, MenuItem selectMenu){

        System.out.println("장바구니에 추가 : "+selectMenu.getMenuName()+"\n");

        int menuCode = selectMenu.getMenuCode();

        //장바구니에 같은 제품 있으면 수량 1 추가
        if(orderItemMap.containsKey(menuCode)){
            OrderItem order = orderItemMap.get(menuCode);
            order.setQuantity(order.getQuantity()+1);
            orderItemMap.put(menuCode, order);
        }else{
            orderItemMap.put(menuCode, new OrderItem(selectMenu, 1));
        }
        return orderItemMap;
    }

    //장바구니 리스트 보여줌
    private void showOrderList(Map<Integer, OrderItem> orderItemMap, Double totalPrice){
        System.out.println("아래와 같이 주문 하시겠습니까?\n");
        System.out.println("[ Orders ]");
        orderItemMap.entrySet().stream()
                .forEach(entry -> System.out.println(entry.getValue().getMenuItem().toString() + "  |  " + entry.getValue().getQuantity()+"개\n"));
        System.out.println("[ Total ]");
        System.out.println("W " + totalPrice + "\n");
    }

    //장바구니 총 금액
    private double getTotalPrice(Map<Integer, OrderItem> orderItemMap){
        return orderItemMap.entrySet().stream()
                .map(entry -> entry.getValue().getMenuItem().getMenuPrice() * entry.getValue().getQuantity())
                .reduce(0.0,(sum, now) -> sum + now);
    }

    //할인정보 보여줌
    private void showDiscountInfo(){
        Arrays.stream(DiscountType.values())
                .forEach(value -> System.out.println(value.typeName));
        System.out.println();
    }

    //할인정보계산
    private double getDiscountPrice(double totalPrice, int selectNum){
        return Arrays.stream(DiscountType.values())//enum 을 스트림으로 사용
                .filter(value -> value.getTypeNumber() == selectNum) //조건
                .findAny()//하나의 값만 반환 -> 없으면 에러
                .map(type -> type.discountApply(totalPrice))
                .orElse(totalPrice);// 계산 없을경우 기존 값 반화 -> 없으면 에러
    }
}
