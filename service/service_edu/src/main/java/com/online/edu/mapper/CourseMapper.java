package com.online.edu.mapper;

import com.online.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-08-03
 */
public interface CourseMapper extends BaseMapper<Course> {

    int pushCourseById(String courseId);

    List<Course> findCourseByHot();
}
