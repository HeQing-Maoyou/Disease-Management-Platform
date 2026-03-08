package com.community.health.service;

import com.community.common.result.ResponseResult;

public interface RiskService {
    ResponseResult<?> getRiskLevel(Long communityId, String building, String unit);
}
