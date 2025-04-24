package com.kiosk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    //menuItem List 로 관리

    private List<MenuItem> menuList = new ArrayList<>();
    Map<Integer, String> categoryNameMap = new HashMap<>() {{
        put(1, "[ BURGERS MENU ]");
        put(2, "[ DRINKS MENU ]");
        put(3, "[ DESSERTS MENU ]");
        put(4, "[ Orders ]");
        put(5, "[ ORDER MENU ]");
    }};

    Map<Integer, List<MenuItem>> categoryListMap = new HashMap<>() {{
        put(1, getBurgerMenuList());
        put(2, getDrinkMenuList());
        put(3, getDessertMenuList());
    }};

    public Menu() {
    }

    public List<MenuItem> getBurgerMenuList() {
        menuList.add(new MenuItem("ShackBurger ", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"));
        menuList.add(new MenuItem("SmokeShack  ", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"));
        menuList.add(new MenuItem("Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"));
        menuList.add(new MenuItem("Hamburger   ", 5.4, "비프패티를 기반으로 야채가 들어간 기본버거"));
        return menuList;
    }
    public List<MenuItem> getDrinkMenuList() {
        menuList.add(new MenuItem("Coke   ", 6.9, "콜라"));
        menuList.add(new MenuItem("Sprite ", 8.9, "사이다"));
        menuList.add(new MenuItem("Fanta  ", 6.9, "환타"));
        menuList.add(new MenuItem("Beer   ", 5.4, "맥주"));
        return menuList;
    }

    public List<MenuItem> getDessertMenuList() {
        menuList.add(new MenuItem("Fries   ", 6.9, "감자튀김"));
        menuList.add(new MenuItem("Tender  ", 8.9, "치킨 텐더"));
        menuList.add(new MenuItem("Coleslaw", 6.9, "코울슬로"));
        menuList.add(new MenuItem("Egg Tart", 5.4, "에그타르트"));
        return menuList;
    }


    public List<MenuItem> showAndGetMenuList(int selectNum){

        System.out.println(categoryNameMap.get(selectNum));

        List<MenuItem> menuItemList = categoryListMap.get(selectNum);
        menuItemList.stream().
                forEach(item -> System.out.println(item.toString()));

        System.out.println("0. 뒤로가기");

        return menuItemList;

    }

    public void showCategory(boolean isMapEmpty) {
        System.out.println("[ MAIN MENU ]");
        System.out.println("1. Burgers");
        System.out.println("2. Drinks");
        System.out.println("3. Desserts");
        System.out.println("0. 종료    | 종료합니다.\n");

        if(!isMapEmpty){
            System.out.println("[ ORDER MENU ]");
            System.out.println("4. Orders       | 장바구니를 확인 후 주문합니다.");
            System.out.println("5. Cancel       | 진행중인 주문을 취소합니다.");
        }

    }
}
