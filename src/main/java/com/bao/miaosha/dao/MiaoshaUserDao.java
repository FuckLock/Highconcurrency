package com.bao.miaosha.dao;

import com.bao.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MiaoshaUserDao {
    @Select("select * from miaosha_user where phone = #{phone}")
    public MiaoshaUser getUserByPhone(@Param("phone")long phone);

    @Select("select * from miaosha_user where id = #{userId}")
    public MiaoshaUser getUserById(long userId);

    @Update("update miaosha_user set password = #{password} where id = #{id}")
    MiaoshaUser updatePassword(MiaoshaUser updateUser);
}
