package com.cmcnally.udacity.project.cloudstorage.services;

import com.cmcnally.udacity.project.cloudstorage.mapper.FileMapper;
import com.cmcnally.udacity.project.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private List<File> storedFiles;
    private FileMapper fileMapper;
    private AuthenticationService authenticationService;

    // Constructor

    public FileService(List<File> storedFiles, FileMapper fileMapper, AuthenticationService authenticationService) {
        this.storedFiles = storedFiles;
        this.fileMapper = fileMapper;
        this.authenticationService = authenticationService;
    }

    // Method to add file to database via mapper
    public void addFile(File file){
        fileMapper.insert(file);
    }

    // Method to get the currently logged in user's files from database
    public List<File> getStoredFiles() {
        return new ArrayList<>(fileMapper.getFiles(authenticationService.getUserId()));
    }

    // Method to delete a file from the database using the file id
    public void deleteFile(Integer fileId) {
        fileMapper.delete(fileId);
    }

}
