package com.community.life.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.community.common.entity.GroupPurchaseProduct;
import com.community.common.entity.Order;
import com.community.common.entity.OrderItem;
import com.community.common.result.ResponseResult;
import com.community.life.mapper.GroupPurchaseProductMapper;
import com.community.life.mapper.OrderItemMapper;
import com.community.life.mapper.OrderMapper;
import com.community.life.service.GroupPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GroupPurchaseServiceImpl implements GroupPurchaseService {

    @Autowired
    private GroupPurchaseProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public ResponseResult<?> getProducts(String category, Integer page, Integer size) {
        if (page == null || page <= 0) page = 1;
        if (size == null || size <= 0) size = 10;

        // 分页查询商品列表
        IPage<GroupPurchaseProduct> productPage = new Page<>(page, size);
        QueryWrapper<GroupPurchaseProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("status", "上架");
        if (category != null && !category.isEmpty()) {
            wrapper.eq("category", category);
        }

        IPage<GroupPurchaseProduct> result = productMapper.selectPage(productPage, wrapper);
        return ResponseResult.success(result);
    }

    @Transactional
    @Override
    public ResponseResult<?> createOrder(Long userId, List<Map<String, Object>> products, String deliveryAddress, String contactPhone) {
        // 创建订单
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
        order.setStatus("待支付");
        order.setDeliveryAddress(deliveryAddress);
        order.setContactPhone(contactPhone);
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());

        // 计算总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Map<String, Object> product : products) {
            Long productId = Long.valueOf(product.get("productId").toString());
            Integer quantity = Integer.valueOf(product.get("quantity").toString());
            
            // 查询商品信息
            GroupPurchaseProduct productInfo = productMapper.selectById(productId);
            if (productInfo == null) {
                return ResponseResult.fail("商品不存在");
            }
            if (productInfo.getStock() < quantity) {
                return ResponseResult.fail("商品库存不足");
            }

            // 计算金额
            totalAmount = totalAmount.add(productInfo.getPrice().multiply(new BigDecimal(quantity)));
        }
        order.setTotalAmount(totalAmount);

        // 保存订单
        orderMapper.insert(order);

        // 创建订单详情
        for (Map<String, Object> product : products) {
            Long productId = Long.valueOf(product.get("productId").toString());
            Integer quantity = Integer.valueOf(product.get("quantity").toString());
            
            // 查询商品信息
            GroupPurchaseProduct productInfo = productMapper.selectById(productId);
            
            // 创建订单详情
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(productId);
            orderItem.setQuantity(quantity);
            orderItem.setUnitPrice(productInfo.getPrice());
            orderItem.setCreatedAt(new Date());
            orderItemMapper.insert(orderItem);

            // 扣减库存
            productInfo.setStock(productInfo.getStock() - quantity);
            productMapper.updateById(productInfo);
        }

        return ResponseResult.success(order);
    }

    @Override
    public ResponseResult<?> getOrderDetail(Long orderId) {
        // 查询订单信息
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            return ResponseResult.fail("订单不存在");
        }

        // 查询订单详情
        QueryWrapper<OrderItem> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderId);
        List<OrderItem> orderItems = orderItemMapper.selectList(wrapper);

        // 构建返回结果
        return ResponseResult.success(Map.of("order", order, "orderItems", orderItems));
    }

    @Override
    public ResponseResult<?> getOrders(Long userId, Integer page, Integer size) {
        if (page == null || page <= 0) page = 1;
        if (size == null || size <= 0) size = 10;

        // 分页查询订单列表
        IPage<Order> orderPage = new Page<>(page, size);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
               .orderByDesc("created_at");

        IPage<Order> result = orderMapper.selectPage(orderPage, wrapper);
        return ResponseResult.success(result);
    }
}
