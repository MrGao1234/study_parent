package com.online.edu.service.impl;

import com.onlin.common.ResultApi;
import com.online.edu.entity.Teacher;
import com.online.edu.mapper.TeacherMapper;
import com.online.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-20
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public ResultApi findTeacherByHot() {
        List<Teacher> teacherList = teacherMapper.findTeacherByHot();
        return ResultApi.ok().data("list",teacherList);
    }
}
