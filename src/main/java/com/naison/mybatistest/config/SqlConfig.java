package com.naison.mybatistest.config;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ServiceLoader;

/**
 * @author naison
 * @since 10/16/2020 23:57
 */
@Component
public class SqlConfig {
    @Bean(value = "developSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier(value = "developConfiguration") Configuration configuration) {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        return builder.build(configuration);
    }

    @Bean(value = "developConfiguration")
    public Configuration configuration(@Qualifier(value = "developEnv") Environment environment) {
        Configuration configuration = new Configuration();
        configuration.setEnvironment(environment);
        configuration.setCacheEnabled(true);
        ServiceLoader.load(TypeHandler.class).stream().forEach(e -> configuration.getTypeHandlerRegistry().register(e.get()));
        return configuration;
    }

    @Bean(value = "developEnv")
    public Environment environment(@Qualifier(value = "testDB") DataSource dataSource) {
        return new Environment.Builder("develop").dataSource(dataSource).transactionFactory(new JdbcTransactionFactory()).build();
    }

    @Bean(value = "testDB")
    public DataSource dataSource() {
//        return DataSourceBuilder.create()
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .url("jdbc:mysql://localhost:3306/test")
//                .username("root")
//                .password("12345678")
//                .build();
        // 内存数据库还挺好玩, 语法和MySQL很类似了
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("create-table-user.sql")
                .build();
    }
}
