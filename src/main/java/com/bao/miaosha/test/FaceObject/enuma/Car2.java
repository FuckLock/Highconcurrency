package com.bao.miaosha.test.FaceObject.enuma;

public enum Car2 {

    FONT("前"), LEFT("左"), RIGHT("右"), HOU("后");

    private String name;

    private Car2(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
