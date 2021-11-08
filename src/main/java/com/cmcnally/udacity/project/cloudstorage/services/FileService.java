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

        // Get logged in user's files
        List<File> authUserFiles = this.getStoredFiles();

        // Search the user's Files to ensure the requested file to be deleted
        // belongs to the logged in user. If not, do nothing.
        for(int i = 0; i < authUserFiles.size(); i++) {
            if(authUserFiles.get(i).getFileId() == fileId) {
                fileMapper.delete(fileId);
                break;
            }
        }
    }

    // Method to get a file from the file ID
    public File getFileById(Integer fileId) {
        // Find the file using the id
        File file = fileMapper.getFileById(fileId);

        // If the file belongs to current logged in user, return the file
        if (file.getUserid() == authenticationService.getUserId()) {
            return fileMapper.getFileById(fileId);
        }
        // Otherwise, return null
        return null;
    }

}
