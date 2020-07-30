package com.yunlong.softpark.mapper;

import com.yunlong.softpark.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import java.util.Date;

@Repository
public interface UserMapper {

    @Insert("insert into \"softpark\".\"USER\"(\"USER_ID\", \"USERNAME\", \"PHONE\", " +
            "\"EMAIL\", \"PASSWORD\" , \"CREATE_TIME\",\"UPDATE_TIME\") \n" +
            "VALUES(#{userId},#{username},#{phone},#{email},#{password}," +
            "#{createTime},#{updateTime});")
    void insertUser(String phone, String password, String userId, String username, String email, Date createTime,Date updateTime);

    @Select("select \"USER_ID\",\"USERNAME\",\"PHONE\",\"EMAIL\",\"PASSWORD\"," +
            "\"CREATE_TIME\",\"UPDATE_TIME\" " +
            "from \"softpark\".\"USER\" " +
            "where \"PHONE\"=#{phone};")
    UserEntity selectByPhone(String phone);

    @Update("update \"softpark\".\"USER\" " +
            "set \"PASSWORD\" = #{password} " +
            "where \"USER_ID\" = #{userId} ;")
    void updatePassword(String userId,String password);

    @Update("update \"softpark\".\"USER\" " +
            "set \"USERNAME\" = #{username}, \"EMAIL\" = #{email}, \"UPDATE_TIME\" = #{updateTime} " +
            "where \"USER_ID\" = #{userId};")
    void updateInfo(String username,String email,Date updateTime,String userId);

    @Select("select \"USER_ID\",\"USERNAME\",\"PHONE\",\"EMAIL\",\"PASSWORD\"," +
            "\"CREATE_TIME\",\"UPDATE_TIME\" " +
            "from \"softpark\".\"USER\" " +
            "where \"USER_ID\"=#{userId}")
    UserEntity selectById(String userId);

}
