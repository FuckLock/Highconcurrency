package com.bao.miaosha.test.Designpatterns.DynamicProxy;

public class StudentDaoImpl implements StudentDao{

    @Override
    public void add() {
        System.out.println("添加成功");
    }

    @Override
    public void delete() {
        System.out.println("删除成功");
    }

    @Override
    public void update() {
        System.out.println("更新成功");
    }

    @Override
    public void select() {
        System.out.println("查询成功");
    }
}
