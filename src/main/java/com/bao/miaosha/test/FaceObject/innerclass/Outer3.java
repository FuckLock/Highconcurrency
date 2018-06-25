package com.bao.miaosha.test.FaceObject.innerclass;

public class Outer3 {
    private int num = 10;

    public void show(){
        final int num1 = 20;
        class Inner3{
            public void innerShow(){
                System.out.println(Outer3.this.num);
                System.out.println(num1);
            }
        }
        Inner3 inner3 = new Inner3();
        inner3.innerShow();
    }
}
