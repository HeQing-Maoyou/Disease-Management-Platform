package com.community.info.service;

import com.community.common.result.ResponseResult;

public interface NoticeService {
    ResponseResult<?> getNotices(Long communityId, String building, String unit);
    ResponseResult<?> markAsRead(Long noticeId, Long userId);
    ResponseResult<?> createNotice(String title, String content, Long communityId, String building, String unit, Boolean isUrgent);
}
