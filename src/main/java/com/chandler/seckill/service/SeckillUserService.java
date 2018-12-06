package com.chandler.seckill.service;

import com.chandler.seckill.Exception.GlobalException;
import com.chandler.seckill.dao.SeckillUserDao;
import com.chandler.seckill.domain.SeckillUser;
import com.chandler.seckill.redis.RedisService;
import com.chandler.seckill.redis.SeckillUserKey;
import com.chandler.seckill.result.CodeMsg;
import com.chandler.seckill.util.MD5Util;
import com.chandler.seckill.util.UUIDUtil;
import com.chandler.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: Seckill
 * @Date: 2018/11/19
 * @Author: chandler
 * @Description:
 */
@Service
public class SeckillUserService {

    public static final String COOKI_NAME_TOKEN = "token";

    @Autowired
    SeckillUserDao seckillUserDao;

    @Autowired
    RedisService redisService;

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (response == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        SeckillUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbpass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass,saltDB);
        if (!dbpass.equals(calcPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response,token,user);
        return true;
    }

    /**
     * 获取Token信息
     * @param response
     * @param token
     * @return
     */
    public SeckillUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        SeckillUser user = redisService.get(SeckillUserKey.token,token,SeckillUser.class);
        //延长有效期
        if (user != null) {
            addCookie(response,token,user);
        }
        return user;
    }

    private void addCookie(HttpServletResponse response, String token, SeckillUser user) {
        redisService.set(SeckillUserKey.token,token,user);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN,token);
        cookie.setMaxAge(SeckillUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public SeckillUser getById(long id) {
        //取缓存
        SeckillUser user = redisService.get(SeckillUserKey.getById,""+id,SeckillUser.class);
        if (user != null) {
            return user;
        }
        //从数据库取
        user = seckillUserDao.getById(id);
        if (user != null) {
            redisService.set(SeckillUserKey.getById,""+id,user);
        }
        return user;
    }

    public boolean updatePassword(String token, long id, String formPass) {
        //取user
        SeckillUser user = getById(id);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新数据库
        SeckillUser toUpdate = new SeckillUser();
        toUpdate.setId(id);
        toUpdate.setPassword(MD5Util.formPassToDBPass(formPass,user.getSalt()));
        seckillUserDao.update(toUpdate);
        //处理缓存
        redisService.delete(SeckillUserKey.getById,""+id);
        user.setPassword(toUpdate.getPassword());
        redisService.set(SeckillUserKey.token,token,user);
        return true;
    }
}
