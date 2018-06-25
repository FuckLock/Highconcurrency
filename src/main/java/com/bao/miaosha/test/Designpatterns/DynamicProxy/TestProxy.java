package com.bao.miaosha.test.Designpatterns.DynamicProxy;

import java.lang.reflect.Proxy;

public class TestProxy {

    public static void main(String[] args) {
//        StudentDao studentDao = new StudentDaoImpl();
//
//        studentDao.add();
//        studentDao.delete();
//        studentDao.update();
//        studentDao.select();

        StudentDao studentDao = new StudentDaoImpl();
        MyInvocationHandler<StudentDao> myInvocationHandler = new MyInvocationHandler<StudentDao>(studentDao);
        StudentDao proxy = (StudentDao)Proxy.newProxyInstance(studentDao.getClass().getClassLoader(), studentDao.getClass().getInterfaces(), myInvocationHandler);
        proxy.add();
        proxy.delete();
        proxy.update();
        proxy.select();


    }
}


