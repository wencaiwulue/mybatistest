package com.naison.mybatistest.entity;

/**
 * @author naison
 * @since 5/16/2020 17:48
 */
public class User {
    private int id;
    private String name;
    private GenderEnum gender;
    private UserTypeEnum type;

    public enum GenderEnum {
        女(0),
        男(1);
        Integer code;

        GenderEnum(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

    public enum UserTypeEnum {
        好人, 坏人;

        UserTypeEnum() {
        }
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum genderEnum) {
        this.gender = genderEnum;
    }

    public UserTypeEnum getType() {
        return type;
    }

    public void setType(UserTypeEnum type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genderEnum=" + gender +
                ", type=" + type +
                '}';
    }
}
