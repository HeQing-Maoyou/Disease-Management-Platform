package com.community.info.service.impl;

import com.community.common.result.ResponseResult;
import com.community.info.service.MapService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MapServiceImpl implements MapService {

    @Override
    public ResponseResult<?> getLocations(String type, Double latitude, Double longitude) {
        // 模拟周边防疫地点数据
        List<Map<String, Object>> locations = new ArrayList<>();

        if ("vaccine".equals(type)) {
            // 疫苗接种点
            Map<String, Object> location1 = new HashMap<>();
            location1.put("id", 1L);
            location1.put("name", "社区卫生服务中心接种点");
            location1.put("address", "社区中心路123号");
            location1.put("latitude", latitude + 0.01);
            location1.put("longitude", longitude + 0.01);
            location1.put("crowdLevel", "低");
            location1.put("phone", "12345678901");
            location1.put("hours", "09:00-17:00");
            locations.add(location1);

            Map<String, Object> location2 = new HashMap<>();
            location2.put("id", 2L);
            location2.put("name", "区人民医院接种点");
            location2.put("address", "健康路456号");
            location2.put("latitude", latitude - 0.02);
            location2.put("longitude", longitude + 0.03);
            location2.put("crowdLevel", "中");
            location2.put("phone", "12345678902");
            location2.put("hours", "08:30-18:00");
            locations.add(location2);
        } else if ("nucleic".equals(type)) {
            // 核酸采样点
            Map<String, Object> location1 = new HashMap<>();
            location1.put("id", 3L);
            location1.put("name", "社区广场采样点");
            location1.put("address", "社区广场");
            location1.put("latitude", latitude + 0.015);
            location1.put("longitude", longitude - 0.01);
            location1.put("crowdLevel", "低");
            location1.put("phone", "12345678903");
            location1.put("hours", "08:00-20:00");
            locations.add(location1);

            Map<String, Object> location2 = new HashMap<>();
            location2.put("id", 4L);
            location2.put("name", "超市门口采样点");
            location2.put("address", "便民超市门口");
            location2.put("latitude", latitude - 0.01);
            location2.put("longitude", longitude - 0.02);
            location2.put("crowdLevel", "高");
            location2.put("phone", "12345678904");
            location2.put("hours", "09:00-19:00");
            locations.add(location2);
        }

        return ResponseResult.success(locations);
    }
}
