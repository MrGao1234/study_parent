package com.online.edu.service;

import com.onlin.common.ResultApi;
import com.online.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-20
 */
public interface TeacherService extends IService<Teacher> {

    ResultApi findTeacherByHot();
}
