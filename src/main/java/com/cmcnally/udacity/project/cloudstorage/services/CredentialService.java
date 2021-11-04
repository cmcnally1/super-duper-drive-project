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

    // Method to get decrypted passwords for user
    // Used to display passwords to user when viewing or editing credentials
    public List<String> getDecryptedPasswords() {
        // Get logged in user's credentials
        List<Credential> userCredentials = credentialMapper.getCredentials(authenticationService.getUserId());
        // Initialise a list of Strings to hold the user's decrypted passwords
        List<String> decryptedPasswords = new ArrayList<>();
        // Loop over each user's credential, decrypting each password
        for(int i = 0; i < userCredentials.size(); i++) {
            decryptedPasswords.add(encryptionService.decryptValue(userCredentials.get(i).getPassword(), userCredentials.get(i).getKey()));
        }
        // Return the decrypted passwords
        return decryptedPasswords;
    }

    // Method to delete a credential using credential id
    public void deleteCredential(Integer credentialid) {
        credentialMapper.delete(credentialid);
    }

    // Method to update a credential with new values inputted buy the user
    public void editCredential(String url, String username, String password, Integer credentialid) {
        /*
            Encrypt the user's inputted credential password before storing
         */
        // Generate an encryption key for the user
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        // Encrypt the user's password using the key
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        credentialMapper.update(url, username, encodedKey, encryptedPassword, credentialid);
    }
}
