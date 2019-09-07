package com.cgq.tmall.service;

import java.util.List;

import com.cgq.tmall.pojo.Order;
import com.cgq.tmall.pojo.OrderItem;

public interface OrderService {
	String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";
    
    void add(Order c);
    
    void delete(int id);
    void update(Order c);
    Order get(int id);
    List<Order> list();
    float add(Order o,List<OrderItem> ois);
    List<Order> list(int uid,String excludedStatus);

}
