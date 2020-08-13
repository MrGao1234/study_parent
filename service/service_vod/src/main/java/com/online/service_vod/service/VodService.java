package com.online.service_vod.service;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.online.service_vod.utils.InitAssClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class VodService {

    @Value("${accessKey}")
    private String accessKey;

    @Value("${accessScreent}")
    private String accessScreent;


    public String uploadVideo(MultipartFile file) throws IOException {
        //文件名
        String fileName = file.getOriginalFilename();
        String title = fileName.substring(0,fileName.lastIndexOf("."));

        InputStream inputStream = file.getInputStream();
        UploadStreamRequest request = new UploadStreamRequest(accessKey,accessScreent,title,fileName,inputStream);

        UploadStreamResponse response = new UploadVideoImpl().uploadStream(request);

        return response.getVideoId();

    }

    public DeleteVideoResponse deleteVideo(String videoId) throws Exception{

        DefaultAcsClient client = InitAssClient.initVodClient(accessKey,accessScreent);

        DeleteVideoRequest request = new DeleteVideoRequest();

        request.setVideoIds(videoId);
        DeleteVideoResponse response = client.getAcsResponse(request);
        return response;

    }
}
