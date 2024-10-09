package com.example.patientproject.service;

import com.example.patientproject.models.Notes;
import com.example.patientproject.models.PhysicalNotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhysicalNotesService {

    @Autowired
    private DataSource dataSource;

    public boolean addOrUpdatePhysicalNotes(PhysicalNotes physicalNote) {
        String checkQuery = "SELECT COUNT(*) FROM physicalnotes WHERE statusid = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement checkPst = conn.prepareStatement(checkQuery)) {

            checkPst.setInt(1, physicalNote.getStatusId());

            try (ResultSet rs = checkPst.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return updateNotesByStatusId(physicalNote);  // Update if exists
                } else {
                    return addPhysicalNotes(physicalNote);  // Insert if not exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


        public boolean addPhysicalNotes(PhysicalNotes physicalNote) {
            if (physicalNote.isCheckValue()) {
                String insertQuery = "INSERT INTO physicalnotes (visitid, examinationid, statusid, checkvalue, notes) VALUES (?, ?, ?, ?, ?)";

                try (Connection conn = dataSource.getConnection();
                     PreparedStatement pst = conn.prepareStatement(insertQuery)) {

                    // Set the parameters in the prepared statement
                    pst.setInt(1, physicalNote.getVisitId());
                    pst.setInt(2, physicalNote.getExaminationId());
                    pst.setInt(3, physicalNote.getStatusId());
                    pst.setBoolean(4, physicalNote.isCheckValue());
                    pst.setString(5, physicalNote.getNotes());

                    // Execute the update and check if it affected rows
                    int rowsAffected = pst.executeUpdate();
                    return rowsAffected > 0;

                } catch (SQLException e) {
                    // Log the error or rethrow a custom exception
                    e.printStackTrace();
                    return false;
                }
            }
            return false;
        }

    public boolean updateNotesByStatusId(PhysicalNotes physicalNote) {
        String updateQuery = "UPDATE physicalnotes SET visitid = ?, examinationid = ?, checkvalue = ?, notes = ? WHERE statusid = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(updateQuery)) {

            pst.setInt(1, physicalNote.getVisitId());
            pst.setInt(2, physicalNote.getExaminationId());
            pst.setBoolean(3, physicalNote.isCheckValue());
            pst.setString(4, physicalNote.getNotes());
            pst.setInt(5, physicalNote.getStatusId());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PhysicalNotes> getAllPhysicalNotes() {
        String selectQuery = "SELECT id, visitid, examinationid, statusid, checkvalue, notes FROM physicalnotes";
        List<PhysicalNotes> notesList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(selectQuery);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                PhysicalNotes note = new PhysicalNotes();
                note.setId(rs.getInt("id"));
                note.setVisitId(rs.getInt("visitid"));
                note.setExaminationId(rs.getInt("examinationid"));
                note.setStatusId(rs.getInt("statusid"));
                note.setCheckValue(rs.getBoolean("checkvalue"));
                note.setNotes(rs.getString("notes"));

                notesList.add(note);  // إضافة الملاحظة إلى القائمة
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notesList;  // إرجاع القائمة التي تحتوي على جميع الملاحظات
    }


}

    // Add a new Physical Note
//    public boolean addPhysicalNotes(PhysicalNotes physicalNote) {
//        String insertQuery = "INSERT INTO PhysicalNotes (examinationId, statusId, visitId, notes, checkValue) VALUES (?, ?, ?, ?, ?)";
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement pst = conn.prepareStatement(insertQuery)) {
//
//            pst.setInt(1, physicalNote.getExaminationId());
//            pst.setInt(2, physicalNote.getStatusId());
//            pst.setInt(3, physicalNote.getVisitId());
//            pst.setString(4, physicalNote.getNotes());
//            pst.setBoolean(5, physicalNote.isCheckValue());
//            int rowsAffected = pst.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Get all Physical Notes
//    public List<PhysicalNotes> getPhysicalNotes() {
//        String query = "SELECT * FROM PhysicalNotes";
//        List<PhysicalNotes> physicalNotesList = new ArrayList<>();
//
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement pst = conn.prepareStatement(query);
//             ResultSet rs = pst.executeQuery()) {
//
//            while (rs.next()) {
//                PhysicalNotes physicalNote = new PhysicalNotes();
//                physicalNote.setId(rs.getInt("id"));
//                physicalNote.setExaminationId(rs.getInt("examinationId"));
//                physicalNote.setStatusId(rs.getInt("statusId"));
//                physicalNote.setVisitId(rs.getInt("visitId"));
//                physicalNote.setNotes(rs.getString("notes"));
//                physicalNote.setCheckValue(rs.getBoolean("checkValue"));
//                physicalNotesList.add(physicalNote);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return physicalNotesList;
//    }
//
//    // Get Physical Note by ID
//    public PhysicalNotes getPhysicalNoteById(int id) {
//        String query = "SELECT * FROM PhysicalNotes WHERE id = ?";
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement pst = conn.prepareStatement(query)) {
//
//            pst.setInt(1, id);
//            try (ResultSet rs = pst.executeQuery()) {
//                if (rs.next()) {
//                    PhysicalNotes physicalNote = new PhysicalNotes();
//                    physicalNote.setId(rs.getInt("id"));
//                    physicalNote.setExaminationId(rs.getInt("examinationId"));
//                    physicalNote.setStatusId(rs.getInt("statusId"));
//                    physicalNote.setVisitId(rs.getInt("visitId"));
//                    physicalNote.setNotes(rs.getString("notes"));
//                    physicalNote.setCheckValue(rs.getBoolean("checkValue"));
//                    return physicalNote;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null; // If no Physical Note is found
//    }
//
//    // Delete Physical Note by ID
//    public boolean removePhysicalNote(int id) {
//        String query = "DELETE FROM PhysicalNotes WHERE id = ?";
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement pst = conn.prepareStatement(query)) {
//
//            pst.setInt(1, id);
//            int rowsAffected = pst.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Update Physical Note
//    public boolean updatePhysicalNote(PhysicalNotes updatedPhysicalNote) {
//        PhysicalNotes existingPhysicalNote = getPhysicalNoteById(updatedPhysicalNote.getId());
//
//        if (existingPhysicalNote != null) {
//            String query = "UPDATE PhysicalNotes SET examinationId = ?, statusId = ?, visitId = ?, notes = ?, checkValue = ? WHERE id = ?";
//
//            try (Connection conn = dataSource.getConnection();
//                 PreparedStatement pst = conn.prepareStatement(query)) {
//
//                pst.setInt(1, updatedPhysicalNote.getExaminationId());
//                pst.setInt(2, updatedPhysicalNote.getStatusId());
//                pst.setInt(3, updatedPhysicalNote.getVisitId());
//                pst.setString(4, updatedPhysicalNote.getNotes());
//                pst.setBoolean(5, updatedPhysicalNote.isCheckValue());
//                pst.setInt(6, updatedPhysicalNote.getId());
//
//                int rowsAffected = pst.executeUpdate();
//                return rowsAffected > 0;
//            } catch (SQLException e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//        return false; // If no Physical Note is found to update
//    }
