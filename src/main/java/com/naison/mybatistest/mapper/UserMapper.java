package com.naison.mybatistest.mapper;

import com.naison.mybatistest.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author naison
 * @since 10/18/2020 17:10
 */
@Repository
@Mapper
public interface UserMapper {
    List<User> listAll(/*@Param(value = "gender") Integer gender*/);
}
