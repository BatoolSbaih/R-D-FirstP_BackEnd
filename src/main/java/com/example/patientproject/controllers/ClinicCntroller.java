package com.example.patientproject.controllers;

import com.example.patientproject.Response;
import com.example.patientproject.models.Clinic;
import com.example.patientproject.models.Doctor;
import com.example.patientproject.models.Patient;
import com.example.patientproject.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clinic")
public class ClinicCntroller {


    @Autowired
    ClinicService clinicService;


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public Response AddClinic(@RequestBody Clinic clinic) {
        System.out.println("Clinic Name: " + clinic.getName());
        System.out.println("Clinic Formal Email: " + clinic.getFormalEmail());
        System.out.println("Clinic Phone: " + clinic.getPhone());
        System.out.println("Clinic Address: " + clinic.getAddress());

        if (clinicService.addClinic(clinic)) {
            return new Response(200, "added successfully", "This clinic " + clinic.getName() + " has been successfully added to the database");
        } else {
            return new Response(404, "failed to add", "This clinic " + clinic.getName() + " already exists");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get")
    public List<Clinic> GetClinics() {
        return clinicService.getClinics();

    }
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/remove/{Id}")
    public Response removeClinic(@PathVariable Integer Id) {
        if (clinicService.removeClinic(Id)) {
            return new Response(200, "Removed successfully", "The account with ID " + Id + " has been successfully removed from the database.");
        } else {
            return new Response(404, "Failed to remove", "No account found with ID " + Id+ ".");
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get/{id}")
    public Clinic getClinicById(@PathVariable int id) {
        return clinicService.getClinicById(id);

    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update")
    public Response updateClinic(@RequestBody Clinic clinic) {
        System.out.println("Received Clinic ID: " + clinic.getId());
        if (clinicService.updateClinic(clinic)) {
            return new Response(200, "Updated successfully", "The account with ID "  + " has been successfully updated.");
        }
        return new Response(404, "Failed to update", "No account found with ID " );
    }

//
//    @CrossOrigin(origins = "http://localhost:3000")
//    @DeleteMapping("/remove/{Id}")
//    public Response removeClinic(@PathVariable String Id) {
//        if (clinicService.removeClinic(Id)) {
//            return new Response(200, "Removed successfully", "The account with ID " + Id + " has been successfully removed from the database.");
//        } else {
//            return new Response(404, "Failed to remove", "No account found with ID " + Id+ ".");
//        }
//    }
//    @CrossOrigin(origins = "http://localhost:3000")
//    @GetMapping("/get/{id}")
//    public Clinic getClinicById(@PathVariable String id) {
//        return clinicService.getClinicById(id);
//    }

//    @CrossOrigin(origins = "http://localhost:3000")
//    @PutMapping("/update")
//    public Response updateClinic(@RequestBody Clinic clinic) {
//        if (clinicService.updateClinic(clinic)) {
//            return new Response(200, "Updated successfully", "The account with ID "  + " has been successfully updated.");
//        }
//        return new Response(404, "Failed to update", "No account found with ID " );
//    }
}


