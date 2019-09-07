package com.cgq.tmall.service;

import java.util.List;

import com.cgq.tmall.pojo.PropertyValue;
import com.cgq.tmall.pojo.Product;

public interface PropertyValueService {
	void init(Product p);
	void update(PropertyValue pv);
	
	PropertyValue get(int id,int pid);
	List<PropertyValue> list(int pid);

}
