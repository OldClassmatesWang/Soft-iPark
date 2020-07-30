package com.yunlong.softpark.service;

import com.yunlong.softpark.dto.*;
import com.yunlong.softpark.form.PublishedForm;
import org.springframework.stereotype.Component;

@Component("softwareService")
public interface SoftwareService {

    FirstMajorDto getFirstMajor();

    SoftMajorDto getMajorSoft(Integer page,Integer plateId);

    MessageSuccessDto publishedSoft(PublishedForm publishedForm,String userId);

    SearchDto search(String key);

    SearchSortByPlateDto getSort(Integer plateId);

    SearchColumnDto getColumnBySort(Integer sortId,Integer page);

    void download(String softId);
}
