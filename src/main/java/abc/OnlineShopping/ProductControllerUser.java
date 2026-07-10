package abc.OnlineShopping;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductControllerUser {
	
	@Autowired
	private ProductRepository productRepository;
	private UserRepository userRepository; 
	
	@GetMapping("/viewshoppinguser")
	public String viewProduct(Model model,HttpSession session)
	{
		List<Product>products=productRepository.findAll();
		model.addAttribute("products",products);
		
		User user = (User)session.getAttribute("loggedInUser");
		if(user != null) {
			model.addAttribute("user", user);
		}
		
		return "productlist_user";
	}
	
}
