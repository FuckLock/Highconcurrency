package com.bao.miaosha.test.FaceObject.enuma;

public class ShiXianCar2 {

    public static final ShiXianCar2 FONT = new ShiXianCar2("前");
    public static final ShiXianCar2 LEFT = new ShiXianCar2("左");
    public static final ShiXianCar2 RIGHT = new ShiXianCar2("右");
    public static final ShiXianCar2 HOU = new ShiXianCar2("后");

    private String name;

    private ShiXianCar2(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}

