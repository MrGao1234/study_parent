package com.online.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onlin.common.ResultApi;
import com.online.edu.client.VodClient;
import com.online.edu.entity.Chapter;
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

    @Autowired
    private VodClient vodClient;

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

        //System.out.println(videoList.size());
        //删除小节前，需要将阿里云视频删除
        for(Video video:videoList){
            //System.out.println(video.getVideoSourceId());
            vodClient.dropVideos(video.getVideoSourceId());
        }

        int index = 0;
        for(Video video:videoList){
            index += videoMapper.deleteById(video);
        }
        return index;
    }


    //批量插入
    @Override
    public int insertVideoByChapter(Chapter chapter) {
        int index = 0;
        if(chapter.getVideoList() == null || chapter.getVideoList().size() <= 0){
            return index;
        }
        for(Video video : chapter.getVideoList()){
            index ++;
            video.setChapterId(chapter.getId());
            videoMapper.insert(video);
        }
        return index;
    }

    //更改某 chapter 的 list 列表
    @Override
    public void updateVideoList(Chapter chapter) {
        if(chapter.getVideoList() == null || chapter.getVideoList().size() <= 0){
            QueryWrapper delWrapper = new QueryWrapper();
            delWrapper.eq("course_id",chapter.getCourseId());
            delWrapper.eq("chapter_id",chapter.getId());
            List<Video> videoList = videoMapper.selectList(delWrapper);
            for(Video video : videoList){
                vodClient.dropVideos(video.getId());
            }
            //删除所有
            videoMapper.delete(delWrapper);
            return;
        }

        //先拿出所有不为空的 videoId，不是删除的就是修改的
        List<String> videoIdList = new ArrayList<>();
        for(Video video : chapter.getVideoList()){
            if(video.getId() != null && video.getId().length() > 0){
                videoIdList.add(video.getId());
            }
        }

        //删除
        QueryWrapper delWrapper = new QueryWrapper();
        delWrapper.eq("course_id",chapter.getVideoList().get(0).getCourseId());
        delWrapper.eq("chapter_id",chapter.getVideoList().get(0).getChapterId());
        delWrapper.notIn("id",videoIdList);
        //删除视频
        List<Video> delVideoList = videoMapper.selectList(delWrapper);
        for(Video video : delVideoList){
            vodClient.dropVideos(video.getVideoSourceId());
        }
        //删除小节
        videoMapper.delete(delWrapper);

        //修改或新增
        for(Video video : chapter.getVideoList()){
            if(video.getId() != null && video.getId().length() > 0){
                //这是要修改
                videoMapper.updateById(video);
            }else{
                //这是新增
                videoMapper.insert(video);
            }
        }
    }

//    @Override
//    public ResultApi operateVideo(List<Video> videoList,List<Video> changeList) {
//
//
//
//
//        //1，找出有课程号和章节号而没有小节的课程（删除或者修改操作）
//        List<String> notAddVideo = new ArrayList<>();
//        for(Video video:changeList){
//            if(video.getId() != null && video.getId().length() > 0){
//                notAddVideo.add(video.getId());
//            }
//        }
//
//        //删除
//        QueryWrapper delWrapper = new QueryWrapper();
//        delWrapper.eq("course_id",videoList.get(0).getCourseId());
//        delWrapper.eq("chapter_id",videoList.get(0).getChapterId());
//        delWrapper.notIn("id",notAddVideo);
//        //List<Video> videos = videoMapper.selectList(delWrapper);
//        System.out.println(videoMapper.delete(delWrapper));
//
//        //修改
//        QueryWrapper upWrapper = new QueryWrapper();
//        upWrapper.eq("course_id",videoList.get(0).getCourseId());
//        upWrapper.eq("chapter_id",videoList.get(0).getChapterId());
//        upWrapper.in("id",notAddVideo);
//        List<Video> updVideoList = videoMapper.selectList(upWrapper);
//        for(Video video:updVideoList){
//            videoMapper.updateById(video);
//        }
//
//        //添加
//        for(Video video : videoList){
//            if(video.getId() == null || video.getId().length() <= 0){
//                videoMapper.insert(video);
//            }
//        }
//
//        return ResultApi.ok();
//    }
}
