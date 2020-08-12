package com.yunlong.softpark.mapper;


import com.yunlong.softpark.entity.CommentEntity;
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
public interface CommentMapper{

    /**
     * 根据栏目id查询所有的评论信息
     * @param colomnId
     * @return
     */
    @Select("select * from \"softpark\".\"COMMENT\" where \"COLUMN_ID\"=#{columnId};")
    List<CommentEntity> selectByColumnId(String colomnId);

    @Insert("insert into \"softpark\".\"COMMENT\"(\"COMMENT_ID\", \"COLUMN_ID\", \"CONTENT\", \"TIME\", \"VERIFY\", \"USER_ID\") \n" +
            "VALUES(#{commentId},#{columnId},#{content},#{time},#{verify},#{userId});")
    void insertNewComment(CommentEntity commentEntity);
}
