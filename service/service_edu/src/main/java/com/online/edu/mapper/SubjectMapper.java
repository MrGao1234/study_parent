package com.online.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.edu.entity.Subject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/* subject */
@Mapper
public interface SubjectMapper extends BaseMapper<Subject> {

    //获取所有的课程，带有层级关系
    List<Subject> getAllSubject();

}
