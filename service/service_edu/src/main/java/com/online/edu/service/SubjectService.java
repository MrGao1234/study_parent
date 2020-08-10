package com.online.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.entity.Subject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SubjectService extends IService<Subject> {

    //存储新分类
    void saveSubjectType(MultipartFile file,SubjectService subjectService);

    //按层级关系查询所有分类
    List<Subject> getAllSubjectByLevel();
}
