package com.yunlong.softpark.service.impl;

import com.yunlong.softpark.dto.TokenInfo;
import com.yunlong.softpark.redis.RedisRepository;
import com.yunlong.softpark.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 宋万顷
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    private RedisRepository redisRepository;


    @Override
    public TokenInfo getTokenInfo(String accessToken) {
        String userId = redisRepository.selectAccessToken(accessToken);
       // User selectByphone = userMapper.selectByPhone ( phone );
        if (userId != null) {
            TokenInfo tokenInfo = new TokenInfo();
            tokenInfo.setUserId(userId);
            tokenInfo.setAccessToken(accessToken);
            return tokenInfo;
        }
        return null;
    }
}
