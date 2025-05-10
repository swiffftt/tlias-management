package com.shuaige.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shuaige.mapper.StudentMapper;
import com.shuaige.pojo.PageResult;
import com.shuaige.pojo.Student;
import com.shuaige.pojo.StudentQueryParam;
import com.shuaige.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 李阳
 * @Date: 2025/04/09/10:50
 * @Description: 学员管理的业务层接口的实现类
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;



    /**
     * 学员管理-分页查询
     */
    @Override
    public PageResult<Student> page(StudentQueryParam studentQueryParam) {
        //1.设置分页参数
        PageHelper.startPage(studentQueryParam.getPage(),studentQueryParam.getPageSize());
        //2.调用分页查询的方法
        List<Student> studentList = studentMapper.list(studentQueryParam);
        //3.封装结果并返回
        Page<Student> pageResult = (Page<Student>) studentList;
        return new PageResult<Student> (pageResult.getTotal(),pageResult.getResult());
    }

    /**
     * 学员管理-新增
     */
    @Override
    public void add(Student student) {
        //补全基本信息
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        //2.调用mapper接口插入数据
        studentMapper.save(student);
    }

    /**
     * 学员管理-根据id查询
     */
    @Override
    public Student findById(Integer id) {

        return studentMapper.findById(id);
    }

    /**
     * 学员管理-根据id修改
     */
    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.update(student);
    }

    /**
     * 学员管理-批量删除
     */

    @Override
    public void delete(List<Integer> ids) {
     studentMapper.delete(ids);
    }

    /**
     * 学员管理-违纪处理
     */
    @Override
    public void violation(Integer id, Integer score) {
        studentMapper.violation(id,score);
    }
}
