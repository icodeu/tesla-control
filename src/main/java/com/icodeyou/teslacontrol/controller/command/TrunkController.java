/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslacontrol.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icodeyou.teslaapi.lib.TrunkUtil;
import com.icodeyou.teslaapistarter.service.TeslaService;

@RestController
@RequestMapping("/trunk")
public class TrunkController {

    @Autowired
    private TeslaService teslaService;

    @RequestMapping("/actuateTrunk")
    public String actuateTrunk(@RequestParam String whichTrunk) {
        return TrunkUtil.actuateTrunk(teslaService.accessToken, teslaService.vehicleId, whichTrunk)
                ? "已开启或关闭"
                : "开关失败";
    }

}
