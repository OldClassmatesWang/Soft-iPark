package com.yunlong.softpark.service.impl;

import com.yunlong.softpark.dto.FeedbackDto;
import com.yunlong.softpark.entity.FeedbackEntity;
import com.yunlong.softpark.entity.SoftwareEntity;
import com.yunlong.softpark.form.FeedbackForm;
import com.yunlong.softpark.mapper.FeedMapper;
import com.yunlong.softpark.mapper.FeedbackMapper;
import com.yunlong.softpark.mapper.SoftwareMapper;
import com.yunlong.softpark.redis.RedisRepository;
import com.yunlong.softpark.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {


    @Autowired
    FeedbackMapper feedbackMapper;

    @Autowired
    RedisRepository redisRepository;

    @Autowired
    SoftwareMapper softwareMapper;

    @Autowired
    FeedMapper feedMapper;

    /**
     * 将传入的一条反馈信息添加到库中
     * @param feedbackForm
     */
    @Override
    public void insertFeedbackData(FeedbackForm feedbackForm, String token) {


        FeedbackEntity feedbackEntity = new FeedbackEntity();
        feedbackEntity.setContent(feedbackForm.getContent());
        feedbackEntity.setSoftId(feedbackForm.getSoftId());
        feedbackEntity.setTime(new Date());
        String userId  = null;
        if (token != null){
            userId = redisRepository.selectAccessToken(token);
        }
        feedbackEntity.setUserId(userId);

        feedbackMapper.insertNewFeedback(feedbackEntity);
    }

    /**
     * 根据前端传递的token返回对应用户发布的对应的软件信息
     *
     * @param token
     * @return
     */
    @Override
    public List<FeedbackDto> selectFeedBackDataByToken(String token) {
        if (token == null){
            return null;
        }else {
            //接收到token
            String userId =redisRepository.selectAccessToken(token);

            List<SoftwareEntity> list = softwareMapper.selectByUerId(userId);

            if (list == null){
                //用户没有发布过软件
                return null;
            }else {
                //用户发布过软件
                List<FeedbackDto> feedlist = new ArrayList<>();

                for (SoftwareEntity softwareEntity : list){

                    FeedbackDto feedbackDto = new FeedbackDto();
                    feedbackDto.setSoftName(softwareEntity.getSoftName());
                    feedbackDto.setEdition(softwareEntity.getEdition());
                    List<String> commentList = feedbackMapper.selectCommentListBySoftId(softwareEntity.getSoftId());

                    if(commentList == null||commentList.size()==0){
                        commentList = null;
                    }else{
                        feedbackDto.setCommentList(commentList);
                        feedlist.add(feedbackDto);
                    }
                }
                return feedlist;

            }


        }
    }

    /**
     * 接收前端传递对网站的反馈并存到库中
     *
     * @param
     */
    @Override
    public void insertFeedForWeb(String content ){
        Date time = new Date();
        int verify = 1;
        feedMapper.insertFeed(content,time,verify);
    }
}