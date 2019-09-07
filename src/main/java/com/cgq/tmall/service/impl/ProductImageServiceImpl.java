package com.cgq.tmall.service.impl;

import java.util.List;

import com.cgq.tmall.mapper.ProductImageMapper;
import com.cgq.tmall.pojo.ProductImage;
import com.cgq.tmall.pojo.ProductImageExample;
import com.cgq.tmall.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductImageServiceImpl implements ProductImageService {
	
	@Autowired
    ProductImageMapper productImageMapper;

	@Override
	public void add(ProductImage pi) {
		productImageMapper.insert(pi);
	}

	@Override
	public void delete(int id) {
		productImageMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(ProductImage pi) {
		productImageMapper.updateByPrimaryKeySelective(pi);
	}

	@Override
	public ProductImage get(int id) {
		return productImageMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ProductImage> list(int pid, String type) {
		ProductImageExample example = new ProductImageExample();
		example.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);
		example.setOrderByClause("id desc");
		return productImageMapper.selectByExample(example);
	}

}
