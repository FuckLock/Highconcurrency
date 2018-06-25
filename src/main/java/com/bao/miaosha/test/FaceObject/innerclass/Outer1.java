package com.bao.miaosha.test.FaceObject.innerclass;

public class Outer1 {

    private int num = 10;
    private static int staticNum = 40;

    //内部类一半定义为private这里是为了测试，在方法内部在提供个方法去访问
    public class  Inner1{
        private int num = 30;

        /**
         *  不可以这么做。因为在外部类加载到时候，只要外部类没有示例花这个类上不会加载到，可是static会加载，那就矛盾了
         */
//        private static int staticNum = 40;

        public void show(){
            int num = 20;
            System.out.println(Outer1.this.num);
            System.out.println(num);
            System.out.println(this.num);
            System.out.println(staticNum);
        }

        /**
         *
         * 同上
         */


//        public static  void show1(){
//            int num = 20;
//            System.out.println(Outer1.this.num);
//            System.out.println(num);
//            System.out.println(this.num);
//            System.out.println(staticNum);
//        }

    }

}
