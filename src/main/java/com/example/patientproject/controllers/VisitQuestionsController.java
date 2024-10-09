package com.example.patientproject.controllers;

import com.example.patientproject.Response;
import com.example.patientproject.models.VisitQuestions;
import com.example.patientproject.service.VisitQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visit-questions")
public class VisitQuestionsController {

    @Autowired
    private VisitQuestionsService visitQuestionsService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public Response addOrUpdateVisitQuestions(@RequestBody VisitQuestions visitQuestions) {
        if (visitQuestionsService.addOrUpdateVisitQuestions(visitQuestions)) {
            return new Response(200, "Success", "Visit questions have been added/updated successfully.");
        } else {
            return new Response(404, "Failed", "Failed to add/update visit questions.");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get")
    public List<VisitQuestions> getVisitQuestions() {
        return visitQuestionsService.getVisitQuestions();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get/{id}")
    public VisitQuestions getVisitQuestionsById(@PathVariable Integer id) {
        return visitQuestionsService.getVisitQuestionsById(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/remove/{id}")
    public Response removeVisitQuestions(@PathVariable int id) {
        if (visitQuestionsService.removeVisitQuestions(id)) {
            return new Response(200, "Removed successfully", "Visit questions with ID " + id + " have been successfully removed.");
        } else {
            return new Response(404, "Failed to remove", "No visit questions found with ID " + id + ".");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update")
    public Response updateVisitQuestions(@RequestBody VisitQuestions visitQuestions) {
        if (visitQuestionsService.addOrUpdateVisitQuestions(visitQuestions)) {
            return new Response(200, "Updated successfully", "Visit questions with ID " + visitQuestions.getId() + " have been successfully updated.");
        } else {
            return new Response(404, "Failed to update", "No visit questions found with ID " + visitQuestions.getId() + ".");
        }
    }
}
