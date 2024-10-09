package com.example.patientproject.controllers;
import com.example.patientproject.Response;
import com.example.patientproject.models.Users;
import com.example.patientproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public Response AddUser(@RequestBody Users user){
        if(userService.addUser(user)){
            Response response = new Response(200, "Added successfully", "This account " + user.getEmail() + " has been successfully added to the database");
            return Response.ok(response);}
            else return (new Response(404,"failed added  ","this account"+user.getEmail()+"has already exists"));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get")
    public List<Users> GetUsers(){
        return userService.getUsers();

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public Response loginUser(@RequestBody Users user) {
        Users foundUser = userService.getUserByEmail(user.getEmail());
        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
            return Response.successLogin(200,"Login successful", "Welcome back " + foundUser.getEmail() ,foundUser.getName());
        } else {
            return Response.failure(401, "Login failed", "Invalid email or password");
        }
    }

}
