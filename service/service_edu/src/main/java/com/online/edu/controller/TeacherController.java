package com.online.edu.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onlin.common.ResultApi;
import com.online.edu.entity.Teacher;
import com.online.edu.pojo.TeacherQuery;
import com.online.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-20
 */
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    //查询所有
    @GetMapping("fjndAllTeacher")
    public ResultApi findAll(){
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("name");

        List<Teacher> teacherList = teacherService.list(wrapper);

//        try{
//            int i = 1 / 0;
//        } catch (Exception e){
//            throw new MyExeption(500,"自定义异常");
//        }

        return ResultApi.ok().data("data",teacherList);
    }

    //根据id逻辑删除
    @DeleteMapping("drop/{id}") //通过路径传
    public ResultApi removeTeacher(@PathVariable String id){
        return ResultApi.ok().success(teacherService.removeById(id));
    }

    //分页查询
    @GetMapping("pageTeacher/{current}/{limit}")
    public ResultApi findTeacherByPage(@PathVariable long current, @PathVariable long limit){
        Page<Teacher> page = new Page(current,limit);

        teacherService.page(page,null);

        long total = page.getTotal();
        List<Teacher> teacherList = page.getRecords();

        return ResultApi.ok().data("total",total).data("list",teacherList);
    }


    //分页条件查询
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public ResultApi getTeacherPageCondition(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) TeacherQuery teacherQuery) throws InterruptedException {


        Page<Teacher> page = new Page<>(current,limit);
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();

        if(teacherQuery != null) {
            String name = teacherQuery.getName();
            Integer level = teacherQuery.getLevel();
            String begin = teacherQuery.getBegin();
            String end = teacherQuery.getEnd();
            if (!StringUtils.isEmpty(name)) {
                wrapper.like("name", name);
            }
            if (!StringUtils.isEmpty(level)) {
                wrapper.eq("level", level);
            }
            if (!StringUtils.isEmpty(begin)) {
                wrapper.ge("gmt_create", begin);
            }
            if (!StringUtils.isEmpty(end)) {
                wrapper.le("gmt_create", end);
            }

            wrapper.orderByDesc("gmt_modified").orderByDesc("gmt_create");
        }
        //调用方法
        teacherService.page(page,wrapper);
        long total = page.getTotal();
        List<Teacher> teacherList = page.getRecords();
        return ResultApi.ok().data("total",total).data("list",teacherList);
    }

    //添加讲师的方法
    @PostMapping("addTeacher")
    public ResultApi addTeaccher(@RequestBody Teacher teacher){
        boolean flag = teacherService.save(teacher);
        if(flag){
            return ResultApi.ok();
        }else{
            return ResultApi.error();
        }
    }

    //根据id查询讲师
    @GetMapping("findTeacherById/{id}")
    public ResultApi findTeacherById(@PathVariable String id){
        return ResultApi.ok().data("data",teacherService.getById(id));
    }

    //根据id修改
    @PostMapping("updateTeacher")
    public ResultApi updateTeacher(@RequestBody Teacher teacher){
        boolean flag = teacherService.updateById(teacher);
        if(flag){
            return ResultApi.ok();
        }else{
            return ResultApi.error();
        }
    }
}

