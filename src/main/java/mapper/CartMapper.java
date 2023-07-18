package mapper;

import entity.Cart;
import entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartMapper implements RowMapper<Cart> {
    @Override
    public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cart cart = new Cart();
        cart.setId(rs.getLong("cart_id"));

        // Get the product details
        List<Product> products = new ArrayList<>();
        do {
            Product product = new Product();
            product.setId(rs.getInt("product_id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getDouble("price"));
            products.add(product);
        } while (rs.next());

        // Set the products in the cart
        cart.setProducts(products);

        return cart;
    }
}
