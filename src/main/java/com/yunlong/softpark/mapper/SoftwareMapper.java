package com.yunlong.softpark.mapper;

import com.yunlong.softpark.entity.ColumnEntity;
import com.yunlong.softpark.entity.PlatesEntity;
import com.yunlong.softpark.entity.SoftwareEntity;
import com.yunlong.softpark.entity.SortEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SoftwareMapper {

    /**
     * 通过userId获取历史发布的软件信息
     * @param userId
     * @param min
     * @return
     */
    @Select("select \"SOFT_ID\",\"SOFT_NAME\",\"PARENT_ID\",\"SOFT_LOGO\",\"EDITION\"," +
            "\"LANGUAGE\",\"PLATFORM\",\"SOFT_SIZE\",\"SOFT_ADDR\",\"USER_ID\"," +
            "\"CREATE_TIME\",\"UPDATE_TIME\",\"VERIFY\",\"DOWNLOADS\"\n" +
            "from \"softpark\".\"SOFTWARE\" " +
            "where \"USER_ID\" = #{userId} order by \"CREATE_TIME\" desc limit #{min},10")
    List<SoftwareEntity> selectByAuthor(String userId, Integer min);

    @Select("select \"PLATE_ID\",\"PLATE_NAME\",\"PLATE_LOGO\"\n" +
            "from \"softpark\".\"PLATES\" limit 0,#{num};")
    List<PlatesEntity> getPlates(Integer num);

    @Select("select \"SORT_ID\",\"SORT_NAME\",\"PARENT_ID\",\"SORT_LOGO\"\n" +
            "from \"softpark\".\"SORT\" where PARENT_ID = #{platesId}")
    List<SortEntity> getSortEntity(Integer platesId);

    @Select("select \"COLUMN_ID\",\"COLUMN_NAME\",\"PARENT_ID\",\"COLUMN_LOGO\",\"COLUMN_WEB\",\"WEB_INTRODUCE\",\"COLUMN_TYPE\",\"LICENSE\",\"INTRODUCE_ID\",\"DOWNLOADS\",\"SHOW_PIC\",\"RECOMMEND\",\"VERIFY\"\n" +
            "from \"softpark\".\"COLUMN\"" +
            "where PARENT_ID = #{sortId} limit #{page},25 ")
    List<ColumnEntity> getColumnEntity(Integer sortId,Integer page);

    @Select("select \"COLUMN_ID\",\"COLUMN_NAME\",\"PARENT_ID\",\"COLUMN_LOGO\",\"COLUMN_WEB\",\"WEB_INTRODUCE\"," +
            "\"COLUMN_TYPE\",\"LICENSE\",\"INTRODUCE_ID\",\"DOWNLOADS\",\"SHOW_PIC\",\"RECOMMEND\"\n" +
            "from \"softpark\".\"COLUMN\" where RECOMMEND = 1")
    List<ColumnEntity> getRecommend();

    @Insert("insert into \"softpark\".\"SOFTWARE\"(\"SOFT_ID\", \"SOFT_NAME\", \"PARENT_ID\", " +
            "\"SOFT_LOGO\", \"EDITION\", \"LANGUAGE\", \"PLATFORM\", \"SOFT_SIZE\", \"SOFT_ADDR\", " +
            "\"USER_ID\", \"CREATE_TIME\", \"UPDATE_TIME\", \"VERIFY\", \"DOWNLOADS\", \"INSTALL_PROLEFT\", " +
            "\"SHOW_PIC\", \"INSTALL_PRORIGHT\", \"BRIEF_INTRO\") \n" +
            "VALUES( #{softId},#{softName},#{parentId},#{softLogo},#{edition},#{language},#{platform},#{softSize}," +
            "#{softAddr},#{userId},#{createTime},#{updateTime},#{verify},#{downloads},#{installProleft}," +
            "#{showPic},#{installProright},#{briefIntro} );")
    void insertSoft(String briefIntro, Date createTime, Integer downloads, String edition,
                    String installProleft, String installProright, String language, String parentId,
                    String platform, String showPic, String softAddr, String softId, String softLogo,
                    String softName, String softSize, Date updateTime, String userId, Integer verify);

    @Select("select *\n" +
            "from \"softpark\".\"SOFTWARE\" \n" +
            "where \"SOFT_NAME\" like '%'||#{key}||'%' or \"EDITION\" like '%'||#{key}||'%' or \"LANGUAGE\" like '%'||#{key}||'%' " +
            "or \"PLATFORM\" like '%'||#{key}||'%' " +
            "or \"BRIEF_INTRO\" like '%'||#{key}||'%';")
    List<SoftwareEntity> selectByKey(String key);

    @Select("select * from \"softpark\".\"SORT\" where \"PARENT_ID\"=#{plateId}")
    List<SortEntity> selectSortByPlate(Integer plateId);

    /**
     * 通过sortId获取column
     */
    @Select("select * from \"softpark\".\"COLUMN\" where \"PARENT_ID\"=#{sortId}")
    List<ColumnEntity> getColumnBySort(Integer sortId);

    /**
     * 根据id获取软件实体
     * @param softId
     * @return
     */
    @Select("select * from \"softpark\".\"SOFTWARE\" where \"SOFT_ID\"=#{softId}")
    SoftwareEntity selectBySoftId(String softId);

    /**
     * 根据id更新软件的下载次数
     * @param softId
     * @param downloads
     */
    @Update("update \"softpark\".\"SOFTWARE\" set \"DOWNLOADS\"= #{downloads} where \"SOFT_ID\" = #{softId}")
    void UpdateDownloads(String softId, int downloads);

    /**
     * 获取所有的分类
     * @return
     */
    @Select("select * from \"softpark\".\"SORT\" ")
    List<SortEntity> getSort();

    /**
     * 获取所有的column
     * @return
     */
    @Select("select * from \"softpark\".\"COLUMN\" where \"VERIFY\"=1")
    List<ColumnEntity> getColumn();

//    /**
//     * 通过userId获取历史发布的软件信息
//     * @param userId
//     * @param min
//     * @return
//     */
//    @Select("select \"SOFT_ID\",\"SOFT_NAME\",\"PARENT_ID\",\"SOFT_LOGO\",\"LICENSE\",\"EDITION\",\"SOFT_TYPE\"," +
//            "\"SOFT_WEB\",\"LANGUAGE\",\"PLATFORM\",\"SOFT_SIZE\",\"SOFT_ADDR\",\"USER_ID\",\"INTRODUCE_ID\"," +
//            "\"CREATE_TIME\",\"UPDATE_TIME\",\"VERIFY\",\"DOWNLOADS\"\n" +
//            "from \"softpark\".\"SOFTWARE\" " +
//            "where \"USER_ID\" = #{userId} order by \"CREATE_TIME\" desc limit #{min},10")
//    List<SoftwareEntity> selectByAuthor(String userId, Integer min);


    /**
     * 通过columnId查询软件信息
     * @param columnId
     * @return
     */
    @Select("select * from \"softpark\".\"SOFTWARE\" where \"PARENT_ID\" = #{columnId};")
    List<SoftwareEntity> selectByColumnId(String columnId);

//    /**
//     * 根据softId查询软件信息
//     * @param softId
//     * @return
//     */
//    @Select("select * from \"softpark\".\"SOFTWARE\" where \"SOFT_ID\" = #{softId};\n")
//    SoftwareEntity selectBySoftId(String softId);

    /**
     * 根据userid查找用户发布过的软件信息
     * @param userId
     * @return
     */
    @Select("select * from \"softpark\".\"SOFTWARE\" where \"USER_ID\" = #{userId};")
    List<SoftwareEntity> selectByUerId(String userId);

}
