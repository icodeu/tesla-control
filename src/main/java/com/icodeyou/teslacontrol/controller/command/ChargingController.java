/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslacontrol.controller.command;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icodeyou.teslaapi.lib.ChargingUtil;
import com.icodeyou.teslacontrol.consts.ConstHolder;

@RestController
@RequestMapping("/charging")
public class ChargingController {

    @RequestMapping("/openChargePortDoor")
    public String openChargePortDoor() {
        return ChargingUtil.openChargePortDoor(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID)
                ? "已开启充电口"
                : "开启充电口失败";
    }

    @RequestMapping("/closeChargePortDoor")
    public String closeChargePortDoor() {
        return ChargingUtil.closeChargePortDoor(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID)
                ? "已关闭充电口"
                : "关闭充电口失败";
    }

    @RequestMapping("/setChargeLimit")
    public String setChargeLimit(Integer limit) {
        return ChargingUtil.setChargeLimit(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID, limit)
                ? String.format("设置为%s", limit)
                : "设置充电限制失败";
    }

}
