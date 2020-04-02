package com.github.vazmin.manage.simple.mat.config;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by Chwing on 2019/7/23.
 */
@Configuration
@EnableConfigurationProperties
@MapperScan(value = {"com.github.vazmin.manage.component.dao",
        "com.github.vazmin.manage.log.context.dao"},
        sqlSessionFactoryRef = "mpSqlSessionFactoryBean")
@ComponentScan({"com.github.vazmin.manage.component",
        "com.github.vazmin.manage.log.context.service"})
public class ManagePlatformDatabaseConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.mp")
    public DataSource datasourceMP() {
        return new AtomikosDataSourceBean();
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration mybatisConfiguration() {
        return new org.apache.ibatis.session.Configuration();
    }

    @Bean
    public SqlSessionFactoryBean mpSqlSessionFactoryBean(
            DatabaseIdProvider databaseIdProvider,
            DataSource datasourceMP,
            org.apache.ibatis.session.Configuration mybatisConfiguration) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setConfiguration(mybatisConfiguration);
        sqlSessionFactoryBean.setDatabaseIdProvider(databaseIdProvider);
        sqlSessionFactoryBean.setDataSource(datasourceMP);
        sqlSessionFactoryBean.setTypeAliasesPackage(
                "com.github.vazmin.framework.web.support.model,"
                        + "com.github.vazmin.manage.component.model,"
                        + "com.github.vazmin.manage.log.context.model");
        return sqlSessionFactoryBean;
    }
}
