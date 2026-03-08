package com.community.health.controller;

import com.community.common.result.ResponseResult;
import com.community.health.service.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health")
public class RiskController {

    @Autowired
    private RiskService riskService;

    @GetMapping("/risk")
    public ResponseResult<?> getRiskLevel(@RequestParam Long communityId, 
                                         @RequestParam String building, 
                                         @RequestParam String unit) {
        return riskService.getRiskLevel(communityId, building, unit);
    }
}
