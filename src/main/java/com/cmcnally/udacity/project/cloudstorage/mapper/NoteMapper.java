package com.cmcnally.udacity.project.cloudstorage.mapper;

import com.cmcnally.udacity.project.cloudstorage.model.Note;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NoteMapper {

    // Select all notes for a user
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    Note getNote(int userid);

    // Insert a new note into the database
    @Insert("INSERT into NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Note note);
}
