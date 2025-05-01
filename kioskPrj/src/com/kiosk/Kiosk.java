package com.kiosk;

import java.text.DecimalFormat;
import java.util.*;

public class Kiosk {
    static int inputNumber; // 입력값 받는 변수
    static double totalPrice; // 총 금액
    static Map<MenuItem, Integer> orderItemMap; //장바구니

    //키오스크 실행
    public void start(Menu menu){
        //스캐너
        Scanner scanner = new Scanner(System.in);
        //장바구니 초기화
        orderItemMap = new HashMap<>();

        //키오스크 반복문 시작
        while (true){
            do{
                //카테고리 출력
                boolean mapIsEmpty = orderItemMap.isEmpty();
                menu.showCategory(mapIsEmpty);

                //메뉴 카테고리 입력
                if(mapIsEmpty){
                    inputNumber = getInputNumber(scanner, DefaultNumber.ZERO.getValue(), DefaultNumber.THREE.getValue());
                }else {
                    inputNumber = getInputNumber(scanner, DefaultNumber.ZERO.getValue(), DefaultNumber.FIVE.getValue());
                }

                //inputNumber 에러 시 입력 반복
            } while( inputNumber == DefaultNumber.ERROR.getValue());

            //0번 입력시 종료
            if(inputNumber == DefaultNumber.ZERO.getValue()){
                scanner.close();
                return;
            }

            //카테고리 번호 입력값 생성
            int categoryNumber = inputNumber;

            //메뉴 선택 후 장바구니 넣기
            if(categoryNumber < DefaultNumber.FOUR.getValue()) {
                List<MenuItem> menuItemList;
                do{
                    //메뉴 정보 가져옴
                    menuItemList = menu.getMenuItemList(categoryNumber);

                    //메뉴 정보 보여주는 곳
                    menu.showMenuList(menuItemList, categoryNumber);

                    //메뉴 선택
                    inputNumber = getInputNumber(scanner, DefaultNumber.ZERO.getValue(), DefaultNumber.FOUR.getValue());
                }while (inputNumber == DefaultNumber.ERROR.getValue());

                // 0번일경우 처음부터 다시 시작
                if(inputNumber == DefaultNumber.ZERO.getValue()){
                    continue;
                }

                //선택된 메뉴 정보 Get
                MenuItem selectedMenu = getSelectedMenuByNumber(menuItemList);

                do{
                    //장바구니 추가 여부
                    System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
                    System.out.println("1. 확인     2. 취소\n");
                    inputNumber = getInputNumber(scanner, DefaultNumber.ONE.getValue(), DefaultNumber.TWO.getValue());
                }while (inputNumber == DefaultNumber.ERROR.getValue());

                //장바구니 추가
                if(inputNumber == DefaultNumber.ONE.getValue()){
                    addToOrderList(selectedMenu);
                }
                continue;


            }
            // 장바구니 확인 후 주문
            else if(inputNumber == DefaultNumber.FOUR.getValue()){
                //총 가격 계산
                totalPrice = getTotalPrice();
                do{
                    //총 주문 목록 출력
                    showOrderList(totalPrice);

                    //결제 할지 메뉴 더 고를지 선택
                    System.out.println("1. 주문     2. 메뉴판\n");
                    inputNumber = getInputNumber(scanner, DefaultNumber.ONE.getValue(), DefaultNumber.TWO.getValue());
                } while (inputNumber == DefaultNumber.ERROR.getValue());
                if(inputNumber == DefaultNumber.TWO.getValue()) continue;

            }
            // 진행중인 주문 취소 (장바구니 비우기)
            else if (inputNumber == DefaultNumber.FIVE.getValue()) {
                orderItemMap.clear();
                continue;
            }

            do{
                //할인 정보 출력
                showDiscountInfo();

                //할인 정보 선택
                inputNumber = getInputNumber(scanner, DefaultNumber.ONE.getValue(), DefaultNumber.FOUR.getValue());
            }while (inputNumber == DefaultNumber.ERROR.getValue());

            //할인정보가 4번이 아닐 시, 할인 계산 -> 4번일 경우 계산 x
            if(inputNumber != DefaultNumber.FOUR.getValue()){
                totalPrice = getDiscountedPriceByNumber();
            }

            System.out.print("주문이 완료되었습니다. 금액은 W ");
            // double 값의 금액표시를 소수 첫째자리까지만 표시
            System.out.print(new DecimalFormat("#.#").format(totalPrice));
            System.out.println(" 입니다.");
            break;
        }
        scanner.close();

    }

    //번호 입력받는 부분
    private int getInputNumber(Scanner scanner, int minNumber, int maxNumber){
        try{
            //스캐너 입력
            inputNumber = scanner.nextInt();
            scanner.nextLine();

            //입력받은 숫자가 max 와 min 사이일 경우 리턴 -> 아닐경우 에러
            if(inputNumber <= maxNumber && inputNumber >= minNumber){
                return inputNumber;
            }else {
                System.out.println("숫자를 제대로 입력해 주세요. (" + minNumber +" ~ " + maxNumber + ")");
                return DefaultNumber.ERROR.getValue();
            }
        //값이 이상할 경우 에러
        } catch (Exception e) {
            System.out.println("숫자를 제대로 입력해 주세요. (" + minNumber +" ~ " + maxNumber + ")");
            scanner.nextLine();
            return DefaultNumber.ERROR.getValue();
        }
    }

    //메뉴 확인 후 선택
    private MenuItem getSelectedMenuByNumber(List<MenuItem> menuList){

        //입력받은 숫자에 해당하는 번호로 선택한 메뉴 정보 가져옴 (인덱스번호 == 선택한 번호 - 1)
        MenuItem selectedMenu = menuList.get(inputNumber - DefaultNumber.ONE.getValue());

        //메뉴 출력
        System.out.println("선택한 메뉴 : " + menuList.get(inputNumber - DefaultNumber.ONE.getValue()).toString());

        return selectedMenu;
    }

    //선택한 메뉴 장바구니에 넣기
    private void addToOrderList(MenuItem selectedMenu) {

        System.out.println("장바구니에 추가 : " + selectedMenu.getMenuName() + "\n");

        //장바구니에 메뉴가 있을경우 수량 가져옴. 없을경우 0 가져옴
        int quantity = orderItemMap.getOrDefault(selectedMenu, DefaultNumber.ZERO.getValue());

        //메뉴가 있을경우 + 1, 없을경우 1.
        orderItemMap.put(selectedMenu, quantity + DefaultNumber.ONE.getValue());
    }

    //장바구니 리스트 출력
    private void showOrderList(Double totalPrice){

        //주문 목록 표시
        System.out.println("아래와 같이 주문 하시겠습니까?\n");
        System.out.println("[ Orders ]");
        orderItemMap.entrySet().stream()
                .forEach(entry -> System.out.println(entry.getKey().toString() + "  |  " + entry.getValue()+"개"));
        
        //합계 금액 표시
        System.out.println("\n[ Total ]");
        System.out.println("W " + new DecimalFormat("#.############").
                format(totalPrice) + "\n");
    }

    //장바구니 총 금액 계산
    private double getTotalPrice(){
        //장바구니 리스트의 수량과 가격 합산하여 리턴
        return orderItemMap.entrySet().stream()
                .map(entry -> entry.getKey().getMenuPrice() * entry.getValue())
                .reduce(0.0,(sum, now) -> sum + now);
    }

    //할인정보 보여줌
    private void showDiscountInfo(){
        Arrays.stream(DiscountType.values())
                .forEach(value -> System.out.println(value.getTypeName()));
        System.out.println();
    }

    //할인 정보 포함하여 계산 후 결과 값 리턴
    private double getDiscountedPriceByNumber(){
        //선택된 숫자로 enum 의 discountType 중 하나 선택 후 할인률 계산
        return Arrays.stream(DiscountType.values())//enum 을 스트림으로 사용
                .filter(value -> value.getTypeNumber() == inputNumber) //조건
                .findAny()//하나의 값만 반환 -> 없으면 에러
                .map(type -> type.getDiscountedPrice(totalPrice))
                .orElse(totalPrice);// 계산 없을경우 기존 값 반화 -> 없으면 에러
    }
}
