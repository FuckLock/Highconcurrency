package com.bao.miaosha.test.HttpServer.version2;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.*;

public class Request {

    private String method;
    private String url;
    private Map<String, List<String>> params;

    private InputStream inputStream;
    private String requestInfo;
    public static final String CRLF="\r\n";

    public Request(){
        params = new HashMap<String, List<String>>();
    }

    public Request(Socket client){
        this();
        try {
            inputStream = client.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initRequestInfo();
        parseRequestInfo();
    }

    public void initRequestInfo(){
        byte[] bytes = new byte[23213];
        try {
            int len = inputStream.read(bytes);
            requestInfo = new String(bytes, 0, len);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseRequestInfo(){
        String firstLine = requestInfo.substring(0, requestInfo.indexOf(CRLF));
        method = firstLine.substring(0, firstLine.indexOf("/")).trim();
        String urlStr = firstLine.substring(firstLine.indexOf("/"), firstLine.indexOf("HTTP/")).trim();

        String paramsString = null;
        if (method.equalsIgnoreCase("post")){
            url = urlStr;
            paramsString = requestInfo.substring(requestInfo.lastIndexOf(CRLF));
        }

        if (method.equalsIgnoreCase("get")){
            if (urlStr.contains("?")){
                String[] str = urlStr.split("\\?");
                url = str[0];
                paramsString = str[1];
            }else {
                url = urlStr;
            }

        }

        parseParams(paramsString);
    }

    public void parseParams(String paramsString){
        /**
         * paramsString 类似于下面的值
         * paramsString = uname=123&pwd=213&fav=0&fav=1&fav=2
         */
        if (StringUtils.isBlank(paramsString)){
            return;
        }

        String[] strings = paramsString.split("&");

        for (int i = 0; i < strings.length; i++) {
            String[] strArr = strings[i].split("=");
            if (strArr.length  == 1){
//                String[] strings1 = new String[2];
//                System.arraycopy(strArr, 0, strings1, 0, 2);
//                strArr = strings1;
                strArr = Arrays.copyOf(strArr, 2);
            }

            String key = strArr[0].trim();
            String value = null;
            try {
                value = URLDecoder.decode(strArr[1], "gbk");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            List<String> valueList = new ArrayList<>();
            valueList.add(value);
            params.put(key, valueList);
        }
    }

    public String getParam(String key){
        if (params.containsKey(key)){
            List<String> keyList = params.get(key);
            return keyList.get(0);
        }
        return null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
