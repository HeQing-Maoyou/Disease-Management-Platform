package com.community.life.controller;

import com.community.common.result.ResponseResult;
import com.community.life.service.GroupPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/group-purchase")
public class GroupPurchaseController {

    @Autowired
    private GroupPurchaseService groupPurchaseService;

    @GetMapping("/products")
    public ResponseResult<?> getProducts(@RequestParam(required = false) String category, 
                                        @RequestParam(required = false) Integer page, 
                                        @RequestParam(required = false) Integer size) {
        return groupPurchaseService.getProducts(category, page, size);
    }

    @PostMapping("/orders")
    public ResponseResult<?> createOrder(@RequestParam Long userId, 
                                        @RequestBody List<Map<String, Object>> products, 
                                        @RequestParam String deliveryAddress, 
                                        @RequestParam String contactPhone) {
        return groupPurchaseService.createOrder(userId, products, deliveryAddress, contactPhone);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseResult<?> getOrderDetail(@PathVariable Long orderId) {
        return groupPurchaseService.getOrderDetail(orderId);
    }

    @GetMapping("/orders")
    public ResponseResult<?> getOrders(@RequestParam Long userId, 
                                     @RequestParam(required = false) Integer page, 
                                     @RequestParam(required = false) Integer size) {
        return groupPurchaseService.getOrders(userId, page, size);
    }
}
