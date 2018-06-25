package com.bao.miaosha.test.Designpatterns.Decorate;

public class TestDecorate {

    public static void main(String[] args) {
//        Phone p = new Iphone();
//        p.call();

        Phone p = new Iphone();
        PhoneDecorate phoneDecorate = new RingDecorate(p);
        phoneDecorate.call();
    }
}
