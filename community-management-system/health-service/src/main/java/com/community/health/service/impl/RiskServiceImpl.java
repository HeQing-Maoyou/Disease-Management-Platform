package com.community.health.service.impl;

import com.community.common.result.ResponseResult;
import com.community.health.service.RiskService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RiskServiceImpl implements RiskService {

    @Override
    public ResponseResult<?> getRiskLevel(Long communityId, String building, String unit) {
        // 模拟风险评估逻辑
        // 实际项目中，这里应该根据健康打卡数据、核酸检测结果等进行综合评估
        Map<String, Object> riskInfo = new HashMap<>();
        
        // 模拟不同区域的风险等级
        if (communityId == 1L && "1栋".equals(building) && "1单元".equals(unit)) {
            riskInfo.put("riskLevel", "高风险");
            riskInfo.put("quarantineCountdown", "3天");
            riskInfo.put("riskArea", "1栋1单元");
            riskInfo.put("riskMessage", "该区域存在确诊病例，请严格执行隔离措施");
        } else if (communityId == 1L && "2栋".equals(building)) {
            riskInfo.put("riskLevel", "中风险");
            riskInfo.put("quarantineCountdown", "0天");
            riskInfo.put("riskArea", "2栋");
            riskInfo.put("riskMessage", "该区域存在密切接触者，请注意防护");
        } else {
            riskInfo.put("riskLevel", "低风险");
            riskInfo.put("quarantineCountdown", "0天");
            riskInfo.put("riskArea", "");
            riskInfo.put("riskMessage", "该区域暂无风险，请注意日常防护");
        }

        return ResponseResult.success(riskInfo);
    }
}
