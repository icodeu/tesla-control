/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslacontrol.controller.command;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icodeyou.teslaapi.lib.ClimateUtil;
import com.icodeyou.teslaapi.lib.StateUtil;
import com.icodeyou.teslaapi.model.ClimateState;
import com.icodeyou.teslacontrol.consts.ConstHolder;

@RestController
@RequestMapping("/scene")
public class SceneController {

    @RequestMapping("/readyGo")
    public String readyGo(@RequestParam Integer minTemperature,
                          @RequestParam Integer maxTemperature,
                          @RequestParam Integer setTemperature) {
        ClimateState climateState = StateUtil.getClimateState(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID);
        if (climateState.getResponse().getInside_temp() > minTemperature
                && climateState.getResponse().getInside_temp() < maxTemperature) {
            return "温度适宜，不用开空调";
        } else {
            ClimateUtil.setTemperature(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID, setTemperature);
            Boolean isSuccess = ClimateUtil.startAutoConditioning(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID);
            String result = isSuccess
                    ? String.format("已开启空调，当前为%s度，设置为%s度", climateState.getResponse().getInside_temp(), setTemperature)
                    : "开启空调失败";
            if (isSuccess) {
                if (climateState.getResponse().getInside_temp() <= minTemperature) {
                    // Winner
                    ClimateUtil.setSeatHeater(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID, 0, 1);
                    ClimateUtil.setSeatHeater(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID, 1, 1);
                    ClimateUtil.setWheelHeater(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID, true);
                    result += "，开启座椅和方向盘加热";
                } else if (climateState.getResponse().getInside_temp() >= maxTemperature) {
                    // Summer
                    ClimateUtil.setSeatHeater(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID, 0, 0);
                    ClimateUtil.setSeatHeater(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID, 1, 0);
                    ClimateUtil.setWheelHeater(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID, false);
                    result += "，关闭座椅和方向盘加热";
                }
            }

            return result;
        }
    }

}
