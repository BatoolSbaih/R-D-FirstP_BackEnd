package com.example.patientproject.controllers;

import com.example.patientproject.Response;
import com.example.patientproject.models.Patient;
import com.example.patientproject.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public Response addPatient(@RequestBody Patient patient) {
        if (patientService.addPatient(patient)) {
            return new Response(200, "Added successfully", "The account " + patient.getEmail() + " has been successfully added to the database.");
        }
        return new Response(404, "Failed to add", "The account " + patient.getEmail() + " already exists.");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get")
    public List<Patient> getAllPatients() {
        return patientService.getPatients();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/remove/{patientId}")
    public Response removePatient(@PathVariable Integer patientId) {
        if (patientService.removePatient(patientId)) {
            return new Response(200, "Removed successfully", "The account with ID " + patientId + " has been successfully removed from the database.");
        }
        return new Response(404, "Failed to remove", "No account found with ID " + patientId);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get/{id}")
    public Patient getPatientById(@PathVariable Integer id) {
        return patientService.getPatientById(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update")
    public Response updatePatient(@RequestBody Patient patient) {
        if (patientService.updatePatient(patient)) {
            return new Response(200, "Updated successfully", "The account with ID " + patient.getPatientID() + " has been successfully updated.");
        }
        return new Response(404, "Failed to update", "No account found with ID " + patient.getPatientID());
    }
}
