package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT id, username, salt, password, firstName, lastName FROM user WHERE username=#{username}")
    Optional<User> findByUsername(String username);

    @Override
    @Select("SELECT * FROM user")
    List<User> findAll();

    @Override
    @Select("SELECT id, username, salt, password, firstName, lastName FROM user WHERE id=#{id}")
    Optional<User> findById(Long id);

    @Override
    @Insert("INSERT INTO user (username, salt, password, firstName, lastName) VALUES (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(User user);

    @Override
    @Update("UPDATE user SET username = #{user.username}, salt = #{user.salt}, password = #{user.password}, firstName = #{user.firstName}, lastName = #{user.lastName} WHERE ID = #{id}")
    void update(User user, Long id);

    @Override
    @Delete("DELETE from user WHERE id = #{id}")
    void delete(Long id);
}
