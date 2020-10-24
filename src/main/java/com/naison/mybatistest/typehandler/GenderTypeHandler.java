package com.naison.mybatistest.typehandler;

import com.naison.mybatistest.entity.User;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author naison
 * @since 10/18/2020 10:52
 */
@MappedTypes(value = User.GenderEnum.class)
@MappedJdbcTypes(value = {JdbcType.BIT, JdbcType.BOOLEAN})
public class GenderTypeHandler extends EnumTypeHandler<User.GenderEnum> {
    public GenderTypeHandler() {
        super(User.GenderEnum.class);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, User.GenderEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public User.GenderEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String s = rs.getString(columnName);
        return s == null ? null : s.equals(String.valueOf(User.GenderEnum.女.getCode())) ? User.GenderEnum.女 : User.GenderEnum.男;
    }

    @Override
    public User.GenderEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int gender = rs.getInt(columnIndex);
        return gender == User.GenderEnum.女.getCode() ? User.GenderEnum.女 : User.GenderEnum.男;
    }

    @Override
    public User.GenderEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int gender = cs.getInt(columnIndex);
        return gender == User.GenderEnum.女.getCode() ? User.GenderEnum.女 : User.GenderEnum.男;
    }
}
