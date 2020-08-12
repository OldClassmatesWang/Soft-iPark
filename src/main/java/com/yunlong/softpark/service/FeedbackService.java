package com.yunlong.softpark.service;

import com.yunlong.softpark.dto.FeedbackDto;
import com.yunlong.softpark.form.FeedbackForm;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 *
 * @author Cui
 * @email ${email}
 * @date 2020-07-21 16:54:16
 */
@Component("feedbackService")
public interface FeedbackService{

    /**
     * 将传入的反馈信息添加到库中
     * @param feedbackForm
     */
    void insertFeedbackData(FeedbackForm feedbackForm, String token);

    /**
     * 根据前端传递的token返回对应用户发布的对应的软件信息
     * @param token
     * @return
     */
    List<FeedbackDto> selectFeedBackDataByToken(String token);

    /**
     * 接收前端传递对网站的反馈并存到库中
     * @param centent
     */
    void insertFeedForWeb(String centent);
}
