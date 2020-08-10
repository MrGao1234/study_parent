package com.online.edu.mapper;

import com.online.edu.entity.Chapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-08-03
 */
@Mapper
public interface ChapterMapper extends BaseMapper<Chapter> {
    //int removeChapter(List<String> chapterIdList);
}
