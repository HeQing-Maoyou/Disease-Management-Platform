package com.community.info.service;

import com.community.common.result.ResponseResult;

public interface MapService {
    ResponseResult<?> getLocations(String type, Double latitude, Double longitude);
}
