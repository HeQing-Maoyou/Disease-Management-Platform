package com.community.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.community.common.entity.HealthCheckin;
import com.community.common.result.ResponseResult;
import com.community.health.mapper.HealthCheckinMapper;
import com.community.health.service.HealthCheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class HealthCheckinServiceImpl implements HealthCheckinService {

    @Autowired
    private HealthCheckinMapper healthCheckinMapper;

    @Override
    public ResponseResult<?> checkin(Long userId, BigDecimal temperature, String symptoms) {
        // 创建健康打卡记录
        HealthCheckin checkin = new HealthCheckin();
        checkin.setUserId(userId);
        checkin.setTemperature(temperature);
        checkin.setSymptoms(symptoms);
        checkin.setCheckinTime(new Date());
        checkin.setCreatedAt(new Date());

        // 风险评估：体温超过37.3度或有症状标记为异常
        if (temperature.compareTo(new BigDecimal("37.3")) > 0 || (symptoms != null && !symptoms.isEmpty())) {
            checkin.setStatus("异常");
        } else {
            checkin.setStatus("正常");
        }

        // 保存打卡记录
        healthCheckinMapper.insert(checkin);

        // 返回打卡结果与风险提示
        return ResponseResult.success("打卡成功，状态：" + checkin.getStatus());
    }

    @Override
    public ResponseResult<?> getCheckinHistory(Long userId, Integer page, Integer size) {
        if (page == null || page <= 0) page = 1;
        if (size == null || size <= 0) size = 10;

        // 分页查询打卡历史
        IPage<HealthCheckin> checkinPage = new Page<>(page, size);
        QueryWrapper<HealthCheckin> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
               .orderByDesc("checkin_time");

        IPage<HealthCheckin> result = healthCheckinMapper.selectPage(checkinPage, wrapper);
        return ResponseResult.success(result);
    }
}
