package com.example.patientproject.controllers;

import com.example.patientproject.Response;
import com.example.patientproject.models.SetUp;
import com.example.patientproject.service.SetUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setups")
public class SetUpController {

    @Autowired
    private SetUpService setUpService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/by-type/{type}")
    public List<SetUp> getSetUpsByType(@PathVariable String type) {
        return setUpService.getSetUpsByType(type);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/by-type-notdeleted/{type}")
    public List<SetUp> getSetUpsByTypeANDNotDeleted(@PathVariable String type) {
        return setUpService.getSetUpsByTypeANDNullDeleted(type);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/by-id/{id}")
    public SetUp getSetUpById(@PathVariable Long id) {
        SetUp setup = setUpService.getSetUpById(id);
        return setup;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/all")
    public List<SetUp> getAllSetUps() {
        return setUpService.getAllSetUps();
    }

    // دالة إضافة SetUp مع التحقق من الوجود المسبق
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public Response addSetUp(@RequestBody SetUp setUp) {

        System.out.println("SetUp Type: " + setUp.getType());
        System.out.println("SetUp Price: " + setUp.getPrice());
        System.out.println("SetUp Name: " + setUp.getName());
        System.out.println("SetUp Code: " + setUp.getCode());

        if (setUpService.addOrUpdateSetUp(setUp)) {
            return new Response(200, "added successfully", "This SetUp " + setUp.getName() + " has been successfully added to the database");
        } else {
            return new Response(404, "failed to add", "This SetUp " + setUp.getName() + " already exists");
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/delete/{id}/{deletedBy}")
    public Response deleteSetUp(@PathVariable Long id, @PathVariable String deletedBy) {
        // Check if the record exists
        if (!setUpService.checkIfSetUpExistsById(id)) {
            return new Response(404, "Not Found", "SetUp with ID " + id + " does not exist");
        }

        // Execute the delete operation (update the record)
        if (setUpService.deleteSetUp(id, deletedBy)) { // Pass deletedBy directly
            return new Response(200, "Deleted Successfully", "SetUp with ID " + id + " has been deleted by " + deletedBy);
        } else {
            return new Response(500, "Failed to Delete", "Failed to delete SetUp with ID " + id);
        }




    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/restore/{id}") // استخدام PUT للتحديث بدلاً من DELETE
    public Response restoreSetUp(@PathVariable Long id) {
        // Check if the record exists
        if (!setUpService.checkIfSetUpExistsById(id)) {
            return new Response(404, "Not Found", "SetUp with ID " + id + " does not exist");
        }

        // Proceed to restore the record
        boolean isRestored = setUpService.restoreSetUp(id);
        if (isRestored) {
            return new Response(200, "Success", "SetUp with ID " + id + " has been restored");
        } else {
            return new Response(500, "Error", "Failed to restore SetUp with ID " + id);
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/update/{id}/{updatedBy}")
    public Response updateSetUp(@PathVariable Long id, @RequestBody SetUp setUp, @PathVariable String updatedBy) {
        // تحقق مما إذا كان السجل موجودًا وقابل للتعديل (أي لم يتم حذفه)
        // التحقق مما إذا كان السجل موجودًا وقابل للتعديل (أي لم يتم حذفه)
        if (!setUpService.checkIfSetUpExistsAndNotDeleted(id)) {
            return new Response(404, "Not Found", "SetUp with ID " + id + " does not exist or has been deleted");
        }

        // تنفيذ عملية التحديث
        if (setUpService.updateSetUp(id, setUp, updatedBy)) {
            return new Response(200, "Updated Successfully", "SetUp with ID " + id + " has been successfully updated.");
        } else {
            return new Response(500, "Failed to Update", "Failed to update SetUp with ID " + id);
        }
    }




    // حذف SetUp باستخدام ID
//    @CrossOrigin(origins = "http://localhost:3000")
//    @DeleteMapping("/remove/{id}")
//    public Response removeSetUp(@PathVariable Long id) {
//        if (setUpService.removeSetUp(id)) {
//            return new Response(200, "Removed successfully", "The SetUp with ID " + id + " has been successfully removed from the database.");
//        } else {
//            return new Response(404, "Failed to remove", "No SetUp found with ID " + id + ".");
//        }
//    }
//
//    // تحديث SetUp
//    @CrossOrigin(origins = "http://localhost:3000")
//    @PutMapping("/update")
//    public Response updateSetUp(@RequestBody SetUp setUp) {
//        System.out.println("Received SetUp ID: " + setUp.getId());
//        if (setUpService.updateSetUp(setUp)) {
//            return new Response(200, "Updated successfully", "The SetUp with ID " + setUp.getId() + " has been successfully updated.");
//        }
//        return new Response(404, "Failed to update", "No SetUp found with ID " + setUp.getId());
//    }
}
