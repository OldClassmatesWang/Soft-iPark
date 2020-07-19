package com.yunlong.softpark.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface test {

//    @Select("select name,num,gender from test.city")
    @Select("select \"name\",\"num\",\"gender\" from \"test\".\"city\"")
    List<city> findN();
}
