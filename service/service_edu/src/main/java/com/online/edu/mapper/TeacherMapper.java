package com.online.edu.mapper;

import com.online.edu.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-07-20
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

    List<Teacher> findTeacherByHot();
}
