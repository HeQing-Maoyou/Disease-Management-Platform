package com.community.life.controller;

import com.community.common.result.ResponseResult;
import com.community.life.service.QuarantineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/quarantine")
public class QuarantineController {

    @Autowired
    private QuarantineService quarantineService;

    @PostMapping("/services")
    public ResponseResult<?> bookService(@RequestParam Long userId, 
                                        @RequestParam String serviceType, 
                                        @RequestParam Date appointmentTime) {
        return quarantineService.bookService(userId, serviceType, appointmentTime);
    }

    @GetMapping("/services")
    public ResponseResult<?> getServices(@RequestParam Long userId) {
        return quarantineService.getServices(userId);
    }

    @PutMapping("/services/{serviceId}/status")
    public ResponseResult<?> updateServiceStatus(@PathVariable Long serviceId, @RequestParam String status) {
        return quarantineService.updateServiceStatus(serviceId, status);
    }
}
