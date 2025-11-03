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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author nami
 */
@Controller
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
    
    @PostMapping("/post")
    public ResponseEntity<?> addDevice(Device entry, BindingResult result, Model model) {
        try {
            Device created = deviceService.createDevice(entry);
             return ResponseEntity.status(201).body(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
   @PutMapping
    public ResponseEntity<Device> update(Device entry, Integer id) {
        entry.setDeviceId(id);
        return ResponseEntity.ok(entry);

    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.ok(id);
    }
    
    
    
    
    
}
