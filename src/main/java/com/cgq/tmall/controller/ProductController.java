package com.cgq.tmall.controller;

import java.util.Date;
import java.util.List;

import com.cgq.tmall.pojo.ProductImage;
import com.cgq.tmall.service.CategoryService;
import com.cgq.tmall.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cgq.tmall.pojo.Category;
import com.cgq.tmall.pojo.Product;
import com.cgq.tmall.service.ProductService;
import com.cgq.tmall.util.Page;

@Controller
@RequestMapping("")
public class ProductController {
	@Autowired
    CategoryService categoryService;
	@Autowired
	ProductService productService;
	@Autowired
	ProductImageService productImageService;
	
	@RequestMapping("admin_product_add")
	public String add(Product p) {
		p.setCreateDate(new Date());
		productService.add(p);
		return "redirect:admin_product_list?cid="+p.getCid();
	}
	
	@RequestMapping("admin_product_delete")
	public String delete(int id) {
		Product p = productService.get(id);
		productService.delete(id);
		return "redirect:admin_product_list?cid="+p.getCid();
	}
	
	@RequestMapping("admin_product_edit")
	public String edit(Model model,int id) {
		Product p = productService.get(id);
		model.addAttribute("p", p);
		return "admin/editProduct";
	}
	
	@RequestMapping("admin_product_update")
	public String update(Product p) {
		productService.update(p);
		return "redirect:admin_product_list?cid="+p.getCid();
	}
	
	@RequestMapping("admin_product_list")
	public String list(int cid,Model model,Page page) {
		Category c = categoryService.get(cid);
		
		PageHelper.offsetPage(page.getStart(), page.getCount());
		List<Product> ps = productService.list(cid);

		int total = (int) new PageInfo<>(ps).getTotal();
		page.setTotal(total);
		page.setParam("&cid="+c.getId());
		
		model.addAttribute("ps", ps);
		model.addAttribute("c", c);
		model.addAttribute("page", page);
		
		return "admin/listProduct";
	}
	

}
