package com.online.edu.service;

import com.onlin.common.ResultApi;
import com.online.edu.entity.Chapter;
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

    //新增
    int insertVideoByChapter(Chapter chapter);

    void updateVideoList(Chapter chapter);
    //第一个参数是数据库中的，第二个是需要修改的
    //ResultApi operateVideo(List<Video> videoList,List<Video> changedVideoLists);

}
