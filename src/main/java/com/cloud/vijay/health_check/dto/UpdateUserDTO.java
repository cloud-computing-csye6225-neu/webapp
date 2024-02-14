package com.cloud.vijay.health_check.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

public class UpdateUserDTO {

    @JsonProperty("first_name")
    @Size(max = 255, message = "length of the first name excedded")
    private String firstName;

    @JsonProperty("last_name")
    @Size(max = 255, message = "length of the last name excedded")
    private String lastName;

    @Size(min = 8, max = 15, message = "password length is not satisfied")
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    public String getLastName() {
        return lastName.trim();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }
}
