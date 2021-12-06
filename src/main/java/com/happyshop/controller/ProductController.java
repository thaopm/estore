package com.happyshop.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happyshop.bean.MailInfo;
import com.happyshop.dao.CommentDAO;
import com.happyshop.dao.ProductDAO;
import com.happyshop.entity.Comment;
import com.happyshop.entity.Product;
import com.happyshop.entity.User;
import com.happyshop.service.CookieService;
import com.happyshop.service.MailService;

@Controller
public class ProductController {

	@Autowired
	ProductDAO pdao;
	
	@Autowired
	CommentDAO commentDao;

	@Autowired
	CookieService cookie;

	@Autowired
	MailService mail;
	
	@Autowired
	HttpSession session;
	
	@RequestMapping("/product/list-by-category/{cid}")
	public String listByCategory(Model model, @PathVariable("cid") Integer categoryId) {
		List<Product> list = pdao.findByCategoryId(categoryId);
		model.addAttribute("list", list);
		return "product/list";
	}
	
	@RequestMapping("/product/list-by-categorys/{cid}")
	public String listByCategorys(Model model, @PathVariable("cid") Integer categoryId) {
		List<Product> list = pdao.findByCategoryId(categoryId);
		model.addAttribute("list", list);
		return "product/list_copy";
	}

	@RequestMapping("/product/list-by-keywords")
	public String listByKeywords(Model model, @RequestParam("keywords") String keywords) {
		List<Product> list = pdao.findByKeywords(keywords);
		model.addAttribute("list5", list);
		return "product/list";
	}

	@RequestMapping("/product/list-by-special/{id}")
	public String listBySpecial(Model model, @PathVariable("id") Integer id) {
		List<Product> list = pdao.findBySpecial(id);
		model.addAttribute("list", list);
		return "product/list_special_full";
	}
	@RequestMapping("/product/list-by-new/{id}")
	public String listByNews(Model model, @PathVariable("id") Integer id) {
		List<Product> list = pdao.findBySpecial(id);
		model.addAttribute("list1", list);
		return "product/list-by-new_full";
	}


	@RequestMapping("/product/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Product prod = pdao.findById(id);
		model.addAttribute("prod", prod);

		System.out.println(prod.toString());
		// Tăng số lần xem
		prod.setViewCount(prod.getViewCount() + 1);
		pdao.update(prod);

		// Hàng cùng loại
		List<Product> list = pdao.findByCategoryId(prod.getCategory().getId());
		model.addAttribute("list", list);

		// Hàng yêu thích
		Cookie favo = cookie.read("favo");
		if (favo != null) {
			String ids = favo.getValue();
			List<Product> favo_list = pdao.findByIds(ids);
			model.addAttribute("favo", favo_list);
		}

		// Hàng đã xem
		Cookie viewed = cookie.read("viewed");
		String value = id.toString();
		if (viewed != null) {
			value = viewed.getValue();
			value += "," + id.toString();
		}
		cookie.create("viewed", value, 10);
		List<Product> viewed_list = pdao.findByIds(value);
		model.addAttribute("viewed", viewed_list);

		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		
		System.out.println(user);
		return "product/detail";
	}
	

	@ResponseBody
	@RequestMapping("/product/add-to-favo/{id}")
	public String addToFavorite(Model model, @PathVariable("id") Integer id) {
		Cookie favo = cookie.read("favo");
		String value = id.toString();
		if (favo != null) {
			value = favo.getValue();
			if (!value.contains(id.toString())) {
				value += "," + id.toString();
			}else {
				return "false";
			}
		}
			cookie.create("favo", value, 30);
			return "true";
		
	}

	@ResponseBody
	@RequestMapping("/product/send-to-friend")
	public String sendToFriend(Model model,MailInfo info,HttpServletRequest req) {
			//send mail
			info.setSubject("Thông tin hàng hóa");
			try {
				String id=req.getParameter("id");
				String link=req.getRequestURL().toString().replace("send-to-friend", "detail/"+id);
				info.setBody(info.getBody()+"<hr><a href='"+link+"'>Xem chi tiết...</a>");
				mail.send(info);
				return "true";
			} catch (Exception e) {
				e.printStackTrace();
				return "false";
			}
	}
	
	@RequestMapping("/product/favo")
	public String favo(Model model) {

		// Hàng yêu thích
				Cookie favo = cookie.read("favo");
				if (favo != null) {
					String ids = favo.getValue();
					List<Product> favo_list = pdao.findByIds(ids);
					model.addAttribute("favo", favo_list);
				}
		return "product/favo";
	}

	@RequestMapping("/product/list-by-price/{start}/{end}")
	public String listByPrice(Model model, @PathVariable("start") Integer startPrice, @PathVariable("end") Integer endPrice) {
		List<Product> list = pdao.findByPrice(startPrice, endPrice);
		list.forEach(pro -> System.out.println(pro.toString()));
		model.addAttribute("list", list);
		return "product/list-by-price";
	}

	
	@PostMapping("/product/comment")
	public String comment(@RequestParam String content, Integer productId, Integer parentId, String userId) {
		System.out.println(content);
		System.out.println(productId);
		System.out.println(userId);
		
		Comment comment = new Comment();
		Product product = new Product();
		User user = new User();
		product.setId(productId);
		user.setId(userId);
		
		comment.setContent(content);
		comment.setProduct(product);
		comment.setUser(user);
		if (parentId != null) {
			Comment parentComment = new Comment();
			parentComment.setId(parentId);
			comment.setParentComment(parentComment);
		}
	
		commentDao.create(comment);
		
		return "redirect:detail/" + productId;
	}

	
}
