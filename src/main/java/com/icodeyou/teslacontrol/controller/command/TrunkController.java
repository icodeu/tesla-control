/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslacontrol.controller.command;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icodeyou.teslaapi.lib.TrunkUtil;
import com.icodeyou.teslacontrol.consts.ConstHolder;

@RestController
@RequestMapping("/trunk")
public class TrunkController {

    @RequestMapping("/actuateTrunk")
    public String actuateTrunk(@RequestParam String whichTrunk) {
        return TrunkUtil.actuateTrunk(ConstHolder.ACCESS_TOKEN, ConstHolder.VEHICLE_ID, whichTrunk)
                ? "已开启或关闭"
                : "开关失败";
    }

}
