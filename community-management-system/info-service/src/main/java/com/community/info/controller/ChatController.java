package com.community.info.controller;

import com.community.common.result.ResponseResult;
import com.community.info.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/messages")
    public ResponseResult<?> sendMessage(@RequestParam Long userId, 
                                       @RequestParam Long receiverId, 
                                       @RequestParam String content) {
        return chatService.sendMessage(userId, receiverId, content);
    }

    @GetMapping("/messages")
    public ResponseResult<?> getMessages(@RequestParam Long userId, 
                                       @RequestParam Long otherUserId, 
                                       @RequestParam(required = false) Integer page, 
                                       @RequestParam(required = false) Integer size) {
        return chatService.getMessages(userId, otherUserId, page, size);
    }
}
