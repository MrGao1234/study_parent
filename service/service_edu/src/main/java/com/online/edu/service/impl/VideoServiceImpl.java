package com.online.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onlin.common.ResultApi;
import com.online.edu.entity.Video;
import com.online.edu.mapper.VideoMapper;
import com.online.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-08-03
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    //根据章节和课程号查找所有小节
    @Override
    public List<Video> findVideoByChapter(String courseId, String chapterId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.eq("chapter_id",chapterId);
        return videoMapper.selectList(queryWrapper);
    }

    @Override
    public int removeVideoByList(List<Video> videoList) {
        int index = 0;
        for(Video video:videoList){
            index += videoMapper.deleteById(video);
        }
        return index;
    }

    /**
     *  对视频小节的操作
     *  1，先做删除操作，将课程和章节对应，小节不在列表中的小节信息做逻辑删除
     *  2，对能对应上课程、章节、小节的进行更新
     *  3，没有的小节添加
     */



    @Override
    public ResultApi operateVideo(List<Video> videoList) {

        if(videoList == null || videoList.size() <= 0){
            return ResultApi.ok();
        }


        //1，找出有课程号和章节号而没有小节的课程（删除或者修改操作）
        List<String> notAddVideo = new ArrayList<>();
        for(Video video:videoList){
            if(video.getId() != null && video.getId().length() > 0){
                notAddVideo.add(video.getId());
            }
        }

        //删除
        QueryWrapper delWrapper = new QueryWrapper();
        delWrapper.eq("course_id",videoList.get(0).getCourseId());
        delWrapper.eq("chapter_id",videoList.get(0).getChapterId());
        delWrapper.notIn("id",notAddVideo);
        videoMapper.delete(delWrapper);

        //修改
        QueryWrapper upWrapper = new QueryWrapper();
        upWrapper.eq("course_id",videoList.get(0).getCourseId());
        upWrapper.eq("chapter_id",videoList.get(0).getChapterId());
        upWrapper.in("id",notAddVideo);
        List<Video> updVideoList = videoMapper.selectList(upWrapper);
        for(Video video:updVideoList){
            videoMapper.updateById(video);
        }

        //添加
        for(Video video : videoList){
            if(video.getId() == null || video.getId().length() <= 0){
                videoMapper.insert(video);
            }
        }

        return ResultApi.ok();
    }
}
