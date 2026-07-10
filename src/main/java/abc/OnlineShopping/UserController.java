package abc.OnlineShopping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;


@Controller

public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/home")
	public String userhome() {
		return "userhome";
	}
	
	
	
	@GetMapping("/register")
	public String showRegisterationForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/register1")
	public String registerUser(@ModelAttribute("user") User user) {
		userRepository.save(user);
		return "index";
	}
	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@PostMapping("/login1")
	public String loginUser(@ModelAttribute("user") User loginUser, Model model, HttpSession session) {
		User existingUser = userRepository.findByUsernameAndPassword(loginUser.getUsername(), loginUser.getPassword());
		
		if(existingUser != null && existingUser.isEnabled()) {
			session.setAttribute("loggedInUser", existingUser);
			model.addAttribute("user", existingUser);
			
			if("ADMIN".equalsIgnoreCase(existingUser.getRole())) {
				return "adminhome";
			}
			else {
				return "userhome";
			}
		}
		else {
			model.addAttribute("loginerror", "Invalid username or password");
			return "login";
		}
	}
	
	@GetMapping("/logout")
	public String logoutUser(HttpSession session)
	{
		session.invalidate();
		return "redirect:/";
	}
	

	@GetMapping("/userhome")
	public String returnToUserhome(Model model,HttpSession session){
		User loginUser = (User)session.getAttribute("loggedInUser");
		User existingUser = userRepository.findByUsernameAndPassword(loginUser.getUsername(), loginUser.getPassword());
		session.setAttribute("loggedInUser", existingUser);
		model.addAttribute("user", existingUser);
		
		return "userhome";
	}

}
