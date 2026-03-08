package com.community.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.common.entity.CommunityNotice;
import com.community.common.result.ResponseResult;
import com.community.info.mapper.CommunityNoticeMapper;
import com.community.info.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private CommunityNoticeMapper noticeMapper;

    @Override
    public ResponseResult<?> getNotices(Long communityId, String building, String unit) {
        // 查询通知列表
        QueryWrapper<CommunityNotice> wrapper = new QueryWrapper<>();
        wrapper.eq("community_id", communityId)
               .orderByDesc("is_urgent")
               .orderByDesc("created_at");

        // 按网格精准推送
        if (building != null && !building.isEmpty()) {
            wrapper.and(w -> w.eq("building", building).or().isNull("building"));
        }
        if (unit != null && !unit.isEmpty()) {
            wrapper.and(w -> w.eq("unit", unit).or().isNull("unit"));
        }

        List<CommunityNotice> notices = noticeMapper.selectList(wrapper);
        return ResponseResult.success(notices);
    }

    @Override
    public ResponseResult<?> markAsRead(Long noticeId, Long userId) {
        // 这里可以添加通知已读状态的记录逻辑
        // 实际项目中，可能需要创建一个notice_read表来记录用户的已读状态
        return ResponseResult.success("标记已读成功");
    }

    @Override
    public ResponseResult<?> createNotice(String title, String content, Long communityId, String building, String unit, Boolean isUrgent) {
        // 创建社区通知
        CommunityNotice notice = new CommunityNotice();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setCommunityId(communityId);
        notice.setBuilding(building);
        notice.setUnit(unit);
        notice.setIsUrgent(isUrgent);
        notice.setCreatedAt(new Date());
        notice.setUpdatedAt(new Date());

        // 保存通知
        noticeMapper.insert(notice);

        return ResponseResult.success(notice);
    }
}
