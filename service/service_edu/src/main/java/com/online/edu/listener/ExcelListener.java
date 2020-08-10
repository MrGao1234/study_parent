package com.online.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.common.Handler.MyExeption;
import com.online.edu.entity.Subject;
import com.online.edu.pojo.SubjectExcelData;
import com.online.edu.service.SubjectService;

//学科表读取监听器
public class ExcelListener extends AnalysisEventListener<SubjectExcelData> {

    //不能使用自动注入，需要通过构造方法将 SubjectService 传递进来
    public SubjectService subjectService;
    public ExcelListener(){}
    public ExcelListener(SubjectService subjectService){
        this.subjectService = subjectService;
    }

    //subject 代表每次读的一行
    @Override
    public void invoke(SubjectExcelData subjectExcelData, AnalysisContext analysisContext) {
        //System.out.println(subjectExcelData.getOneSubjectName()+":"+subjectExcelData.getTwoSubjectName());
        //读取的文件为空
        if(subjectExcelData == null) {
            throw new MyExeption(20001, "文件数据为空");
        }

        //一级分类不能有重复
        Subject oneSubject = existOneSubject(subjectExcelData,subjectService);
        if(oneSubject == null){
            oneSubject = new Subject();
            oneSubject.setTitle(subjectExcelData.getOneSubjectName());
            oneSubject.setParentId("0");
            subjectService.save(oneSubject);

        }

        //二级分类不能有重复,此时 oneSubject 要么是已经有的，要么新添加的，所以不可能有空
        Subject twoSubject = exitsTwoSubject(subjectExcelData,oneSubject.getId(),subjectService);
        if(twoSubject == null){
            twoSubject = new Subject();
            twoSubject.setTitle(subjectExcelData.getTwoSubjectName());
            twoSubject.setParentId(oneSubject.getId());
            subjectService.save(twoSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    /**
     * 判断一级分类没有重复
     * */
    private Subject existOneSubject(SubjectExcelData subject,SubjectService subjectService){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",subject.getOneSubjectName());
        wrapper.eq("parent_id","0");
        Subject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    /**
     * 判断二级分类有没有重复
     * */
    private Subject exitsTwoSubject(SubjectExcelData subject,String pid,SubjectService subjectService){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",subject.getTwoSubjectName());
        wrapper.eq("parent_id",pid);
        Subject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }
}
