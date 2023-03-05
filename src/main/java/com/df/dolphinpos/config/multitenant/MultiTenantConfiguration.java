/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.df.dolphinpos.config.multitenant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 *
 * @author mulyadi
 */
@Configuration
public class MultiTenantConfiguration {

    @Bean
    public DataSource dataSource() throws IOException {
        try {
            Map<Object, Object> resolvedDataSource = new HashMap<>();
            Properties prop = new Properties();
            prop.load(new FileInputStream(new File("datasources.properties")));
            String[] tenant = prop.getProperty("tenant").split(";");
            for (String data : tenant) {
                DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
                String[] tenantData = data.split(":");
                String tenantId = tenantData[0];
                String tenantDb = tenantData[1];
                dataSourceBuilder.driverClassName("org.postgresql.Driver");
                dataSourceBuilder.username("dolphin");
                dataSourceBuilder.password("Bk201!@#");
                dataSourceBuilder.url("jdbc:postgresql://digitalfantasi.com:5432/"+tenantDb);
                resolvedDataSource.put(tenantId, dataSourceBuilder.build());
            }

            AbstractRoutingDataSource datasource = new MultitenantDataDource();
            datasource.setDefaultTargetDataSource(resolvedDataSource.get("public"));
            datasource.setTargetDataSources(resolvedDataSource);
            datasource.afterPropertiesSet();
            return datasource;
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

    }
}
