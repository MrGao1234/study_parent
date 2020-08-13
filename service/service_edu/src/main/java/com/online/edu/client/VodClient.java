package com.online.edu.client;

import com.onlin.common.ResultApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name ="service-vod",fallback = VodClientRealize.class)
@Component
public interface VodClient {

    @PostMapping("/vod/videos/dropVideos")
    ResultApi dropVideos(@RequestBody String videoId);

}
