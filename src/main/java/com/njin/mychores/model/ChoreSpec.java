/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.njin.mychores.converter.ChoreFrequencyConverter;
import com.njin.mychores.converter.LocalDateTimeConverter;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
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
    @NamedQuery(name = "ChoreSpec.findByChoreGroupId", query = "SELECT c FROM ChoreSpec c WHERE c.choreGroup = :choreGroup"),
    @NamedQuery(name = "ChoreSpec.findByPreferredDoer", query = "SELECT c FROM ChoreSpec c WHERE c.preferredDoer = :preferredDoer"),
    @NamedQuery(name = "ChoreSpec.findByPastDate", query = "SELECT c FROM ChoreSpec c WHERE c.nextInstanceDate <= :date"),
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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "chore_group")
    private ChoreGroup choreGroup;

    @ManyToOne
    @JoinColumn(name = "preferred_doer")
    private ChoreGroupUser preferredDoer;
    
    @Column(name = "frequency")
    @Convert(converter = ChoreFrequencyConverter.class)
    private ChoreFrequency frequency;

    @Column(name = "next_instance_date")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime nextInstanceDate;
        
    @Transient
    private Long nextInstance;

    public ChoreSpec() {
    }

    public ChoreSpec(Long choreSpecId) {
        this.choreSpecId = choreSpecId;
    }

    public ChoreSpec(Long choreSpecId, String name, ChoreGroup choreGroup) {
        this.choreSpecId = choreSpecId;
        this.name = name;
        this.choreGroup = choreGroup;
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

    public ChoreGroup getChoreGroup() {
        return choreGroup;
    }

    public void setChoreGroup(ChoreGroup choreGroup) {
        this.choreGroup = choreGroup;
    }

    public ChoreGroupUser getPreferredDoer() {
        return preferredDoer;
    }

    public void setPreferredDoer(ChoreGroupUser preferredDoer) {
        this.preferredDoer = preferredDoer;
    }

    public ChoreFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(ChoreFrequency frequency) {
        this.frequency = frequency;
    }

    @JsonIgnore
    public LocalDateTime getNextInstanceDate() {
        return nextInstanceDate;
    }

    public void setNextInstanceDate(LocalDateTime nextInstanceDate) {
        this.nextInstanceDate = nextInstanceDate;
    }
    
    public void setNextInstanceDateFromLong(Long nextInstanceDate) {
        if (nextInstanceDate == null && this.nextInstanceDate == null) {
            this.nextInstanceDate = null;
        } else if(this.nextInstanceDate == null) {
            this.nextInstanceDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(nextInstanceDate), TimeZone.getDefault().toZoneId());
        }
    }   
    
    public void setNextInstance(Long nextInstance) {        
        this.nextInstance = nextInstance;
    }
    
    public Long getNextInstance() {
        return (this.nextInstanceDate == null ? null : this.nextInstanceDate.toEpochSecond(ZoneOffset.UTC));
    }
    
    @JsonIgnore
    public Long getNextInstanceFromLong() {
        return this.nextInstance;
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
