package com.yunlong.softpark.service;

import com.yunlong.softpark.dto.CommentCheckDto;
import com.yunlong.softpark.form.CommentForm;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;

/**
 * 
 *
 * @author Cui
 * @email ${email}
 * @date 2020-07-21 16:54:16
 */
@Component("commentService")
public interface CommentService {

    /**
     * 根据colomn获取评论信息
     * @param columnId
     * @return
     * @throws ParseException
     */
    List<CommentCheckDto> getCommentData(String columnId) throws ParseException;

    /**
     * 将传入的评论的相关信息添加到库中
     * @param commentForm
     */
    void insertCommentData(CommentForm commentForm, String token);

}

