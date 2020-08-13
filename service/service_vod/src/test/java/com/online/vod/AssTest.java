package com.online.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.online.service_vod.utils.InitAssClient;

import java.util.List;

public class AssTest{

    private static final String accessKeyId = "LTAI4GFm7Cy1sZsHvUv44e9C";
    private static final String accessKeySecret = "oN0X7HWVqhb1eT2vRmRxoYeOXpnNb4";
    private static final String templateId = "6e22c716d1f6bcc27bf24913a1e63fce";

    public static void main(String[] args){
        //testUploadVideo(accessKeyId, accessKeySecret, "test.mp4", "D:\\myData\\video\\Video_2020-08-10_093957.wmv");
        try {
            getUrlById();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testUploadVideo(String accessKeyId, String accessKeySecret, String s, String s1) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, "test.mp4", "D:\\myData\\video\\Video_2020-08-10_093957.wmv");
        /* 可指定分片上传时每个分片的大小，默认为1M字节 */
        request.setPartSize(1 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        request.setEnableCheckpoint(false);
        request.setTemplateGroupId(templateId);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }


    //根据id获取视频路径
    public static void getUrlById() throws Exception{
        //创建初始化对象
        DefaultAcsClient client = InitAssClient.initVodClient(accessKeyId,accessKeySecret);

        //创建响应和请求对象
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //向 request 对象设置视频 id
        request.setVideoId("06edc9f94f164320806ca6e7ba208395");

        //获取数据
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
