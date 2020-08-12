package com.yunlong.softpark.service.impl;

import com.yunlong.softpark.dto.CommentCheckDto;
import com.yunlong.softpark.entity.CommentEntity;
import com.yunlong.softpark.form.CommentForm;
import com.yunlong.softpark.mapper.CommentMapper;
import com.yunlong.softpark.redis.RedisRepository;
import com.yunlong.softpark.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    RedisRepository redisRepository;

    /**
     * 根据colomn获取评论信息
     *
     * @param columnId
     * @return
     * @throws ParseException
     */
    @Override
    public List<CommentCheckDto> getCommentData(String columnId) throws ParseException {
        List<CommentEntity> list =commentMapper.selectByColumnId(columnId);
        List<CommentCheckDto> checkDtos = new ArrayList<>();
        Collections.sort(list, new Comparator<CommentEntity>() {
            @Override
            public int compare(CommentEntity commentEntity, CommentEntity t1) {
                int flag = commentEntity.getTime().compareTo(t1.getTime());
                if (flag == -1){
                    return flag = 1;
                }else if (flag == 1){
                    return flag = -1;
                }else {
                    return flag;
                }
            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for(CommentEntity commentEntity:list){
            CommentCheckDto check = new CommentCheckDto();
            if (commentEntity.getVerify()!=0){
                check.setUser_id( commentEntity.getUserId());
                check.setContent(commentEntity.getContent());
                check.setDate(
                        sdf.format(commentEntity.getTime()));
                checkDtos.add(check);
            }
        }

        return checkDtos;
    }

    /**
     * 根据传入的评论的相关信息添加到库中
     *
     * @param commentForm,token
     */
    @Override
    public void insertCommentData(CommentForm commentForm, String token) {
        CommentEntity commentEntity = new CommentEntity();
        if (token !=null){
            String userId = redisRepository.selectAccessToken(token);
            commentEntity.setUserId(userId);
        }
        commentEntity.setColumnId(commentForm.getColumnId());
        commentEntity.setContent(commentForm.getContent());
        commentEntity.setCommentId(UUID.randomUUID().toString());
        commentEntity.setVerify(0);
        Date date = new Date();
        commentEntity.setTime(date);

        commentMapper.insertNewComment(commentEntity);
    }
}