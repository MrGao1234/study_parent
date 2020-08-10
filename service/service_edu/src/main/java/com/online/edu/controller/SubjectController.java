package com.online.edu.controller;

import com.onlin.common.ResultApi;
import com.online.edu.entity.Subject;
import com.online.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/edu/subject")
@RestController
@CrossOrigin
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/saveSubjectType")
    public ResultApi saveSubjectType(MultipartFile file){
        subjectService.saveSubjectType(file,subjectService);
        return ResultApi.ok();
    }

    @GetMapping("/findSubjectByLevel")
    public ResultApi findSubjectByLevel(){
        List<Subject> subjectList = subjectService.getAllSubjectByLevel();
        return ResultApi.ok().data("list",subjectList);
    }

}
