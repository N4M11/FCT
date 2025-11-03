/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.spring.boot.app.service;

import com.spring.boot.app.model.Device;
import java.util.Optional;

/**
 *
 * @author nami
 */
public interface DeviceService {
    
    Device createDevice(Device device);
    
    void updateDevice(Integer id, Device device);
    
    Iterable<Device> getDevices();
    
    Optional<Device> getDeviceById(Integer id);
    
    void deleteDevice(Integer id);
    
}
