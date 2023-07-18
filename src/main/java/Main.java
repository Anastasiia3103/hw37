import dao.CartDao;
import dao.ProductDao;
import entity.Cart;
import entity.Product;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DatabaseConfig.class);
        context.scan("dao");
        context.refresh();

        CartDao cartDao = context.getBean(CartDao.class);
        ProductDao productDao = context.getBean(ProductDao.class);

        Cart cart = cartDao.createCart();
        Product product1 = new Product("Product 1", 10.0);
        Product product2 = new Product("Product 2", 20.0);
        productDao.addProduct(product1);
        productDao.addProduct(product2);
        cartDao.addToCart(cart, product1);
        cartDao.addToCart(cart, product2);

        System.out.println("Cart ID: " + cart.getId());
        System.out.println("Products in Cart:");
        for (Product product : cart.getProducts()) {
            System.out.println("ID: " + product.getId() + ", Name: " + product.getName() + ", Price: " + product.getPrice());
        }

        context.close();
    }
}
