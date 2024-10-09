package com.example.patientproject.controllers;

import com.example.patientproject.models.Status;
import com.example.patientproject.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statuses")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/by-physical-examination/{physicalExaminationId}")
    public List<Status> getStatusesByPhysicalExaminationId(@PathVariable int physicalExaminationId) {
        return statusService.getStatusesByPhysicalExaminationId(physicalExaminationId);
    }
}
