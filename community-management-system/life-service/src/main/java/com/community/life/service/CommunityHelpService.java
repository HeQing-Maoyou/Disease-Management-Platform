package com.community.life.service;

import com.community.common.result.ResponseResult;

public interface CommunityHelpService {
    ResponseResult<?> publishHelp(Long userId, String type, String content, String contactInfo);
    ResponseResult<?> getHelpList(String type, Integer page, Integer size);
    ResponseResult<?> updateHelpStatus(Long helpId, String status);
}
