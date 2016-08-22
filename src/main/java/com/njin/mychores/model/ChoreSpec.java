/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.model;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "chore_spec")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChoreSpec.findAll", query = "SELECT c FROM ChoreSpec c"),
    @NamedQuery(name = "ChoreSpec.findByChoreSpecId", query = "SELECT c FROM ChoreSpec c WHERE c.choreSpecId = :choreSpecId"),
    @NamedQuery(name = "ChoreSpec.findByName", query = "SELECT c FROM ChoreSpec c WHERE c.name = :name"),
    @NamedQuery(name = "ChoreSpec.findByChoreGroupId", query = "SELECT c FROM ChoreSpec c WHERE c.choreGroupId = :choreGroupId"),
    @NamedQuery(name = "ChoreSpec.findByPreferredDoer", query = "SELECT c FROM ChoreSpec c WHERE c.preferredDoer = :preferredDoer"),
    @NamedQuery(name = "ChoreSpec.findByFrequency", query = "SELECT c FROM ChoreSpec c WHERE c.frequency = :frequency")})
public class ChoreSpec implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "chore_spec_id")
    private Long choreSpecId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "chore_group_id")
    private long choreGroupId;
    @Column(name = "preferred_doer")
    private BigInteger preferredDoer;
    @Size(max = 255)
    @Column(name = "frequency")
    private String frequency;

    public ChoreSpec() {
    }

    public ChoreSpec(Long choreSpecId) {
        this.choreSpecId = choreSpecId;
    }

    public ChoreSpec(Long choreSpecId, String name, long choreGroupId) {
        this.choreSpecId = choreSpecId;
        this.name = name;
        this.choreGroupId = choreGroupId;
    }

    public Long getChoreSpecId() {
        return choreSpecId;
    }

    public void setChoreSpecId(Long choreSpecId) {
        this.choreSpecId = choreSpecId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getChoreGroupId() {
        return choreGroupId;
    }

    public void setChoreGroupId(long choreGroupId) {
        this.choreGroupId = choreGroupId;
    }

    public BigInteger getPreferredDoer() {
        return preferredDoer;
    }

    public void setPreferredDoer(BigInteger preferredDoer) {
        this.preferredDoer = preferredDoer;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (choreSpecId != null ? choreSpecId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChoreSpec)) {
            return false;
        }
        ChoreSpec other = (ChoreSpec) object;
        if ((this.choreSpecId == null && other.choreSpecId != null) || (this.choreSpecId != null && !this.choreSpecId.equals(other.choreSpecId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.njin.mychores.model.ChoreSpec[ choreSpecId=" + choreSpecId + " ]";
    }
    
}
