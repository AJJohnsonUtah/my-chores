/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "chore_group_user")
public class ChoreGroupUser {

    @Id
    @GeneratedValue
    @Column(name = "chore_group_user_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chore_group")
    private ChoreGroup choreGroup;
    
    @ManyToOne
    @JoinColumn(name = "chore_user")
    private ChoreUser choreUser;

    @ManyToOne
    @JoinColumn(name = "invited_by")
    private ChoreUser invitedBy;

    @Column(name = "chore_group_user_role")
    private ChoreGroupUserRole choreGroupUserRole;

    @Column(name = "status")
    private ChoreGroupUserStatus status;

    @UpdateTimestamp
    @Column(name = "updated")
    private Timestamp updated;

    public ChoreGroupUser() {
    }
    
    public ChoreGroupUser(ChoreGroup choreGroup, ChoreUser choreUser, ChoreUser invitedBy, ChoreGroupUserRole choreGroupUserRole, ChoreGroupUserStatus status) {
        this.choreGroup = choreGroup;
        this.choreUser = choreUser;
        this.invitedBy = invitedBy;
        this.choreGroupUserRole = choreGroupUserRole;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChoreGroup getChoreGroup() {
        return choreGroup;
    }

    public void setChoreGroup(ChoreGroup choreGroup) {
        this.choreGroup = choreGroup;
    }

    public ChoreUser getChoreUser() {
        return choreUser;
    }

    public void setChoreUser(ChoreUser choreUser) {
        this.choreUser = choreUser;
    }

    public ChoreUser getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(ChoreUser invitedBy) {
        this.invitedBy = invitedBy;
    }

    public ChoreGroupUserRole getChoreGroupUserRole() {
        return choreGroupUserRole;
    }

    public void setChoreGroupUserRole(ChoreGroupUserRole choreGroupUserRole) {
        this.choreGroupUserRole = choreGroupUserRole;
    }

    public ChoreGroupUserStatus getStatus() {
        return status;
    }

    public void setStatus(ChoreGroupUserStatus status) {
        this.status = status;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

}
