package com.online.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onlin.common.ResultApi;
import com.online.edu.entity.Subject;
import com.online.edu.listener.ExcelListener;
import com.online.edu.mapper.SubjectMapper;
import com.online.edu.pojo.SubjectExcelData;
import com.online.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//学科实现类
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public void saveSubjectType(MultipartFile file,SubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();

            //读这个excel，具体读取过程逻辑实现由监听器实现
            EasyExcel.read(in, SubjectExcelData.class,new ExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Subject> getAllSubjectByLevel() {
        List<Subject> subjectList = subjectMapper.getAllSubject();
        return subjectList;
    }
}
