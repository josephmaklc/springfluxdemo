package com.optimal.solutions.springfluxdemo.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;

@Configuration

public class R2DBCDatasourceConfig extends AbstractR2dbcConfiguration {

    @Value("${spring.datasource.host}")
    private String host;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.database}")
    private String database;

    @Bean
    @Override
    public ConnectionFactory connectionFactory() {
        System.out.println("Creating ConnectionFactory. See application.properties");
        System.out.println("host: "+ host);
        System.out.println("database:"+ database);
        final PostgresqlConnectionConfiguration pgConfig = PostgresqlConnectionConfiguration.builder()
                .host(host)
                .username(username)
                .password(password)
                .database(database)
                //.sslMode(SSLMode.REQUIRE)
                .connectTimeout(Duration.ofSeconds(1000))
                .build();

        final PostgresqlConnectionFactory pgConFactory = new PostgresqlConnectionFactory(pgConfig);
        final ConnectionPoolConfiguration configuration = ConnectionPoolConfiguration.builder()
                .connectionFactory(pgConFactory)
                .maxSize(15)
                .build();
        return new ConnectionPool(configuration);
    }

    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate() {
        return new R2dbcEntityTemplate(connectionFactory());
    }

    @Bean
    public DatabaseClient postgresDatabaseClient() throws NullPointerException {
        try {
            return DatabaseClient.create(connectionFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}