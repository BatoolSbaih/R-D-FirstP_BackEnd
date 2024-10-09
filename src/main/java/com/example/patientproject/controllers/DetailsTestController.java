package com.example.patientproject.controllers;

import com.example.patientproject.Response;
import com.example.patientproject.models.DetailsTest;
import com.example.patientproject.service.DetailsTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/details-tests")
public class DetailsTestController {

    @Autowired
    private DetailsTestService detailsTestService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/all")
    public List<DetailsTest> getAllDetailsTests() {
        return detailsTestService.getAllDetailsTests();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public Response addDetailsTest(@RequestBody DetailsTest detailsTest) {
        System.out.println("DetailsTest Name: " + detailsTest.getName());
        System.out.println("DetailsTest Dose: " + detailsTest.getDose());
        System.out.println("DetailsTest Route: " + detailsTest.getRoute());

        if (detailsTestService.addOrUpdateDetailsTest(detailsTest)) {
            return new Response(200, "Added Successfully", "DetailsTest " + detailsTest.getName() + " has been successfully added to the database.");
        } else {
            return new Response(404, "Failed to Add", "DetailsTest " + detailsTest.getName() + " already exists or related records do not exist.");
        }
    }

//    @CrossOrigin(origins = "http://localhost:3000")
//    @DeleteMapping("/delete/{id}/{deletedBy}")
//    public Response deleteDetailsTest(@PathVariable Long id, @PathVariable String deletedBy) {
//        // Check if the record exists
//        if (!detailsTestService.checkIfDetailsTestExists(id)) {
//            return new Response(404, "Not Found", "DetailsTest with ID " + id + " does not exist or has been deleted.");
//        }
//
//        // Execute the delete operation
//        if (detailsTestService.deleteDetailsTest(id, deletedBy)) { // Pass deletedBy directly
//            return new Response(200, "Deleted Successfully", "DetailsTest with ID " + id + " has been deleted by " + deletedBy);
//        } else {
//            return new Response(500, "Failed to Delete", "Failed to delete DetailsTest with ID " + id);
//        }
//    }
//
//    @CrossOrigin(origins = "http://localhost:3000")
//    @PostMapping("/update/{id}")
//    public Response updateDetailsTest(@PathVariable Long id, @RequestBody DetailsTest detailsTest) {
//        // Check if the record exists and is not deleted
//        if (!detailsTestService.checkIfDetailsTestExists(id)) {
//            return new Response(404, "Not Found", "DetailsTest with ID " + id + " does not exist or has been deleted.");
//        }
//
//        // Execute the update operation
//        if (detailsTestService.updateDetailsTest(id, detailsTest)) {
//            return new Response(200, "Updated Successfully", "DetailsTest with ID " + id + " has been successfully updated.");
//        } else {
//            return new Response(500, "Failed to Update", "Failed to update DetailsTest with ID " + id);
//        }
//    }
}
