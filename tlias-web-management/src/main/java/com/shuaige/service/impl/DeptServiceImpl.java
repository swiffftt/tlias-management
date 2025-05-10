package com.shuaige.service.impl;

import com.shuaige.mapper.DeptMapper;
import com.shuaige.pojo.Dept;
import com.shuaige.service.DeptService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service // ① 声明为Spring管理的业务Bean
public class DeptServiceImpl implements DeptService {

    @Autowired//Mapper交给IOC管理了，这里要注入对象，就需要添加注解//// ② 自动注入Mapper接口代理对象（MyBatis动态生成）
    private DeptMapper deptMapper;

    @Override// ③ 实现接口定义的方法
    public List<Dept> findAll() {
        return deptMapper.findAll();  // ④ 直接透传Mapper查询结果
    }

    @Override
    public void deleteById(Integer id) {
        deptMapper.deleteById(id);//dml语句的返回值是受影响的行数，所以不需要返回值
    }

    @Override
    public void add(Dept dept) {
        //1.补全基础属性 - 创建时间crateTime、更新时间updateTime
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

        //2.调用Mapper接口方法来插入数据
        deptMapper.insert(dept);

    }

    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    @Override
    public void update(Dept dept) {
        //1.补全属性-updateTime
        dept.setUpdateTime(LocalDateTime.now());
        //2.调用Mapper接口方法来插入数据
        deptMapper.update(dept);
    }
}
