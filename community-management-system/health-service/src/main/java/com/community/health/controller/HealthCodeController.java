package com.community.health.controller;

import com.community.common.result.ResponseResult;
import com.community.health.service.HealthCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health")
public class HealthCodeController {

    @Autowired
    private HealthCodeService healthCodeService;

    @GetMapping("/code")
    public ResponseResult<?> getHealthCode(@RequestParam Long userId) {
        return healthCodeService.getHealthCode(userId);
    }

    @PostMapping("/code/proxy")
    public ResponseResult<?> proxyHealthCode(@RequestParam Long userId, @RequestParam Long proxyUserId) {
        return healthCodeService.proxyHealthCode(userId, proxyUserId);
    }
}
