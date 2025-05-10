package com.shuaige.mapper;


import com.shuaige.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper // ① MyBatis标识接口需生成代理实现类
public interface DeptMapper {
    /**
     * 查询所有部门信息
     */
    //方式一：手动结果映射
//@Results({
//            @Result(column = "create_time", property = "createTime"),
//            @Result(column = "update_time", property = "updateTime")
//    })

    //方式二：起别名
//@Select("select id, name, create_time createTime, update_time updateTime from dept order by update_time desc;")
    //方式三：开启驼峰命名映射开关，在application.yml中配置即可
@Select("select id, name, create_time, update_time from dept order by update_time desc;")
    List<Dept> findAll(); // ③ 方法名对应SQL操作

    /**
     * 根据id删除部门信息
     */
    @Delete("delete from dept where id=#{id}")
    void deleteById(Integer id);

    /**
     * 新增部门
     */
    @Insert("insert into dept(name, create_time, update_time) values(#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);

    /**
     * 修改部门（根据ID查询部门数据
     */
    @Select("select id, name, create_time, update_time from dept where id =#{id}")
    Dept getById(Integer id);

    /**
     * 修改部门
     */
    @Update("update dept set name = #{name} ,update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}
