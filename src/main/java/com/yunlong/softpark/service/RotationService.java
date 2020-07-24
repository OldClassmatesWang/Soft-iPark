package com.yunlong.softpark.service;

import com.yunlong.softpark.dto.RotationDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("rotationService")
public interface RotationService {

    List<RotationDto> getRotation();
}
