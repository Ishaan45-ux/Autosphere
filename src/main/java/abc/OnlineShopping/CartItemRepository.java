package abc.OnlineShopping;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long>{

	void deleteAllByCartId(Long cartId);
	List<CartItem> findByCartId(Long cartId);
	CartItem findByCartIdAndProductId(Long cartId, Integer productId);
}
