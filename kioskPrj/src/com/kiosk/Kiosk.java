package com.kiosk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Kiosk {

    int selectNum;

    //키오스크 실행 및 입력 부분
    public void start(Menu menu){
        //Scanner sc = new Scanner(System.in);
        Map<MenuItem, Integer> orderListMap = new HashMap<>();

        //카테고리 출력
        menu.showCategory(orderListMap.isEmpty());

        selectNum = 1;

        //메뉴 선택 후 장바구니 넣기
        if(selectNum < 4) {
            orderListMap = selectMenu(menu, orderListMap, selectNum);
        }else if(selectNum == 4){
            System.out.println(orderListMap);
        }

    }

    //메뉴 확인 후 장바구니 표시
    private Map<MenuItem, Integer> selectMenu(Menu menu, Map<MenuItem, Integer> orderListMap, int selectNum){
        //메뉴 표시
        List<MenuItem> menuList = menu.showAndGetMenuList(selectNum);

        selectNum = 2;

        MenuItem selectMenu = menuList.get(selectNum-1);

        System.out.println("선택한 메뉴 : " + menuList.get(selectNum-1).toString());
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인     2. 취소");

        selectNum = 1;

        if(selectNum == 1) orderListMap.put(selectMenu, 1);

        return orderListMap;
    }

    private Double showOrderList(Map<MenuItem, Integer> orderListMap){
        System.out.println("아래와 같이 주문 하시겠습니까?\n");
        System.out.println("[ Orders ]");
        orderListMap.entrySet().stream()
                .forEach(entry -> System.out.println(entry.getKey() + "  |  " + entry.getValue()+"개"));

        System.out.println("[ Total ]");
        double price =  orderListMap.entrySet().stream()
                .map(num -> num.getKey().getMenuPrice() * num.getValue())
                .reduce(0.0,(sum, now) -> sum + now);

        System.out.println("W " + price);

        selectNum = 1;

        if(selectNum == 1) return price;
        else return 0.0;

    }
}
