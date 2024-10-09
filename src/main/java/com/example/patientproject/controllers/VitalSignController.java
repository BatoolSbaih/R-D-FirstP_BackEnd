package com.example.patientproject.controllers;

import com.example.patientproject.Response;
import com.example.patientproject.models.VitalSign;
import com.example.patientproject.service.VitalSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vital-signs")
public class VitalSignController {

    @Autowired
    private VitalSignService vitalSignService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public Response addVitalSign(@RequestBody VitalSign vitalSigns) {
        if (vitalSignService.addVitalSign(vitalSigns)) {
            return new Response(200, "Added successfully", "The vital sign has been added successfully.");
        } else {
            return new Response(404, "Failed to add", "Failed to add the vital sign.");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get")
    public List<VitalSign> getVitalSigns() {
        return vitalSignService.getVitalSigns();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get/{id}")
    public VitalSign getVitalSignById(@PathVariable Integer id) {
        return vitalSignService.getVitalSignById(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/remove/{id}")
    public Response removeVitalSign(@PathVariable int id) {
        if (vitalSignService.removeVitalSign(id)) {
            return new Response(200, "Removed successfully", "The vital sign with ID " + id + " has been successfully removed.");
        } else {
            return new Response(404, "Failed to remove", "No vital sign found with ID " + id + ".");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update")
    public Response updateVitalSign(@RequestBody VitalSign vitalSigns) {
        if (vitalSignService.updateVitalSign(vitalSigns)) {
            return new Response(200, "Updated successfully", "The vital sign with ID " + vitalSigns.getId() + " has been successfully updated.");
        } else {
            return new Response(404, "Failed to update", "No vital sign found with ID " + vitalSigns.getId() + ".");
        }
    }
}

