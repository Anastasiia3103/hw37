package dao;

import entity.Cart;
import entity.Product;
import mapper.CartMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class CartDao {
    private JdbcTemplate jdbcTemplate;
    private CartMapper cartMapper;

    public CartDao(final JdbcTemplate jdbcTemplate, final CartMapper cartMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.cartMapper = cartMapper;
    }

    public Cart createCart() {
        jdbcTemplate.update("INSERT INTO Carts DEFAULT VALUES");
        Long cartId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM Carts", Long.class);
        Cart cart = new Cart();
        cart.setId(cartId);
        return cart;
    }

    public boolean addToCart(final Cart cart, final Product product) {
        Objects.requireNonNull(cart);
        Objects.requireNonNull(product);
        int insertionResult = jdbcTemplate.update("INSERT INTO CartItems (cart_id, product_id) VALUES (?, ?)",
                cart.getId(), product.getId());
        return insertionResult >= 1;
    }

    public boolean removeFromCart(Long productId) {
        validatePositiveIdNumber(productId);
        int removingResult = jdbcTemplate.update("DELETE FROM CartItems WHERE product_id = ?", productId);
        return removingResult >= 1;
    }

    public boolean deleteCart(Long cartId) {
        validatePositiveIdNumber(cartId);
        int removingProducts = jdbcTemplate.update("DELETE FROM CartItems WHERE cart_id = ?", cartId);
        int deletingCart = jdbcTemplate.update("DELETE FROM Carts WHERE id = ?", cartId);
        return removingProducts >= 1 && deletingCart >= 1;
    }

    public Cart getCartById(Long cartId) {
        validatePositiveIdNumber(cartId);
        return jdbcTemplate.queryForObject("SELECT c.id AS cart_id, p.id AS product_id, p.name, p.price " +
                "FROM Carts c JOIN CartItems ci ON c.id = ci.cart_id " +
                "JOIN Products p ON ci.product_id = p.id WHERE c.id = ?", cartMapper, cartId);
    }

    private void validatePositiveIdNumber(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID can't be zero or negative!");
        }
    }
}

