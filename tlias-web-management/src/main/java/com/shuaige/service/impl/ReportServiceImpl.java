package com.shuaige.service.impl;

import com.shuaige.mapper.EmpMapper;
import com.shuaige.mapper.StudentMapper;
import com.shuaige.pojo.JobOption;
import com.shuaige.pojo.StudentCountOption;
import com.shuaige.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public JobOption getEmpJobData() {
        //1.调用mapper接口，获取统计数据
        List<Map<String, Object>> list = empMapper.countEmpJobData();
        //map(第一个map集合):pos =教研主管，num =1 第二个map集合:pos = 学工主管，num = 2
        //2.组装结果，并返回
        // 拆是拆从数据库获取来的，装是为了把数据装给JobOption再返回给Controller层
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("num")).toList();
        return new JobOption(jobList,dataList);//getEmpJobData() 方法的返回值是一个 JobOption 对象，它会被传递给上层调用者（通常是 Controller 层）。Controller 层可以进一步将这些数据转换为 JSON 格式，供前端页面展示。
       /* 1. list.stream()
        list 是一个 List<Map<String, Object>> 类型的集合。
        调用 stream() 方法会将 list 转换为一个流（Stream），流是一种支持连续操作的数据序列，可以方便地对集合中的元素进行处理。

        2. .map(dataMap -> dataMap.get("pos"))
                .map() 是流的一个中间操作，用于将流中的每个元素映射成另一个对象。
        这里的 dataMap -> dataMap.get("pos") 是一个 Lambda 表达式：
        dataMap 是流中当前处理的元素（即 list 中的每一个 Map<String, Object>）。
        dataMap.get("pos") 从当前的 Map 中获取键为 "pos" 的值。
        因此，这一步的作用是从 list 中的每个 Map 中提取出 "pos" 对应的值，并生成一个新的流，流中的每个元素是提取出来的 "pos" 值。

        3. .toList()
                .toList() 是流的一个终端操作，用于将流中的元素收集到一个 List 中。
        在这行代码中，最终的结果是一个 List<Object>，其中包含从 list 中提取的所有 "pos" 值。

        4. 整体逻辑
        这段代码的作用是从 list 中的每个 Map 提取出键为 "pos" 的值，并将这些值存入一个新的 List 中。
        dataMap 是一个临时变量名，表示流中的当前元素。这个变量名可以随意命名，比如你可以写成 item -> item.get("pos") 或 map -> map.get("pos")，效果是一样的。
        .map() 是 Stream API 提供的一个中间操作，它的作用是对流中的每个元素应用一个函数，并生成一个新的流。*/
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }

    /**
     * 获取班级人数统计
     */
    @Override
    public StudentCountOption getStudentCountData() {
        List<Map<String, Object>> maps = studentMapper.countStuNumData();

        List<Object> jobList = maps.stream().map(dataMap -> dataMap.get("cname")).toList();

        List<Object> dataList = maps.stream().map(dataMap -> dataMap.get("scount")).toList();
        return new StudentCountOption(jobList,dataList);
    }

    /**
     * 获取学历统计
     */
    @Override
    public List<Map<String, Object>> getStudentEduData() {
        return studentMapper.countStuEduData();
    }
}

