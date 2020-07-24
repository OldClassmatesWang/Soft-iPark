package com.yunlong.softpark.service.impl;

import com.yunlong.softpark.dto.RotationDto;
import com.yunlong.softpark.entity.RotationEntity;
import com.yunlong.softpark.mapper.RotationMapper;
import com.yunlong.softpark.service.RotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Cui
 * @Date: 2020/7/24
 * @Description:
 */
@Service
public class RotationServiceImpl implements RotationService {

    @Autowired
    RotationMapper rotationMapper;

    /**
     * 获取轮播图信息
     * @return
     */
    @Override
    public List<RotationDto> getRotation() {
        List<RotationEntity> rotationInfo = rotationMapper.getRotationInfo();
        List<RotationDto> rotationDtoList = new ArrayList<>();
        for(RotationEntity r : rotationInfo){
            RotationDto rotationDto = new RotationDto();
            rotationDto.setRotationAddr(r.getRotationAddr());
            rotationDto.setSoftwareId(r.getSoftwareId());
            rotationDto.setSort(r.getSort());
            rotationDto.setSoftName(r.getSoftName());
            rotationDtoList.add(rotationDto);
        }
        return rotationDtoList;
    }
}
