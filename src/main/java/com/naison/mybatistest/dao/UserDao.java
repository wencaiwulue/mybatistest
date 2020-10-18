package com.naison.mybatistest.dao;

import com.naison.mybatistest.base.BaseDao;
import com.naison.mybatistest.entity.User;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * @author naison
 * @since 10/17/2020 13:03
 */
@Repository
public class UserDao extends BaseDao<User> {
    public List<User> query(String sql, Object... args) throws SQLException {
        return super.list(sql, args);
    }
}
