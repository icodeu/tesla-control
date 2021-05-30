/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslacontrol.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icodeyou.teslaapi.lib.AuthUtil;
import com.icodeyou.teslaapi.lib.VehicleUtil;
import com.icodeyou.teslaapi.lib.WakeUtil;
import com.icodeyou.teslacontrol.consts.ConstHolder;

@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/getAccessToken")
    public String getAccessToken(@RequestParam String email, @RequestParam String password) {
        String accessToken = AuthUtil.login(email, password);
        ConstHolder.ACCESS_TOKEN = accessToken;
        return accessToken;
    }

    @RequestMapping("/init")
    public String init(@RequestParam String email, @RequestParam String password) {
        String accessToken = AuthUtil.login(email, password);
        ConstHolder.ACCESS_TOKEN = accessToken;
        Long vehicleId = VehicleUtil.getFirstVehicleId(accessToken);
        ConstHolder.VEHICLE_ID = vehicleId;

        Boolean isWakeUp = WakeUtil.wakeUp(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID);
        return isWakeUp ? "success " + accessToken.substring(0, 5) : "登录失败";
    }

}
