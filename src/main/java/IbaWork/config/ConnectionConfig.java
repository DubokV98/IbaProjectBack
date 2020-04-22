package IbaWork.config;

import com.mysql.jdbc.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.DriverManager;
import java.sql.SQLException;

@PropertySource(value = "classpath:Application.properties")
@Configuration
public class ConnectionConfig {

    @Value("${spring.datasource.url}")
    private String host;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public Connection connection() throws SQLException {

        Connection conn = (Connection) DriverManager.getConnection(host, username, password);
        return conn;
    }
}
