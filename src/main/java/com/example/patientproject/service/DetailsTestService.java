package com.example.patientproject.service;

import com.example.patientproject.models.DetailsTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DetailsTestService {

    @Autowired
    private DataSource dataSource;

    public boolean addOrUpdateDetailsTest(DetailsTest detailsTest) {
        // Check if the same visitId, testId, and orderId combination already exists in detailsTest table
        Long existingId = getIdByVisitTestOrder(detailsTest.getVisitId(), detailsTest.getTestId(), detailsTest.getOrderId());

        if (existingId != null) {
            System.out.println("A record with the same visitId, testId, and orderId already exists. Updating the existing record.");
            detailsTest.setId(existingId); // Set the existing ID for the update
            return updateDetailsTest(detailsTest); // Update existing record
        }

        // Add new record since combination does not exist
        return addDetailsTest(detailsTest);
    }

    private boolean checkIfDetailsTestCombinationExists(int visitId, long testId, long orderId) {
        String query = "SELECT COUNT(*) FROM detailstest WHERE visitId = ? AND testId = ? AND orderId = ? AND deletedBy IS NULL";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, visitId);
            pst.setLong(2, testId);
            pst.setLong(3, orderId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0; // Check if the combination exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Assume not found in case of error
    }

    private boolean addDetailsTest(DetailsTest detailsTest) {
        String query = "INSERT INTO detailstest (visitid, testid, orderid, name, dose, route, frequency, unit, q1anwar, createdat, createdby, updatedat, updatedby, deletedat, deletedby) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, detailsTest.getVisitId());
            pst.setLong(2, detailsTest.getTestId());
            pst.setLong(3, detailsTest.getOrderId());
            pst.setString(4, detailsTest.getName());
            pst.setInt(5, detailsTest.getDose());
            pst.setString(6, detailsTest.getRoute());
            pst.setString(7, detailsTest.getFrequency());
            pst.setString(8, detailsTest.getUnit());
            pst.setString(9, detailsTest.getQ1anwar()); // تأكد من أن هذه القيمة موجودة في الكائن
            pst.setTimestamp(10, detailsTest.getCreatedAt());
            pst.setString(11, detailsTest.getCreatedBy());
            pst.setTimestamp(12, detailsTest.getUpdatedAt());
            pst.setString(13, detailsTest.getUpdatedBy());
            pst.setTimestamp(14, detailsTest.getDeletedAt()); // أو قم بتعيين القيمة إذا كان لديك قيمة لـ deletedAt
            pst.setString(15, detailsTest.getDeletedBy()); // أو قم بتعيين القيمة إذا كان لديك قيمة لـ deletedBy

            return pst.executeUpdate() > 0; // Return true if added successfully
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<DetailsTest> getAllDetailsTests() {
        List<DetailsTest> detailsTests = new ArrayList<>();
        String query = "SELECT * FROM detailstest WHERE deletedBy IS NULL"; // Fetch only non-deleted records

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                DetailsTest detailsTest = new DetailsTest();
                detailsTest.setId(rs.getLong("id"));
                detailsTest.setVisitId(rs.getInt("visitId"));
                detailsTest.setTestId(rs.getLong("testId"));
                detailsTest.setOrderId(rs.getLong("orderId"));
                detailsTest.setName(rs.getString("name"));
                detailsTest.setDose(rs.getInt("dose"));
                detailsTest.setRoute(rs.getString("route"));
                detailsTest.setFrequency(rs.getString("frequency"));
                detailsTest.setUnit(rs.getString("unit"));
                detailsTest.setQ1anwar(rs.getString("q1anwar"));
                detailsTest.setCreatedAt(rs.getTimestamp("createdAt"));
                detailsTest.setCreatedBy(rs.getString("createdBy"));
                detailsTest.setUpdatedAt(rs.getTimestamp("updatedAt"));
                detailsTest.setUpdatedBy(rs.getString("updatedBy"));
                detailsTest.setDeletedAt(rs.getTimestamp("deletedAt"));
                detailsTest.setDeletedBy(rs.getString("deletedBy"));

                detailsTests.add(detailsTest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detailsTests; // Return the list of details tests
    }

    public Long getIdByVisitTestOrder(int visitId, long testId, long orderId) {
        String query = "SELECT id FROM detailstest WHERE visitid = ? AND testid = ? AND orderid = ? AND deletedby IS NULL";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, visitId);
            pst.setLong(2, testId);
            pst.setLong(3, orderId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id"); // Return the found ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no record found or in case of error
    }

    private boolean updateDetailsTest(DetailsTest detailsTest) {
        String query = "UPDATE detailstest SET visitid = ?, testid = ?, orderid = ?, name = ?, dose = ?, route = ?, frequency = ?, unit = ?, q1anwar = ?, updatedat = ?, updatedby = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, detailsTest.getVisitId());
            pst.setLong(2, detailsTest.getTestId());
            pst.setLong(3, detailsTest.getOrderId());
            pst.setString(4, detailsTest.getName());
            pst.setInt(5, detailsTest.getDose());
            pst.setString(6, detailsTest.getRoute());
            pst.setString(7, detailsTest.getFrequency());
            pst.setString(8, detailsTest.getUnit());
            pst.setString(9, detailsTest.getQ1anwar());
            pst.setTimestamp(10, new Timestamp(System.currentTimeMillis())); // وقت التحديث الحالي
            pst.setString(11, detailsTest.getUpdatedBy());
            pst.setLong(12, detailsTest.getId()); // معرف السجل للتحديث

            return pst.executeUpdate() > 0; // Return true if updated successfully
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
//package com.example.patientproject.service;
//
//import com.example.patientproject.models.DetailsTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class DetailsTestService {
//
//    @Autowired
//    private DataSource dataSource;
//
//    public boolean addOrUpdateDetailsTest(DetailsTest detailsTest) {
//        // Check if the same visitId, testId, and orderId combination already exists in detailsTest table
//        if (checkIfDetailsTestCombinationExists(detailsTest.getVisitId(), detailsTest.getTestId(), detailsTest.getOrderId())) {
//            System.out.println("A record with the same visitId, testId, and orderId already exists. No new record added.");
//            return false; // Cannot add if the same combination exists
//        }
//
//        // Add new record since combination does not exist
//        return addDetailsTest(detailsTest);
//    }
//
//    private boolean checkIfDetailsTestCombinationExists(int visitId, long testId, long orderId) {
//        String query = "SELECT COUNT(*) FROM detailsTest WHERE visitId = ? AND testId = ? AND orderId = ? AND deletedBy IS NULL";
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement pst = conn.prepareStatement(query)) {
//            pst.setInt(1, visitId);
//            pst.setLong(2, testId);
//            pst.setLong(3, orderId);
//            try (ResultSet rs = pst.executeQuery()) {
//                return rs.next() && rs.getInt(1) > 0; // Check if the combination exists
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false; // Assume not found in case of error
//    }
//
//    private boolean addDetailsTest(DetailsTest detailsTest) {
//        String query = "INSERT INTO detailsTest (visitId, testId, orderId, name, dose, route, frequency, unit, Q1anwar, createdAt, createdBy, updatedAt, updatedBy, deletedAt, deletedBy) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement pst = conn.prepareStatement(query)) {
//            pst.setInt(1, detailsTest.getVisitId());
//            pst.setLong(2, detailsTest.getTestId());
//            pst.setLong(3, detailsTest.getOrderId());
//            pst.setString(4, detailsTest.getName());
//            pst.setInt(5, detailsTest.getDose());
//            pst.setString(6, detailsTest.getRoute());
//            pst.setString(7, detailsTest.getFrequency());
//            pst.setString(8, detailsTest.getUnit());
//            pst.setString(9, detailsTest.getQ1anwar()); // تأكد من أن هذه القيمة موجودة في الكائن
//            pst.setTimestamp(10, detailsTest.getCreatedAt());
//            pst.setString(11, detailsTest.getCreatedBy());
//            pst.setTimestamp(12, detailsTest.getUpdatedAt());
//            pst.setString(13, detailsTest.getUpdatedBy());
//            pst.setTimestamp(14,  detailsTest.getDeletedAt()); // أو قم بتعيين القيمة إذا كان لديك قيمة لـ deletedAt
//            pst.setString(15, detailsTest.getDeletedBy()); // أو قم بتعيين القيمة إذا كان لديك قيمة لـ deletedBy
//
//            return pst.executeUpdate() > 0; // Return true if added successfully
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//
//    public List<DetailsTest> getAllDetailsTests() {
//        List<DetailsTest> detailsTests = new ArrayList<>();
//        String query = "SELECT * FROM detailsTest WHERE deletedBy IS NULL"; // Fetch only non-deleted records
//
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement pst = conn.prepareStatement(query);
//             ResultSet rs = pst.executeQuery()) {
//
//            while (rs.next()) {
//                DetailsTest detailsTest = new DetailsTest();
//                detailsTest.setId(rs.getLong("id"));
//                detailsTest.setVisitId(rs.getInt("visitId"));
//                detailsTest.setTestId(rs.getLong("testId"));
//                detailsTest.setOrderId(rs.getLong("orderId"));
//                detailsTest.setName(rs.getString("name"));
//                detailsTest.setDose(rs.getInt("dose"));
//                detailsTest.setRoute(rs.getString("route"));
//                detailsTest.setFrequency(rs.getString("frequency"));
//                detailsTest.setUnit(rs.getString("unit"));
//                detailsTest.setQ1anwar(rs.getString("Q1anwar"));
//                detailsTest.setCreatedAt(rs.getTimestamp("createdAt"));
//                detailsTest.setCreatedBy(rs.getString("createdBy"));
//                detailsTest.setUpdatedAt(rs.getTimestamp("updatedAt"));
//                detailsTest.setUpdatedBy(rs.getString("updatedBy"));
//                detailsTest.setDeletedAt(rs.getTimestamp("deletedAt"));
//                detailsTest.setDeletedBy(rs.getString("deletedBy"));
//
//                detailsTests.add(detailsTest);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return detailsTests; // Return the list of details tests
//    }
//}
