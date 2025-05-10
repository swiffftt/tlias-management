package com.shuaige.aop;

import com.shuaige.mapper.OperateLogMapper;
import com.shuaige.pojo.OperateLog;
import com.shuaige.utils.CurrentHolder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class OperateLogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    // 定义切入点：com.shuaige.controller包下所有方法，并且带有@Log注解的方法
    @Around("@annotation(com.shuaige.anno.Log)")


    public Object logOperate(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录开始时间
        long startTime = System.currentTimeMillis();


        // 执行目标方法
        Object result = joinPoint.proceed();

        // 记录结束时间并计算耗时
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;

        // 创建操作日志对象
        OperateLog olog = new OperateLog();
        olog.setOperateEmpId(getCurrentUserId());// 这里需要根据实际情况获取当前操作人的ID
        olog.setOperateTime(LocalDateTime.now());
        olog.setClassName(joinPoint.getTarget().getClass().getName());
        olog.setMethodName(joinPoint.getSignature().getName());
        olog.setMethodParams(Arrays.toString(joinPoint.getArgs()));
        olog.setReturnValue(result != null ? result.toString() : "void");
        olog.setCostTime(costTime);

        // 调用Mapper保存日志
        log.info("操作日志记录成功: {}", olog);
        operateLogMapper.insert(olog);

        return result;


    }

    /**
     * 获取操作人ID
     */
    private Integer getCurrentUserId() {
        return CurrentHolder.getCurrentId();
    }
}