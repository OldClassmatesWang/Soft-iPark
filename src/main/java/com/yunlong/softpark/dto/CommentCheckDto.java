package com.yunlong.softpark.dto;

import lombok.Data;

/**
 * @Author 王海澎
 * @Date 2020/7/27 16:24
 * @Version 1.0
 */
@Data
public class CommentCheckDto {

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private String date;

    /**
     *用户id
     */
    private String user_id;

}
