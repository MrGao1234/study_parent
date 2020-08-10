package com.online.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onlin.common.ResultApi;
import com.online.edu.entity.Chapter;
import com.online.edu.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-08-03
 */
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    /**
     * 根据提交上来的章节信息，进行操作
     */
    @PostMapping("/operateChapter")
    public ResultApi operateChapter(@RequestBody  List<Chapter> chapterList){
       return chapterService.operateChapter(chapterList);
    }


    /**
     * 查询某章节信息
     */
    @GetMapping("/findChapterByCourseId")
    public ResultApi findChapterByCoursId(String courseId){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("course_id",courseId);
        return ResultApi.ok().data("chapterList",chapterService.list(wrapper));
    }

}

