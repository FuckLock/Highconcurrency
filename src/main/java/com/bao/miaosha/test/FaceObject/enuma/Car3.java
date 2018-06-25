package com.bao.miaosha.test.FaceObject.enuma;

public enum  Car3 {

    FONT("前") {
        @Override
        public void show() {
            System.out.println("前");
        }
    }, LEFT("左") {
        @Override
        public void show() {
            System.out.println("左");
        }
    }, RIGHT("右") {
        @Override
        public void show() {
            System.out.println("右");
        }
    }, HOU("后") {
        @Override
        public void show() {
            System.out.println("后");
        }
    };

    private String name;

    private Car3(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public abstract void show();
}
