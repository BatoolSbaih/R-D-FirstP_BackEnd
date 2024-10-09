package com.example.patientproject.controllers;

import com.example.patientproject.models.PhysicalExamination;
import com.example.patientproject.service.PhysicalExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/physical-examinations")
public class PhysicalExaminationController {

    @Autowired
    private PhysicalExaminationService physicalExaminationService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get")
    public List<PhysicalExamination> getPhysicalExaminations() {
        return physicalExaminationService.getPhysicalExaminations();
    }
}
