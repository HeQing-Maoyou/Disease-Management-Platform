package com.community.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.common.entity.HealthCode;
import com.community.common.result.ResponseResult;
import com.community.health.mapper.HealthCodeMapper;
import com.community.health.service.HealthCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HealthCodeServiceImpl implements HealthCodeService {

    @Autowired
    private HealthCodeMapper healthCodeMapper;

    @Override
    public ResponseResult<?> getHealthCode(Long userId) {
        // 查询用户的健康码
        QueryWrapper<HealthCode> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        HealthCode healthCode = healthCodeMapper.selectOne(wrapper);

        if (healthCode == null) {
            // 如果没有健康码，创建一个默认的绿码
            healthCode = new HealthCode();
            healthCode.setUserId(userId);
            healthCode.setCodeType("绿码");
            healthCode.setLastUpdate(new Date());
            // 健康码有效期为7天
            Date validUntil = new Date();
            validUntil.setTime(validUntil.getTime() + 7 * 24 * 60 * 60 * 1000);
            healthCode.setValidUntil(validUntil);
            healthCode.setCreatedAt(new Date());
            healthCodeMapper.insert(healthCode);
        }

        return ResponseResult.success(healthCode);
    }

    @Override
    public ResponseResult<?> proxyHealthCode(Long userId, Long proxyUserId) {
        // 查询用户的健康码
        QueryWrapper<HealthCode> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        HealthCode healthCode = healthCodeMapper.selectOne(wrapper);

        if (healthCode == null) {
            // 如果没有健康码，创建一个默认的绿码
            healthCode = new HealthCode();
            healthCode.setUserId(userId);
            healthCode.setProxyUserId(proxyUserId);
            healthCode.setCodeType("绿码");
            healthCode.setLastUpdate(new Date());
            // 健康码有效期为7天
            Date validUntil = new Date();
            validUntil.setTime(validUntil.getTime() + 7 * 24 * 60 * 60 * 1000);
            healthCode.setValidUntil(validUntil);
            healthCode.setCreatedAt(new Date());
            healthCodeMapper.insert(healthCode);
        } else {
            // 更新代办人信息
            healthCode.setProxyUserId(proxyUserId);
            healthCodeMapper.updateById(healthCode);
        }

        return ResponseResult.success(healthCode);
    }
}
