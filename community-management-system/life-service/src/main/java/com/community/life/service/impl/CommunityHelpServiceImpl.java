package com.community.life.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.community.common.entity.CommunityHelp; // 注意：这里需要创建CommunityHelp实体类
import com.community.common.result.ResponseResult;
import com.community.life.mapper.CommunityHelpMapper; // 注意：这里需要创建CommunityHelpMapper接口
import com.community.life.service.CommunityHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommunityHelpServiceImpl implements CommunityHelpService {

    @Autowired
    private CommunityHelpMapper communityHelpMapper;

    @Override
    public ResponseResult<?> publishHelp(Long userId, String type, String content, String contactInfo) {
        // 创建社区互助信息
        CommunityHelp help = new CommunityHelp();
        help.setUserId(userId);
        help.setType(type);
        help.setContent(content);
        help.setContactInfo(contactInfo);
        help.setStatus("待解决");
        help.setCreatedAt(new Date());
        help.setUpdatedAt(new Date());

        // 保存互助信息
        communityHelpMapper.insert(help);

        return ResponseResult.success(help);
    }

    @Override
    public ResponseResult<?> getHelpList(String type, Integer page, Integer size) {
        if (page == null || page <= 0) page = 1;
        if (size == null || size <= 0) size = 10;

        // 分页查询互助信息列表
        IPage<CommunityHelp> helpPage = new Page<>(page, size);
        QueryWrapper<CommunityHelp> wrapper = new QueryWrapper<>();
        if (type != null && !type.isEmpty()) {
            wrapper.eq("type", type);
        }
        wrapper.orderByDesc("created_at");

        IPage<CommunityHelp> result = communityHelpMapper.selectPage(helpPage, wrapper);
        return ResponseResult.success(result);
    }

    @Override
    public ResponseResult<?> updateHelpStatus(Long helpId, String status) {
        // 查询互助信息
        CommunityHelp help = communityHelpMapper.selectById(helpId);
        if (help == null) {
            return ResponseResult.fail("互助信息不存在");
        }

        // 更新互助状态
        help.setStatus(status);
        help.setUpdatedAt(new Date());
        communityHelpMapper.updateById(help);

        return ResponseResult.success(help);
    }
}
