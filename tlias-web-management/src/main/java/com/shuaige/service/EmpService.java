package com.shuaige.service;

import com.shuaige.pojo.Emp;
import com.shuaige.pojo.EmpQueryParam;
import com.shuaige.pojo.LoginInfo;
import com.shuaige.pojo.PageResult;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {

    /**
     * 分页查询
     */
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    /**
     * 新增员工信息
     */
    void save(Emp emp) throws Exception;

    /**
     * 批量删除员工信息
     */
    void delete(List<Integer> ids);

    /**
     * 根据ID查询员工信息
     */
    Emp getInfo(Integer id);

    /**
     * 修改员工
     */
    void update(Emp emp);

    /**
     * 查询所有员工
     */
    List<Emp> findAll();

    /**
     * 员工登录
     */
    LoginInfo login(Emp emp);

    /**
     * 分页查询
     //* @param page 页码
     //* @param pageSize 每页展示的记录数
     * @return
     */
//    PageResult<Emp> page(Integer page, Integer pageSize,String name, Integer gender, LocalDate begin, LocalDate end);
}
