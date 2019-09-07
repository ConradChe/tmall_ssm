package com.cgq.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgq.tmall.mapper.OrderItemMapper;
import com.cgq.tmall.pojo.Order;
import com.cgq.tmall.pojo.OrderItem;
import com.cgq.tmall.pojo.OrderItemExample;
import com.cgq.tmall.pojo.Product;
import com.cgq.tmall.service.OrderItemService;
import com.cgq.tmall.service.ProductService;

@Service
public class OrderItemServiceImpl implements OrderItemService {
	@Autowired
	OrderItemMapper orderItemMapper;
	@Autowired
	ProductService productService;

	@Override
	public void add(OrderItem c) {
		orderItemMapper.insert(c);
	}

	@Override
	public void delete(int id) {
		orderItemMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(OrderItem c) {
		orderItemMapper.updateByPrimaryKeySelective(c);
	}

	@Override
	public OrderItem get(int id) {
		OrderItem orderItem = orderItemMapper.selectByPrimaryKey(id);
		setProduct(orderItem);
		return orderItem;
	}

	private void setProduct(OrderItem oi) {
		Product p = productService.get(oi.getPid());
		oi.setProduct(p);
	}
	
	public void setProduct(List<OrderItem> ois){
        for (OrderItem oi: ois) {
            setProduct(oi);
        }
    }

	@Override
	public List<OrderItem> list() {
		OrderItemExample example = new OrderItemExample();
		example.setOrderByClause("id desc");
		return orderItemMapper.selectByExample(example);
	}

	@Override
	public void fill(List<Order> os) {
		for (Order o : os) {
			fill(o);
		}
	}

	@Override
	public void fill(Order o) {//计算每个订单的订单项，价格
		OrderItemExample example = new OrderItemExample();
		example.createCriteria().andOidEqualTo(o.getId());
		example.setOrderByClause("id desc");
		List<OrderItem> ois = orderItemMapper.selectByExample(example);
		setProduct(ois);
		
		float total = 0;
		int totalNumber = 0;
		for (OrderItem oi : ois) {
			total += oi.getNumber()*oi.getProduct().getPromotePrice();
			totalNumber += oi.getNumber();
			productService.setFirstProductImage(oi.getProduct());
		}
		o.setTotal(total);
		o.setTotalNumber(totalNumber);
		o.setOrderItems(ois);
		
	}

	@Override
	public int getSaleCount(int pid) {
		OrderItemExample example = new OrderItemExample();
		example.createCriteria().andPidEqualTo(pid);
		List<OrderItem> ois = orderItemMapper.selectByExample(example);
		int count = 0;
		for (OrderItem oi : ois) {
			count += oi.getNumber();
		}
		return count;
	}

	@Override
	public List<OrderItem> listByUser(int uid) {
		OrderItemExample example = new OrderItemExample();
		example.createCriteria().andUidEqualTo(uid).andOidIsNull();
		List<OrderItem> ois = orderItemMapper.selectByExample(example);
		setProduct(ois);
		return ois;
	}

}
