/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author AJ
 */
@Entity
@Table(name="chore_group")
public class ChoreGroup implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(name="chore_group_id")
    private Long id;
    
    @Column(name="chore_group_name")
    private String choreGroupName;

    @OneToMany(mappedBy="choreGroup", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ChoreGroupUser> choreGroupUsers;
    
    @OneToMany(mappedBy="choreGroup", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ChoreSpec> choreSpecs;
    
    public ChoreGroup() {        
    }
    
    public ChoreGroup(String choreGroupName) {
        this.choreGroupName = choreGroupName;
    }

    @JsonIgnore
    public List<ChoreGroupUser> getChoreGroupUsers() {
        return choreGroupUsers;
    }
    
    public void setChoreGroupUsers(List<ChoreGroupUser> choreGroupUsers) {
        this.choreGroupUsers = choreGroupUsers;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChoreGroupName() {
        return choreGroupName;
    }

    public void setChoreGroupName(String choreGroupName) {
        this.choreGroupName = choreGroupName;
    }      

    @JsonIgnore
    public List<ChoreSpec> getChoreSpecs() {
        return choreSpecs;
    }

    public void setChoreSpecs(List<ChoreSpec> choreSpecs) {
        this.choreSpecs = choreSpecs;
    }            
}
