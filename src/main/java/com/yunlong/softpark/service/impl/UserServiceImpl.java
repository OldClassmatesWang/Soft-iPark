package com.yunlong.softpark.service.impl;

import com.yunlong.softpark.common.util.TokenUtil;
import com.yunlong.softpark.core.exception.SysException;
import com.yunlong.softpark.dto.*;
import com.yunlong.softpark.entity.SoftwareEntity;
import com.yunlong.softpark.entity.UserEntity;
import com.yunlong.softpark.form.*;
import com.yunlong.softpark.mapper.SoftwareMapper;
import com.yunlong.softpark.mapper.UserMapper;
import com.yunlong.softpark.redis.RedisRepository;
import com.yunlong.softpark.service.UserService;
import com.yunlong.softpark.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Cui
 * @Date: 2020/7/23
 * @Description:
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public SoftwareMapper softwareMapper;

    @Autowired
    public RedisRepository redisRepository;


    /**
     * 注册
     * @param registerForm
     * @return
     */
    @Override
    public MessageSuccessDto register(RegisterForm registerForm) {
        if(null == redisRepository.selectMessageCodeByPhone(registerForm.getPhone())){
            log.info("系统未能检测到您的验证码,请核对您的电话号码是否正确！");
            throw new SysException( "系统未能检测到您的验证码，请核对您的电话号码是否正确！" );
        }else if(!redisRepository.selectMessageCodeByPhone(registerForm.getPhone())
                .equals(registerForm.getCode())){
            log.info("验证码错误！");
            throw new SysException("验证码错误！");
        }else if(null != userMapper.selectByPhone(registerForm.getPhone())){
            log.info("该手机号已被注册！");
            throw new SysException("该手机号已被注册！");
        }else {
            log.info("验证成功！");
            //生成UUID
            String userId = UUID.randomUUID().toString().replace("-","");
            //默认用户名
            String username = "信创好友";
            //手机号
            String phone = registerForm.getPhone();
            //邮件
            String email = registerForm.getEmail();
            //密码
            String password = Md5Util.MD5Encode(registerForm.getPassword());
            //注册时间
            Date createTime = new Date();
            //修改时间
            Date updateTime = new Date();
            userMapper.insertUser(phone,password,userId,username,email,createTime,
                    updateTime);
            //构造返回值
            MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
            messageSuccessDto.setMessage("注册成功！");
            return messageSuccessDto;
        }
    }

    /**
     * 登录
     * @return
     */
    @Override
    public LoginSuccessDto login(LoginForm loginForm) {
        String result = Md5Util.MD5Encode(loginForm.getPassword());
        UserEntity userEntity = userMapper.selectByPhone(loginForm.getPhone());
        if(null == userEntity){
            log.info("用户不存在！");
            throw new SysException("用户不存在！");
        }else if(!userEntity.getPassword().equals(result)){
            log.info("账号或密码错误");
            throw new SysException("账号或密码错误");
        }else {
            //删除旧的token
            deleteToken(userEntity.getUserId());
            //得到一个accessToken，生成tokenInfo
            TokenInfo tokenInfo = new TokenInfo();
            tokenInfo.setAccessToken(TokenUtil.genToken());
            tokenInfo.setUserId(userEntity.getUserId());
            LoginSuccessDto loginSuccessDto = new LoginSuccessDto();
            loginSuccessDto.setAccessToken(tokenInfo.getAccessToken());
            //保存这个用户和token信息
            redisRepository.saveAccessToken(tokenInfo);
            redisRepository.saveLoginAccessToken(tokenInfo);
            System.out.println("token信息:"+tokenInfo.getAccessToken());
            return loginSuccessDto;
        }
    }

    /**
     * 忘记密码
     * @param forgetPasswordForm
     * @return
     */
    @Override
    public MessageSuccessDto forgetPassword(ForgetPasswordForm forgetPasswordForm) {
        UserEntity userEntity = userMapper.selectByPhone(forgetPasswordForm.getPhone());
        if(null == userEntity){
            log.info("用户不存在！");
            throw new SysException("用户不存在！");
        }else if(!redisRepository.selectMessageCodeByPhone(forgetPasswordForm.getPhone())
                .equals(forgetPasswordForm.getCode())){
            log.info("验证码错误！");
            throw new SysException("验证码错误！");
        }else {
            String result = Md5Util.MD5Encode(forgetPasswordForm.getPassword());
            userMapper.updatePassword(userEntity.getUserId(), result);
            MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
            messageSuccessDto.setMessage("修改成功！");
            return messageSuccessDto;
        }
    }

    /**
     * 修改个人中心
     * @param updateInfoForm
     * @param userId
     * @return
     */
    @Override
    public MessageSuccessDto updateInfo(UpdateInfoForm updateInfoForm,String userId) {
        userMapper.updateInfo(updateInfoForm.getUsername(),updateInfoForm.getEmail(),new Date(),userId);
        MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
        messageSuccessDto.setMessage("操作成功！");
        return messageSuccessDto;
    }

    /**
     * 通过旧密码修改密码
     * @param updatePassByOldForm
     * @param userId
     * @return
     */
    @Override
    public MessageSuccessDto updatePasswordByOld(UpdatePassByOldForm updatePassByOldForm, String userId) {
        UserEntity userEntity = userMapper.selectById(userId);
        String result = Md5Util.MD5Encode(updatePassByOldForm.getOldPassword());
        if(!result.equals(userEntity.getPassword())){
            throw new SysException("密码错误");
        }
        userMapper.updatePassword(userId,Md5Util.MD5Encode(updatePassByOldForm.getNewPassword()));
        //删除旧的token重新登录
        deleteToken(userId);
        MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
        messageSuccessDto.setMessage("修改成功！");
        return messageSuccessDto;
    }

    /**
     * 通过验证码修改密码
     * @param updatePassByCodeForm
     * @param userId
     * @return
     */
    @Override
    public MessageSuccessDto updatePasswordByCode(UpdatePassByCodeForm updatePassByCodeForm, String userId) {
        UserEntity userEntity = userMapper.selectById(userId);
        if(!redisRepository.selectMessageCodeByPhone(userEntity.getPhone())
                .equals(updatePassByCodeForm.getCode())){
            throw new SysException("验证码错误！");
        }
        //修改密码
        userMapper.updatePassword(userId,Md5Util.MD5Encode(updatePassByCodeForm.getPassword()));
        //删除旧的token重新登录
        deleteToken(userId);
        MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
        messageSuccessDto.setMessage("操作成功");
        return messageSuccessDto;
    }

    /**
     * 获取过去发表的软件
     * @param userId
     * @return
     */
    @Override
    public HistorySoftDto getHistoryPublished(String userId,Integer page) {
        //查询过去发布的软件
        List<SoftwareEntity> softs = softwareMapper.selectByAuthor(
                userId,(page-1)*10);
        //封装
        List<HistorySoft> historySofts = new ArrayList<>();
        for(SoftwareEntity s : softs){
            HistorySoft historySoft = new HistorySoft();
            historySoft.setEdition(s.getEdition());
            historySoft.setLanguage(s.getLanguage());
            historySoft.setPlatform(s.getPlatform());
            historySoft.setSoftLogo(s.getSoftLogo());
            historySoft.setSoftName(s.getSoftName());
            historySoft.setSoftSize(s.getSoftSize());
            historySoft.setUpdateTime(s.getUpdateTime());
            historySoft.setVerify(s.getVerify());
            historySoft.setDownloads(s.getDownloads());
            historySoft.setSoftId(s.getSoftId());
            historySofts.add(historySoft);
        }
        HistorySoftDto historySoftDto = new HistorySoftDto();
        historySoftDto.setList(historySofts);
        return historySoftDto;
    }

    /**
     * 获取个人中心
     * @param userId
     * @return
     */
    @Override
    public UserInfoDto getUserInfo(String userId) {
        UserEntity userEntity = userMapper.selectById(userId);
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUsername(userEntity.getUsername());
        userInfoDto.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(userEntity.getCreateTime()));
        userInfoDto.setPhone(userEntity.getPhone());
        userInfoDto.setEmail(userEntity.getEmail());
        return userInfoDto;
    }

    /**
     * 退出登录
     * @param userId
     */
    @Override
    public void logout(String userId) {
        deleteToken(userId);
    }

    /**
     * 验证码登录
     * @param loginCodeForm
     * @return
     */
    @Override
    public LoginSuccessDto login(LoginCodeForm loginCodeForm) {
        UserEntity userEntity = userMapper.selectByPhone(loginCodeForm.getPhone());
        if(null == userEntity){
            log.info("用户不存在！");
            throw new SysException("用户不存在！");
        }else if(!redisRepository.selectMessageCodeByPhone(loginCodeForm.getPhone())
                .equals(loginCodeForm.getCode())){
            log.info("验证码错误！");
            throw new SysException("验证码错误！");
        }else {
            //删除旧的token
            deleteToken(userEntity.getUserId());
            //得到一个accessToken，生成tokenInfo
            TokenInfo tokenInfo = new TokenInfo();
            tokenInfo.setAccessToken(TokenUtil.genToken());
            tokenInfo.setUserId(userEntity.getUserId());
            LoginSuccessDto loginSuccessDto = new LoginSuccessDto();
            loginSuccessDto.setAccessToken(tokenInfo.getAccessToken());
            //保存这个用户和token信息
            redisRepository.saveAccessToken(tokenInfo);
            redisRepository.saveLoginAccessToken(tokenInfo);
            System.out.println("token信息:"+tokenInfo.getAccessToken());
            return loginSuccessDto;
        }
    }

    //修改密码或异地登录删除旧的token
    private void deleteToken(String userId){
        String oldToken = redisRepository.selectLoginAccessToken(userId);
        if(null != oldToken){
            redisRepository.deleteAccessToken(oldToken);
            redisRepository.deleteLoginAccessToken(userId);
        }
    }
}
