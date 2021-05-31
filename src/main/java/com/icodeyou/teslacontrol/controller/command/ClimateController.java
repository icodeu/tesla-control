/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslacontrol.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icodeyou.teslaapi.lib.ClimateUtil;
import com.icodeyou.teslaapistarter.service.TeslaService;

@RestController
@RequestMapping("/climate")
public class ClimateController {

    @Autowired
    private TeslaService teslaService;

    @RequestMapping("/startAutoConditioning")
    public String startAutoConditioning(@RequestParam Integer temperature) {
        ClimateUtil.setTemperature(teslaService.accessToken, teslaService.vehicleId, temperature);
        return ClimateUtil.startAutoConditioning(teslaService.accessToken, teslaService.vehicleId)
                ? String.format("已开启空调，设置为%s度", temperature)
                : "开启空调失败";
    }

    @RequestMapping("/stopAutoConditioning")
    public String stopAutoConditioning(@RequestParam Integer temperature) {
        ClimateUtil.setTemperature(teslaService.accessToken, teslaService.vehicleId, temperature);
        return ClimateUtil.stopAutoConditioning(teslaService.accessToken, teslaService.vehicleId)
                ? "已关闭空调"
                : "关闭空调失败";
    }

}
