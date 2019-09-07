package com.cgq.tmall.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import com.cgq.tmall.pojo.Product;
import com.cgq.tmall.pojo.ProductImage;
import com.cgq.tmall.service.ProductImageService;
import com.cgq.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cgq.tmall.service.ProductService;
import com.cgq.tmall.util.ImageUtil;

@Controller
@RequestMapping("")
public class ProductImageController {
	@Autowired
	ProductService productService;
	@Autowired
	ProductImageService productImageService;
	
	@RequestMapping("admin_productImage_add")
	public String add(ProductImage pi, HttpSession session, UploadedImageFile uploadedImageFile) {
		productImageService.add(pi);
		String fileName = pi.getId()+".jpg";
		String imageFolder;
		String imageFolder_small=null;
		String imageFolder_middle=null;
		if (productImageService.type_single.equals(pi.getType())) {
			imageFolder =session.getServletContext( ).getRealPath("/img/productSingle");
			imageFolder_small = session.getServletContext().getRealPath("/img/productSingle_small");
			imageFolder_middle = session.getServletContext().getRealPath("/img/imageFolder_middle");
			System.out.println(imageFolder);
			System.out.println(imageFolder_small);
			System.out.println(imageFolder_middle);
		} else {
			imageFolder = session.getServletContext().getRealPath("/img/productDetail");
		}
		File f = new File(imageFolder,fileName);
		f.getParentFile().mkdirs();
		try {
			//通过UploadedImageFile 把浏览器传递过来的图片保存在上述指定的位置
			uploadedImageFile.getImage().transferTo(f);
			//通过ImageUtil.change2jpg(file); 确保图片格式一定是jpg，而不仅仅是后缀名是jpg.
			BufferedImage img = ImageUtil.change2jpg(f);
			ImageIO.write(img, "jpg", f);
			
			if (productImageService.type_single.equals(pi.getType())) {
				File f_small = new File(imageFolder_small,fileName);
				File f_middle =new File(imageFolder_middle,fileName);
				//把正常大小的图片，改变大小之后，分别复制到productSingle_middle和productSingle_small目录下
				ImageUtil.resizeImage(f, 56, 56,f_small);
				ImageUtil.resizeImage(f, 217,190,f_middle);
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:admin_productImage_list?pid="+pi.getPid();
	}
	
	@RequestMapping("admin_productImage_delete")
	public String delete(int id,HttpSession session) {
		ProductImage pi = productImageService.get(id);
		String fileName = pi.getId()+".jpg";
		String imageFolder;
		String imageFolder_small=null;
		String imageFolder_middle=null;
		
		if (productImageService.type_single.equals(pi.getType())) {
			imageFolder = session.getServletContext().getRealPath("/img/productSingle");
			imageFolder_small = session.getServletContext().getRealPath("/img/productSingle_small");
			imageFolder_middle = session.getServletContext().getRealPath("/img/productSingle_middle");
			File imageFile = new File(imageFolder,fileName);
            File f_small = new File(imageFolder_small,fileName);
            File f_middle = new File(imageFolder_middle,fileName);
            imageFile.delete();
            f_small.delete();
            f_middle.delete();
		} else {
			imageFolder= session.getServletContext().getRealPath("/img/productDetail");
			File imageFile = new File(imageFolder,fileName);
			imageFile.delete();
		}
		
		productImageService.delete(id);
		return "redirect:admin_productImage_list?pid="+pi.getPid();
	}
	@RequestMapping("admin_productImage_list")
	public String list(int pid,Model model) {
		Product p =productService.get(pid);
        List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.type_single);
        List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.type_detail);

        model.addAttribute("p", p);
        model.addAttribute("pisSingle", pisSingle);
        model.addAttribute("pisDetail", pisDetail);
 
        return "admin/listProductImage";
	}
	

}
