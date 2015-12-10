package com.splunk.hollywood;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class SpringConfig extends WebMvcConfigurerAdapter {
    public SpringConfig() {
        config = ConfigFactory.load();
    }

    private final Config config;
    
    @Bean
    @Scope
    public DataSource getDataSource() throws Exception {
        Config params = config.getConfig("hollywood.datasource");

        System.out.println("==== data source url:");
        System.out.println(params.getString("url"));

        HikariConfig config = new HikariConfig();
        config.setUsername(params.getString("user"));
        config.setPassword(params.getString("password"));
        config.setJdbcUrl(params.getString("url"));
        config.setDriverClassName(params.getString("driver"));
        config.setMaximumPoolSize(params.getInt("poolSize"));
        config.setConnectionTimeout(params.getInt("connectionTimeout") * 1000);
        // The following settings pertain to MySQL performance improvements.
        // https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
        //
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");

        return new HikariDataSource(config);
    }
}
