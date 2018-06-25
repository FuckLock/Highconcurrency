package com.bao.miaosha.test.Designpatterns.Decorate;

public class RingDecorate extends PhoneDecorate {

    public RingDecorate(Phone phone) {
        super(phone);
    }

    @Override
    public void call() {
        System.out.println("手机可以听彩铃");
        super.call();
    }
}
