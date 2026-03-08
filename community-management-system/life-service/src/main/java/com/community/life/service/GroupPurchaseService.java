package com.community.life.service;

import com.community.common.result.ResponseResult;
import java.util.List;
import java.util.Map;

public interface GroupPurchaseService {
    ResponseResult<?> getProducts(String category, Integer page, Integer size);
    ResponseResult<?> createOrder(Long userId, List<Map<String, Object>> products, String deliveryAddress, String contactPhone);
    ResponseResult<?> getOrderDetail(Long orderId);
    ResponseResult<?> getOrders(Long userId, Integer page, Integer size);
}
