package com.naison.mybatistest.converter;

import com.naison.mybatistest.entity.User;
import org.springframework.core.convert.converter.Converter;

/**
 * @author naison
 * @since 10/18/2020 15:11
 */
public class StringToGenderEnumConverter implements Converter<String, User.GenderEnum> {
    @Override
    public User.GenderEnum convert(String source) {
        return User.GenderEnum.男.getCode().toString().equals(source) ? User.GenderEnum.男 : User.GenderEnum.女;
    }
}
