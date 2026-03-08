package com.community.health.controller;

import com.community.common.entity.HealthCheckin;
import com.community.common.result.ResponseResult;
import com.community.health.service.HealthCheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/health")
public class HealthCheckinController {

    @Autowired
    private HealthCheckinService healthCheckinService;

    @PostMapping("/checkin")
    public ResponseResult<?> checkin(@RequestParam Long userId, 
                                    @RequestParam BigDecimal temperature, 
                                    @RequestParam(required = false) String symptoms) {
        return healthCheckinService.checkin(userId, temperature, symptoms);
    }

    @GetMapping("/checkin/history")
    public ResponseResult<?> getCheckinHistory(@RequestParam Long userId, 
                                              @RequestParam(required = false) Integer page, 
                                              @RequestParam(required = false) Integer size) {
        return healthCheckinService.getCheckinHistory(userId, page, size);
    }
}
