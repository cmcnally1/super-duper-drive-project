package com.cmcnally.udacity.project.cloudstorage.mapper;

import com.cmcnally.udacity.project.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CredentialMapper {

    // Select method for getting credentials stored for a user
    @Select("Select * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getCredentials(Integer userid);

    // Insert a new credential into the database
    @Insert("INSERT into CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credential credential);
}
