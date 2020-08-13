package com.online.edu.client;

import com.onlin.common.ResultApi;
import org.springframework.stereotype.Component;

@Component
public class VodClientRealize implements VodClient {
    @Override
    public ResultApi dropVideos(String videoId) {
        return ResultApi.error().message("删除视频失败");
    }
}
