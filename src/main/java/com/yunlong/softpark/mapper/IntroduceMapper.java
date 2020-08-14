package com.yunlong.softpark.mapper;


import com.yunlong.softpark.dto.ColumnDetailDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author Cui
 * @email ${email}
 * @date 2020-07-21 16:54:16
 */
@Repository
public interface IntroduceMapper {

    /**
     * 增加栏目介绍
     * @param introduceId
     * @param briefIntro
     * @param function
     * @param character
     * @param updateLog
     */
    @Insert("insert into \"softpark\".\"INTRODUCE\"(\"INTRODUCE_ID\", \"BRIEF_INTRO\", \"FUNCTION\", \"CHARACTER\", \"UPDATE_LOG\") " +
            "VALUES(#{introduceId},#{briefIntro},#{function},#{character},#{updateLog})")
    void insertIntroduce(String introduceId, String briefIntro, String function, String character, String updateLog);

    /**
     * 根据IntroduceId搜索出简介单中栏目的相关详细信息
     * @param introduceId
     * @return
     */
    @Select("select \"BRIEF_INTRO\",\"FUNCTION\",\"CHARACTER\",\"INSTALL_ADDR\",\"UPDATE_LOG\"\n" +
            "from \"softpark\".\"INTRODUCE\" where \"INTRODUCE_ID\" = #{introduceId};")
    ColumnDetailDto selectDataForDetailColumn(String introduceId);
}
