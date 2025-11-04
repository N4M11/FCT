/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.boot.app.controller;

import com.spring.boot.app.model.Device;
import com.spring.boot.app.service.DeviceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nami
 */
@RestController
@RequestMapping("/api/jta/device")
public class DeviceController {
    
    private DeviceService deviceService;
    
    @Autowired
    public DeviceController(DeviceService deviceService){
        this.deviceService = deviceService;
        
    }
    
    @GetMapping
    public ResponseEntity<List<Device>> getAll() {     
        List<Device> results = (List<Device>) deviceService.getDevices();
        return ResponseEntity.ok(results);
    }
    
    @PostMapping
    public ResponseEntity<?> addDevice(@RequestBody Device entry) {
        try {
            Device created = deviceService.createDevice(entry);
            return ResponseEntity.status(201).body(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Device entry, @PathVariable Integer id) {
        try {
            Device updatedDevice = deviceService.updateDevice(id, entry);
            return ResponseEntity.ok(updatedDevice);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            deviceService.deleteDevice(id);
            return ResponseEntity.ok("Device with id " + id + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    
    
    
}
