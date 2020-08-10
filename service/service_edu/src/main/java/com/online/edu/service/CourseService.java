package com.online.edu.service;

import com.onlin.common.ResultApi;
import com.online.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.pojo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-08-03
 */
public interface CourseService extends IService<Course> {

    ResultApi saveCourse(CourseInfoVo courseInfoVo);

    ResultApi pushCourse(String courseId);

    ResultApi findCourseByCourse(CourseInfoVo course);

/*
    ResultApi getAllSubjectTypeOne();
*/
}
