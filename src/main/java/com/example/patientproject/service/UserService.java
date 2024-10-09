package com.example.patientproject.service;
import com.example.patientproject.models.Doctor;
import com.example.patientproject.models.Users;
import org.springframework.stereotype.Service;
import com.example.patientproject.models.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private List<Users> users = new ArrayList<>();

    public List<Users> getUsers() {
        return users;
    }

    public UserService() {
        users.add(new Users("Doctor 1", "doctor1@gmail.com", "password1", "password1"));
        users.add(new Users("Doctor 2", "doctor2@gmail.com", "password2", "password2"));
        users.add(new Users("Doctor 3", "doctor3@gmail.com", "password3", "password3"));
        users.add(new Users("Doctor 4", "doctor4@gmail.com", "password4", "password4"));
        users.add(new Users("Doctor 5", "doctor5@gmail.com", "password5", "password5"));
    }
    public Users getUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
    public boolean addUser(Users user) {
        // Check if the email already exists and if the password matches the confirmPassword
        if (getUserByEmail(user.getEmail()) == null && user.getPassword().equals(user.getConfirmPassword())) {
            users.add(user);
            return true;
        } else {
            return false;
        }
    }
    public String printUsers() {
        StringBuilder output = new StringBuilder("[\n");
        for (Users user : users) {
            output.append("{")
                    .append("Name: ").append(user.getName()).append(", ")
                    .append("Email: ").append(user.getEmail()).append(", ")
                    .append("Password: ").append(user.getPassword()).append(", ")
                    .append("Confirm Password: ").append(user.getConfirmPassword())
                    .append("}\n");
        }
        output.append("]");
        return output.toString();
    }

}
