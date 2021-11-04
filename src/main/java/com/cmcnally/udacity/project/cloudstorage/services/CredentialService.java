package com.cmcnally.udacity.project.cloudstorage.services;

import com.cmcnally.udacity.project.cloudstorage.mapper.CredentialMapper;
import com.cmcnally.udacity.project.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private AuthenticationService authenticationService;
    private EncryptionService encryptionService;

    // Constructor
    public CredentialService(CredentialMapper credentialMapper, AuthenticationService authenticationService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.authenticationService = authenticationService;
        this.encryptionService = encryptionService;
    }

    // Method to add a new credential to the database
    public void addCredential(Credential credential) {

        /*
            Encrypt the user's inputted credential password before storing
         */
        // Generate an encryption key for the user
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        // Encrypt the user's password using the key
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        // Insert credential into database via mapper with key and encrypted password
        credentialMapper.insert(new Credential(null, credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, credential.getUserid()));
    }

    // Method to get the current stored credentials for a user
    public List<Credential> getStoredCredentials() {
        return new ArrayList<>(credentialMapper.getCredentials(authenticationService.getUserId()));
    }

    // Method to delete a credential using credential id
    public void deleteCredential(Integer credentialid) {
        credentialMapper.delete(credentialid);
    }
}
