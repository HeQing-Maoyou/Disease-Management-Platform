package com.community.health.service;

import com.community.common.result.ResponseResult;

public interface HealthCodeService {
    ResponseResult<?> getHealthCode(Long userId);
    ResponseResult<?> proxyHealthCode(Long userId, Long proxyUserId);
}
