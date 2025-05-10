package com.shuaige.controller;

import com.shuaige.pojo.Emp;
import com.shuaige.pojo.EmpQueryParam;
import com.shuaige.pojo.PageResult;
import com.shuaige.pojo.Result;
import com.shuaige.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * 员工管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {
   @Autowired
    private EmpService empService;
    /**
     * 分页查询
     */
    @GetMapping
    public Result page(EmpQueryParam empQueryParam){
     log.info("分页查询:{}", empQueryParam);
     PageResult<Emp> pageResult = empService.page(empQueryParam);
     return Result.success(pageResult);
    }

 /**
  * 新增员工
  */
 @PostMapping
 public Result save(@RequestBody Emp emp) throws Exception {
  ////前段返回过来的是json格式的数据，@RequestBody 表示将请求体中的JSON数据映射为Java对象
    log.info("新增员工:{}",emp);
    empService.save(emp);
    return Result.success();

    }

    /**
     * 删除员工 - 数组
     */
/*
    @DeleteMapping
    public  Result delete(Integer[] ids){
        log.info("根据ID删除员工:{}", Arrays.toString(ids));
        return Result.success();

    }
*/

    /**
     * 删除员工 - List
     */
    @DeleteMapping
    public  Result delete(@RequestParam List<Integer> ids){
        //@RequestParam(像list这样的复杂类型中不能省略)
        log.info("根据ID删除员工:{}",ids);
        empService.delete(ids);
        return Result.success();

    }

    /**
     * 根据ID查询员工信息
     * @param id
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据ID查询员工信息: {}", id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

    /**
     * 修改员工
     */
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("修改员工:{}",emp);
        empService.update(emp);
        return Result.success();

    }
    /**
     * 员工管理--查询所有员工
     */
    @GetMapping("/list")
    public Result findAll(){
        log.info("查询所有员工信息");
        List<Emp> empList = empService.findAll();
        return Result.success(empList);
    }
}
