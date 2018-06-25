package com.bao.miaosha.service;

import com.bao.miaosha.Vo.LoginVo;
import com.bao.miaosha.dao.MiaoshaUserDao;
import com.bao.miaosha.domain.MiaoshaUser;
import com.bao.miaosha.exception.GlobalException;
import com.bao.miaosha.redis.MiaoshaUserKey;
import com.bao.miaosha.redis.RedisService;
import com.bao.miaosha.result.CodeMsg;
import com.bao.miaosha.result.Result;
import com.bao.miaosha.util.CookieUtil;
import com.bao.miaosha.util.MD5Util;
import com.bao.miaosha.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {

    @Autowired
    private MiaoshaUserDao miaoshaUserDao;

    @Autowired
    private RedisService redisService;

    public MiaoshaUser getByPhone(long phone) {
        return miaoshaUserDao.getUserByPhone(phone);
    }

    public MiaoshaUser getUserById(String userId){
        //先从缓存取
        MiaoshaUser user = redisService.get(MiaoshaUserKey.id, userId, MiaoshaUser.class);
        if (user != null){
            return user;
        }
        //为空到数据库取
        user = miaoshaUserDao.getUserById(Long.parseLong(userId));
        if (user != null){
            user.setPassword(null);
            redisService.set(MiaoshaUserKey.id, userId, user);
        }

        return user;
    }

    public Result<CodeMsg> updatePassword(String userId, String NewPassword, String token){
        if (StringUtils.isEmpty(token)){
            return Result.error(CodeMsg.TOKEN_ERROR);
        }

        MiaoshaUser user = getUserById(userId);
        if (user == null){
            return Result.error(CodeMsg.USER_NOT_EXIST);
        }

        //更新数据库
        MiaoshaUser updateUser = new MiaoshaUser();
        updateUser.setId(user.getId());
        updateUser.setPassword(MD5Util.inputPassToDbPass(NewPassword, user.getSalt()));
        MiaoshaUser NewUser = miaoshaUserDao.updatePassword(updateUser);

        if (NewUser == null){
            return Result.error(CodeMsg.UPDATE_PASSWORD_FAIL);
        }

        //更新成功处理缓存
        redisService.set(MiaoshaUserKey.id, userId, user);
        NewUser.setPassword(null);
        redisService.set(MiaoshaUserKey.token, token, NewUser);

        return Result.success(CodeMsg.UPDATE_PASSWORD_SUCCESS);
    }

    public Result<String> login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }

        String moblie = loginVo.getMobile();
        String password = loginVo.getPassword();
        MiaoshaUser user = getByPhone(Long.valueOf(moblie));
        if (user == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }

        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        String str = MD5Util.formPassToDBPass(password, dbSalt);
        if (!str.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        user.setPassword(null);

        String token = UUIDUtil.uuid();
        saveUserToRedis(user, token);
        CookieUtil.addCookie(response, token);

        return Result.success(token);
    }

    private void saveUserToRedis(MiaoshaUser user, String token) {
        redisService.set(MiaoshaUserKey.token, token, user);
    }

    public MiaoshaUser getUserByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        CookieUtil.addCookie(response, token);
        return user;
    }
}
