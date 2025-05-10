package com.shuaige.service;

import com.shuaige.pojo.Dept;

import java.util.List;

public interface DeptService {
    /**
     * 查询所有部门信息
     */
    List<Dept> findAll();

    /**
     * 根据id删除部门信息
     */
    void deleteById(Integer id);

    /**
     * 添加部门信息
     * @param dept
     */
    void add(Dept dept);

    /**
     * 修改部门（根据ID查询部门）
     */
    Dept getById(Integer id);

    /**
     * 修改部门
     */
    void update(Dept dept);
}
