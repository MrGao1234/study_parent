package com.online.edu.controller;

import com.onlin.common.ResultApi;
import com.online.edu.entity.Course;
import com.online.edu.pojo.CourseInfoVo;
import com.online.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-08-03
 */
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/saveCourse")
    public ResultApi saveCourse(@RequestBody CourseInfoVo courseInfoVo){
        return courseService.saveCourse(courseInfoVo);
    }

    //发布课程
    @PostMapping("/pushCourse")
    public ResultApi getSubjectTypeOne(@RequestBody String courseId){
        return courseService.pushCourse(courseId);
    }


    @PostMapping("/findCourseByTrem")
    public ResultApi getCourseByTerm(@RequestBody(required = false) CourseInfoVo course){

        return courseService.findCourseByCourse(course);
    }

}

