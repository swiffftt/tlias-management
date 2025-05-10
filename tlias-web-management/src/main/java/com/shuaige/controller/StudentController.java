package com.shuaige.controller;

import com.shuaige.pojo.PageResult;
import com.shuaige.pojo.Result;
import com.shuaige.pojo.Student;
import com.shuaige.pojo.StudentQueryParam;
import com.shuaige.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 李阳
 * @Date: 2025/04/07/21:19
 * @Description: 学员管理的一系列接口
 */

@RestController
@Slf4j
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 学员管理--学员列表分页查询
     */
    @GetMapping
    public Result page(StudentQueryParam studentQueryParam) {
        log.info("分页查询学员信息,参数：{}", studentQueryParam);
        PageResult<Student> pageResult = studentService.page(studentQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 学员管理-- 新增学员
     */
    @PostMapping
    public Result add(@RequestBody Student student) {
        log.info("新增学员信息,参数：{}", student);
        studentService.add(student);
        return Result.success();
    }

    /**
     * 学员管理-- 根据id查询学员
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        log.info("根据id查询学员信息,参数：{}", id);
        Student student = studentService.findById(id);
        return Result.success(student);
    }

    /**
     * 学员管理-- 根据id修改学员
     */
    @PutMapping
    public Result update(@RequestBody Student student) {
        log.info("修改学员信息,参数：{}", student);
        studentService.update(student);
        return Result.success();
    }

    /**
     * 学员管理-- 根据id批量删除学员
     */
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable("ids") List<Integer> ids) {
        log.info("删除学员信息,参数：{}", ids);
        studentService.delete(ids);
        return Result.success();
    }

    /**
     * 学员管理-- 违纪处理
     */
    @PutMapping("/violation/{id}/{score}")
    public Result violation(@PathVariable("id") Integer id, @PathVariable("score") Integer score) {
        log.info("学员违纪处理,参数：{},违纪分数:{}", id,score);
        studentService.violation(id, score);
        return Result.success();
    }
}
