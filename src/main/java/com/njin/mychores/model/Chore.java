/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author AJ
 */
@Entity
@Table(name = "chore")
@NamedQueries({
    @NamedQuery(name = "Chore.findAll", query = "SELECT c FROM Chore c"),
    @NamedQuery(name = "Chore.findByChoreId", query = "SELECT c FROM Chore c WHERE c.choreId = :choreId"),
    @NamedQuery(name = "Chore.findByChoreSpec", query = "SELECT c FROM Chore c WHERE c.choreSpec = :choreSpec"),
    @NamedQuery(name = "Chore.findByDoer", query = "SELECT c FROM Chore c WHERE c.choreDoer = :doer"),
    @NamedQuery(name = "Chore.findByDoerAndStatus", query = "SELECT c FROM Chore c WHERE c.choreDoer = :doer AND c.status = :status"),
    @NamedQuery(name = "Chore.findByChoreUser", query = "SELECT c FROM Chore c WHERE c.choreDoer IN (SELECT u.id FROM ChoreGroupUser u WHERE u.choreUser = :choreUser)"),
    @NamedQuery(name = "Chore.findByChoreUserAndStatus", query = "SELECT c FROM Chore c WHERE c.choreDoer IN (SELECT u.id FROM ChoreGroupUser u WHERE u.choreUser = :choreUser) AND c.status = :status"),
    @NamedQuery(name = "Chore.findByChoreGroup", query = "SELECT c FROM Chore c WHERE c.choreSpec IN (SELECT s.choreSpecId FROM ChoreSpec s WHERE s.choreGroup = :choreGroup)"),
    @NamedQuery(name = "Chore.findByChoreGroupAndStatus", query = "SELECT c FROM Chore c WHERE c.choreSpec IN (SELECT s.choreSpecId FROM ChoreSpec s WHERE s.choreGroup = :choreGroup) AND c.status = :status"),
    @NamedQuery(name = "Chore.findByStatus", query = "SELECT c FROM Chore c WHERE c.status = :status"),
    @NamedQuery(name = "Chore.findByDuration", query = "SELECT c FROM Chore c WHERE c.duration = :duration"),
    @NamedQuery(name = "Chore.findByCreated", query = "SELECT c FROM Chore c WHERE c.created = :created"),
    @NamedQuery(name = "Chore.findByUpdated", query = "SELECT c FROM Chore c WHERE c.updated = :updated"),
    @NamedQuery(name = "Chore.findByLocation", query = "SELECT c FROM Chore c WHERE c.location = :location")})
public class Chore implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "chore_id")
    private Long choreId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "chore_spec")
    private ChoreSpec choreSpec;

    @ManyToOne
    @JoinColumn(name = "doer")
    private ChoreGroupUser choreDoer;

    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private ChoreStatus status;

    @Basic(optional = false)
    @NotNull
    @Column(name = "duration")
    private long duration;

    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Basic(optional = false)
    @Column(name = "updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @PrePersist
    @PreUpdate
    public void updateDates() {
        this.updated = new Date();
        if(this.created == null) {
            this.created = new Date();
        }
    }
    
    @Column(name = "location")
    private BigInteger location;

    public Chore() {
    }

    public Chore(Long choreId) {
        this.choreId = choreId;   
    }

    public Chore(Long choreId, ChoreSpec choreSpec, ChoreGroupUser choreDoer, ChoreStatus status, int duration, Date created, Date updated) {
        this.choreId = choreId;
        this.choreSpec = choreSpec;
        this.choreDoer = choreDoer;
        this.status = status;
        this.duration = duration;
        this.created = created;
        this.updated = updated;
    }

    public Long getChoreId() {
        return choreId;
    }

    public void setChoreId(Long choreId) {
        this.choreId = choreId;
    }

    public ChoreSpec getChoreSpec() {
        return choreSpec;
    }

    public void setChoreSpec(ChoreSpec choreSpec) {
        this.choreSpec = choreSpec;
    }

    public ChoreGroupUser getChoreDoer() {
        return choreDoer;
    }

    public void setChoreDoer(ChoreGroupUser choreDoer) {
        this.choreDoer = choreDoer;
    }

    public ChoreStatus getStatus() {
        return status;
    }

    public void setStatus(ChoreStatus status) {
        this.status = status;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public BigInteger getLocation() {
        return location;
    }

    public void setLocation(BigInteger location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (choreId != null ? choreId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chore)) {
            return false;
        }
        Chore other = (Chore) object;
        if ((this.choreId == null && other.choreId != null) || (this.choreId != null && !this.choreId.equals(other.choreId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.njin.mychores.model.Chore[ choreId=" + choreId + " ]";
    }

}
