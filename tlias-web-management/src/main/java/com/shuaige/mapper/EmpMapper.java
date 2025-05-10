package com.shuaige.mapper;
import com.shuaige.pojo.Emp;
import com.shuaige.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 员工信息
 */


@Mapper
public interface EmpMapper {
    // ------------------------------------------原始分页查询实现方式------------------
    /**
     * 查询总记录数
     */
//    @Select("select count(*) from emp e left join dept d on e.dept_id =d.id")
//    public Long count();

    /**
     *分页查询
     */
//    @Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id =d.id" +
//            " order by e.update_time desc limit #{start},#{pageSize}; ")
//    public List<Emp> list(Integer start, Integer pageSize);


    //@Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id =d.id order by e.update_time desc ")

    /**
     * 条件查询员工信息
     */
    public List<Emp> list(EmpQueryParam empQueryParam);


    /**
     * 新增员工基本信息
     */
    @Options(useGeneratedKeys = true,keyProperty = "id")//获取到生成的主键----主键返回
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            "values(#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);

    /**
     * 根据ID批量删除员工的基本信息
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 根据ID查询员工信息以及员工工作经历信息
     * @param id
     * @return
     */
    Emp getById(Integer id);

    /**
     * 根据ID更新员工信息
     */
    void updateById(Emp emp);

    /**
     *统计员工职位人数
     */
    @MapKey("pos")//mybatisx的误报错，可以忽略
    List<Map<String,Object>> countEmpJobData();

    /**
     * 统计员工性别人数
     */
    @MapKey("name")//mybatisx的误报错，可以忽略
    List<Map<String, Object>> countEmpGenderData();

    /**
     * 查询所有员工信息
     */
    List<Emp> findAll();

    /**
     * 根据员工用户名和密码查询员工信息
     */
    @Select("select id,username,name from emp where username = #{username} and password = #{password}")
    Emp selectByUsernameAndPassword(Emp emp);
}
