package com.example.patientproject.service;

import com.example.patientproject.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private DataSource dataSource;

    // Add a new patient to the database
    public boolean addPatient(Patient patient) {
        if (checkIfPatientExistsByEmail(patient.getEmail())) {
            System.out.println("A patient with this email already exists.");
            return false;
        }

        String insertPatientQuery = "INSERT INTO patients (fullName, email, phoneNumber, age, address, notes, fileNumber, medicalHistory) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement pst = conn.prepareStatement(insertPatientQuery);
            pst.setString(1, patient.getFullName());
            pst.setString(2, patient.getEmail());
            pst.setString(3, patient.getPhoneNumber());
            pst.setInt(4, patient.getAge());
            pst.setString(5, patient.getAddress());
            pst.setString(6, patient.getNotes());
            pst.setString(7, patient.getFileNumber());
            pst.setString(8, patient.getMedicalHistory());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if a patient already exists using email
    private boolean checkIfPatientExistsByEmail(String email) {
        String query = "SELECT COUNT(*) FROM patients  WHERE email = ?";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all patients from the database
    public List<Patient> getPatients() {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM patients ";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Patient patient = new Patient();
                patient.setPatientID(rs.getInt("patientID"));
                patient.setFullName(rs.getString("fullName"));
                patient.setEmail(rs.getString("email"));
                patient.setPhoneNumber(rs.getString("phoneNumber"));
                patient.setAge(rs.getInt("age"));
                patient.setAddress(rs.getString("address"));
                patient.setNotes(rs.getString("notes"));
                patient.setFileNumber(rs.getString("fileNumber"));
                patient.setMedicalHistory(rs.getString("medicalHistory"));
                patients.add(patient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }

    // Update patient details in the database
    public boolean updatePatient(Patient updatedPatient) {
        String query = "UPDATE patients  SET fullName = ?, email = ?, phoneNumber = ?, age = ?, address = ?, notes = ?, fileNumber = ?, medicalHistory = ? WHERE patientID = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, updatedPatient.getFullName());
            pst.setString(2, updatedPatient.getEmail());
            pst.setString(3, updatedPatient.getPhoneNumber());
            pst.setInt(4, updatedPatient.getAge());
            pst.setString(5, updatedPatient.getAddress());
            pst.setString(6, updatedPatient.getNotes());
            pst.setString(7, updatedPatient.getFileNumber());
            pst.setString(8, updatedPatient.getMedicalHistory());
            pst.setInt(9, updatedPatient.getPatientID());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Remove a patient by ID
    public boolean removePatient(int patientId) {
        String query = "DELETE FROM patients  WHERE patientID = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, patientId);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get a patient by ID
    public Patient getPatientById(int patientId) {
        String query = "SELECT * FROM patients  WHERE patientID = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, patientId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Patient patient = new Patient();
                    patient.setPatientID(rs.getInt("patientID"));
                    patient.setFullName(rs.getString("fullName"));
                    patient.setEmail(rs.getString("email"));
                    patient.setPhoneNumber(rs.getString("phoneNumber"));
                    patient.setAge(rs.getInt("age"));
                    patient.setAddress(rs.getString("address"));
                    patient.setNotes(rs.getString("notes"));
                    patient.setFileNumber(rs.getString("fileNumber"));
                    patient.setMedicalHistory(rs.getString("medicalHistory"));
                    return patient;
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}













//package com.example.patientproject.service;
//
//import com.example.patientproject.models.Patient;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class PatientService {
//    private List<Patient> patients = new ArrayList<>();
//
//    public List<Patient> getPatients() {
//        return patients;
//    }
//
//    public Patient getPatientByEmail(String email) {
//        return patients.stream()
//                .filter(patient -> patient.getEmail().equals(email))
//                .findFirst()
//                .orElse(null);
//    }
//
//    public Patient getPatientById(String patientId) {
//        return patients.stream()
//                .filter(patient -> patient.getPatientID().equals(patientId))
//                .findFirst()
//                .orElse(null);
//    }
//
//    public boolean addPatient(Patient patient) {
//        if (getPatientByEmail(patient.getEmail()) == null) {
//            // Generate a random ID
//            String randomId = UUID.randomUUID().toString();
//            patient.setPatientID(randomId);
//            patients.add(patient);
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public boolean removePatient(String patientId) {
//        Patient patient = getPatientById(patientId);
//        if (patient != null) {
//            patients.remove(patient);
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public boolean updatePatient(Patient updatedPatient) {
//        String patientId = updatedPatient.getPatientID();
//
//        if (patientId == null) {
//            return false; // No ID provided, update cannot proceed
//        }
//
//        Patient existingPatient = getPatientById(patientId);
//        if (existingPatient != null) {
//            // Update fields as necessary
//            existingPatient.setFullName(updatedPatient.getFullName());
//            existingPatient.setEmail(updatedPatient.getEmail());
//            existingPatient.setPhoneNumber(updatedPatient.getPhoneNumber());
//            existingPatient.setAddress(updatedPatient.getAddress());
//            existingPatient.setNotes(updatedPatient.getNotes());
//            existingPatient.setFileNumber(updatedPatient.getFileNumber());
//            existingPatient.setAge(updatedPatient.getAge());
//            existingPatient.setMedicalHistory(updatedPatient.getMedicalHistory());
//            return true;
//        }
//        return false;
//    }

//}
