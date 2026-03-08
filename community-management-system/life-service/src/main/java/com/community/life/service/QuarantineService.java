package com.community.life.service;

import com.community.common.result.ResponseResult;
import java.util.Date;

public interface QuarantineService {
    ResponseResult<?> bookService(Long userId, String serviceType, Date appointmentTime);
    ResponseResult<?> getServices(Long userId);
    ResponseResult<?> updateServiceStatus(Long serviceId, String status);
}
