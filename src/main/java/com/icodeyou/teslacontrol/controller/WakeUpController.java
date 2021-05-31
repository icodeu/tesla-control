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
@RequestMapping("/wake")
public class WakeUpController {

    @Autowired
    private TeslaService teslaService;

    @RequestMapping("/wakeUp")
    public Boolean wakeUp() {
        return WakeUtil.wakeUp(teslaService.accessToken, teslaService.vehicleId);
    }

}
