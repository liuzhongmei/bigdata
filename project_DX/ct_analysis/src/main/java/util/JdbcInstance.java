package util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author
 */
public class JdbcInstance {
    private static Connection connection = null;

    public JdbcInstance() {
    }

    public static Connection getInstance(){
        try {
            if (connection == null || connection.isClosed() || !connection.isValid(3)) {
                connection = JdbcUtils.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
