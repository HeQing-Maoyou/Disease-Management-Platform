package com.community.health.service;

import com.community.common.result.ResponseResult;
import java.math.BigDecimal;

public interface HealthCheckinService {
    ResponseResult<?> checkin(Long userId, BigDecimal temperature, String symptoms);
    ResponseResult<?> getCheckinHistory(Long userId, Integer page, Integer size);
}
