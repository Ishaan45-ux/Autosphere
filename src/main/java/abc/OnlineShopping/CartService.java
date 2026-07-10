package abc.OnlineShopping;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class CartService {

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<CartItem> getCartItems(Long cartId){
		return cartItemRepository.findByCartId(cartId);
	}
	
	public CartItem addCartItem(CartItem cartItem) {
		return cartItemRepository.save(cartItem);
	}

	public void addToCart(Long userId, Long productId) {
        // 1. Find or create cart for the user
        Cart cart = cartRepository.findByUserId(userId.intValue())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId.intValue());
                    return cartRepository.save(newCart);
                });

		
        CartItem existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId.intValue());
        if (existingItem != null) {
            // Increase quantity
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            cartItemRepository.save(existingItem);
        } else {
        	
            // ✅ Fetch product details
            Product product = productRepository.findById(productId.intValue())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

            // ✅ Create new cart item with name and price
            CartItem newItem = new CartItem();
            newItem.setCartId(cart.getId());
            newItem.setProductId(product.getId());
            newItem.setProductName(product.getName());
            newItem.setPrice(product.getPrice());
            newItem.setQuantity(1);
            cartItemRepository.save(newItem);
        }
    }
	
	public Cart getCartByUserId(Integer userId) {
		return cartRepository.findByUserId(userId).orElse(null);
	}
	
	@Transactional
	public void clearCart(Long cartId) {
		cartItemRepository.deleteAllByCartId(cartId);
	}
	
	@Transactional
	public void deleteCart(Long cartId) {
		cartRepository.deleteById(cartId);
	}
}
