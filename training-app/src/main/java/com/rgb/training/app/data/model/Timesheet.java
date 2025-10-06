/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rgb.training.app.data.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author nami
 */
@Entity
@Table(name = "timesheet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Timesheet.findAll", query = "SELECT t FROM Timesheet t"),
    @NamedQuery(name = "Timesheet.findById", query = "SELECT t FROM Timesheet t WHERE t.id = :id"),
    @NamedQuery(name = "Timesheet.findByDate", query = "SELECT t FROM Timesheet t WHERE t.date = :date"),
    @NamedQuery(name = "Timesheet.findByDescription", query = "SELECT t FROM Timesheet t WHERE t.description = :description"),
    @NamedQuery(name = "Timesheet.findByTime", query = "SELECT t FROM Timesheet t WHERE t.time = :time")})
public class Timesheet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time")
    @Temporal(TemporalType.TIME)
    private Date time;
    @JoinColumn(name = "reparation_id", referencedColumnName = "reparation_id")
    @JsonbTransient
    @ManyToOne
    private Reparation reparationId;

    public Timesheet() {
    }

    public Timesheet(Integer id) {
        this.id = id;
    }

    public Timesheet(Integer id, Date date, String description, Date time) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Reparation getReparationId() {
        return reparationId;
    }

    public void setReparationId(Reparation reparationId) {
        this.reparationId = reparationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Timesheet)) {
            return false;
        }
        Timesheet other = (Timesheet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rgb.training.app.data.model.Timesheet[ id=" + id + " ]";
    }
    
}
