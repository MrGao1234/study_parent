package com.online.oss.Controller;

import com.onlin.common.ResultApi;
import com.online.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/oss/file")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/uploadHeadImage")
    public ResultApi uploadHeadImage(MultipartFile file){
        String url = ossService.uploadFileToOss(file);
        return ResultApi.ok().data("fileUrl",url);
    }
}
