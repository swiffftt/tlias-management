package com.shuaige.controller;

import com.shuaige.pojo.JobOption;
import com.shuaige.pojo.Result;
import com.shuaige.pojo.StudentCountOption;
import com.shuaige.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/report")
@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;
    /**
     * 统计员工职位人数
     * @return
     */
    @GetMapping("/empJobData")
    public Result getEmpJobData(){
        log.info("统计员工职位人数");
         JobOption jobOption= reportService.getEmpJobData();
         return Result.success(jobOption);
    }

    /**
     * 统计员工性别人数
     */
    @GetMapping("/empGenderData")
    public Result getEmpGenderData(){
    List<Map<String,Object>> genderList =  reportService.getEmpGenderData();
    return Result.success(genderList);
    }


    /**
     * 获取班级人数统计
     */
    @GetMapping("/studentCountData")
    public Result getClassNumData() {
        log.info("获取班级人数统计");
        StudentCountOption studentCountOption= reportService.getStudentCountData();
        return Result.success(studentCountOption);
    }

    /**
     * 获取学员学历统计
     */
    @GetMapping("/studentDegreeData")
    public Result getStudentEduData() {
        log.info("获取学员学历统计");
        List<Map<String,Object>> eduList= reportService.getStudentEduData();
        return Result.success(eduList);
    }
}
