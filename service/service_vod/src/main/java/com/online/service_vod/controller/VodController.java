package com.online.service_vod.controller;

import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.onlin.common.ResultApi;
import com.online.service_vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@CrossOrigin
@RequestMapping("/vod/videos")
public class VodController {


    @Autowired
    private VodService vodService;

    @PostMapping("/uploadVideos")
    public ResultApi uploadVideos(MultipartFile file){
        String videoId = null;
        try {
            videoId = vodService.uploadVideo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultApi.ok().data("videoId",videoId);
    }

    //将视频从阿里云删除
    @PostMapping("/dropVideos")
    public ResultApi dropVideos(@RequestBody String videoId){
        //System.out.println(videoId);
        DeleteVideoResponse response = null;
        try {
            response = vodService.deleteVideo(videoId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultApi.ok().data("response",response);
    }

}
