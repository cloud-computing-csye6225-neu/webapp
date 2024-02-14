package com.cloud.vijay.health_check.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;

public class UserDTO {


    private String id;

    @JsonProperty("first_name")
    @NotBlank(message = "first name should not be blank")
    @Size(max = 255, message = "length of the first name excedded")
    private String firstName;

    @Size(max = 255, message = "length of the last name excedded")
    @NotBlank(message = "last name should not be blank")
    @JsonProperty("last_name")
    private String lastName;

    @Email(message = "Email is not valid")
    @NotBlank(message = "user name should not be blank")
    @JsonProperty("username")
    private String userName;

    @NotBlank(message = "password should not be blank")
    @Size(min = 8, max = 15, message = "password length is not satisfied")
    private String password;

    @JsonProperty("account-created")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Timestamp createdOn;

    @JsonProperty("account-updated")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Timestamp modifiedOn;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName.trim();
    }

    //	@JsonIgnore
    public String getId() {
        return id;
    }

    public void setId(String userId) {
        id = userId;
    }

    @JsonProperty
    public Timestamp getCreatedOn() {
        return createdOn;
    }

    @JsonProperty
    public Timestamp getModifiedOn() {
        return modifiedOn;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password.trim();
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    @JsonIgnore
    public void setModifiedOn(Timestamp modifiedOn) {
        this.modifiedOn = modifiedOn;
    }
}
