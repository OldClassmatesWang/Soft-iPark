package com.yunlong.softpark.controller;

import  com.yunlong.softpark.core.support.web.controller.BaseController;
import com.yunlong.softpark.core.wrapper.ResultWrapper;
import com.yunlong.softpark.dto.*;
import com.yunlong.softpark.form.ColumnSimpForm;
import com.yunlong.softpark.form.PublishedForm;
import com.yunlong.softpark.form.SoftwareSimpForm;
import com.yunlong.softpark.service.SoftwareService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Cui
 * @Date: 2020/7/24
 * @Description:
 */
@Api(value = "SoftwareController", tags = {"软件API"})
@RestController
@Slf4j
@RequestMapping("/software")
public class SoftwareController extends BaseController<UserInfo> {

    @Autowired
    SoftwareService softwareService;

    /**
     * 分类软件主体
     * @return
     */
    @GetMapping("/sort")
    public ResultWrapper getMajorSoft(@RequestParam("page") Integer page,@RequestParam("plateId")Integer plateId){
        return ResultWrapper.successWithData(softwareService.getMajorSoft(page,plateId));
    }

    /**
     * 首页的软件主体
     * @return
     */
    @GetMapping("/major")
    public ResultWrapper getFirstMajor(){
        return ResultWrapper.successWithData(softwareService.getFirstMajor());
    }

    /**
     * 上传
     * @param publishedForm
     * @return
     */
    @PostMapping("/publish")
    public ResultWrapper publishSoft(@RequestBody PublishedForm publishedForm){
        return ResultWrapper.successWithData(softwareService.publishedSoft(publishedForm,getCurrentUserInfo().getUserId()));
    }


    /**
     * 模糊搜索
     * @param key
     * @return
     */
    @GetMapping("/search")
    public ResultWrapper search(@RequestParam("key") String key){
        return ResultWrapper.successWithData(softwareService.search(key));
    }

    /**
     * 通过板块获取分类
     * @param plateId
     * @return
     */
    @GetMapping("/search/{plateId}")
    public ResultWrapper getSortByPlateId(@PathVariable("plateId") Integer plateId){
        return ResultWrapper.successWithData(softwareService.getSort(plateId));
    }

    /**
     * 通过分类id得到栏目
     * @param sortId
     * @param page
     * @return
     */
    @GetMapping("/searchColumn")
    public ResultWrapper getColumnBySort(@RequestParam("sortId") Integer sortId,
                                             @RequestParam("page") Integer page){
        return ResultWrapper.successWithData(softwareService.getColumnBySort(sortId,page));
    }

    /**
     * 下载
     * @param softId
     * @return
     */
    @GetMapping("/download")
    public ResultWrapper downloadSoft(@RequestParam("softId")String softId){
        softwareService.download(softId);
        return ResultWrapper.success();
    }

    /**
     * 发布软件的获取的三级菜单
     * @return
     */
    @GetMapping("/menu")
    public ResultWrapper publishMenu(){
        return ResultWrapper.successWithData(softwareService.publishMenu());
    }

    /**
     * 根据前端传入的columnId获取在栏目栏展示的软件相关信息
     *
     * @param columnSimpForm
     * @return
     */
    @RequestMapping(path = "/versionShow", method = RequestMethod.POST)
    ResultWrapper getSimpVersionIntroduce(@RequestBody ColumnSimpForm columnSimpForm) {
        List<SoftwareSimpVersionDto> list = softwareService.getSimpVersionIntroduce(columnSimpForm.getColumnId());
        return ResultWrapper.successWithData(list);
    }

    /**
     * 根据前端传入的softId获取在软件点开栏上方的简单信息
     *
     * @param softwareSimpForm
     * @return
     */
    @RequestMapping(path = "/simpInto", method = RequestMethod.POST)
    ResultWrapper getSimpIntroduce(@RequestBody SoftwareSimpForm softwareSimpForm) {
        SoftwareSimpIntroDto simpIntroduc = softwareService.getSimpIntroduc(softwareSimpForm.getSoftId(), softwareSimpForm
                .getColumnId());


        return ResultWrapper.successWithData(simpIntroduc);
    }

    /**
     * 根据前端传入的softid获取软件点开栏的安装步骤右
     * @param softwareSimpForm
     * @return
     */
    @RequestMapping(path = "/detailIntro",method = RequestMethod.POST)
    ResultWrapper getDetailIntroduce(@RequestBody SoftwareSimpForm softwareSimpForm) {
        SoftwareDetailDto softwareDetailDto = softwareService.getDetailIntroduce(softwareSimpForm.getSoftId());
        return ResultWrapper.successWithData(softwareDetailDto);
    }

    @RequestMapping(path = "/basedata",method = RequestMethod.POST)
    ResultWrapper getBaseData(@RequestBody SoftwareSimpForm softwareSimpForm){
        SoftwareBaseDataDto softwareBaseDataDto = softwareService.getBaseData(softwareSimpForm.getColumnId(),softwareSimpForm.getSoftId());
        return ResultWrapper.successWithData(softwareBaseDataDto);
    }
}
