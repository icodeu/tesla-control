/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslacontrol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icodeyou.teslaapi.lib.WakeUtil;
import com.icodeyou.teslaapistarter.service.TeslaService;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private TeslaService teslaService;

    @RequestMapping("/init")
    public String init() {
        teslaService.initLogin();
        teslaService.initFirstVehicleId();
        Boolean isWakeUp = WakeUtil.wakeUp(teslaService.accessToken, teslaService.vehicleId);
        return isWakeUp ? "success " + teslaService.accessToken.substring(0, 5) : "登录失败";
    }

}
