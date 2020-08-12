package com.yunlong.softpark.mapper;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @Author 王海澎
 * @Date 2020/8/1 16:04
 * @Version 1.0
 */
@Repository
public interface FeedMapper {

    /**
     * 将前端传递的对网站的反馈存到库中
     * @param content
     */
    @Insert("insert into \"softpark\".\"FEED\"(\"CONTENT\", \"TIME\", \"VERIFY\") \n" +
            "VALUES(#{content},#{time},#{verify});\n")
    void insertFeed(String content, Date time, int verify);
}