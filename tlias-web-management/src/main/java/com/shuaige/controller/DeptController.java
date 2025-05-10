package com.shuaige.controller;

import com.shuaige.anno.Log;
import com.shuaige.pojo.Dept;
import com.shuaige.pojo.Result;
import com.shuaige.service.DeptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RequestMapping("/depts")
@RestController // ① 声明为REST控制器，自动将返回值转为JSON
public class DeptController {

    @Autowired// ② 自动注入Service层实现类（实际是DeptServiceImpl实例）
    private DeptService deptService;
//    @RequestMapping(value = "/depts",method = RequestMethod.GET )

    @GetMapping// ③ 映射GET请求到/depts路径
    public Result list(){
//        System.out.println("查询全部部门数据");
        log.info("查询全部部门数据");
        List<Dept> deptList = deptService.findAll(); // ④ 调用业务层方法
        return Result.success(deptList); // ⑤ 统一响应封装

    }

    /**
     * 删除部门 --方式一： 基于HttpServletRequest 获取请求参数
     */
/*    @DeleteMapping("/depts")
    public Result delete(HttpServletRequest request){
        String Str = request.getParameter("id");
        Integer id = Integer.parseInt(Str);
        System.out.println("根据ID删除部门: "+ id);
        return Result.success();

    }*/

    /**
     * 删除部门 --方式二： 基于@RequestParam注解获取请求参数
     * 注意事项：一旦声明了@RequestParam注解，那么该参数在请求时必须传递，否则报错（默认 required=true）
     */
  /*  @DeleteMapping("/depts")
    public Result delete(@RequestParam(value = "id",required = false) Integer deptId){
        System.out.println("根据ID删除部门: "+ deptId);
        return Result.success();
    }*/

    /**
     * 删除部门 --方式三： 省略@RequestParam注解，(前端传递的参数名与服务端形参名一致)
     */
    @Log
    @DeleteMapping
    public Result delete(Integer id){
//        System.out.println("根据ID删除部门: "+ id);
        log.info("根据ID删除部门:{}",id);
        deptService.deleteById(id);
        return Result.success();
    }

    /**
     * 添加部门
     */
    @Log
    @PostMapping
    public Result add(@RequestBody Dept dept){
        //@RequestBody 表示将请求体中的JSON数据映射为Java对象
//        System.out.println("添加部门: "+ dept);
        log.info("添加部门:{}",dept);
        deptService.add(dept);
        return Result.success();
    }

    /**
     * 修改部门(根据ID查询数据）
     */
/*    @GetMapping("/depts/{id}")
    public Result getInfo(@PathVariable("id") Integer deptId){
        System.out.println("根据ID查询部门:" + deptId);
        return Result.success();

    }*/

    /**
     * 修改部门(根据ID查询数据）
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        //@PathVariable 注解表示从URL路径中获取参数
//        System.out.println("根据ID查询部门:" + id);
        log.info("根据ID查询部门: {}" , id);
        Dept dept = deptService.getById(id);//根据id查询的数据（集合/单个对象）需要返回给前端，这里返回的数据是单个对象，因为id是主键，最多只会查询出一条数据
        return Result.success(dept);

    }
    /**
     * 修改部门
     */
    @Log
    @PutMapping
    public Result update(@RequestBody Dept dept){
//        System.out.println("修改部门" +dept);
        log.info("修改部门: {}" ,dept);
        deptService.update(dept);
        return Result.success();

    }


}
