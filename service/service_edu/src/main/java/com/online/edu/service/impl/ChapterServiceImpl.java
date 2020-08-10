package com.online.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onlin.common.ResultApi;
import com.online.edu.entity.Chapter;
import com.online.edu.entity.Video;
import com.online.edu.mapper.ChapterMapper;
import com.online.edu.mapper.VideoMapper;
import com.online.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-08-03
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private VideoService videoService;

    @Override
    public ResultApi operateChapter(List<Chapter> chapterList) {

        if(chapterList == null || chapterList.size() <= 0){
            return ResultApi.ok();
        }

        //所有的 chapterId
        List<String> chapterIdList = new ArrayList<>();

        for(Chapter chapter:chapterList){

            //所有不为空的
            if(chapter.getId() != null && chapter.getId().length() > 0) {
                chapterIdList.add(chapter.getId());
            }
        }
        //删除的
        QueryWrapper delWrapper = new QueryWrapper();
        delWrapper.notIn("id",chapterIdList);
        delWrapper.eq("course_id",chapterList.get(0).getCourseId());
        //先删除所有小节
        List<Chapter> delChapterList = chapterMapper.selectList(delWrapper);
        for(Chapter chapter : delChapterList){
            List<Video> delVideoList = videoService.findVideoByChapter(chapter.getCourseId(),chapter.getId());
            videoService.removeVideoByList(delVideoList);

        }
        chapterMapper.delete(delWrapper);


        //修改的
        QueryWrapper updWrapper = new QueryWrapper();
        updWrapper.in("id",chapterIdList);
        updWrapper.eq("course_id",chapterList.get(0).getCourseId());
        List<Chapter> updChapterList = chapterMapper.selectList(updWrapper);
        for(Chapter chapter : updChapterList){
            videoService.operateVideo(chapter.getVideoList());
            chapterMapper.updateById(chapter);
        }

        for(Chapter chapter:chapterList){

            //所有不为空的
            if(chapter.getId() != null && chapter.getId().length() > 0) {
            }else{
                //新增的
                chapterMapper.insert(chapter);

                if(chapter.getVideoList() != null){
                    //插入章节
                    List<Video> videos = chapter.getVideoList();
                    for(Video video : videos){
                        video.setChapterId(chapter.getId());
                    }
                    videoService.operateVideo(videos);
                }
            }
        }
        return ResultApi.ok();
    }

}
