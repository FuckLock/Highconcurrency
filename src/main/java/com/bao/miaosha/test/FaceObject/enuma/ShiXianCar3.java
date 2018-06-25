package com.bao.miaosha.test.FaceObject.enuma;

public abstract class ShiXianCar3 {

    public static final ShiXianCar3 FONT = new ShiXianCar3("前") {
        @Override
        public void show() {
            System.out.println("前");
        }
    };
    public static final ShiXianCar3 LEFT = new ShiXianCar3("左") {
        @Override
        public void show() {
            System.out.println("左");
        }
    };
    public static final ShiXianCar3 RIGHT = new ShiXianCar3("右") {
        @Override
        public void show() {
            System.out.println("右");
        }
    };
    public static final ShiXianCar3 HOU = new ShiXianCar3("后") {
        @Override
        public void show() {
            System.out.println("后");
        }
    };

    private String name;

    private ShiXianCar3(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public abstract void show();
}
