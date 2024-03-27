package com.cloud.vijay.health_check.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;


@Entity
@Table(name="confirmationTokenDetail")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="token_id")
    private String tokenId;

    @Column(name="confirmation_token")
    private String confirmationToken;
    private Date expirationDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id")
    private User user;

    public VerificationToken() {}

    public VerificationToken(User user) {
        this.user = user;
        expirationDate = null;
        confirmationToken = UUID.randomUUID().toString();
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenid) {
        this.tokenId = tokenid;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date createdDate) {
        this.expirationDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
