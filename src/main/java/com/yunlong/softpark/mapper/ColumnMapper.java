package com.yunlong.softpark.mapper;

import com.yunlong.softpark.dto.ColumnSimDto;
import com.yunlong.softpark.dto.RankDto;
import com.yunlong.softpark.entity.ColumnEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColumnMapper {
    /**
     * 根据id查询栏目实体
     * @param columnId
     * @return
     */
    @Select("select * from \"softpark\".\"COLUMN\" where \"COLUMN_ID\"=#{columnId}")
    ColumnEntity selectByColumnId(String columnId);

    /**
     * 根据id更新栏目的下载次数
     * @param columnId
     * @param downloads
     */
    @Update("update \"softpark\".\"COLUMN\" set \"DOWNLOADS\"= #{downloads} where \"COLUMN_ID\" = #{columnId}")
    void UpdateDownloads(String columnId, int downloads);

    @Insert("insert into \"softpark\".\"COLUMN\"(\"COLUMN_ID\", \"COLUMN_NAME\", \"PARENT_ID\", \"COLUMN_LOGO\", \"COLUMN_WEB\", " +
            "\"WEB_INTRODUCE\", \"COLUMN_TYPE\", \"LICENSE\",\"INTRODUCE_ID\", \"DOWNLOADS\", \"SHOW_PIC\", \"RECOMMEND\", \"VERIFY\") \n" +
            "VALUES(#{columnId},#{columnName},#{parentId},#{columnLogo},#{columnWeb},#{webIntroduce},#{columnType},#{license},#{introduceId}," +
            "#{downloads},#{showPic},#{recommend},#{verify}) ")
    void insertColumn(String columnId, String columnName, int parentId, String columnType, String license, String columnLogo,
                      String columnWeb, String webIntroduce, String introduceId, String showPic, int downloads,
                      int recommend, int verify);

    /**
     * 搜索出栏目名，栏目logo，栏目下载量三个属性
     * @return
     */
    @Select("select \"COLUMN_NAME\",\"COLUMN_LOGO\",\"DOWNLOADS\"\n,\"COLUMN_ID\"" +
            "from \"softpark\".\"COLUMN\";\n")
    List<RankDto> selectDataForRank();

    /**
     * * 搜索出
     *      * 栏目名
     *      * 栏目logo
     *      * 栏目官网
     *      * 官方介绍
     *      * 授权信息
     *      * 下载次数
     *      * 页面展示
     * @param columnId
     * @return
     */
    @Select("select \"COLUMN_NAME\",\"COLUMN_LOGO\",\"COLUMN_WEB\",\"WEB_INTRODUCE\",\"LICENSE\",\"SHOW_PIC\",\"DOWNLOADS\",\"COLUMN_TYPE\"\n" +
            "from \"softpark\".\"COLUMN\" where \"COLUMN_ID\"=#{columnId};\n")
    ColumnSimDto selectDataForSimpleColumn(String columnId);

    /**
     * 根据columnId搜索出introduceId
     * @param columnId
     * @return
     */
    @Select("select \"INTRODUCE_ID\"\n" +
            "from \"softpark\".\"COLUMN\" where \"COLUMN_ID\" = #{columnId};")
    String selectIntroducIdBycolumnId(String columnId);

    /**
     * 根据columnId搜索出官网地址
     * @param columnId
     */
    @Select("select \"COLUMN_WEB\"\n" +
            "from \"softpark\".\"COLUMN\" where \"COLUMN_ID\"=#{columnId};\n" +
            "\n")
    String selectDataForBaseData(String columnId);
}
