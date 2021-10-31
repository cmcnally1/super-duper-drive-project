package com.cmcnally.udacity.project.cloudstorage.mapper;

import com.cmcnally.udacity.project.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    // Select all notes for a user
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getNotes(Integer userid);


    // Insert a new note into the database
    @Insert("INSERT into NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Note note);


    // Delete an existing note
    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    int delete(Integer noteid);

    // Update an existing note
    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    int update(String notetitle, String notedescription, Integer noteid);
}
