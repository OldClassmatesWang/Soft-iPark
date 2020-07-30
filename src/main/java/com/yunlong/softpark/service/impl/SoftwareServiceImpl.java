package com.yunlong.softpark.service.impl;

import com.yunlong.softpark.dto.*;
import com.yunlong.softpark.entity.ColumnEntity;
import com.yunlong.softpark.entity.PlatesEntity;
import com.yunlong.softpark.entity.SoftwareEntity;
import com.yunlong.softpark.entity.SortEntity;
import com.yunlong.softpark.form.PublishedForm;
import com.yunlong.softpark.mapper.ColumnMapper;
import com.yunlong.softpark.mapper.SoftwareMapper;
import com.yunlong.softpark.service.SoftwareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Cui
 * @Date: 2020/7/24
 * @Description:
 */
@Slf4j
@Service
public class SoftwareServiceImpl implements SoftwareService {

    @Autowired
    SoftwareMapper softwareMapper;

    @Autowired
    ColumnMapper columnMapper;

    public FirstMajorDto getFirstMajor(){
        List<PlatesEntity> plates = softwareMapper.getPlates(4);
        List<PlatesEntity> plateList = new ArrayList<>();
        for(PlatesEntity p : plates){
            PlatesEntity m = new PlatesEntity();
            m.setPlateId(p.getPlateId());
            m.setPlateName(p.getPlateName());
            plateList.add(m);
        }
        List<MajorColumn> columnList = new ArrayList<>();
        List<ColumnEntity> columnEntities = softwareMapper.getRecommend();
        for(ColumnEntity c : columnEntities){
            MajorColumn m = new MajorColumn();
            m.setColumnId(c.getColumnId());
            m.setDownloads(c.getDownloads());
            m.setColumnName(c.getColumnName());
            m.setColumnLogo(c.getColumnLogo());
            columnList.add(m);
        }
        FirstMajorDto firstMajorDto = new FirstMajorDto();
        firstMajorDto.setColumnList(columnList);
        firstMajorDto.setPlateList(plateList);
        return firstMajorDto;
    }

    /**
     * 获取分类页面的软件主体及板块分类
     * @return
     */
    @Override
    public SoftMajorDto getMajorSoft(Integer page,Integer plateId) {
        //从表中查询所有的板块
        List<PlatesEntity> platesEntities = softwareMapper.getPlates(100);
//        Integer platesMinId = getPlatesMin(platesEntities);
        //根据板块id查询子分类
        SearchSortByPlateDto sortEntities = getSort(plateId);
        Integer sortMinId = getSortMin(sortEntities.getSearchSortByPlates());
        //根据分类id查询子栏目
        List<MajorColumn> columnEntities = getColumnBySort(sortMinId,page).getSearchColumns();
        SoftMajorDto softMajorDto = new SoftMajorDto();
        //构造返回值
        softMajorDto.setColumnEntities(columnEntities);
        softMajorDto.setPlatesEntities(platesEntities);
        softMajorDto.setSortEntities(sortEntities.getSearchSortByPlates());
        return softMajorDto;
    }


    /**
     * 通过分类id得到column
     * @param sortId
     * @return
     */
    public SearchColumnDto getColumnBySort(Integer sortId,Integer page){
        List<ColumnEntity> columnEntities = softwareMapper.getColumnEntity(sortId,(page-1)*25);
        List<MajorColumn> searchColumns = new ArrayList<>();
        for(ColumnEntity c : columnEntities){
            MajorColumn searchColumn = new MajorColumn();
            searchColumn.setColumnId(c.getColumnId());
            searchColumn.setColumnLogo(c.getColumnLogo());
            searchColumn.setDownloads(c.getDownloads());
            searchColumn.setColumnName(c.getColumnName());
            searchColumns.add(searchColumn);
        }
        SearchColumnDto searchColumnDto = new SearchColumnDto();
        searchColumnDto.setSearchColumns(searchColumns);
        return searchColumnDto;
    }

    @Override
    public void download(String softId) {
        SoftwareEntity softwareEntity = softwareMapper.selectBySoftId(softId);
        int downloads = softwareEntity.getDownloads();
        downloads++;
        softwareMapper.UpdateDownloads(softId,downloads);
        ColumnEntity columnEntity = columnMapper.selectByColumnId(softwareEntity.getParentId());
        int columnEntityDownloads = columnEntity.getDownloads();
        columnEntityDownloads++;
        columnMapper.UpdateDownloads(columnEntity.getColumnId(),columnEntityDownloads);
    }


    /**
     * 发布软件
     * @param publishedForm
     * @param userId
     * @return
     */
    @Override
    public MessageSuccessDto publishedSoft(PublishedForm publishedForm,String userId) {

        SoftwareEntity softwareEntity = new SoftwareEntity();
        softwareEntity.setBriefIntro(publishedForm.getBriefIntro());
        softwareEntity.setCreateTime(new Date());
        softwareEntity.setDownloads(0);
        softwareEntity.setEdition(publishedForm.getEdition());
        softwareEntity.setInstallProleft(publishedForm.getInstallProleft());
        softwareEntity.setInstallProright(publishedForm.getInstallProright());
        softwareEntity.setLanguage(publishedForm.getLanguage());
        softwareEntity.setParentId(publishedForm.getParentId());
        softwareEntity.setShowPic(publishedForm.getShowPic());
        softwareEntity.setPlatform(publishedForm.getPlatform());
        softwareEntity.setSoftAddr(publishedForm.getSoftAddr());
        softwareEntity.setSoftId(UUID.randomUUID().toString().replace("-",""));
        softwareEntity.setSoftLogo(publishedForm.getSoftLogo());
        softwareEntity.setUpdateTime(new Date());
        softwareEntity.setUserId(userId);
        softwareEntity.setVerify(0);
        softwareEntity.setSoftName(publishedForm.getSoftName());
        softwareEntity.setSoftSize(publishedForm.getSoftSize());
        softwareMapper.insertSoft(softwareEntity.getBriefIntro(),softwareEntity.getCreateTime(),
                softwareEntity.getDownloads(),softwareEntity.getEdition(),softwareEntity.getInstallProleft(),
                softwareEntity.getInstallProright(),softwareEntity.getLanguage(),softwareEntity.getParentId(),
                softwareEntity.getPlatform(),softwareEntity.getShowPic(),softwareEntity.getSoftAddr(),
                softwareEntity.getSoftId(),softwareEntity.getSoftLogo(),softwareEntity.getSoftName(),
                softwareEntity.getSoftSize(),softwareEntity.getUpdateTime(),softwareEntity.getUserId(),
                softwareEntity.getVerify());
        MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
        messageSuccessDto.setMessage("上传成功,等待管理员审核中。");
        return messageSuccessDto;
    }

    /**
     * 模糊搜索
     * @param key
     * @return
     */
    @Override
    public SearchDto search(String key) {
        List<SoftwareEntity> softwareEntities = softwareMapper.selectByKey(key);
        List<SearchSoft> searchDtos = new ArrayList<>();
        for(SoftwareEntity s : softwareEntities){
            SearchSoft searchSoft = new SearchSoft();
            searchSoft.setBriefIntro(s.getBriefIntro());
            searchSoft.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(s.getCreateTime()));
            searchSoft.setDownloads(s.getDownloads());
            searchSoft.setEdition(s.getEdition());
            searchSoft.setLanguage(s.getLanguage());
            searchSoft.setPlatform(s.getPlatform());
            searchSoft.setSoftLogo(s.getSoftLogo());
            searchSoft.setSoftName(s.getSoftName());
            searchSoft.setSoftSize(s.getSoftSize());
            searchDtos.add(searchSoft);
        }
        SearchDto searchDto = new SearchDto();
        searchDto.setSearchSofts(searchDtos);
        return searchDto;
    }

    @Override
    public SearchSortByPlateDto getSort(Integer plateId) {

        List<SortEntity> sortEntities = softwareMapper.selectSortByPlate(plateId);
        SearchSortByPlateDto searchSortByPlateDto = new SearchSortByPlateDto();
        List<MajorSort> majorSorts = new ArrayList<>();
        for(SortEntity s : sortEntities){
            MajorSort majorSort = new MajorSort();
            majorSort.setSortId(s.getSortId());
            majorSort.setSortName(s.getSortName());
            majorSorts.add(majorSort);
        }
        searchSortByPlateDto.setSearchSortByPlates(majorSorts);
        return searchSortByPlateDto;
    }


    /**
     * 获取板块中最小的id
     * @param platesEntities
     * @return
     */
    private Integer getPlatesMin(List<PlatesEntity> platesEntities){
        int min=100;
        for(PlatesEntity p : platesEntities){
            min = min < p.getPlateId() ? min : p.getPlateId();
        }
        return min;
    }

    /**
     * 获取分类中最小的id
     * @param majorSort
     * @return
     */
    private Integer getSortMin(List<MajorSort> majorSort){
        int min=100;
        for(MajorSort m : majorSort){
            min = min < m.getSortId() ? min : m.getSortId();
        }
        return min;
    }
}
