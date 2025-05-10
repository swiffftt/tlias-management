package com.shuaige.service;

import com.shuaige.pojo.JobOption;
import com.shuaige.pojo.StudentCountOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    /**
     * 统计员工职位人数
     */
    JobOption getEmpJobData();

    /**
     * 统计员工性别人数
     */
    List<Map<String, Object>> getEmpGenderData();

    /**
     * 统计班级人数
     */
    StudentCountOption getStudentCountData();

    /**
     * 统计学员学历
     */
    List<Map<String, Object>> getStudentEduData();
}
