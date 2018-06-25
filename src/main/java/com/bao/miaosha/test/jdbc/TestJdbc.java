package com.bao.miaosha.test.jdbc;


import com.bao.miaosha.result.Result;

import java.io.*;
import java.sql.*;

public class TestJdbc {

    public static void  selectUser(){
        Connection connection = DateBaseUtil.connection();
        String sql = "select * from users";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement =  connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1)
                        + resultSet.getString(2)
                        + resultSet.getString(3)
                        + resultSet.getDate(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void  inserttUser(){
        Connection connection = DateBaseUtil.connection();
        String sql = "insert into users(username, password, register_date)" +
                "values(?, ?, ?)";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement =  connection.prepareStatement(sql);
            preparedStatement.setObject(1, "bao");
            preparedStatement.setObject(2, "6666");
            preparedStatement.setObject(3, new Timestamp(System.currentTimeMillis()));
            int a = preparedStatement.executeUpdate();
            System.out.println(a);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void  updatetUser(){
        Connection connection = DateBaseUtil.connection();
        String sql = "update users set username = ? where id = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement =  connection.prepareStatement(sql);
            preparedStatement.setObject(1, "bao");
            preparedStatement.setObject(2, 23);
            int a = preparedStatement.executeUpdate();
            System.out.println(a);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void  deletetUser(){
        Connection connection = DateBaseUtil.connection();
        String sql = "delete from users where id = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement =  connection.prepareStatement(sql);
            preparedStatement.setObject(1, 23);
            int a = preparedStatement.executeUpdate();
            System.out.println(a);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void  selectUser1(){
        Connection connection = DateBaseUtil.connection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1)
                        + resultSet.getString(2)
                        + resultSet.getString(3)
                        + resultSet.getDate(4));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void  insertMostUser(int count){
        Connection connection = DateBaseUtil.connection();
        long start = System.currentTimeMillis();
        try {
            Statement statement = connection.createStatement();
            for (int i = 0; i < count; i++) {
                Timestamp a = new Timestamp(System.currentTimeMillis());
                statement.addBatch("insert into users(username, password, register_date) " +
                        "values('dong"+i+"', 6666, '"+ a +"')");
            }
            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("statement插入大量数据的时间是" + (end - start));
    }

    public static void  insertMostUser1(int count){
        Connection connection = DateBaseUtil.connection();
        long start = System.currentTimeMillis();
        try {
            String sql = "insert into users(username, password, register_date)" +
                    "values(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < count; i++) {
                preparedStatement.setObject(1, "gao" + i);
                preparedStatement.setObject(2, "6666");
                preparedStatement.setObject(3, new Timestamp(System.currentTimeMillis()));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("prestamen消耗的时间"+ (end - start));
    }

    public static void shiwu(){
        Connection connection = DateBaseUtil.connection();
        String sql1 = "insert into users(username, password, register_date)" +
                "values(?, ?, ?)";

        String sql2 = "insert into users(username, password, register_date)" +
                "values(?, ?, ?, ?)";

        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        try {
            preparedStatement1 =  connection.prepareStatement(sql1);
            preparedStatement1.setObject(1, "bao");
            preparedStatement1.setObject(2, "6666");
            preparedStatement1.setObject(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement1.executeUpdate();
            System.out.println("提交第一套记录");

            preparedStatement2 =  connection.prepareStatement(sql2);
            preparedStatement2.setObject(1, "大多数");
            preparedStatement2.setObject(2, "6666");
            preparedStatement2.setObject(3, new Timestamp(System.currentTimeMillis()));
             preparedStatement2.executeUpdate();
            System.out.println("提交第二套记录");
        } catch (SQLException e) {

        }

    }

    public static void  insertClob(){
        Connection connection = DateBaseUtil.connection();
        String sql = "insert into users(username, password, register_date, info)" +
                "values(?, ?, ?, ?)";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement =  connection.prepareStatement(sql);
            preparedStatement.setObject(1, "bao");
            preparedStatement.setObject(2, "6666");
            preparedStatement.setObject(3, new Timestamp(System.currentTimeMillis()));
            try {
//                preparedStatement.setClob(4, new FileReader("a.txt"));
                preparedStatement.setClob(4, new InputStreamReader(new ByteArrayInputStream("nihao".getBytes())));
            } catch (Exception e) {
                e.printStackTrace();
            }
            int a = preparedStatement.executeUpdate();
            System.out.println(a);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void  selectClob() {
        Connection connection = DateBaseUtil.connection();
        String sql = "select * from users where id = ?";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement =  connection.prepareStatement(sql);
            preparedStatement.setObject(1, 3034);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Clob clob = resultSet.getClob(5);
                Reader reader = clob.getCharacterStream();
                char[] chars = new char[1024];
                int len;
                while ((len = reader.read(chars)) != -1){
                    System.out.println(new String(chars, 0, len));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void  insertBlob(){
        Connection connection = DateBaseUtil.connection();
        String sql = "insert into users(username, password, register_date, image)" +
                "values(?, ?, ?, ?)";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement =  connection.prepareStatement(sql);
            preparedStatement.setObject(1, "bao");
            preparedStatement.setObject(2, "6666");
            preparedStatement.setObject(3, new Timestamp(System.currentTimeMillis()));
            try {
                preparedStatement.setBlob(4, new FileInputStream("dada.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            int a = preparedStatement.executeUpdate();
            System.out.println(a);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void  selectBlob(){
        Connection connection = DateBaseUtil.connection();
        String sql = "select * from users where id = ?";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement =  connection.prepareStatement(sql);
            preparedStatement.setObject(1, 3036);
            resultSet = preparedStatement.executeQuery();
            FileOutputStream fileOutputStream = new FileOutputStream("c.png");
            while (resultSet.next()){
                Blob blob = resultSet.getBlob("image");
                InputStream inputStream = blob.getBinaryStream();
                byte[] bytes = new byte[1024];
                int len;
                while ((len = inputStream.read(bytes)) != -1){
                    fileOutputStream.write(bytes);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        TestJdbc.selectUser();
//        TestJdbc.inserttUser();
//          TestJdbc.updatetUser();
//        TestJdbc.deletetUser();
//        TestJdbc.selectUser1();
//        TestJdbc.insertMostUser(500);
//        TestJdbc.insertMostUser1(500);
//        TestJdbc.shiwu();
//        TestJdbc.insertClob();
//        TestJdbc.selectClob();
//        TestJdbc.insertBlob();
        TestJdbc.selectBlob();
    }



}
