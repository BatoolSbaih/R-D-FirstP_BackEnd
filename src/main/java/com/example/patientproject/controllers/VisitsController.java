package com.example.patientproject.controllers;

import com.example.patientproject.Response;
import com.example.patientproject.models.Doctor;
import com.example.patientproject.models.Visits;
import com.example.patientproject.service.DoctorService;
import com.example.patientproject.service.VisitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visits")
public class VisitsController {

    @Autowired
    private VisitsService visitsService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public Response AddVisit(@RequestBody Visits visit) {
        if(visitsService.addVisit(visit)){
            return (new Response(200,"added successfully ","this account"));}
        else return (new Response(404,"failed added  ","this account"));
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get")
    public List<Visits> GetVisits(){
        return visitsService.getVisits();

    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get/{visitID}")
    public Visits getVisitById(@PathVariable Integer visitID) {
        return visitsService.getVisitByID(visitID);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/remove/{visitID}")
    public Response removeVisit(@PathVariable int visitID) {
        if (visitsService.removeVisit(visitID)) {
            return new Response(200, "Removed successfully", "The account with ID " + visitID + " has been successfully removed from the database.");
        } else {
            return new Response(404, "Failed to remove", "No account found with ID " + visitID + ".");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update")
    public Response updateVisit(@RequestBody Visits visit) {
        if (visitsService.updateVisit(visit)) {
            return new Response(200, "Updated successfully", "The account with ID " +visit.getVisitID() + " has been successfully updated.");
        } else {
            return new Response(404, "Failed to update", "No account found with ID " + visit.getVisitID() + ".");
        }
    }


}
