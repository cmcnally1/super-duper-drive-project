package com.cmcnally.udacity.project.cloudstorage.mapper;

import com.cmcnally.udacity.project.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    // Select all files for a user
    @Select("Select * FROM FILES WHERE userid = #{userid}")
    List<File> getFiles(Integer userid);

    // Insert a new file into the database
    @Insert("INSERT into FILES (fileId, filename, contenttype, filesize, userid, filedata) VALUES(#{fileId}, #{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    // Delete an existing file
    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int delete(Integer fileId);

    // Select the file that matches the file id
    @Select("Select * FROM FILES WHERE fileId = #{fileId}")
    File getFileById(Integer fileId);

}
