package com.bao.miaosha.test.FaceObject.innerclass;

public class Outer {
    private int num = 10;

    public void method(){
        Inner i = new Inner() {
            @Override
            public void show1() {
                System.out.println("show1");
            }

            @Override
            public void show2() {
                System.out.println("show2");
            }
        };
        i.show1();
        i.show2();
    }
}
