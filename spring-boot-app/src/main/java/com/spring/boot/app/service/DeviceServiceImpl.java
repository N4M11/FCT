/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.boot.app.service;

import com.spring.boot.app.model.Device;
import com.spring.boot.app.repository.DeviceRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nami
 */
@Service
public class DeviceServiceImpl implements DeviceService{
    
    private DeviceRepository deviceRepository;
    
    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository){
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Device createDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public void updateDevice(Integer id, Device device) {
        Optional<Device> optionalDevice=deviceRepository.findById(id.longValue());
        if(optionalDevice.isPresent()){
            Device dbDevice = optionalDevice.get();
            dbDevice.setBrand(device.getBrand());
            dbDevice.setModel(device.getModel());
            dbDevice.setSerialNumber(device.getSerialNumber());
            deviceRepository.save(dbDevice);
        }
    }

    @Override
    public Iterable<Device> getDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public Optional<Device> getDeviceById(Integer id) {
        return deviceRepository.findById(id.longValue());
    }
    
    @Override
    public void deleteDevice(Integer id) {
        deviceRepository.deleteById(id.longValue());
    }
    
}
