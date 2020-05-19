package web.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import web.model.User;

@Controller
@ComponentScan("web")
@RequestMapping("/")
public class MainController {
	@GetMapping("/")
	public ModelAndView printWelcome() {
		return new ModelAndView("redirect:/login");
	}

	@GetMapping("admin")
	public String printAdmin(@AuthenticationPrincipal User user, ModelMap model) {
		model.addAttribute("admin", user);
		return "admin";
	}

    @GetMapping("login")
    public String loginPage() {
		return "login";
    }

    @GetMapping("user")
	public String userPage(@AuthenticationPrincipal User user, ModelMap model) {
		model.addAttribute("authUser", user);
		return "user";
	}
}