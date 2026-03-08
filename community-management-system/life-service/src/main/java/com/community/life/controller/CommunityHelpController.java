package com.community.life.controller;

import com.community.common.result.ResponseResult;
import com.community.life.service.CommunityHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/community")
public class CommunityHelpController {

    @Autowired
    private CommunityHelpService communityHelpService;

    @PostMapping("/help")
    public ResponseResult<?> publishHelp(@RequestParam Long userId, 
                                       @RequestParam String type, 
                                       @RequestParam String content, 
                                       @RequestParam String contactInfo) {
        return communityHelpService.publishHelp(userId, type, content, contactInfo);
    }

    @GetMapping("/help")
    public ResponseResult<?> getHelpList(@RequestParam(required = false) String type, 
                                       @RequestParam(required = false) Integer page, 
                                       @RequestParam(required = false) Integer size) {
        return communityHelpService.getHelpList(type, page, size);
    }

    @PutMapping("/help/{helpId}/status")
    public ResponseResult<?> updateHelpStatus(@PathVariable Long helpId, @RequestParam String status) {
        return communityHelpService.updateHelpStatus(helpId, status);
    }
}
