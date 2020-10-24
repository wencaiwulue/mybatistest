package com.naison.mybatistest.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author naison
 * @since 10/16/2020 23:57
 */
@Configuration
public class SqlConfig {

    @Bean(value = "mysql")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/test")
                .username("root")
                .password("12345678")
                .build();
    }

//    @Bean(value = "hsql")
//    public DataSource dataSource1() {
//        // 内存数据库还挺好玩, 语法和MySQL很类似了
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.HSQL)
//                .addScript("create-table-user-hsql.sql")
//                .build();
//    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(
            DataSource dataSource,
            @Value("${mybatis.mapper-locations}") String mapperLocations,
            @Value("${mybatis.type-handlers-package}") String typeHandlerPackage) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeHandlersPackage(typeHandlerPackage);
        sqlSessionFactoryBean.setMapperLocations(findMapperLocations(mapperLocations));
        return sqlSessionFactoryBean;
    }

    private Resource[] findMapperLocations(String resourcePaths) {
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        return Stream.of(resourcePaths.split(","))
                .map(resolverResources -> {
                    try {
                        return patternResolver.getResources(resolverResources);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .flatMap(Stream::of)
                .toArray(Resource[]::new);
    }
}
