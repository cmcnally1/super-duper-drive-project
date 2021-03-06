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
    public int addCredential(Credential credential) {

        // Get list of user credentials
        List<Credential> userCreds = this.getStoredCredentials();

        // Search user credentials to see if username already taken. Return error if taken
        for (int i = 0; i < userCreds.size(); i++){
            if (userCreds.get(i).getUsername().equals(credential.getUsername())){
                return -1;
            }
        }
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
        return credentialMapper.insert(new Credential(null, credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, credential.getUserid()));
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
    public int deleteCredential(Integer credentialid) {
        // Get logged in user's credentials
        List<Credential> authUserCreds = this.getStoredCredentials();

        // Search the user's credentials to ensure the requested credential to be deleted
        // belongs to the logged in user. If not, do nothing and return -1 to inform user
        for(int i = 0; i < authUserCreds.size(); i++) {
            if(authUserCreds.get(i).getCredentialid() == credentialid) {
                return credentialMapper.delete(credentialid);
            }
        }
        // Used in home controller to display to user they cannot delete this
        return -1;
    }

    // Method to update a credential with new values inputted buy the user
    public int editCredential(String url, String username, String password, Integer credentialid) {
        // Get list of user credentials
        List<Credential> userCreds = this.getStoredCredentials();

        // Search user credentials to see if username already taken. Return error if taken
        for (int i = 0; i < userCreds.size(); i++){
            if (userCreds.get(i).getUsername().equals(username)){
                return -1;
            }
        }

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

        return credentialMapper.update(url, username, encodedKey, encryptedPassword, credentialid);
    }
}
