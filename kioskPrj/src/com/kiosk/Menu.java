package com.kiosk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private List<MenuItem> menuList = new ArrayList<>();

    //메뉴 정보 Map
    private Map<Integer, String> categoryNameMap = new HashMap<>() {{
        put(1, "[ BURGERS MENU ]");
        put(2, "[ DRINKS MENU ]");
        put(3, "[ DESSERTS MENU ]");
    }};

    public Menu() {
    }

    //버거 리스트
    public List<MenuItem> getBurgerMenuList() {
        menuList.add(new MenuItem(1,"ShackBurger ", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"));
        menuList.add(new MenuItem(2,"SmokeShack  ", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"));
        menuList.add(new MenuItem(3,"Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"));
        menuList.add(new MenuItem(4,"Hamburger   ", 5.4, "비프패티를 기반으로 야채가 들어간 기본버거"));
        return menuList;
    }
    
    //음료 리스트
    public List<MenuItem> getDrinkMenuList() {
        menuList.add(new MenuItem(11,"Coke   ", 6.9, "콜라"));
        menuList.add(new MenuItem(12,"Sprite ", 8.9, "사이다"));
        menuList.add(new MenuItem(13,"Fanta  ", 6.9, "환타"));
        menuList.add(new MenuItem(14,"Beer   ", 5.4, "맥주"));
        return menuList;
    }

    //디저트 리스트
    public List<MenuItem> getDessertMenuList() {
        menuList.add(new MenuItem(21,"Fries   ", 6.9, "감자튀김"));
        menuList.add(new MenuItem(22,"Tender  ", 8.9, "치킨 텐더"));
        menuList.add(new MenuItem(23,"Coleslaw", 6.9, "코울슬로"));
        menuList.add(new MenuItem(24,"Egg Tart", 5.4, "에그타르트"));
        return menuList;
    }


    //선택한 카테고리의 메뉴판 보여주고 리턴
    public List<MenuItem> showAndGetMenuList(int selectNum){

        System.out.println(categoryNameMap.get(selectNum));

        List<MenuItem> menuItemList = categoryList(selectNum);
        menuItemList.stream().
                forEach(item -> System.out.println(item.toString()));

        System.out.println("0. 뒤로가기");

        return menuItemList;

    }

    //카테고리 종류 선택
    private List<MenuItem> categoryList(int selectNum){
        switch (selectNum){
            case 1 : return getBurgerMenuList();
            case 2 : return getDrinkMenuList();
            case 3 : return getDessertMenuList();
            default: return null;
        }
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
            System.out.println("5. Cancel       | 진행중인 주문을 취소합니다.\n");
        }

    }
}
