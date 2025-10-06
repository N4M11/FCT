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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author nami
 */
@Entity
@Table(name = "reparation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reparation.findAll", query = "SELECT r FROM Reparation r"),
    @NamedQuery(name = "Reparation.findByReparationId", query = "SELECT r FROM Reparation r WHERE r.reparationId = :reparationId"),
    @NamedQuery(name = "Reparation.findByName", query = "SELECT r FROM Reparation r WHERE r.name = :name"),
    @NamedQuery(name = "Reparation.findByReceivedDate", query = "SELECT r FROM Reparation r WHERE r.receivedDate = :receivedDate"),
    @NamedQuery(name = "Reparation.findByDeliveryDate", query = "SELECT r FROM Reparation r WHERE r.deliveryDate = :deliveryDate"),
    @NamedQuery(name = "Reparation.findByCompletionDate", query = "SELECT r FROM Reparation r WHERE r.completionDate = :completionDate"),
    @NamedQuery(name = "Reparation.findByProblemDescription", query = "SELECT r FROM Reparation r WHERE r.problemDescription = :problemDescription"),
    @NamedQuery(name = "Reparation.findBySolutionDescription", query = "SELECT r FROM Reparation r WHERE r.solutionDescription = :solutionDescription"),
    @NamedQuery(name = "Reparation.findByEstate", query = "SELECT r FROM Reparation r WHERE r.estate = :estate"),
    @NamedQuery(name = "Reparation.findByStatus", query = "SELECT r FROM Reparation r WHERE r.status = :status")})
public class Reparation implements Serializable {

    @Size(max = 50)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "problem_description")
    private String problemDescription;
    @Size(max = 100)
    @Column(name = "solution_description")
    private String solutionDescription;
    @Size(max = 2147483647)
    @Column(name = "estate")
    private String estate;
    @Size(max = 2147483647)
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "reparationId")
    private Collection<Timesheet> timesheetCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "reparation_id")
    private Integer reparationId;
    @Column(name = "received_date")
    @Temporal(TemporalType.DATE)
    private Date receivedDate;
    @Column(name = "delivery_date")
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;
    @Column(name = "completion_date")
    @Temporal(TemporalType.DATE)
    private Date completionDate;
    @JoinColumn(name = "device_id", referencedColumnName = "device_id")
    @JsonbTransient
    @ManyToOne
    private Device deviceId;

    public Reparation() {
    }

    public Reparation(Integer reparationId) {
        this.reparationId = reparationId;
    }

    public Reparation(Integer reparationId, String problemDescription) {
        this.reparationId = reparationId;
        this.problemDescription = problemDescription;
    }

    public Integer getReparationId() {
        return reparationId;
    }

    public void setReparationId(Integer reparationId) {
        this.reparationId = reparationId;
    }


    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getSolutionDescription() {
        return solutionDescription;
    }

    public void setSolutionDescription(String solutionDescription) {
        this.solutionDescription = solutionDescription;
    }


    public Device getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Device deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reparationId != null ? reparationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reparation)) {
            return false;
        }
        Reparation other = (Reparation) object;
        if ((this.reparationId == null && other.reparationId != null) || (this.reparationId != null && !this.reparationId.equals(other.reparationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rgb.training.app.data.model.Reparation[ reparationId=" + reparationId + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEstate() {
        return estate;
    }

    public void setEstate(String estate) {
        this.estate = estate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Timesheet> getTimesheetCollection() {
        return timesheetCollection;
    }

    public void setTimesheetCollection(Collection<Timesheet> timesheetCollection) {
        this.timesheetCollection = timesheetCollection;
    }
    
}
