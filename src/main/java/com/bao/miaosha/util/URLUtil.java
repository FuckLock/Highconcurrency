package com.bao.miaosha.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class URLUtil {

    private static Logger logger = LoggerFactory.getLogger(URLUtil.class);

    public static String requestGet(String url){
        HttpURLConnection conn = null;
        BufferedOutputStream buffOut = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL requestUrl = new URL(url.trim());
            conn = (HttpURLConnection) requestUrl.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() == conn.HTTP_OK){
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
            }
        } catch (MalformedURLException e) {
            logger.error("get请求出现异常 {}", e);
        } catch (IOException e) {
            logger.error("get请求出现异常 {}", e);
        }finally {
            IOUtil.close(bufferedReader, buffOut);
            conn.disconnect();
        }
        return  sb.toString();
    }

    public static String requestPost(String url, Map<String, String> params){
        String requestParams = null;
        HttpURLConnection conn = null;
        URL requestUrl = null;
        PrintWriter pw = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder();
        if (params != null && params.size() > 0){
            requestParams = parseParams(params);
        }
        try {
            requestUrl = new URL(url);
            conn = (HttpURLConnection) requestUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            pw = new PrintWriter(conn.getOutputStream());
            pw.write(requestParams);
            pw.flush();
            if (conn.getResponseCode() == conn.HTTP_OK){
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
            }
        } catch (MalformedURLException e) {
            logger.error("post请求出现异常 {}", e);
        } catch (IOException e) {
            logger.error("post请求出现异常 {}", e);
        }finally {
            IOUtil.close(pw, bufferedReader);
            conn.disconnect();
        }

        return sb.toString();
    }

    private static String parseParams(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> map = iterator.next();
            sb.append(map.getKey()).append("=").append(map.getValue()).append("&");
        }

        return sb.substring(0, sb.lastIndexOf("&"));
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("u", "123");
        String str = URLUtil.requestPost("http://www.baidu.com", map);
        System.out.println(str);
    }
}
