/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "chore_group_user")
@NamedQueries({
    @NamedQuery(name = "findAllForUser", query="SELECT u FROM ChoreGroupUser u WHERE u.choreUser = :choreUser"),
    @NamedQuery(name = "findAllForChoreGroup", query="SELECT u FROM ChoreGroupUser u WHERE u.choreGroup = :choreGroup"),
    @NamedQuery(name = "findChoreGroupUser", query="SELECT u FROM ChoreGroupUser u WHERE u.choreGroup = :choreGroup AND u.choreUser = :choreUser"),
    @NamedQuery(name = "findAllForUserWithStatus", query="SELECT u FROM ChoreGroupUser u WHERE u.choreUser = :choreUser AND u.status = :status"),
    @NamedQuery(name = "findAllForInviterWithStatus", query="SELECT u FROM ChoreGroupUser u WHERE u.invitedBy = :invitedBy AND u.status = :status")
})
public class ChoreGroupUser implements Serializable {

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

    @Column(name = "updated")
    private Timestamp updated;
    
    @OneToMany(mappedBy="preferredDoer", fetch = FetchType.LAZY)
    private List<ChoreSpec> choresThatPreferUser;

    @OneToMany(mappedBy="choreDoer", fetch = FetchType.LAZY)
    private List<Chore> chores;
    
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

    @JsonIgnore
    public List<ChoreSpec> getChoresThatPreferUser() {
        return choresThatPreferUser;
    }

    public void setChoresThatPreferUser(List<ChoreSpec> choresThatPreferUser) {
        this.choresThatPreferUser = choresThatPreferUser;
    }        

    @JsonIgnore
    public List<Chore> getChores() {
        return chores;
    }

    public void setChores(List<Chore> chores) {
        this.chores = chores;
    }   
}
