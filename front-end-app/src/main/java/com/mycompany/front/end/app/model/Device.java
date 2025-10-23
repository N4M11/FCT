/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.front.end.app.model;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author nami
 */
@Entity
@Table(name = "device")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Device.findAll", query = "SELECT d FROM Device d"),
    @NamedQuery(name = "Device.findByDeviceId", query = "SELECT d FROM Device d WHERE d.deviceId = :deviceId"),
    @NamedQuery(name = "Device.findByModel", query = "SELECT d FROM Device d WHERE d.model = :model"),
    @NamedQuery(name = "Device.findByBrand", query = "SELECT d FROM Device d WHERE d.brand = :brand"),
    @NamedQuery(name = "Device.findBySerialNumber", query = "SELECT d FROM Device d WHERE d.serialNumber = :serialNumber")})
public class Device implements Serializable {
    
    
    public static final String ID_TAG = "id";
    public static final String MODEL_TAG = "model";
    public static final String BRAND_TAG = "brand";
    public static final String SERIAL_NUMBER_TAG = "serial_number";
    public static final String FROM_ODOO_TAG = "fromOdoo";


    

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "model")
    private String model;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "brand")
    private String brand;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "serial_number")
    private String serialNumber;
    @Column(name = "fromodoo")
    private Boolean fromodoo;


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "device_id", nullable = false)
    private Integer deviceId;
   

    public Device() {
    }

    public Device(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Device(String model, String brand, String serialNumber, Boolean fromOdoo) {
        this.model = model;
        this.brand = brand;
        this.serialNumber = serialNumber;
        this.fromodoo = fromOdoo;
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


    public Boolean getFromodoo() {
        return fromodoo;
    }

    public void setFromodoo(Boolean fromodoo) {
        this.fromodoo = fromodoo;
    }
    
    
    public Map<String, Object> getFieldsAsHashMap() {
        Map result = new HashMap();
        if (model != null) {
            result.put(MODEL_TAG, model);
        }
        if (brand != null) {
            result.put(BRAND_TAG, brand);
        }
        if (serialNumber != null) {
            result.put(SERIAL_NUMBER_TAG, serialNumber);
        }
       
        
        return result;
    }

 
   

 

   
    
}
