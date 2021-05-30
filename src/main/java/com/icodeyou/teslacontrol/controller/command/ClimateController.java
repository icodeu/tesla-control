/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslacontrol.controller.command;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icodeyou.teslaapi.lib.ClimateUtil;
import com.icodeyou.teslacontrol.consts.ConstHolder;

@RestController
@RequestMapping("/climate")
public class ClimateController {

    @RequestMapping("/startAutoConditioning")
    public String startAutoConditioning(@RequestParam Integer temperature) {
        ClimateUtil.setTemperature(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID, temperature);
        return ClimateUtil.startAutoConditioning(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID)
                ? String.format("已开启空调，设置为%s度", temperature)
                : "开启空调失败";
    }

    @RequestMapping("/stopAutoConditioning")
    public String stopAutoConditioning(@RequestParam Integer temperature) {
        ClimateUtil.setTemperature(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID, temperature);
        return ClimateUtil.stopAutoConditioning(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID)
                ? "已关闭空调"
                : "关闭空调失败";
    }

}
