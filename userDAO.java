import java.sql.*;
import java.util.logging.Logger;

public class UserDAO {
    private static final String SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    public User login(String username, String password) {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_USER_BY_USERNAME)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    if (storedPassword.equals(password)) {
                        // 비밀번호가 일치할 때
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        // 사용자 정보 반환
                        return new User(id, username, name);
                    } else {
                        // 비밀번호가 일치하지 않을 때
                        logger.warning("Login failed for user " + username + ": Incorrect password");
                        throw new SQLException("Incorrect password");
                    }
                } else {
                    // 사용자가 존재하지 않을 때
                    logger.warning("Login failed: User not found - " + username);
                    throw new SQLException("User not found");
                }
            }
        } catch (SQLException e) {
            // 오류 처리
            logger.warning("Error during login: " + e.getMessage());
            // 사용자에게 적절한 오류 메시지를 전달하기 위해 예외를 다시 던짐
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }
}
