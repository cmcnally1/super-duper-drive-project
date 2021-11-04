package com.cmcnally.udacity.project.cloudstorage.model;

/*
    Model class for credential creation form
 */
public class CredentialForm {

    private Integer formCredentialId;
    private String formCredentialUrl;
    private String formCredentialUsername;
    private String formCredentialPassword;


    // Getters and Setters
    public Integer getFormCredentialId() {
        return formCredentialId;
    }

    public void setFormCredentialId(Integer formCredentialId) {
        this.formCredentialId = formCredentialId;
    }

    public String getFormCredentialUrl() {
        return formCredentialUrl;
    }

    public void setFormCredentialUrl(String formCredentialUrl) {
        this.formCredentialUrl = formCredentialUrl;
    }

    public String getFormCredentialUsername() {
        return formCredentialUsername;
    }

    public void setFormCredentialUsername(String formCredentialUsername) {
        this.formCredentialUsername = formCredentialUsername;
    }

    public String getFormCredentialPassword() {
        return formCredentialPassword;
    }

    public void setFormCredentialPassword(String formCredentialPassword) {
        this.formCredentialPassword = formCredentialPassword;
    }
}
