package com.community.life.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.common.entity.QuarantineService; // 注意：这里需要创建QuarantineService实体类
import com.community.common.result.ResponseResult;
import com.community.life.mapper.QuarantineServiceMapper; // 注意：这里需要创建QuarantineServiceMapper接口
import com.community.life.service.QuarantineService as QuarantineServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class QuarantineServiceImpl implements QuarantineServiceInterface {

    @Autowired
    private QuarantineServiceMapper quarantineServiceMapper;

    @Override
    public ResponseResult<?> bookService(Long userId, String serviceType, Date appointmentTime) {
        // 创建隔离服务预约记录
        QuarantineService service = new QuarantineService();
        service.setUserId(userId);
        service.setServiceType(serviceType);
        service.setAppointmentTime(appointmentTime);
        service.setStatus("待处理");
        service.setCreatedAt(new Date());
        service.setUpdatedAt(new Date());

        // 保存服务预约
        quarantineServiceMapper.insert(service);

        return ResponseResult.success(service);
    }

    @Override
    public ResponseResult<?> getServices(Long userId) {
        // 查询用户的隔离服务列表
        QueryWrapper<QuarantineService> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
               .orderByDesc("created_at");

        List<QuarantineService> services = quarantineServiceMapper.selectList(wrapper);
        return ResponseResult.success(services);
    }

    @Override
    public ResponseResult<?> updateServiceStatus(Long serviceId, String status) {
        // 查询服务记录
        QuarantineService service = quarantineServiceMapper.selectById(serviceId);
        if (service == null) {
            return ResponseResult.fail("服务记录不存在");
        }

        // 更新服务状态
        service.setStatus(status);
        service.setUpdatedAt(new Date());
        quarantineServiceMapper.updateById(service);

        return ResponseResult.success(service);
    }
}
