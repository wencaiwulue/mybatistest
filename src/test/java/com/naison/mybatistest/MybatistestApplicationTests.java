package com.naison.mybatistest;

import com.naison.mybatistest.dao.UserDao;
import com.naison.mybatistest.entity.User;
import com.naison.mybatistest.entity.User.GenderEnum;
import com.naison.mybatistest.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class MybatistestApplicationTests {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @BeforeEach
    public void createTable() throws IOException {
        Connection connection = sqlSessionFactory.openSession().getConnection();
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        scriptRunner.runScript(Resources.getResourceAsReader("create-table-user-mysql.sql"));
    }

    @Test
    public void testSession() throws SQLException {
        List<User> list = userDao.query("select * from user where gender = ?", GenderEnum.男);
        list.forEach(e -> System.out.println(e.toString()));
    }

    @Test
    public void list() throws SQLException {
        List<User> list = userDao.query("select * from user");
        list.forEach(e -> System.out.println(e.toString()));
    }

    @Test
    public void testMapperList() {
        List<User> list = userMapper.listAll(0);
        list.forEach(e -> System.out.println(e.toString()));
    }


}
