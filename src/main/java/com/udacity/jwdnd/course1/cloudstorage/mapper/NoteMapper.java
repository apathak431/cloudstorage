package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;
import java.util.Optional;

@Mapper
public interface NoteMapper extends BaseUserRelatedMapper<Note> {

    @Override
    @Select("SELECT * FROM note WHERE userId=#{userId}")
    List<Note> findAllByUserId(Long userId);

    @Override
    @Select("SELECT * FROM note")
    List<Note> findAll();

    @Override
    @Select("SELECT * FROM note WHERE id=#{id}")
    Optional<Note> findById(Long id);

    @Override
    @Insert("INSERT INTO note (noteTitle, noteDescription, userId) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Note note);

    @Override
    @Update("UPDATE note SET noteTitle = #{note.noteTitle}, noteDescription = #{note.noteDescription} WHERE ID = #{id}")
    void update(Note note, Long id);

    @Override
    @Delete("DELETE from note WHERE id=#{id}")
    void delete(Long id);

}
