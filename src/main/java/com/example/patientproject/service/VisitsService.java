package com.example.patientproject.service;

import com.example.patientproject.models.Visits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class VisitsService {

    @Autowired
    private DataSource dataSource;

    // Add a new visit to the database
    public boolean addVisit(Visits visit) {
        String insertVisitQuery = "INSERT INTO visits (patientid, filenumber, doctorid, clinicid, date, time) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement pst = conn.prepareStatement(insertVisitQuery);
            pst.setInt(1, visit.getPatientID());
            pst.setString(2, visit.getFileNumber());
            pst.setInt(3, visit.getDoctorID());
            pst.setInt(4, visit.getClinicID());
            pst.setDate(5, Date.valueOf(visit.getDate()));
            pst.setTime(6, Time.valueOf(visit.getTime()));

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all visits from the database
    public List<Visits> getVisits() {
        List<Visits> visits = new ArrayList<>();
        String query = "SELECT * FROM visits";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Visits visit = new Visits();
                visit.setVisitID(rs.getInt("visitid"));
                visit.setPatientID(rs.getInt("patientid"));
                visit.setFileNumber(rs.getString("filenumber"));
                visit.setDoctorID(rs.getInt("doctorid"));
                visit.setClinicID(rs.getInt("clinicid"));
                visit.setDate(rs.getDate("date").toLocalDate());
                visit.setTime(rs.getTime("time").toLocalTime());
                visits.add(visit);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return visits;
    }

    // Get a visit by ID
    public Visits getVisitByID(Integer visitID) {
        String query = "SELECT * FROM visits WHERE visitID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, visitID);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Visits visit = new Visits();
                    visit.setVisitID(rs.getInt("visitid"));
                    visit.setPatientID(rs.getInt("patientid"));
                    visit.setFileNumber(rs.getString("fileNumber"));
                    visit.setDoctorID(rs.getInt("doctorid"));
                    visit.setClinicID(rs.getInt("clinicid"));
                    visit.setDate(rs.getDate("date").toLocalDate());
                    visit.setTime(rs.getTime("time").toLocalTime());
                    return visit;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update visit details in the database
    public boolean updateVisit(Visits updatedVisit) {
        String query = "UPDATE visits SET patientName = ?, fileNumber = ?, doctorName = ?, clinicName = ?, date = ?, time = ? WHERE visitID = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, updatedVisit.getPatientID());
            pst.setString(2, updatedVisit.getFileNumber());
            pst.setInt(3, updatedVisit.getDoctorID());
            pst.setInt(4, updatedVisit.getClinicID());
            pst.setDate(5, Date.valueOf(updatedVisit.getDate()));
            pst.setTime(6, Time.valueOf(updatedVisit.getTime()));
            pst.setInt(7, updatedVisit.getVisitID());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Remove a visit by ID
    public boolean removeVisit(Integer visitID) {
        String query = "DELETE FROM visits WHERE visitid = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, visitID);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Print visits (for debugging purposes)
    public String printVisits() {
        StringBuilder output = new StringBuilder("[\n");
        for (Visits visit : getVisits()) {
            output.append("{")
                    .append(visit.getVisitID()).append(", ")
                    .append(visit.getPatientID()).append(", ")
                    .append(visit.getDoctorID()).append(", ")
                    .append(visit.getDate()).append(", ")
                    .append(visit.getTime()).append("}\n");
        }
        output.append("]");
        return output.toString();
    }
}






//package com.example.patientproject.service;
//import com.example.patientproject.models.Doctor;
//import com.example.patientproject.models.Visits;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class VisitsService {
//    private List<Visits> visits = new ArrayList<>();
//
//    public List<Visits> getVisits() {
//        return visits;
//    }
//
//
//    public Visits getVisitByID(Integer visitID) {
//        // Assuming visits is a collection of Visit objects
//       return visits.stream()
//               .filter(visit -> visit.getVisitID().equals(visitID))
//               .findFirst()
//               .orElse(null);
//
//    }
//
//        public boolean removeVisit(Integer visitID) {
//        Visits visit = getVisitByID( visitID) ;
//
//                if (visit != null) {
//                    visits.remove(visit);
//            return true;
//        } else {
//            return false;
//        }
//    }
//    public boolean addVisit(Visits visit ) {
//        if (getVisitByID(visit.getVisitID()) == null) {
//
//            visits.add(visit);
//            return true;
//        } else {
//            return false;
//        }
//    }
//        public boolean updateVisit(Visits updatedVisit) {
//        Visits existingVisit = getVisitByID(updatedVisit.getVisitID());
//        if (existingVisit != null) {
//            // Update fields as necessary
//            existingVisit.setDate(updatedVisit.getDate());
//            existingVisit.setClinicName(updatedVisit.getClinicName());
//            existingVisit.setTime(updatedVisit.getTime());
//            existingVisit.setDoctorName(updatedVisit.getDoctorName());
//            existingVisit.setPatientName(updatedVisit.getPatientName());
//            existingVisit.setFileNumber(updatedVisit.getFileNumber());
//
//            return true;
//        }
//        return false;
//    }
//
//
//        public String printVisits() {
//            StringBuilder output = new StringBuilder("[\n");
//            for (Visits visit : visits) {
//                output.append("{")
//                        .append(visit.getVisitID()).append(", ")
//                        .append(visit.getPatientName()).append(", ")
//                        .append(visit.getDoctorName()).append(", ")
//                        .append(visit.getDate()).append(", ")
//                        .append(visit.getTime()).append("}\n");
//            }
//            output.append("]");
//            return output.toString();
//        }
//    }



