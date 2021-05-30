/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslacontrol.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icodeyou.teslaapi.lib.WakeUtil;
import com.icodeyou.teslacontrol.consts.ConstHolder;

@RestController
@RequestMapping("/wake")
public class WakeUpController {

    @RequestMapping("/wakeUp")
    public Boolean wakeUp() {
        return WakeUtil.wakeUp(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID);
    }

}
