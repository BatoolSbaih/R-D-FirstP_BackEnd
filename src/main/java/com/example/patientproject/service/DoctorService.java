package com.example.patientproject.service;

import com.example.patientproject.models.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DataSource dataSource;

    public Doctor addDoctor(Doctor doctor) {
        if (checkIfDoctorExistsByEmail(doctor.getEmail())) {
            System.out.println("Doctor with email " + doctor.getEmail() + " already exists.");
            return null; // Return null if doctor already exists
        }

        String insertDoctorQuery = "INSERT INTO doctors (fullname, email, phone, address, specialty) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(insertDoctorQuery)) {

            pst.setString(1, doctor.getFullname());
            pst.setString(2, doctor.getEmail());
            pst.setInt(3, doctor.getPhone());
            pst.setString(4, doctor.getAddress());
            pst.setString(5, doctor.getSpecialty());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                // Fetch the inserted doctor using the email
                String selectQuery = "SELECT * FROM doctors WHERE email = ?";
                try (PreparedStatement selectPst = conn.prepareStatement(selectQuery)) {
                    selectPst.setString(1, doctor.getEmail());
                    try (ResultSet rs = selectPst.executeQuery()) {
                        if (rs.next()) {
                            Doctor addedDoctor = new Doctor();
                            addedDoctor.setDoctorID(rs.getInt("doctor_id"));
                            addedDoctor.setFullname(rs.getString("fullname"));
                            addedDoctor.setEmail(rs.getString("email"));
                            addedDoctor.setPhone(rs.getInt("phone"));
                            addedDoctor.setAddress(rs.getString("address"));
                            addedDoctor.setSpecialty(rs.getString("specialty"));
                            return addedDoctor;
                        }
                    }
                }
            } else {
                System.out.println("Failed to add doctor.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


    private boolean checkIfDoctorExistsByEmail(String email) {
        String checkEmailQuery = "SELECT COUNT(*) FROM doctors WHERE email = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(checkEmailQuery)) {

            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Doctor> getDoctors() {
        String query = "SELECT * FROM doctors";
        List<Doctor> doctors = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setDoctorID(rs.getInt("doctor_id"));
                doctor.setFullname(rs.getString("fullname"));
                doctor.setEmail(rs.getString("email"));
                doctor.setPhone(rs.getInt("phone"));
                doctor.setAddress(rs.getString("address"));
                doctor.setSpecialty(rs.getString("specialty"));
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    public Doctor getDoctorById(int doctorId) {
        String query = "SELECT * FROM doctors WHERE doctor_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, doctorId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Doctor doctor = new Doctor();
                    doctor.setDoctorID(rs.getInt("doctor_id"));
                    doctor.setFullname(rs.getString("fullname"));
                    doctor.setEmail(rs.getString("email"));
                    doctor.setPhone(rs.getInt("phone"));
                    doctor.setAddress(rs.getString("address"));
                    doctor.setSpecialty(rs.getString("specialty"));
                    return doctor;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // If no doctor is found
    }

    public boolean removeDoctor(int doctorId) {
        String query = "DELETE FROM doctors WHERE doctor_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, doctorId);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDoctor(Doctor updatedDoctor) {
        Doctor existingDoctor = getDoctorById(updatedDoctor.getDoctorID());

        if (existingDoctor != null) {
            String query = "UPDATE doctors SET fullname = ?, email = ?, phone = ?, address = ?, specialty = ? WHERE doctor_id = ?";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pst = conn.prepareStatement(query)) {

                pst.setString(1, updatedDoctor.getFullname());
                pst.setString(2, updatedDoctor.getEmail());
                pst.setInt(3, updatedDoctor.getPhone());
                pst.setString(4, updatedDoctor.getAddress());
                pst.setString(5, updatedDoctor.getSpecialty());
                pst.setInt(6, updatedDoctor.getDoctorID());

                int rowsAffected = pst.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false; // If doctor to update is not found
    }
}


//    public boolean removeDoctor(String doctorId) {
//        Doctor doctor = getDoctorById(doctorId);
//        if (doctor != null) {
//            doctors.remove(doctor);
//            return true;
//        } else {
//            return false;
//        }
//    }

//    public boolean updateDoctor(Doctor updatedDoctor) {
//        Doctor existingDoctor = getDoctorById(updatedDoctor.getDoctorID());
//        if (existingDoctor != null) {
//            existingDoctor.setFullname(updatedDoctor.getFullname());
//            existingDoctor.setEmail(updatedDoctor.getEmail());
//            existingDoctor.setPhone(updatedDoctor.getPhone());
//            existingDoctor.setAddress(updatedDoctor.getAddress());
//            existingDoctor.setSpecialty(updatedDoctor.getSpecialty());
//            return true;
//        }
//        return false;
//    }

//    public String printDoctors() {
//        StringBuilder output = new StringBuilder("[\n");
//        for (Doctor doctor : doctors) {
//            output.append("{")
//                    .append("Name: ").append(doctor.getFullname()).append(", ")
//                    .append("Email: ").append(doctor.getEmail()).append(", ")
//                    .append("Phone: ").append(doctor.getPhone()).append(", ")
//                    .append("Address: ").append(doctor.getAddress()).append(", ")
//                    .append("Specialty: ").append(doctor.getSpecialty())
//                    .append("}\n");
//        }
//        output.append("]");
//        return output.toString();
//    }
//}

















//package com.example.patientproject.service;
//
//import com.example.patientproject.models.Doctor;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class DoctorService {
//    private List<Doctor> doctors = new ArrayList<>();
//
//    public List<Doctor> getDoctors() {
//        return doctors;
//    }
//
//    public Doctor getDoctorByEmail(String email) {
//        return doctors.stream()
//                .filter(doctor -> doctor.getEmail().equals(email))
//                .findFirst()
//                .orElse(null);
//    }
//
//    public Doctor getDoctorById(String doctorId) {
//        return doctors.stream()
//                .filter(doctor -> doctor.getDoctorID().equals(doctorId))
//                .findFirst()
//                .orElse(null);
//    }
//
//    public boolean addDoctor(Doctor doctor) {
//        if (getDoctorByEmail(doctor.getEmail()) == null) {
//            // Generate a random ID
//            String randomId = UUID.randomUUID().toString();
//            doctor.setDoctorID(randomId);
//            doctors.add(doctor);
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public boolean removeDoctor(String doctorId) {
//        Doctor doctor = getDoctorById(doctorId);
//        if (doctor != null) {
//            doctors.remove(doctor);
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public boolean updateDoctor(Doctor updatedDoctor) {
//        Doctor existingDoctor = getDoctorById(updatedDoctor.getDoctorID());
//        if (existingDoctor != null) {
//            // Update fields as necessary
//            existingDoctor.setFullname(updatedDoctor.getFullname());
//            existingDoctor.setEmail(updatedDoctor.getEmail());
//            existingDoctor.setPhone(updatedDoctor.getPhone());
//            existingDoctor.setAddress(updatedDoctor.getAddress());
//            existingDoctor.setSpecialty(updatedDoctor.getSpecialty());
//            return true;
//        }
//        return false;
//    }
//
//    public String printDoctors() {
//        StringBuilder output = new StringBuilder("[\n");
//        for (Doctor doctor : doctors) {
//            output.append("{")
//                    .append("Name: ").append(doctor.getFullname()).append(", ")
//                    .append("Email: ").append(doctor.getEmail()).append(", ")
//                    .append("Phone: ").append(doctor.getPhone()).append(", ")
//                    .append("Address: ").append(doctor.getAddress()).append(", ")
//                    .append("Specialty: ").append(doctor.getSpecialty())
//                    .append("}\n");
//        }
//        output.append("]");
//        return output.toString();
//    }
//}
