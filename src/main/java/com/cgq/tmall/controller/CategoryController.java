package com.cgq.tmall.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cgq.tmall.pojo.Category;
import com.cgq.tmall.service.CategoryService;
import com.cgq.tmall.util.ImageUtil;
import com.cgq.tmall.util.Page;
import com.cgq.tmall.util.UploadedImageFile;

@Controller
@RequestMapping("")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping("admin_category_list")
	public String list(Model model,Page page) {
		//通过分页插件指定分页参数
		PageHelper.offsetPage(page.getStart(), page.getCount());
		//获取对应分页数据
		List<Category> cs = categoryService.list();
		//通过PageInfo获取总数
		int total = (int) new PageInfo<>(cs).getTotal();
		page.setTotal(total);
		model.addAttribute("cs",cs);
		model.addAttribute("page",page);
		return "admin/listCategory";
	}
	@RequestMapping("admin_category_add")
	private String add(Category c,HttpSession session,UploadedImageFile uploadedImageFile) throws IllegalStateException, IOException {
		categoryService.add(c);
		//通过session获取ControllerContext,再通过getRealPath定位存放分类图片的路径
		File imageFolder = new File(session.getServletContext().getRealPath("/img/category"));
		System.out.println(imageFolder);
		//根据分类id创建文件名
		File file = new File(imageFolder,c.getId()+".jpg");
		// 如果/img/category目录不存在，则创建该目录，否则后续保存浏览器传过来图片，会提示无法保存
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		//通过UploadedImageFile 把浏览器传递过来的图片保存在上述指定的位置
		uploadedImageFile.getImage().transferTo(file);
		//通过ImageUtil.change2jpg(file); 确保图片格式一定是jpg，而不仅仅是后缀名是jpg.
		BufferedImage img = ImageUtil.change2jpg(file);
		//jpg格式的文件存放到指定位置
		ImageIO.write(img, "jpg", file);
		return "redirect:/admin_category_list";
	}
	
	@RequestMapping("admin_category_delete")
	public String delete(int id,HttpSession session){
		//删除数据库中的数据
		categoryService.delete(id);
		File imageFolder = new File(session.getServletContext().getRealPath("/img/category"));
		File file = new File(imageFolder, id + ".jpg");
		//删除本地图片
		file.delete();
		return "redirect:/admin_category_list";
	}
	
	@RequestMapping("admin_category_edit")
	public String edit(int id,Model model){
		Category c = categoryService.get(id);
		model.addAttribute("c",c);
		
		return "admin/editCategory";
	}
	
	@RequestMapping("admin_category_update")
	public String update(Category category,HttpSession session,UploadedImageFile uploadedImageFile) throws IllegalStateException, IOException{
		categoryService.update(category);
		MultipartFile image = uploadedImageFile.getImage();
		if (null != image && !image.isEmpty()){
			File imageFolder = new File(session.getServletContext().getRealPath("/img/category"));
			File file = new File(imageFolder, category.getId() + ".jpg");
			image.transferTo(file);
			BufferedImage img = ImageUtil.change2jpg(file);
			ImageIO.write(img,".jpg",file);
		}

		return "redirect:/admin_category_list";
	}
	

}
