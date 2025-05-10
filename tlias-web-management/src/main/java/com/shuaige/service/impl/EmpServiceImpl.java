package com.shuaige.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shuaige.mapper.EmpExprMapper;
import com.shuaige.mapper.EmpMapper;
import com.shuaige.pojo.*;
import com.shuaige.service.EmpLogService;
import com.shuaige.service.EmpService;
import com.shuaige.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;//事务的控制写在业务层

    /**
     * 原始分页查询
     //* @param page 页码
     //* @param pageSize 每页展示的记录数
     * @return
     */

    /*@Override
    public PageResult<Emp> page(Integer page, Integer pageSize) {
        //1.调用mapper 接口，查询总记录数
        Long total = empMapper.count();


        //2.调用mapper接口，查询结果列表
        //第一个参数page在service层本身是页码，在mapper层要转换成符合SQL语法的起始记录
        Integer start = (page - 1)*pageSize;
//        List<Emp> rows = empMapper.list(page, pageSize);错误写法   是起始索引不是页码
        List<Emp> rows = empMapper.list(start, pageSize);

        //3.封装结果 pageResult
        return new PageResult<Emp>(total,rows);
    }*/

    /**
     * PageHelper分页查询
//     * @param page 页码
//     * @param pageSize 每页展示的记录数
     * 注意事项：
     *        1.定义的SQL语句结尾不能加分号，
     *        2.PageHelper仅仅能在其后的第一个查询语句进行分页处理
     */
/*    @Override
    public PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end) {
        //1.设置分页参数(PageHelper)
        PageHelper.startPage(page,pageSize);

        //2.执行查询
        List<Emp> empList = empMapper.list(name, gender, begin, end);

        //3.解析查询结果，并封装
        Page<Emp> p = (Page<Emp>) empList;

        return new PageResult<Emp>(p.getTotal(),p.getResult());

    }*/
    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        //1.设置分页参数(PageHelper)
        PageHelper.startPage(empQueryParam.getPage(),empQueryParam.getPageSize());

        //2.执行查询
        List<Emp> empList = empMapper.list(empQueryParam);

        //3.解析查询结果，并封装
        Page<Emp> p = (Page<Emp>) empList;

        return new PageResult<Emp>(p.getTotal(),p.getResult());

    }

    // 使用@Transactional注解声明该方法将作为一个事务执行
    // rollbackFor参数指定哪些异常会导致事务回滚，在这里是所有的Exception类异常
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void save(Emp emp) throws Exception {
        try {
            // 1. 保存员工的基本信息
            // 设置创建时间和更新时间为当前时间
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            // 执行员工基本信息的插入操作
            empMapper.insert(emp);

            // 2. 保存员工工作信息
            // 获取刚插入的员工ID，
            // 这一行代码的作用是获取刚刚插入的员工记录的主键ID，并将其赋值给局部变量empId。这个ID将在后续代码中用于关联其他数据，例如保存该员工的工作经历信息。
            Integer empId = emp.getId();
            // 获取员工的工作经历列表
            List<EmpExpr> exprList = emp.getExprList();
            // 检查工作经历列表是否不为空
            if (!CollectionUtils.isEmpty(exprList)) {
                // 遍历集合，为每个工作经历设置员工ID
                exprList.forEach(empExpr -> {
                    empExpr.setEmpId(emp.getId());
                });
                // 执行批量插入工作经历的操作
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            // 记录操作日志
            // 创建一个新的日志对象，记录当前时间的操作内容
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工：" + emp);
            // 调用服务方法插入日志
            empLogService.insertLog(empLog);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(List<Integer> ids) {
        //1.批量删除员工基本信息
        empMapper.deleteByIds(ids);

        //2.批量删除员工工作信息
        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(Emp emp) {
        //1.根据ID修改员工的工作经历信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        //2.根据ID修改员工的工作经历信息
        //先删除原有的工作经历(复用deleteByEmpIds这个方法，之前形参被定义成了List,所以这里必须转成List
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));

        //后添加这个员工新的工作经历 这边要设置id的原因是存在有新增员工时没有插入员工工作经历信息的数据，然后在修改时增加员工的工作经历信息,
        // 如果是空的话就代表他删除工作经历后，没在添加工作经历，继而就不需要后续操作了
        //首先他是删除的数据库里面的工作经历，但是遍历的是传递来的emp对象里面的工作经历

        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(empExpr -> empExpr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }
    }

    /**
     * 查询所有员工
     */

    @Override
    public List<Emp> findAll() {
        return empMapper.findAll();
    }

    /**
     * 员工登录
     */
    @Override
    public LoginInfo login(Emp emp) {
        //调用Mapper接口,根据用户名和密码查询信息
        Emp e = empMapper.selectByUsernameAndPassword(emp);
        //判断用户判断是否存在这个员工，如果存在则组装信息
        if(e!=null){
            log.info("登录成功:{}",e);
            //生成JWT令牌
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",e.getId());
            claims.put("username",e.getUsername());
          String jwt = JwtUtils.generateToken(claims);//工具类使用 static 关键字修饰的方法可以直接通过类名调用，无需创建类的对象。
            return new LoginInfo(e.getId(),e.getUsername(),e.getName(),jwt);
        }
        //不存在则返回null
        return null;
    }
}
