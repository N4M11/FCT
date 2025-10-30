/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.boot.app.model;
import jakarta.persistence.*;



/**
 *
 * @author nami
 */
@Entity
@Table(name="device")
public class Device{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Integer deviceId;
    
    @Column(name = "model")
    private String model;
    
    
    @Column(name = "brand")
    private String brand;
    
    @Column(name = "serial_number")
    private String serialNumber;
    
    
    public Device(){
    }
    
    public Device(String model, String brand, String serialNumber) {
        this.model = model;
        this.brand = brand;
        this.serialNumber = serialNumber;
    }
    
    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }


    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    
     @Override
    public int hashCode() {
        int hash = 0;
        hash += (deviceId != null ? deviceId.hashCode() : 0);
        return hash;
    }
    
     @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Device)) {
            return false;
        }
        Device other = (Device) object;
        if ((this.deviceId == null && other.deviceId != null) || (this.deviceId != null && !this.deviceId.equals(other.deviceId))) {
            return false;
        }
        return true;
    }
    
     @Override
    public String toString() {
        return "com.rgb.training.app.data.model.Device[ deviceId=" + deviceId + " ]";
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    
}
