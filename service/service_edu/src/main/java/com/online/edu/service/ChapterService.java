package com.online.edu.service;

import com.onlin.common.ResultApi;
import com.online.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-08-03
 */
public interface ChapterService extends IService<Chapter> {

    ResultApi operateChapter(List<Chapter> chapterList);
}
