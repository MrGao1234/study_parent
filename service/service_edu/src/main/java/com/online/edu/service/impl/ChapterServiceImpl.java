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

    /**
     * 前端传过来的 chapterList
     * 1，获取前端传过来所有的章节ID（chapterIdList）
     * 2，删除：存在数据库中，而不存在 chapterIdList 中的，做逻辑删除，其下所有的 Video 做逻辑删除 ( delChapterList )
     * 3，修改：遍历 chapterList
     *          同时存在于数据库和前端传送过来的列表中，并且id 不为空的时候，就是修改，此时应该遍历
     * 4, 新增：再遍历 chapterList，id 为空的是要新增的，做新增操作，同时新增 Video
     * */
    @Override
    public ResultApi operateChapter(List<Chapter> chapterList) {

        if(chapterList == null || chapterList.size() <= 0){
            return ResultApi.ok();
        }
        /**
         * 1，获取所有章节ID
         */
        List<String> chapterIdList = new ArrayList<>();
        for(Chapter chapter:chapterList){
            if(chapter.getId() != null && chapter.getId().length() > 0) {
                chapterIdList.add(chapter.getId());
            }
        }
        /**
         * 2，所有本门课不在传递过来的 list 中的，都是要删除的章节，根据章节号先删除小节，再删除章节
         */
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


        /**
         * 3，前端过来的，不是修改就是新增
         *    有 id 的是修改，无 id 的是新增
         */
        for(Chapter chapter : chapterList){
            if(chapter.getId() != null && chapter.getId().length() > 0){
                //表示是修改的
                chapterMapper.updateById(chapter);
                //修改比较复杂，也要判断是否需要删除新增等操作，做一个统一的方法
                videoService.updateVideoList(chapter);
            }else{
                //这是新增的
                chapterMapper.insert(chapter);
                //既然章节是新增的，那么小节肯定也是新增的
                videoService.insertVideoByChapter(chapter);
            }
        }

//        /**
//         * 要修改的章节
//         * 1，先找到所有的要修改的章节
//         * 2，根据章节信息，查找小节信息
//         * 3，修改章节信息
//         */
//        QueryWrapper updWrapper = new QueryWrapper();
//        updWrapper.in("id",chapterIdList);
//        updWrapper.eq("course_id",chapterList.get(0).getCourseId());
//        List<Chapter> updChapterList = chapterMapper.selectList(updWrapper);
//        for(Chapter chapter : updChapterList){
////            for(Chapter chapter1 : chapterListChanged){
////                videoService.operateVideo(chapter.getVideoList(),chapter1.getVideoList());
////            }
//
//            chapterMapper.updateById(chapter);
//        }
//
//        for(Chapter chapter:chapterList){
//            //所有不为空的
//            if(chapter.getId() != null && chapter.getId().length() > 0) {
//            }else{
//                //新增的
//                chapterMapper.insert(chapter);
//
//                if(chapter.getVideoList() != null){
//                    //插入章节
//                    List<Video> videos = chapter.getVideoList();
//                    for(Video video : videos){
//                        video.setChapterId(chapter.getId());
//                    }
//
//                    //videoService.operateVideo(videos);
//                }
//            }
//        }
        return ResultApi.ok();
    }

}
