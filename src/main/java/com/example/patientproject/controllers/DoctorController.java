package com.example.patientproject.controllers;

import com.example.patientproject.Response;
import com.example.patientproject.models.Doctor;
import com.example.patientproject.service.DoctorService;
import com.example.patientproject.models.DoctorClinic;
import com.example.patientproject.service.DoctorClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DoctorClinicService doctorClinicService;
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public Response addDoctor(@RequestBody Doctor doctor) {
        Doctor addedDoctor = doctorService.addDoctor(doctor);

        if (addedDoctor != null) {
            return Response.successAdd(200, "Added successfully", addedDoctor);
        } else {
            return new Response(404, "Failed to add", "This account " + doctor.getEmail() + " already exists.");
        }
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get")
    public List<Doctor> getDoctors() {
        return doctorService.getDoctors();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get/{doctorID}")
    public Doctor getDoctorById(@PathVariable int doctorID) {
        return doctorService.getDoctorById(doctorID);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/remove/{doctorID}")
    public Response removeDoctor(@PathVariable int doctorID) {
        if (doctorService.removeDoctor(doctorID)) {
            return new Response(200, "Removed successfully", "The account with ID " + doctorID + " has been successfully removed from the database.");
        } else {
            return new Response(404, "Failed to remove", "No account found with ID " + doctorID + ".");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update")
    public Response updateDoctor(@RequestBody Doctor doctor) {
        if (doctorService.updateDoctor(doctor)) {
            return new Response(200, "Updated successfully", "The account with ID " + doctor.getDoctorID() + " has been successfully updated.");
        } else {
            return new Response(404, "Failed to update", "No account found with ID " + doctor.getDoctorID() + ".");
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/addDoctorClinics")
    public Response addDoctorClinics(@RequestBody DoctorClinic doctorClinic) {
        if (doctorClinicService.addOrUpdateDoctorClinic(doctorClinic)) {
            return new Response(200, "Added successfully", "This account  has been successfully added to the database.");
        } else {
            return new Response(404, "Failed to add", "This account ");
        }
    }
}
