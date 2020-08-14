package com.yunlong.softpark.service.impl;

import com.yunlong.softpark.dto.*;
import com.yunlong.softpark.entity.*;
import com.yunlong.softpark.form.PublishedForm;
import com.yunlong.softpark.mapper.ColumnMapper;
import com.yunlong.softpark.mapper.SoftwareMapper;
import com.yunlong.softpark.mapper.UserMapper;
import com.yunlong.softpark.service.SoftwareService;
import com.zaxxer.hikari.util.FastList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Autowired
    UserMapper userMapper;

    /**
     * 获取首页软件
     * @return
     */
    @Override
    public FirstMajorDto getFirstMajor(){
        //添加综合推荐到列表
        FirstMajor firstMajor = new FirstMajor();
        firstMajor.setPlateName("综合推荐");
        List<MajorColumn> majorColumns = new ArrayList<>();
        List<ColumnEntity> columnEntities = softwareMapper.getRecommend();
        for(ColumnEntity c : columnEntities){
            MajorColumn majorColumn = new MajorColumn();
            majorColumn.setColumnLogo(c.getColumnLogo());
            majorColumn.setColumnName(c.getColumnName());
            majorColumn.setDownloads(c.getDownloads());
            majorColumn.setColumnId(c.getColumnId());
            majorColumns.add(majorColumn);
        }
        firstMajor.setSonList(majorColumns);
        FirstMajorDto firstMajorDto = new FirstMajorDto();
        List<FirstMajor> firstMajors = new ArrayList<>();
        List<PlatesEntity> plates = softwareMapper.getPlates(4);
        List<SortEntity> sortEntities = softwareMapper.getSort();
        List<ColumnEntity> columnBySort = softwareMapper.getColumn();
        //匹配三级菜单到一级菜单
        firstMajors = plates.stream().map((p)->{
            FirstMajor fm = new FirstMajor();
            fm.setPlateName(p.getPlateName());
            List<MajorColumn> l = new ArrayList<>();
            sortEntities.stream().filter((s) ->
                    s.getParentId() == p.getPlateId()
            ).forEach((c)->{
                columnBySort.stream().filter((columnEntity)->
                    columnEntity.getParentId() == c.getSortId()
                ).map((x)->{
                    MajorColumn majorColumn = new MajorColumn();
                    majorColumn.setColumnLogo(x.getColumnLogo());
                    majorColumn.setColumnName(x.getColumnName());
                    majorColumn.setDownloads(x.getDownloads());
                    majorColumn.setColumnId(x.getColumnId());
                    l.add(majorColumn);
                    return majorColumn;
                }).collect(Collectors.toList());
            });
            fm.setSonList(l);
            return fm;
        }).collect(Collectors.toList());
        firstMajors.add(0,firstMajor);
        firstMajorDto.setFirstMajors(firstMajors);
        return firstMajorDto;
    }

    /**
     * 获取分类页面的软件主体及板块分类
     * @return
     */
    @Override
    public ColumnSortDto getMajorSoft(Integer page,Integer plateId) {
        //从数据库中查取板块，分类，栏目
//        List<PlatesEntity> platesEntities = softwareMapper.getPlates(100);
//        List<SortEntity> sortEntities = softwareMapper.getSort();
//        List<ColumnEntity> columnEntities = softwareMapper.getColumn();
//        //封装三级菜单
//        List<MajorPlate> majorPlates = platesEntities.stream().map((p)->{
//            MajorPlate majorPlate = new MajorPlate();
//            majorPlate.setPlateName(p.getPlateName());
//            List<MajorSort> majorSorts = sortEntities.stream().map((s)->{
//                MajorSort majorSort = new MajorSort();
//                majorSort.setSortName(s.getSortName());
//                majorSort.setSortId(s.getSortId());
//                List<MajorColumn> majorColumns = columnEntities.stream().map((c)->{
//                    MajorColumn majorColumn = new MajorColumn();
//                    majorColumn.setColumnId(c.getColumnId());
//                    majorColumn.setDownloads(c.getDownloads());
//                    majorColumn.setColumnName(c.getColumnName());
//                    majorColumn.setColumnLogo(c.getColumnLogo());
//                    return majorColumn;
//                }).collect(Collectors.toList());
//                majorSort.setMajorColumns(majorColumns);
//                return majorSort;
//            }).collect(Collectors.toList());
//            majorPlate.setMajorSorts(majorSorts);
//            return majorPlate;
//        }).collect(Collectors.toList());
//        SoftMajorDto softMajorDto = new SoftMajorDto();
//        softMajorDto.setPlatesEntities(majorPlates);
//        return softMajorDto;

        List<PlatesEntity> platesEntities = softwareMapper.getPlates(100);
//        Integer platesMinId = getPlatesMin(platesEntities);
        //根据板块id查询子分类
        SearchSortByPlateDto sortEntities = getSort(plateId);
        Integer sortMinId = getSortMin(sortEntities.getSearchSortByPlates());
        //根据分类id查询子栏目
        List<MajorColumn> columnEntities = getColumnBySort(sortMinId,page).getSearchColumns();
        ColumnSortDto columnSortDto = new ColumnSortDto();
        //构造返回值
        columnSortDto.setColumnEntities(columnEntities);
        columnSortDto.setPlatesEntities(platesEntities);
        columnSortDto.setSortEntities(sortEntities.getSearchSortByPlates());
        return columnSortDto;
    }


    /**
     * 通过分类id得到column
     * @param sortId
     * @return
     */
    @Override
    public SearchColumnDto getColumnBySort(Integer sortId, Integer page){
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

    /**
     * 更新下载次数
     * @param softId
     */
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
     * 发布页面的三级菜单
     * @return
     */
    @Override
    public PublishMenuDto publishMenu() {
        //从数据库中查询板块，分类和栏目
        List<PlatesEntity> plates = softwareMapper.getPlates(100);
        List<SortEntity> sort = softwareMapper.getSort();
        List<ColumnEntity> column = softwareMapper.getColumn();
        //封装三级菜单
        List<PlateMenu> plateMenus = plates.stream().map((menu)->{
            PlateMenu p = new PlateMenu();
            p.setPlateName(menu.getPlateName());
            List<SortMenu> sortMenus = sort.stream().filter((s)->
                s.getParentId() == menu.getPlateId()
            ).map((s)->{
                SortMenu sortMenu = new SortMenu();
                sortMenu.setSortName(s.getSortName());
                List<ColumnMenu> columnMenus = column.stream().filter((c)->
                    c.getParentId() == s.getSortId()
                ).map((c)->{
                    ColumnMenu columnMenu = new ColumnMenu();
                    columnMenu.setColumnId(c.getColumnId());
                    columnMenu.setColumnName(c.getColumnName());
                    return columnMenu;
                }).collect(Collectors.toList());
                sortMenu.setColumnMenus(columnMenus);
                return sortMenu;
            }).collect(Collectors.toList());
            p.setSonList(sortMenus);
            return p;
        }).collect(Collectors.toList());
        PublishMenuDto p = new PublishMenuDto();
        p.setPlatesMenu(plateMenus);
        return p;
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
            searchSoft.setSoftId(s.getSoftId());
            searchDtos.add(searchSoft);
        }
        SearchDto searchDto = new SearchDto();
        searchDto.setSearchSofts(searchDtos);
        return searchDto;
    }

    /**
     * 通过板块id获取分类
     * @param plateId
     * @return
     */
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

    /**
     * 返回在栏目下栏展示的软件集信息
     *
     * @param columnId
     * @return
     */
    @Override
    public List<SoftwareSimpVersionDto> getSimpVersionIntroduce(String columnId) {
        List<SoftwareEntity> list = softwareMapper.selectByColumnId(columnId);

        List<SoftwareSimpVersionDto> list1 = new ArrayList<>();
        for (SoftwareEntity softwareEntity : list) {
            if (softwareEntity.getVerify() == 1) {
                SoftwareSimpVersionDto ssvd = new SoftwareSimpVersionDto();
                ssvd.setSoftId(softwareEntity.getSoftId());
                ssvd.setSoftName(softwareEntity.getSoftName());
                ssvd.setSoftLogo(softwareEntity.getSoftLogo());
                ssvd.setDownloads(softwareEntity.getDownloads());
                ssvd.setEdition(softwareEntity.getEdition());
                ssvd.setPlatform(softwareEntity.getPlatform());
                ssvd.setSoftAddr(softwareEntity.getSoftAddr());
                ssvd.setSoftSize(softwareEntity.getSoftSize());
                ssvd.setBriefIntro(softwareEntity.getBriefIntro());

                Date date = softwareEntity.getUpdateTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String time = sdf.format(date);
                ssvd.setUpdateTime(time);
                list1.add(ssvd);
            }
        }

        return list1;
    }

    /**
     * 返回在软件点开栏上栏显示的软件简介信息
     *
     * @param softId
     * @return
     */
    @Override
    public SoftwareSimpIntroDto getSimpIntroduc(String softId) {
        SoftwareEntity softwareEntity = softwareMapper.selectBySoftId(softId);
        String columnId = softwareEntity.getParentId();
        ColumnSimDto simDto = columnMapper.selectDataForSimpleColumn(columnId);
        SoftwareSimpIntroDto simpIntroDto = new SoftwareSimpIntroDto();

        simpIntroDto.setSoftName(softwareEntity.getSoftName());
        simpIntroDto.setBriefIntro(softwareEntity.getBriefIntro());
        simpIntroDto.setColumnWeb(simDto.getColumnWeb());
        simpIntroDto.setDownloads(softwareEntity.getDownloads());
        simpIntroDto.setEdition(softwareEntity.getEdition());
        simpIntroDto.setShowPic(softwareEntity.getShowPic());
        simpIntroDto.setSoftLogo(softwareEntity.getSoftLogo());
        simpIntroDto.setSoftSize(softwareEntity.getSoftSize());
        simpIntroDto.setPlatform(softwareEntity.getPlatform());

        return simpIntroDto;
    }

    /**
     * 根据软件id返回软件的右安装步骤
     *
     * @param softId
     * @return
     */
    @Override
    public SoftwareDetailDto getDetailIntroduce(String softId) {
        SoftwareEntity softwareEntity = softwareMapper.selectBySoftId(softId);
        SoftwareDetailDto softwareDetailDto =new SoftwareDetailDto();
        softwareDetailDto.setInstallProRight(softwareEntity.getInstallProright());
        return softwareDetailDto;
    }

    /**
     * 根据前端传递的columnId和softId返回软件的信息
     * 点开栏下的基本信息
     *
     * @param softId
     * @return
     */
    @Override
    public SoftwareBaseDataDto getBaseData( String softId) {
        SoftwareBaseDataDto softwareBaseDataDto = new SoftwareBaseDataDto();


        SoftwareEntity softwareEntity = softwareMapper.selectBySoftId(softId);
        String columnWeb = columnMapper.selectDataForBaseData(softwareEntity.getParentId());
        UserEntity userEntity = userMapper.selectById(softwareEntity.getUserId());

        softwareBaseDataDto.setColumnWeb(columnWeb);
        softwareBaseDataDto.setUsername(userEntity.getUsername());
        softwareBaseDataDto.setLanguage(softwareEntity.getLanguage());
        softwareBaseDataDto.setEdition(softwareEntity.getEdition());
        softwareBaseDataDto.setSoftAddr(softwareEntity.getSoftAddr());

        return softwareBaseDataDto;
    }


}
