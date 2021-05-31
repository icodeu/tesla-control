/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslacontrol.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icodeyou.teslaapi.lib.ChargingUtil;
import com.icodeyou.teslaapistarter.service.TeslaService;

@RestController
@RequestMapping("/charging")
public class ChargingController {

    @Autowired
    private TeslaService teslaService;

    @RequestMapping("/openChargePortDoor")
    public String openChargePortDoor() {
        return ChargingUtil.openChargePortDoor(teslaService.accessToken, teslaService.vehicleId)
                ? "已开启充电口"
                : "开启充电口失败";
    }

    @RequestMapping("/closeChargePortDoor")
    public String closeChargePortDoor() {
        return ChargingUtil.closeChargePortDoor(teslaService.accessToken, teslaService.vehicleId)
                ? "已关闭充电口"
                : "关闭充电口失败";
    }

    @RequestMapping("/setChargeLimit")
    public String setChargeLimit(Integer limit) {
        return ChargingUtil.setChargeLimit(teslaService.accessToken, teslaService.vehicleId, limit)
                ? String.format("设置为%s", limit)
                : "设置充电限制失败";
    }

}
