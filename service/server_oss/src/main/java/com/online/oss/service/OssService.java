package com.online.oss.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.online.oss.utils.ReadPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssService {

    public String uploadFileToOss(MultipartFile multipartFile){

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ReadPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ReadPropertiesUtils.KEY_ID;
        String accessKeySecret = ReadPropertiesUtils.KEY_SECRET;
        String bucketName = ReadPropertiesUtils.BUCKET_NAME;

        //文件名 + UUID 防止名称重复
        String fileName = UUID.randomUUID().toString().replace("-","") +"_"+ multipartFile.getOriginalFilename();
        //获取当前日期
        String datePath = new DateTime().toString("yyyy/MM/dd");
        fileName = datePath + "/" + fileName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
            ossClient.putObject(bucketName, fileName, inputStream);

            return "https://" + bucketName + "." + endpoint + "/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }

    }
}
