package com.naison.mybatistest.base;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author naison
 * @since 10/17/2020 12:49
 */
@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
public class BaseDao<T> {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public List<T> list(String sql, Object... args) throws SQLException {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        Connection connection = sqlSession.getConnection();
        TypeHandlerRegistry typeHandlerRegistry = sqlSession.getConfiguration().getTypeHandlerRegistry();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            Class<?> javaType = ResolvableType.forClass(args[i].getClass()).getRawClass();
            TypeHandler typeHandler = typeHandlerRegistry.getTypeHandler(javaType);
            typeHandler.setParameter(preparedStatement, i + 1, args[i], null);
        }

        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        ResultSet resultSet = preparedStatement.executeQuery();
        BeanPropertyRowMapper<T> rowMapper = new BeanPropertyRowMapper<>(tClass);

        DefaultConversionService defaultConversionService = new DefaultConversionService();
        // 这里加载类型转换器，由于没有使用mybatis的xml写法，所以也就没法儿使用MappedStatement，所以得自己解析，Spring有对应得解析器，但这里需要加入自定义得Converter
        // 所以前边定义得typeHandler的从ResultSet中getResult的方法其实是无法生效的，也就是说这时两套机制
        ServiceLoader.load(Converter.class).stream().forEach(converterProvider -> defaultConversionService.addConverter(converterProvider.get()));
        rowMapper.setConversionService(defaultConversionService);
        RowMapperResultSetExtractor<T> resultSetExtractor = new RowMapperResultSetExtractor<>(rowMapper);
        return resultSetExtractor.extractData(resultSet);
    }


}
