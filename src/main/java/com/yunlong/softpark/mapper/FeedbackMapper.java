package com.yunlong.softpark.mapper;


import com.yunlong.softpark.entity.FeedbackEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author Cui
 * @email ${email}
 * @date 2020-07-21 16:54:16
 */
@Repository
public interface FeedbackMapper{

    /**
     * 将传入的一条反馈信息存到库中
     * @param feedbackEntity
     */
    @Insert("insert into \"softpark\".\"FEEDBACK\"(\"CONTENT\", \"TIME\", \"USER_ID\", \"SOFT_ID\") \n" +
            "VALUES(#{content},#{time},#{userId},#{softId});")
    void insertNewFeedback(FeedbackEntity feedbackEntity);

    /**
     * 根据传入的softId寻找它的所有反馈信息
     * @param softId
     * @return
     */
    @Select("select \"CONTENT\" from \"softpark\".\"FEEDBACK\" where \"SOFT_ID\" = #{softId};")
    List<String> selectCommentListBySoftId(String softId);
}
