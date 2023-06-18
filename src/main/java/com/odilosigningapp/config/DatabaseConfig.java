package com.odilosigningapp.config;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .url("jdbc:postgresql://db-odilo-assignment.cbmusoefacg9.us-east-1.rds.amazonaws.com:5432/initialdb")
                .username("masterusername")
                .password("masterpassword")
                .build();
    }
}
