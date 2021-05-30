/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslacontrol.controller.command;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icodeyou.teslaapi.lib.VehicleUtil;
import com.icodeyou.teslacontrol.consts.ConstHolder;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @RequestMapping("/getFirstVehicleId")
    public Long getFirstVehicleId() {
        return VehicleUtil.getFirstVehicleId(ConstHolder.ACCESS_TOKEN);
    }

}
