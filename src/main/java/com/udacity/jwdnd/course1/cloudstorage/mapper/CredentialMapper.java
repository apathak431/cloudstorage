package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;
import java.util.Optional;

@Mapper
public interface CredentialMapper extends BaseUserRelatedMapper<Credential> {

    @Select("SELECT * FROM credential WHERE userId=#{userId}")
    List<Credential> findAllByUserId(Long userId);

    @Override
    @Select("SELECT * FROM credential")
    List<Credential> findAll();

    @Override
    @Select("SELECT * FROM credential WHERE id=#{id}")
    Optional<Credential> findById(Long id);

    @Override
    @Insert("INSERT INTO credential (url, username, `key`, password, userId) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Credential credential);

    @Override
    @Update("UPDATE credential SET `url` = #{credential.url}, username = #{credential.username}, `key` = #{credential.key}, password = #{credential.password} WHERE id = #{id}")
    void update(Credential credential, Long id);

    @Override
    @Delete("DELETE from credential WHERE id = #{id}")
    void delete(Long id);
}
