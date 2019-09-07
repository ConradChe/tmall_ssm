package com.cgq.tmall.service.impl;

import java.util.List;

import com.cgq.tmall.pojo.ReviewExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgq.tmall.mapper.ReviewMapper;
import com.cgq.tmall.pojo.Review;
import com.cgq.tmall.pojo.User;
import com.cgq.tmall.service.ReviewService;
import com.cgq.tmall.service.UserService;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	ReviewMapper reviewMapper;
	@Autowired
	UserService userService;

	@Override
	public void add(Review r) {
		reviewMapper.insert(r);
	}

	@Override
	public void delete(int id) {
		reviewMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Review r) {
		reviewMapper.updateByPrimaryKeySelective(r);
	}

	@Override
	public Review get(int id) {
		return reviewMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Review> list(int pid) {
		ReviewExample example = new ReviewExample();
		example.createCriteria().andPidEqualTo(pid);
		example.setOrderByClause("id desc");
		
		List<Review> reviews = reviewMapper.selectByExample(example);
		setUser(reviews);
		return reviews;
	}

	private void setUser(List<Review> Reviews) {
		for (Review review : Reviews) {
			setUser(review);
		}
	}

	private void setUser(Review review) {
		int uid = review.getUid();
		User user = userService.get(uid);
		review.setUser(user);
	}

	@Override
	public int getCount(int pid) {
		return list(pid).size();//通过产品得到评论的集合，每个评论都设置了用户信息，最后得到评论数
	}

}
