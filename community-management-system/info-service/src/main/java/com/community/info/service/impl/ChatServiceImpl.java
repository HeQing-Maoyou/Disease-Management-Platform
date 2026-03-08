package com.community.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.community.common.entity.Message; // 注意：这里需要创建Message实体类
import com.community.common.result.ResponseResult;
import com.community.info.mapper.MessageMapper; // 注意：这里需要创建MessageMapper接口
import com.community.info.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public ResponseResult<?> sendMessage(Long userId, Long receiverId, String content) {
        // 创建消息记录
        Message message = new Message();
        message.setSenderId(userId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setSendTime(new Date());
        message.setReadStatus(false);
        message.setCreatedAt(new Date());

        // 保存消息
        messageMapper.insert(message);

        return ResponseResult.success(message);
    }

    @Override
    public ResponseResult<?> getMessages(Long userId, Long otherUserId, Integer page, Integer size) {
        if (page == null || page <= 0) page = 1;
        if (size == null || size <= 0) size = 20;

        // 分页查询聊天记录
        IPage<Message> messagePage = new Page<>(page, size);
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.and(w -> w.eq("sender_id", userId).eq("receiver_id", otherUserId)
                .or().eq("sender_id", otherUserId).eq("receiver_id", userId))
                .orderByAsc("send_time");

        IPage<Message> result = messageMapper.selectPage(messagePage, wrapper);
        return ResponseResult.success(result);
    }
}
