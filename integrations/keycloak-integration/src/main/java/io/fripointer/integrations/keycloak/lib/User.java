package io.fripointer.integrations.keycloak.lib;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class User {
    
    private Map<String, Object> attributes;
    
    private Map<String, Object> clientRoles;
    
    private Date createdTimestamp;
    
    private List<UserCredentials> credentials;
    
    private String email;
    
    private boolean emailVerified;
    
    private boolean enabled;
    
    private String firstName;
    
    private String id;
    
    private List<String> groups;
    
    private String lastName;
    
    private List<String> realmRoles;
    
    private String username;
    
    private List<UserFederation> federatedIdentities;
    
    private String federationLink;
    
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    
    public Map<String, Object> getClientRoles() {
        return clientRoles;
    }
    
    public void setClientRoles(Map<String, Object> clientRoles) {
        this.clientRoles = clientRoles;
    }
    
    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }
    
    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
    
    public List<UserCredentials> getCredentials() {
        return credentials;
    }
    
    public void setCredentials(List<UserCredentials> credentials) {
        this.credentials = credentials;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public boolean isEmailVerified() {
        return emailVerified;
    }
    
    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public List<String> getGroups() {
        return groups;
    }
    
    public void setGroups(List<String> groups) {
        this.groups = groups;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public List<String> getRealmRoles() {
        return realmRoles;
    }
    
    public void setRealmRoles(List<String> realmRoles) {
        this.realmRoles = realmRoles;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public List<UserFederation> getFederatedIdentities() {
        return federatedIdentities;
    }
    
    public void setFederatedIdentities(List<UserFederation> federatedIdentities) {
        this.federatedIdentities = federatedIdentities;
    }
    
    public String getFederationLink() {
        return federationLink;
    }
    
    public void setFederationLink(String federationLink) {
        this.federationLink = federationLink;
    }
}
