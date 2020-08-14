package com.yunlong.softpark.controller;

import com.yunlong.softpark.core.support.web.controller.BaseController;
import com.yunlong.softpark.core.wrapper.ResultWrapper;
import com.yunlong.softpark.dto.FeedbackDto;
import com.yunlong.softpark.dto.UserInfo;
import com.yunlong.softpark.form.FeedBackDataForm;
import com.yunlong.softpark.form.FeedbackForm;
import com.yunlong.softpark.redis.RedisRepository;
import com.yunlong.softpark.service.FeedbackService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 
 *
 * @author Cui
 * @email ${email}
 * @date 2020-07-21 16:54:16
 */
@Api(value = "FeedbackController", tags = {"反馈API"})
@RestController
@Slf4j
@RequestMapping("/feedback")
public class FeedbackController extends BaseController<UserInfo> {

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    RedisRepository redisRepository;

    /**
     * 向FeedBack表中添加一条记录
     * @param feedbackForm
     * @return
     */
    @RequestMapping(path = "/insert",method = RequestMethod.POST)
    ResultWrapper insetFeedbackData(@RequestBody FeedbackForm feedbackForm, @RequestHeader("ANSWER_ACCESS_TOKEN") String token){

        if (feedbackForm.getSoftId().equals(" ")||feedbackForm.getSoftId().equals("")||feedbackForm.getSoftId()==null){
            return ResultWrapper.failure("失败！未传递softId");
        }else {
            if (feedbackForm.getContent().equals(" ")||feedbackForm.getContent().equals("")||feedbackForm.getContent()==null){
                return ResultWrapper.failure("失败！反馈内容为空！");
            }else {
                feedbackService.insertFeedbackData(feedbackForm,token);
                return  ResultWrapper.success();
            }

        }

    }

    /**
     * 只针对登录过的用户
     * 查询FeedBack表中的记录
     * @param token
     * @return
     */
    @RequestMapping(path = "/select" , method = RequestMethod.POST)
    ResultWrapper selectFeedbackByToken(@RequestHeader("ANSWER_ACCESS_TOKEN") String token){

        List<FeedbackDto> list = feedbackService.selectFeedBackDataByToken(token);
        if (list==null || list.size()==0){
            return ResultWrapper.failure("用户未发布过软件或没有软件反馈");
        }else {
            return ResultWrapper.successWithData(list);
        }
    }

    /**
     * 向Feed表中添加一条记录
     * @param feedBackDataForm
     * @return
     */
    @RequestMapping( path = "/insertForWeb" , method = RequestMethod.POST)
    ResultWrapper insertFeedForWeb(@RequestBody FeedBackDataForm feedBackDataForm){
        
        if(feedBackDataForm.getContent()==null||feedBackDataForm.getContent().equals(" ")||feedBackDataForm.getContent().equals("")){
            return ResultWrapper.failure("失败！反馈内容为空");
        }else {
            feedbackService.insertFeedForWeb(feedBackDataForm.getContent());
            return ResultWrapper.success();
        }

    }
}
