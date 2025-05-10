package com.shuaige.mapper;

import com.shuaige.pojo.Clazz;
import com.shuaige.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 班级管理的mapper
 */
@Mapper
public interface ClazzMapper {

    /**
     * 分页查询班级列表
     */
    List<Clazz> list(ClazzQueryParam clazzQueryParam);

    /**
     * 新增班级
     */
    void save(Clazz clazz);

    /**
     * 根据id删除班级
     */
    void delete(Integer id);

    /**
     * 根据id查询班级
     */
    Clazz findById(Integer id);

    /**
     * 根据id修改班级
     */
    void update(Clazz clazz);

    /**
     * 查询所有班级
     */
    List<Clazz> findAll();
}
