package com.yunlong.softpark.controller;

import com.yunlong.softpark.core.wrapper.ResultWrapper;
import com.yunlong.softpark.dto.UploadDto;
import com.yunlong.softpark.util.FtpFileUtils;
import com.yunlong.softpark.util.FtpUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: Cui
 * @Date: 2020/7/29
 * @Description:
 */
@RestController
@RequestMapping("/upload")
public class UploadController{


    @Value("${FTP.ADDRESS}")
    private String host;
    // 端口
    @Value("${FTP.PORT}")
    private int port;
    // ftp用户名
    @Value("${FTP.USERNAME}")
    private String userName;
    // ftp用户密码
    @Value("${FTP.PASSWORD}")
    private String passWord;
    // 文件在服务器端保存的主目录
    @Value("${FTP.BASEPATH}")
    private String basePath;
    @Value("${FTP.BASEURL}")
    private String baseUrl;

    @RequestMapping("/images")
    @ResponseBody
    public ResultWrapper pictureUpload(@RequestParam("file") MultipartFile uploadFile) {
        try {
            //1、给上传的图片生成新的文件名
            //1.1获取原始文件名
            String oldName = uploadFile.getOriginalFilename();
            //1.2使用IDUtils工具类生成新的文件名，新文件名 = newName + 文件后缀
            String newName = FtpFileUtils.genImageName();
            try {
                if(!(oldName.substring(oldName.lastIndexOf(".")).equals(".jpeg")
                        || oldName.substring(oldName.lastIndexOf(".")).equals(".jpg")
                        || oldName.substring(oldName.lastIndexOf(".")).equals(".png")
                        || oldName.substring(oldName.lastIndexOf(".")).equals(".ico"))){
                    return ResultWrapper.failure("暂不支持这种格式的图标");
                }
            }catch (StringIndexOutOfBoundsException e){
                return ResultWrapper.failure("暂不支持这种格式的图标");
            }
            newName = newName + oldName.substring(oldName.lastIndexOf("."));
            //1.3生成文件在服务器端存储的子目录
            String filePath = new DateTime().toString("/yyyy/MM/dd");
            //3、把图片上传到图片服务器
            //3.1获取上传的io流
            InputStream input = uploadFile.getInputStream();
            String b =basePath+ "/images";
            //3.2调用FtpUtil工具类进行上传
            boolean result = FtpUtil.uploadFile(host, port, userName, passWord, b, filePath, newName, input);
            UploadDto uploadDto = new UploadDto();

            if(result) {
                uploadDto.setFileUrl(baseUrl+filePath+"/"+newName);
                uploadDto.setMessage("上传成功！");
                return ResultWrapper.successWithData(uploadDto);
            }else {
                return ResultWrapper.failure("上传失败！");
            }
        } catch (IOException e) {
            return ResultWrapper.failure("上传失败！");
        }
    }

    @RequestMapping("/file")
    @ResponseBody
    public ResultWrapper fileUpload(@RequestParam("file") MultipartFile uploadFile) {
        try {
            UploadDto uploadDto = new UploadDto();
            //1、给上传的图片生成新的文件名
            //1.1获取原始文件名
            String oldName = uploadFile.getOriginalFilename();
            //1.2使用IDUtils工具类生成新的文件名，新文件名 = newName + 文件后缀
            String newName = FtpFileUtils.genImageName();
            //计算软件大小
            Long size = uploadFile.getSize();
            if(size>=1073741824){
                uploadDto.setSize(size/1073741824+"GB");
            }else if(size>=1048576){
                uploadDto.setSize(size/1048576+"MB");
            }else {
                uploadDto.setSize(size/1024+"KB");
            }
            try {
                newName = newName + oldName.substring(oldName.lastIndexOf("."));
            }catch (StringIndexOutOfBoundsException e){

            }
            //1.3生成文件在服务器端存储的子目录
            String filePath = new DateTime().toString("/yyyy/MM/dd");
            //3、把图片上传到图片服务器
            //3.1获取上传的io流
            InputStream input = uploadFile.getInputStream();
            System.out.println(baseUrl);
            String b =basePath+ "/softs";
            //3.2调用FtpUtil工具类进行上传
            boolean result = FtpUtil.uploadFile(host, port, userName, passWord, b, filePath, newName, input);

            if(result) {
                uploadDto.setFileUrl(baseUrl+filePath+"/"+newName);
                uploadDto.setMessage("上传成功！");
                return ResultWrapper.successWithData(uploadDto);
            }else {
                return ResultWrapper.failure("上传失败！");
            }
        } catch (IOException e) {
            return ResultWrapper.failure("上传失败！");
        }
    }

}
