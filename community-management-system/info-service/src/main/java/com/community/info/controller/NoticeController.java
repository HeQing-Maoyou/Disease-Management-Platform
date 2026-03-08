package com.community.info.controller;

import com.community.common.result.ResponseResult;
import com.community.info.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping
    public ResponseResult<?> getNotices(@RequestParam Long communityId, 
                                      @RequestParam(required = false) String building, 
                                      @RequestParam(required = false) String unit) {
        return noticeService.getNotices(communityId, building, unit);
    }

    @PostMapping("/read")
    public ResponseResult<?> markAsRead(@RequestParam Long noticeId, @RequestParam Long userId) {
        return noticeService.markAsRead(noticeId, userId);
    }

    @PostMapping
    public ResponseResult<?> createNotice(@RequestParam String title, 
                                        @RequestParam String content, 
                                        @RequestParam Long communityId, 
                                        @RequestParam(required = false) String building, 
                                        @RequestParam(required = false) String unit, 
                                        @RequestParam Boolean isUrgent) {
        return noticeService.createNotice(title, content, communityId, building, unit, isUrgent);
    }
}
