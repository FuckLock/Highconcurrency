package com.bao.miaosha.test.commonClass.math;

public class TestMath {

    public static int rand(int a){
        return (int) (Math.random() * a + 1);
    }

    public static int randAB(int a, int b){
        return (int) ((Math.random() * Math.abs(a - b)) + Math.min(a, b));
    }

    public static void main(String[] args) {

        System.out.println(TestMath.rand(1000));
        System.out.println(TestMath.randAB(1, 700));
    }
}
