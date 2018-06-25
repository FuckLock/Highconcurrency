package com.bao.miaosha.util;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class ObjectMapperUtil {

    private static ObjectMapper objectMapper;
    private  static Logger logger = LoggerFactory.getLogger(ObjectMapperUtil.class);

    static {
        objectMapper = new ObjectMapper();

        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);

        //取消默认转换timestamps形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);

        //忽略空Bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);

        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateUtil.DEFAULT_FORMAT));

        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    public static <T> String beanToString(T obj){
        if (obj == null){
            return null;
        }

        try {
            return obj.getClass().equals(String.class) ? (String)obj : objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            logger.error("parse bean to String error {}", e);
        }

        return null;
    }

    public static <T> T stringToBean(String str, Class<T> clazz){
        if (StringUtils.isEmpty(str) || clazz == null){
            return null;
        }

        try {
            return str.getClass().equals(clazz) ? (T)str : objectMapper.readValue(str, clazz);
        } catch (IOException e) {
            logger.error("parse string to bean error {}", e);
        }

        return null;
    }
}
