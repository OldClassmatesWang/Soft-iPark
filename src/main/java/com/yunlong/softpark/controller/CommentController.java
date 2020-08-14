package com.yunlong.softpark.controller;

import com.yunlong.softpark.core.support.web.controller.BaseController;
import com.yunlong.softpark.core.wrapper.ResultWrapper;
import com.yunlong.softpark.dto.CommentCheckDto;
import com.yunlong.softpark.dto.UserInfo;
import com.yunlong.softpark.form.CommentForm;
import com.yunlong.softpark.form.CommentGetForm;
import com.yunlong.softpark.service.CommentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * 
 *
 * @author Cui
 * @email ${email}
 * @date 2020-07-21 16:54:16
 */
@Api(value = "CommentController", tags = {"评论API"})
@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentController extends BaseController<UserInfo> {

    @Autowired
    CommentService commentService;

    /**
     * 不需要token
     * 接收前端传递的colomn_id，并根据colomn_id查询所有该栏目下的评论
     * 返回含（content，time，user_id）的list集合
     *
     * @param commentGetForm
     * @return
     */
    @RequestMapping(path = "/check",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultWrapper getCommentData(@RequestBody CommentGetForm commentGetForm) throws ParseException {
        if (commentGetForm.getColumnId().equals("")||commentGetForm.getColumnId()==null||commentGetForm.equals(" ")){
            return ResultWrapper.failure("失败，未传递columnId");
        }else {
            List<CommentCheckDto> list = commentService.getCommentData(commentGetForm.getColumnId());
            return ResultWrapper.successWithData(list);
        }

    }

    /**
     * 需要token
     * 接收前端传递的评论信息（userId,content，colomnId）
     * 向数据库中添加一条新的评论
     * 成功则返回Succes
     * @param commentForm
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/insert",method = RequestMethod.POST)
    public ResultWrapper insertCommentData(@RequestBody CommentForm commentForm, @RequestHeader("ANSWER_ACCESS_TOKEN")String token) {

        if (commentForm.getColumnId()!=null){
            if (commentForm.getContent()==null||commentForm.getContent().equals("")||commentForm.getContent().equals(" ")){
               return ResultWrapper.success("失败！评论值为空！");
            }else {
                commentService.insertCommentData(commentForm,token);
                return ResultWrapper.success();
            }
        }else {
            return ResultWrapper.failure("失败！为传递columnId为空！");
        }
    }

}
