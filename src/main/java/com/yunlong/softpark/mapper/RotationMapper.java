package com.yunlong.softpark.mapper;

import com.yunlong.softpark.entity.RotationEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RotationMapper {


    @Select("select \"ROTATION_ID\",\"SOFTWARE_ID\",\"ROTATION_ADDR\",\"SORT\",\"IS_ROTATION\"," +
            "\"SOFT_NAME\"\n" +
            "from \"softpark\".\"ROTATION\" where \"IS_ROTATION\" = 1;")
    List<RotationEntity> getRotationInfo();
}
