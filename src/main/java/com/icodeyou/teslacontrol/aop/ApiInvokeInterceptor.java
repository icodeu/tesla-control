/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslacontrol.aop;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.icodeyou.teslaapi.lib.WakeUtil;
import com.icodeyou.teslacontrol.consts.ConstHolder;

@Aspect
@Component
public class ApiInvokeInterceptor {

    @Around("execution(* com.icodeyou.teslacontrol.controller.command..*Controller.*(..))")
    public Object invokeAround(ProceedingJoinPoint jp) {
        if (StringUtils.isEmpty(ConstHolder.ACCESS_TOKEN) || ConstHolder.VEHICLE_ID == null) {
            return "请先登录";
        }
        Boolean isWakeUp = WakeUtil.wakeUp(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID);
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