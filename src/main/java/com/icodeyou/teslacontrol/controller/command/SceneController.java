/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslacontrol.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icodeyou.teslaapi.lib.ClimateUtil;
import com.icodeyou.teslaapi.lib.StateUtil;
import com.icodeyou.teslaapi.model.ClimateState;
import com.icodeyou.teslaapistarter.service.TeslaService;

@RestController
@RequestMapping("/scene")
public class SceneController {

    @Autowired
    private TeslaService teslaService;

    @RequestMapping("/readyGo")
    public String readyGo(@RequestParam Integer minTemperature,
                          @RequestParam Integer maxTemperature,
                          @RequestParam Integer setTemperature) {
        ClimateState climateState = StateUtil.getClimateState(teslaService.accessToken, teslaService.vehicleId);
        if (climateState.getResponse().getInside_temp() > minTemperature
                && climateState.getResponse().getInside_temp() < maxTemperature) {
            return "温度适宜，不用开空调";
        } else {
            ClimateUtil.setTemperature(teslaService.accessToken, teslaService.vehicleId, setTemperature);
            Boolean isSuccess = ClimateUtil.startAutoConditioning(teslaService.accessToken, teslaService.vehicleId);
            String result = isSuccess
                    ? String.format("已开启空调，当前为%s度，设置为%s度", climateState.getResponse().getInside_temp(), setTemperature)
                    : "开启空调失败";
            if (isSuccess) {
                if (climateState.getResponse().getInside_temp() <= minTemperature) {
                    // Winner
                    ClimateUtil.setSeatHeater(teslaService.accessToken, teslaService.vehicleId, 0, 1);
                    ClimateUtil.setSeatHeater(teslaService.accessToken, teslaService.vehicleId, 1, 1);
                    ClimateUtil.setWheelHeater(teslaService.accessToken, teslaService.vehicleId, true);
                    result += "，开启座椅和方向盘加热";
                } else if (climateState.getResponse().getInside_temp() >= maxTemperature) {
                    // Summer
                    ClimateUtil.setSeatHeater(teslaService.accessToken, teslaService.vehicleId, 0, 0);
                    ClimateUtil.setSeatHeater(teslaService.accessToken, teslaService.vehicleId, 1, 0);
                    ClimateUtil.setWheelHeater(teslaService.accessToken, teslaService.vehicleId, false);
                    result += "，关闭座椅和方向盘加热";
                }
            }

            return result;
        }
    }

}
