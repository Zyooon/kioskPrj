package com.kiosk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Menu {

    //메뉴 정보 Map 속성
    private static Map<Integer, String> menuCategory = new HashMap<>() {{
        put(1, "[ BURGERS MENU ]");
        put(2, "[ DRINKS MENU ]");
        put(3, "[ DESSERTS MENU ]");
    }};

    private static List<MenuItem> burgerMenuList = new ArrayList<>() {{
        add(new MenuItem("ShackBurger ", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"));
        add(new MenuItem("SmokeShack  ", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"));
        add(new MenuItem("Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"));
        add(new MenuItem("Hamburger   ", 5.4, "비프패티를 기반으로 야채가 들어간 기본버거"));
    }};

    private static List<MenuItem> drinkMenuList = new ArrayList<>(){{
        add(new MenuItem("Coke   ", 6.9, "콜라"));
        add(new MenuItem("Sprite ", 8.9, "사이다"));
        add(new MenuItem("Fanta  ", 6.9, "환타"));
        add(new MenuItem("Beer   ", 5.4, "맥주"));
    }};

    private static List<MenuItem> dessertMenuList = new ArrayList<>(){{
        add(new MenuItem("Fries   ", 6.9, "감자튀김"));
        add(new MenuItem("Tender  ", 8.9, "치킨텐더"));
        add(new MenuItem("Coleslaw", 6.9, "코울슬로"));
        add(new MenuItem("Egg Tart", 5.4, "에그타르트"));
    }};

    //기본 생성자
    public Menu() {
    }

    //리스트 Getter
    //버거 리스트
    public List<MenuItem> getBurgerMenuList() {
        return burgerMenuList;
    }
    
    //음료 리스트
    public List<MenuItem> getDrinkMenuList() {
        return drinkMenuList;
    }

    //디저트 리스트
    public List<MenuItem> getDessertMenuList() {
        return dessertMenuList;
    }


    //선택한 카테고리의 메뉴판 보여주고 리턴
    public void showMenuList(List<MenuItem> menuItemList, int selectNum){

        System.out.println(menuCategory.get(selectNum));

        IntStream.range(0, menuItemList.size())
                .forEach(item -> System.out.println(item+1 + ". "+ menuItemList.get(item).toString()));

        System.out.println("0. 뒤로가기");
    }

    //카테고리 종류 선택
    public List<MenuItem> getMenuItemList(int selectNum){
        switch (selectNum){
            case 1 : return getBurgerMenuList();
            case 2 : return getDrinkMenuList();
            case 3 : return getDessertMenuList();
            default: return null;
        }
    }

    //카테고리 목록 보여주기
    public void showCategory(boolean isMapEmpty) {
        System.out.println("[ MAIN MENU ]");
        System.out.println("1. Burgers");
        System.out.println("2. Drinks");
        System.out.println("3. Desserts");
        System.out.println("0. 종료    | 종료합니다.\n");

        if(!isMapEmpty){
            System.out.println("[ ORDER MENU ]");
            System.out.println("4. Orders       | 장바구니를 확인 후 주문합니다.");
            System.out.println("5. Cancel       | 진행중인 주문을 취소합니다.\n");
        }

    }
}
