package com.bao.miaosha.util;

import com.bao.miaosha.domain.MiaoshaUser;
import com.bao.miaosha.result.Result;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class UserUtil {

    private List<MiaoshaUser> users;
    private int count;
    private static final int DEFAULT_COUNT = 5000;
    private static Logger logger = LoggerFactory.getLogger(UserUtil.class);

    public UserUtil(){
       this(DEFAULT_COUNT);
    }

    public UserUtil(int count){
        this.count = count;
        users = new ArrayList<>(count);
    }

    private void createUserToDatebase(){
        users =  adduserToList();
        insertUser(DBUtil.connection());
        requestBrowswer();
    }

    public void insertUser(Connection connection) {
        String sql = "insert into miaosha_user(login_count, nickname, register_date, salt, password, phone)" +
                     "values(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            for(int i=0;i< users.size();i++) {
                MiaoshaUser user = users.get(i);
                pstmt.setInt(1, user.getLoginCount());
                pstmt.setString(2, user.getNickname());
                pstmt.setTimestamp(3, new Timestamp(user.getRegisterDate().getTime()));
                pstmt.setString(4, user.getSalt());
                pstmt.setString(5, user.getPassword());
                pstmt.setLong(6, user.getPhone());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            logger.error("sql 调用异常", e);
        }finally {
            if (pstmt != null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error("pstmt关闭异常", e);
                }
            }

            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("connection关闭异常", e);
                }
            }

        }

        logger.info("====================================== {}", "用户插入数据库成功");
    }

    private List<MiaoshaUser> adduserToList() {
        for (int i = 0; i < count; i++) {
            MiaoshaUser user = new MiaoshaUser();
            user.setPhone(13000000000L + i);
            user.setLoginCount(1);
            user.setNickname("user" + i);
            user.setRegisterDate(new Date());
            user.setSalt("1a2b3c");
            user.setPassword(MD5Util.inputPassToDbPass("123456", user.getSalt()));
            users.add(user);
        }
        logger.info("================================================= {}", "用户添加到list成功");
        return users;
    }

    public void requestBrowswer(){
        File file = new File("token.txt");
        if (file.exists()){
            file.delete();
        }
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(0);
        } catch (FileNotFoundException e) {
            logger.error("创建文件异常 {}", e);
        } catch (IOException e) {
            logger.error("randomAccessFile异常 {}", e);
        }

        for (MiaoshaUser  user:users){
            StringBuilder sb = new StringBuilder();
            Map<String, String> params = new HashMap<>();
            params.put("mobile", String.valueOf(user.getPhone()));
            params.put("password", MD5Util.inputPassToFormPass("123456"));
            String result = URLUtil.requestPost("http://localhost:8080/login/do_login", params);
            logger.info("======================== result", result);
            Result<String> stringResult = ObjectMapperUtil.stringToBean(result, Result.class);
            String token = stringResult.getData();


            String string = sb.append(user.getPhone()).append(",").append(token).toString();
            try {
                randomAccessFile.seek(randomAccessFile.length());
                randomAccessFile.write(string.getBytes());
                randomAccessFile.write("\r\n".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        IOUtil.close(randomAccessFile);
        System.out.println("over");
    }

    public static void main(String[] args) {
        UserUtil userUtil = new UserUtil();
        userUtil.createUserToDatebase();
    }
}
