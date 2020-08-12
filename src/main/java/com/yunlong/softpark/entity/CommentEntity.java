package com.yunlong.softpark.entity;

/**
 * @Author 王海澎
 * @Date 2020/7/27 16:41
 * @Version 1.0
 */

import lombok.Data;

import java.util.Date;

/**
 * 评论的实体类
 *
 */
@Data
public class CommentEntity {

    /**
     * 评论id
     */
    private String commentId;

    /**
     * 栏目id
     */
    private String columnId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private Date time;

    /**
     * 评论是否授权
     */
    private int verify;

    /**
     * 评论的用户id
     */
    private String userId;



}
