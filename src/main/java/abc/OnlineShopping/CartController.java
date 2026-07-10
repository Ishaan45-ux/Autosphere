package abc.OnlineShopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/addToCart/{productId}")
    public String addToCartViaGet(@PathVariable Long productId, @RequestParam Long userId) {
        cartService.addToCart(userId, productId); // ✅ This actually adds the product to cart
        System.out.print(userId);
        System.out.print(productId);
        return "redirect:/viewshoppinguser"; // Redirect back to product list or user home
    }
    @GetMapping("/{cartId}/items")
    public List<CartItem> getCartItems(@PathVariable Long cartId) {
        return cartService.getCartItems(cartId);
    }

    @PostMapping("/{cartId}/add")
    public CartItem addItemToCart(@PathVariable Long cartId, @RequestBody CartItem cartItem) {
        cartItem.setCartId(cartId);
        return cartService.addCartItem(cartItem);
    }
    
    @GetMapping("/mycart")
    public String showCart(Model model, HttpSession session) {
    	User user = (User)session.getAttribute("loggedInUser");
    	
    	if(user == null) {
    		return "redirect:/login";
    	}
    	
    	Cart cart = cartService.getCartByUserId(user.getId());
    	if(cart != null) {
    		model.addAttribute("cartItems", cartService.getCartItems(cart.getId()));
    	} else {
    		model.addAttribute("cartItems", Collections.emptyList());
    	}
    	
    	return "cart";
    }
    
    @PostMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
    	User user = (User) session.getAttribute("loggedInUser");
    	
    	if(user == null) {
    		return "redirect:/login";
    	}
    	
    	Cart cart = cartService.getCartByUserId(user.getId());
    	if(cart != null) {
    		cartService.clearCart(cart.getId());
    		cartService.deleteCart(cart.getId());
    	}
    	
    	model.addAttribute("username",user.getUsername());
    	return "order_confirmation";
    }
    
}
