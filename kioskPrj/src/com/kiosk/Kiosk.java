package com.kiosk;

import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;

public class Kiosk {
    int selectNum;
    double totalPrice;

    //숫자 관리
    private enum Number{
        ERROR(-1),
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
        //스캐너
        Scanner sc = new Scanner(System.in);
        //장바구니 map
        Map<MenuItem, Integer> orderItemMap = new HashMap<>();



        while (true){
            do{
                //카테고리 출력
                boolean mapIsEmpty = orderItemMap.isEmpty();
                menu.showCategory(mapIsEmpty);

                //메뉴 카테고리 입력
                if(mapIsEmpty){
                    selectNum = inputNumber(sc, Number.ZERO.getNum(), Number.THREE.getNum());
                }else {
                    selectNum = inputNumber(sc, Number.ZERO.getNum(), Number.FIVE.getNum());
                }

            } while( selectNum == Number.ERROR.getNum());

            //0번 입력시 종료
            if(selectNum == Number.ZERO.getNum()){
                sc.close();
                return;
            }

            int categoryNum = selectNum;

            //메뉴 선택 후 장바구니 넣기
            if(categoryNum < Number.FOUR.getNum()) {
                List<MenuItem> muenList;
                do{
                    //메뉴 보여주는 곳
                    muenList = menu.showAndGetMenuList(categoryNum);

                    //메뉴 입력
                    selectNum = inputNumber(sc, Number.ZERO.getNum(), Number.FOUR.getNum());
                }while (selectNum == Number.ERROR.getNum());

                if(selectNum == Number.ZERO.getNum()){
                    continue;

                }
                //메뉴 선택
                MenuItem selectMenu = selectMenu(muenList, selectNum);

                do{
                    //장바구니 추가 여부
                    System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
                    System.out.println("1. 확인     2. 취소\n");
                    selectNum = inputNumber(sc, Number.ONE.getNum(), Number.TWO.getNum());
                }while (selectNum == Number.ERROR.getNum());

                //장바구니 추가
                if(selectNum == Number.ONE.getNum()){
                    orderItemMap = putOrderList(orderItemMap, selectMenu);
                }
                continue;


            }
            // 장바구니 확인 후 주문
            else if(selectNum == Number.FOUR.getNum()){
                //총 가격 계산
                totalPrice = getTotalPrice(orderItemMap);
                do{
                    //주문 조회
                    showOrderList(orderItemMap, totalPrice);
                    System.out.println("1. 주문     2. 메뉴판\n");
                    selectNum = inputNumber(sc, Number.ONE.getNum(), Number.TWO.getNum());
                } while (selectNum == Number.ERROR.getNum());
                if(selectNum == Number.TWO.getNum()) continue;

            }
            // 진행중인 주문 취소
            else if (selectNum == Number.FIVE.getNum()) {
                orderItemMap.clear();
                continue;
            }

            do{
                //할인정보 출력
                showDiscountInfo();
                selectNum = inputNumber(sc, Number.ONE.getNum(), Number.FOUR.getNum());
            }while (selectNum == Number.ERROR.getNum());

            if(selectNum != Number.FOUR.getNum()){
                totalPrice = getDiscountPrice(totalPrice,selectNum);
            }

            System.out.print("주문이 완료되었습니다. 금액은 W ");
            System.out.print(new DecimalFormat("#.#").format(totalPrice));
            System.out.println(" 입니다.");
            break;
        }
        sc.close();

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
                return Number.ERROR.getNum();
            }

        } catch (Exception e) {
            System.out.println("숫자를 제대로 입력해 주세요. (" + minNum +" ~ " + maxNum + ")");
            sc.nextLine();
            return Number.ERROR.getNum();
        }
    }

    //메뉴 확인 후 선택
    private MenuItem selectMenu(List<MenuItem> menuList, int selectNum){

        MenuItem selectMenu = menuList.get(selectNum - Number.ONE.getNum());

        System.out.println("선택한 메뉴 : " + menuList.get(selectNum - Number.ONE.getNum()).toString());


        return selectMenu;
    }

    //선택한 메뉴 장바구니에 넣기
    private Map<MenuItem, Integer> putOrderList(Map<MenuItem, Integer> orderItemMap, MenuItem selectMenu) {

        System.out.println("장바구니에 추가 : " + selectMenu.getMenuName() + "\n");

        //장바구니에 메뉴가 있을경우 수량 가져옴. 없을경우 0 가져옴
        int quantity = orderItemMap.getOrDefault(selectMenu, 0);

        //메뉴가 있을경우 + 1, 없을경우 1.
        orderItemMap.put(selectMenu, quantity + Number.ONE.getNum());

        return orderItemMap;
    }

    //장바구니 리스트 보여줌
    private void showOrderList(Map<MenuItem, Integer> orderItemMap, Double totalPrice){

        System.out.println("아래와 같이 주문 하시겠습니까?\n");
        System.out.println("[ Orders ]");
        orderItemMap.entrySet().stream()
                .forEach(entry -> System.out.println(entry.getKey().toString() + "  |  " + entry.getValue()+"개"));
        System.out.println("[ Total ]");
        System.out.println("W " + new DecimalFormat("#.############").
                format(totalPrice) + "\n");
    }

    //장바구니 총 금액
    private double getTotalPrice(Map<MenuItem, Integer> orderItemMap){
        return orderItemMap.entrySet().stream()
                .map(entry -> entry.getKey().getMenuPrice() * entry.getValue())
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
