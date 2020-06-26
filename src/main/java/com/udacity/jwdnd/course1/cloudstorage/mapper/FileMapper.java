package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;
import java.util.Optional;

@Mapper
public interface FileMapper extends BaseUserRelatedMapper<File> {

    @Override
    @Select("SELECT * FROM file WHERE userId=#{userId}")
    List<File> findAllByUserId(Long userId);

    @Override
    @Select("SELECT * FROM file")
    List<File> findAll();

    @Override
    @Select("SELECT * FROM file WHERE id=#{id}")
    Optional<File> findById(Long id);

    @Override
    @Insert("INSERT INTO file (filename, contentType, fileSize, fileData, userId) VALUES (#{filename}, #{contentType}, #{fileSize}, #{fileData}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(File file);

    @Override
    @Update("UPDATE file SET filename = #{file.filename}, contentType = #{file.contentType}, fileSize = #{file.fileSize}, fileData = #{file.fileData} WHERE ID = #{id}")
    void update(File file, Long id);

    @Override
    @Delete("DELETE from file WHERE id=#{id}")
    void delete(Long id);
}