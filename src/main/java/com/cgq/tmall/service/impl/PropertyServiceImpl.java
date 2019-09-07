package com.cgq.tmall.service.impl;

import java.util.List;

import com.cgq.tmall.mapper.PropertyMapper;
import com.cgq.tmall.pojo.Property;
import com.cgq.tmall.pojo.PropertyExample;
import com.cgq.tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl implements PropertyService {
	@Autowired
    PropertyMapper propertyMapper;

	@Override
	public void add(Property p) {
		propertyMapper.insert(p);
	}

	@Override
	public void delete(int id) {
		propertyMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Property p) {
		propertyMapper.updateByPrimaryKeySelective(p);
	}

	@Override
	public Property get(int id) {
		return propertyMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Property> list(int cid) {
		PropertyExample example = new PropertyExample();
		example.createCriteria().andCidEqualTo(cid);
		example.setOrderByClause("id desc");
		return propertyMapper.selectByExample(example);
	}

}
