package com.bao.miaosha.test.FaceObject.enuma;

public class TestEnum {
    public static void main(String[] args) {
    /**
     * 通过这里我们知道美剧累实现了toString方法所以打印都不是对象地址
     * 本质美剧累就是个static final
     */
        System.out.println(Car1.FONT);
        System.out.println(ShiXianCar1.FONT);

        System.out.println(Car2.FONT.getName());
        System.out.println(ShiXianCar2.FONT.getName());

        Car3.FONT.show();
        ShiXianCar3.FONT.show();
    }
}
