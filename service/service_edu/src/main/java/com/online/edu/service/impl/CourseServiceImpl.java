package com.online.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onlin.common.ResultApi;
import com.online.common.Handler.MyExeption;
import com.online.edu.entity.Course;
import com.online.edu.entity.CourseDescription;
import com.online.edu.mapper.CourseMapper;
import com.online.edu.pojo.CourseInfoVo;
import com.online.edu.service.CourseDescriptionService;
import com.online.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-08-03
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private CourseMapper courseMappe;

    //根据 courseId 分辨添加还是修改
    @Override
    public ResultApi saveCourse(CourseInfoVo courseInfoVo) {

        //插入课程
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        System.out.println(course.getId() + "************************");

        //判断是否有 id，有的话就是修改操作，没有就是插入
        if(course.getId() == null || "".equals(course.getId())){
            int insert = baseMapper.insert(course);
            if(insert <= 0){
                //添加失败
                throw new MyExeption(20001,"数据插入失败！");
            }
            String courseId = course.getId();
            //课程简介
            CourseDescription courseDescription = new CourseDescription();
            BeanUtils.copyProperties(courseInfoVo,courseDescription);
            courseDescription.setId(courseId);
            courseDescriptionService.save(courseDescription);
            return ResultApi.ok().data("courseId",courseId).data("message","添加课程成功");
        }else{
            int update = baseMapper.updateById(course);
            if(update <= 0){
                //添加失败
                throw new MyExeption(20001,"数据修改失败！");
            }
            String courseId = course.getId();
            //课程简介
            CourseDescription courseDescription = courseDescriptionService.getById(courseId);
            courseDescriptionService.updateById(courseDescription);
            return ResultApi.ok().data("courseId",courseId).data("message","修改课程成功");
        }

    }


    @Override
    public ResultApi pushCourse(String courseId) {
        int bounds = courseMappe.pushCourseById(courseId);
        System.out.println(bounds);
        return ResultApi.ok();
    }

    //根据条件查询
    @Override
    public ResultApi findCourseByCourse(CourseInfoVo course) {

        QueryWrapper wrapper = new QueryWrapper();
        if(course.getSubjectParentId() != null && course.getSubjectParentId().length() > 0){
            wrapper.eq("subject_parent_id",course.getSubjectParentId());
        }
        if(course.getSubjectId() != null && course.getSubjectId().length() > 0){
            wrapper.eq("subject_id",course.getSubjectId());
        }
        if(course.getTeacherId() != null && course.getTeacherId().length() > 0){
            wrapper.eq("teacher_id",course.getTeacherId());
        }

        return ResultApi.ok().data("courseList", courseMappe.selectList(wrapper));
    }

}
