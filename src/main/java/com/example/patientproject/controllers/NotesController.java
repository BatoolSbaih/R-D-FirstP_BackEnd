package com.example.patientproject.controllers;

import com.example.patientproject.Response;
import com.example.patientproject.models.Notes;
import com.example.patientproject.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    // دالة لاستقبال الطلبات POST وإضافة أو تحديث الملاحظات
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public Response addNotes(@RequestBody List<Notes> notesList) {
        boolean allAdded = true;  // للتحقق من إذا تمت إضافة جميع الملاحظات
        for (Notes note : notesList) {
            boolean added = notesService.addOrUpdateNotes(note);  // إضافة أو تحديث الملاحظة
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
    public  List<Notes> getAllNotes() {
        List<Notes> notesList = notesService.getAllNotes();  // استرجاع جميع الملاحظات


            return  notesList;  // إرجاع 200 مع البيانات

}
}
//    // دالة أخرى لاستقبال الطلبات GET للحصول على الملاحظات
//    @GetMapping("/all")
//    public List<Notes> getAllNotes() {
//        // يمكن تنفيذ الدالة للحصول على جميع الملاحظات
//        return notesService.getAllNotes();
//    }
//}
