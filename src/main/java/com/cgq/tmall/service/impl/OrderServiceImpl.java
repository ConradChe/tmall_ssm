package com.cgq.tmall.service.impl;

import java.util.List;

import com.cgq.tmall.mapper.OrderMapper;
import com.cgq.tmall.pojo.Order;
import com.cgq.tmall.pojo.OrderExample;
import com.cgq.tmall.pojo.OrderItem;
import com.cgq.tmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cgq.tmall.pojo.User;
import com.cgq.tmall.service.OrderItemService;
import com.cgq.tmall.service.UserService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
    OrderMapper orderMapper;
	@Autowired
	UserService userService;
	@Autowired
	OrderItemService orderItemService;

	@Override
	public void add(Order c) {
		orderMapper.insert(c);
	}

	@Override
	public void delete(int id) {
		orderMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Order c) {
		orderMapper.updateByPrimaryKeySelective(c);
	}

	@Override
	public Order get(int id) {
		return orderMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Order> list() {
		OrderExample example = new OrderExample();
		example.setOrderByClause("id desc");
		List<Order> orders = orderMapper.selectByExample(example);
		setUser(orders);
		return orders;
	}

	public void setUser(List<Order> os) {
		for (Order o : os) {
			setUser(o);
		}
	}

	public void setUser(Order o){
		User u = userService.get(o.getUid());
		o.setUser(u);
	}

	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
	public float add(Order o, List<OrderItem> ois) {//计算总商品价格
		float total = 0;
		add(o);
		for (OrderItem oi : ois) {
			oi.setOid(o.getId());
			orderItemService.update(oi);
			total  += oi.getProduct().getPromotePrice()*oi.getNumber();
		}	
		
		return total;
	}

	@Override
	public List<Order> list(int uid, String excludedStatus) {
		OrderExample example = new OrderExample();
		example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
		example.setOrderByClause("id desc");
		return orderMapper.selectByExample(example);
	}

}
