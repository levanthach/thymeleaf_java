package vn.ikornsolution.thymeleaf.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.ikornsolution.thymeleaf.demo.User;
import vn.ikornsolution.thymeleaf.demo.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/")
	public String index(Model model) {
		
		return findPaginated(1, model);
		
	}
	
	@RequestMapping(value = "add")
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		return "addUser";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editUser(@RequestParam("id") Long userId, Model model) {
		Optional<User> userEdit = userService.findUserById(userId);
		userEdit.ifPresent(user -> model.addAttribute("user", user));
		return "editUser";
	}
	
	@RequestMapping(value = "/findid", method = RequestMethod.GET)
	public String find(@RequestParam("keyword") String keyword, Model model) {
		
		if(keyword == "") {
			return findPaginated(1, model);
		} else {
			List<User> user = userService.searchUsers(keyword);
			model.addAttribute("users", user);
		}
		
		return "find";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Valid User user, BindingResult bindingResult, RedirectAttributes model) {
		if(bindingResult.hasErrors()) {
			return "addUser";
		} else {
			userService.saveUser(user);
			model.addFlashAttribute("success", "Thêm mới thành công!");
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/saveUpdate", method = RequestMethod.POST, headers = "Content-Type=application/x-www-form-urlencoded")
	public String saveUpdate(@RequestBody User user,  BindingResult bindingResult, RedirectAttributes model) {
		if(bindingResult.hasErrors()) {
			return "addUser";
		} else {
			userService.updateUser(user);
			model.addFlashAttribute("success", "Cập nhật thành công!");
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteUser(@RequestParam("id") Long userId, RedirectAttributes model) {
		userService.deleteUser(userId);
		model.addFlashAttribute("success", "Xóa thành công!");
		return "redirect:/";
	}
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value ="pageNo") int pageNo, Model model) {
		int pageSize = 20;
		
		Page<User> page = userService.findPaginated(pageNo, pageSize);
		List<User> users = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("users", users);
		return "index";
	}

}
