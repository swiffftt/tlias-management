package com.shuaige.service.impl;

import com.shuaige.mapper.EmpLogMapper;
import com.shuaige.pojo.EmpLog;
import com.shuaige.service.EmpLogService;
import com.shuaige.pojo.EmpLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpLogServiceImpl implements EmpLogService {

    @Autowired
    private EmpLogMapper empLogMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)//需要在一个新的事务中运行
    @Override
    public void insertLog(EmpLog empLog) {

        empLogMapper.insert(empLog);
    }
}
