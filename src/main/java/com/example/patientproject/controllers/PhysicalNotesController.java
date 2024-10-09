package com.example.patientproject.controllers;

import com.example.patientproject.Response;
import com.example.patientproject.models.Notes;
import com.example.patientproject.models.PhysicalNotes;
import com.example.patientproject.service.PhysicalNotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/physicalnotes")
public class PhysicalNotesController {

    @Autowired
    private PhysicalNotesService physicalNotesService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public Response addPhysicalNotes(@RequestBody List<PhysicalNotes> notesList) {
        boolean allAdded = true;  // للتحقق من إذا تمت إضافة جميع الملاحظات
        for (PhysicalNotes note : notesList) {
            boolean added = physicalNotesService.addOrUpdatePhysicalNotes(note);  // إضافة أو تحديث الملاحظة
            if (!added) {
                allAdded = false;  // إذا فشل أي عنصر في الإضافة
            }
        }

        // إرجاع استجابة بناءً على النتيجة
        if (allAdded) {
            return new Response(200, "All notes added successfully", "All notes have been added successfully.");
        } else {
            return new Response(207, "Some notes failed to add", "Some notes failed to add.");
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/all")
    public  List<PhysicalNotes> getAllNotes() {
        List<PhysicalNotes> notesList =physicalNotesService.getAllPhysicalNotes();  // استرجاع جميع الملاحظات


        return  notesList;  // إرجاع 200 مع البيانات

    }
}

//    @CrossOrigin(origins = "http://localhost:3000")
//    @GetMapping("/get")
//    public List<PhysicalNotes> getPhysicalNotes() {
//        return physicalNotesService.getPhysicalNotes();
//    }
//
//    @CrossOrigin(origins = "http://localhost:3000")
//    @GetMapping("/get/{id}")
//    public PhysicalNotes getPhysicalNoteById(@PathVariable Integer id) {
//        return physicalNotesService.getPhysicalNoteById(id);
//    }
//
//    @CrossOrigin(origins = "http://localhost:3000")
//    @DeleteMapping("/remove/{id}")
//    public Response removePhysicalNote(@PathVariable int id) {
//        if (physicalNotesService.removePhysicalNote(id)) {
//            return new Response(200, "Removed successfully", "The physical note with ID " + id + " has been successfully removed.");
//        } else {
//            return new Response(404, "Failed to remove", "No physical note found with ID " + id + ".");
//        }
//    }
//
//    @CrossOrigin(origins = "http://localhost:3000")
//    @PutMapping("/update")
//    public Response updatePhysicalNote(@RequestBody PhysicalNotes physicalNotes) {
//        if (physicalNotesService.updatePhysicalNote(physicalNotes)) {
//            return new Response(200, "Updated successfully", "The physical note with ID " + physicalNotes.getId() + " has been successfully updated.");
//        } else {
//            return new Response(404, "Failed to update", "No physical note found with ID " + physicalNotes.getId() + ".");
//        }
//    }
