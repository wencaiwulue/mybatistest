package com.naison.mybatistest.typehandler;

import com.naison.mybatistest.entity.User;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author naison
 * @since 10/18/2020 10:52
 */
public class GenderTypeHandler extends EnumTypeHandler<User.GenderEnum> {
    public GenderTypeHandler() {
        super(User.GenderEnum.class);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, User.GenderEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }
}
