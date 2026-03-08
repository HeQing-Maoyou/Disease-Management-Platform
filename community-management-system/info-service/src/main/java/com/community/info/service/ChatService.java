package com.community.info.service;

import com.community.common.result.ResponseResult;

public interface ChatService {
    ResponseResult<?> sendMessage(Long userId, Long receiverId, String content);
    ResponseResult<?> getMessages(Long userId, Long otherUserId, Integer page, Integer size);
}
