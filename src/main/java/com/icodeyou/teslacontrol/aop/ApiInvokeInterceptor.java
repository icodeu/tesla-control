/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslacontrol.aop;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icodeyou.teslaapi.lib.WakeUtil;
import com.icodeyou.teslaapistarter.service.TeslaService;

@Aspect
@Component
public class ApiInvokeInterceptor {

    @Autowired
    private TeslaService teslaService;

    @Around("execution(* com.icodeyou.teslacontrol.controller.command..*Controller.*(..))")
    public Object invokeAround(ProceedingJoinPoint jp) {
        if (StringUtils.isEmpty(teslaService.accessToken) || teslaService.vehicleId == null) {
            return "请先登录";
        }
        Boolean isWakeUp = WakeUtil.wakeUp(teslaService.accessToken, teslaService.vehicleId);
        if (!isWakeUp) {
            return "唤醒失败，请重试";
        }
        try {
            return jp.proceed(jp.getArgs());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return "执行失败";
    }

}