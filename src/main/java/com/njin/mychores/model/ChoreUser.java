/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author aj
 */
@Entity
@Table(name = "chore_user")
@NamedQueries({
    @NamedQuery(name = "findUserToAuthenticate", query = "SELECT u FROM ChoreUser u WHERE u.email LIKE :email")
})
public class ChoreUser implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "screen_name")
    private String screenName;
    
    @OneToMany(mappedBy="choreUser", fetch = FetchType.LAZY)
    private List<ChoreGroupUser> choreGroupUsers;

    @OneToMany(mappedBy="invitedBy", fetch = FetchType.LAZY)
    private List<ChoreGroupUser> choreGroupUserInvites;

    @Transient
    private String password;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @JsonIgnore
    public String getPassword() {
        return passwordHash;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @JsonIgnore
    public List<ChoreGroupUser> getChoreGroupUsers() {
        return choreGroupUsers;
    }

    public void setChoreGroupUsers(List<ChoreGroupUser> choreGroupUsers) {
        this.choreGroupUsers = choreGroupUsers;
    }

    @JsonIgnore
    public List<ChoreGroupUser> getChoreGroupUserInvites() {
        return choreGroupUserInvites;
    }

    public void setChoreGroupUserInvites(List<ChoreGroupUser> choreGroupUserInvites) {
        this.choreGroupUserInvites = choreGroupUserInvites;
    }
    
    @Override
    public String toString() {
        return "Id: " + id + ", Email: " + email + ", Password hash: " + passwordHash + ", Password: " + password + ", screenName + " + screenName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.email);
        hash = 89 * hash + Objects.hashCode(this.passwordHash);
        hash = 89 * hash + Objects.hashCode(this.screenName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChoreUser other = (ChoreUser) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.passwordHash, other.passwordHash)) {
            return false;
        }
        if (!Objects.equals(this.screenName, other.screenName)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    

}
