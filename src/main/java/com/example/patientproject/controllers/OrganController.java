package com.example.patientproject.controllers;

import com.example.patientproject.Response;
import com.example.patientproject.models.Organ;
import com.example.patientproject.service.OrganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organs")
public class OrganController {

    @Autowired
    private OrganService organService;

//    @CrossOrigin(origins = "http://localhost:3000")
//    @PostMapping("/add")
//    public Response addOrgan(@RequestBody Organ organ) {
//        if (organService.addOrgan(organ)) {
//            return new Response(200, "Added successfully", "Organ " + organ.getName() + " has been successfully added to the database.");
//        } else {
//            return new Response(404, "Failed to add", "An organ with the name " + organ.getName() + " already exists.");
//        }
//    }
//
//    @CrossOrigin(origins = "http://localhost:3000")
//    @GetMapping("/get")
//    public List<Organ> getOrgans() {
//        return organService.getOrgans();
//    }
//
//    @CrossOrigin(origins = "http://localhost:3000")
//    @GetMapping("/get/{organId}")
//    public Organ getOrganById(@PathVariable int organId) {
//        return organService.getOrganById(organId);
//    }
//
//    @CrossOrigin(origins = "http://localhost:3000")
//    @DeleteMapping("/remove/{organId}")
//    public Response removeOrgan(@PathVariable int organId) {
//        if (organService.removeOrgan(organId)) {
//            return new Response(200, "Removed successfully", "The organ with ID " + organId + " has been successfully removed from the database.");
//        } else {
//            return new Response(404, "Failed to remove", "No organ found with ID " + organId + ".");
//        }
//    }
//
//    @CrossOrigin(origins = "http://localhost:3000")
//    @PutMapping("/update")
//    public Response updateOrgan(@RequestBody Organ organ) {
//        if (organService.updateOrgan(organ)) {
//            return new Response(200, "Updated successfully", "The organ with ID " + organ.getId() + " has been successfully updated.");
//        } else {
//            return new Response(404, "Failed to update", "No organ found with ID " + organ.getId() + ".");
//        }
//    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/by-system/{systemId}")
    public List<Organ> getOrgansBySystemId(@PathVariable int systemId) {
        return organService.getOrgansBySystemId(systemId);
    }
}
