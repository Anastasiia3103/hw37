package sql;

public class SQLQuery {
    public static final String DELETE_PRODUCT_BY_ID = "DELETE FROM products WHERE id = ?";
    public static final String SELECT_PRODUCT_BY_ID = "SELECT id, name, price FROM products WHERE id = ?";
    public static final String SELECT_ALL_PRODUCTS = "SELECT id, name, price FROM products";
}