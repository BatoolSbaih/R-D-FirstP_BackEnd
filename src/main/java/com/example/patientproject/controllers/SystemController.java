package com.example.patientproject.controllers;

import com.example.patientproject.models.Systems;
import com.example.patientproject.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systems")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get")
    public List<Systems> getSystems() {
        return systemService.getSystems();
    }
}
