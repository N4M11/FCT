package com.spring.boot.app.boundary.rest;

import com.spring.boot.app.model.Device;
import com.spring.boot.app.repository.DeviceJTARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jta/device")
public class DeviceJTARepositoryResource {

    @Autowired
    private DeviceJTARepository deviceRepo;

    
    @GetMapping("/{id}")
    public ResponseEntity<Device> get(@PathVariable Integer id) {
        Device result = deviceRepo.get(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<Device>> getAll(
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "max-results", required = false) Integer maxResults) {
        
        List<Device> results = deviceRepo.getAll(offset, maxResults);
        return ResponseEntity.ok(results);
    }


    @PostMapping("/post")
    public ResponseEntity<?> createDevice(@RequestBody Device newEntry) {
        try {
            Device created = deviceRepo.create(newEntry);
            return ResponseEntity.status(201).body(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Device> update(@RequestBody Device updatedEntry) {
        if (deviceRepo.get(updatedEntry.getDeviceId()) != null) {
            Device result = deviceRepo.update(updatedEntry);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        Integer deleted = deviceRepo.delete(id);
        if (deleted > 0) {
            return ResponseEntity.ok(id);
        }
        return ResponseEntity.notFound().build();
    }
}