package com.yunlong.softpark.controller;

import  com.yunlong.softpark.core.support.web.controller.BaseController;
import com.yunlong.softpark.core.wrapper.ResultWrapper;
import com.yunlong.softpark.dto.TokenInfo;
import com.yunlong.softpark.dto.UserInfo;
import com.yunlong.softpark.form.PublishedForm;
import com.yunlong.softpark.service.SoftwareService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/sort/{plateId}/{page}")
    public ResultWrapper getMajorSoft(@PathVariable("page") Integer page,@PathVariable("plateId")Integer plateId){
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
    @GetMapping("/search/{sortId}/{page}")
    public ResultWrapper getColumnBySort(@PathVariable("sortId") Integer sortId,
                                             @PathVariable("page") Integer page){
        return ResultWrapper.successWithData(softwareService.getColumnBySort(sortId,page));
    }

    /**
     * 下载
     * @param softId
     * @return
     */
    @GetMapping("/download/{softId}")
    public ResultWrapper downloadSoft(@PathVariable("softId")String softId){
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

}
