package com.online.edu.service;

import com.onlin.common.ResultApi;
import com.online.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-08-03
 */
public interface VideoService extends IService<Video> {

    List<Video> findVideoByChapter(String courseId,String chapterId);

    //删除
    int removeVideoByList(List<Video> videoList);

    ResultApi operateVideo(List<Video> videoList);

}
