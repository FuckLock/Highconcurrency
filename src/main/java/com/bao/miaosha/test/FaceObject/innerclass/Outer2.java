package com.bao.miaosha.test.FaceObject.innerclass;

public class Outer2 {
    private int num3 = 30;
    private static int num4 = 40;

    public static class Inner2{
        private  int num = 10;
        private  static  int num2 = 20;

        public void show(){
            /**
             * 不可以
             */
//            System.out.println(Outer2.this.num3);
            System.out.println("show");
            System.out.println(Outer2.num4);
        }

        public static void show1(){
            System.out.println("show1");
            System.out.println(Outer2.num4);
        }
    }

}
