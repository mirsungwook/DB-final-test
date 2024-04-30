import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String INSERT_PRODUCT = "INSERT INTO products (name, price, description, image_url) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM products";

    public void addProduct(Product product) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_PRODUCT)) {
            // Prepared Statement를 사용하여 SQL 쿼리를 정의
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getDescription());
            stmt.setString(4, product.getImageUrl());
            stmt.executeUpdate(); // 실행
        }
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_PRODUCTS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                String imageUrl = rs.getString("image_url");
                Product product = new Product(id, name, price, description, imageUrl);
                products.add(product);
            }
        }
        return products;
    }
}
