package IbaWork.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@Getter
@Setter
public class ValidatorBean {
    public static List<String> validActionAdmin;
    public static List<String> validActionUser;
    public static final String SELECT = "SELECT";
    public static List<String> prohibitedTables;
    public static List<String> validDB;

    @Bean
    public ValidatorBean getCollectionsBean() {
        return new ValidatorBean();
    }

    @Bean
    public List<String> createListValidActionAdmin() {
        validActionAdmin = Arrays.asList( "INSERT", "UPDATE", "DELETE","CREATE TABLE","DROP TABLE");
        return validActionAdmin;
    }

    @Bean
    public List<String> createListValidActionUser() {
        validActionUser = Arrays.asList("SELECT");
        return validActionUser;
    }

    @Bean
    public List<String> createListProhibitedTables() {
        prohibitedTables = Arrays.asList("IBA.ACTION", "IBA.HIBERNATE_SEQUENCE", "IBA.USER", "IBA.USER_ROLE");
        return prohibitedTables;
    }

    @Bean
    public List<String> createListValidTables() {
        validDB = Arrays.asList("IBA");
        return validDB;
    }

}
