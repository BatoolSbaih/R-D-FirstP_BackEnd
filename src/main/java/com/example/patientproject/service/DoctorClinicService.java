package com.example.patientproject.service;

import com.example.patientproject.models.DoctorClinic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class DoctorClinicService {
    @Autowired
    private DataSource dataSource;


    public DoctorClinic getDoctorClinicById(int id)  {
        String query = "SELECT * FROM doctor_clinic WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                DoctorClinic doctorClinic = new DoctorClinic();
                doctorClinic.setId(rs.getInt("id"));
                doctorClinic.setClinicId(rs.getInt("clinic_id"));
                doctorClinic.setDoctorID(rs.getInt("doctor_id"));
                doctorClinic.setCreatedAt(rs.getTimestamp("created_at"));
                doctorClinic.setCreatedBy(rs.getString("created_by"));
                doctorClinic.setUpdatedAt(rs.getTimestamp("updated_at"));
                 doctorClinic.setUpdatedBy(rs.getString("updated_by"));
                return doctorClinic;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    private boolean isDoctorClinicExists(int id) {
        String query = "SELECT COUNT(*) FROM doctor_clinic WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // إرجاع true إذا كان السجل موجودًا
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false; // إرجاع false إذا حدث خطأ أو لم يكن موجودًا
    }
    public boolean addOrUpdateDoctorClinic(DoctorClinic doctorClinic) {
        try {
            // تحقق مما إذا كان ID السجل موجودًا
            if (doctorClinic.getId() != null && isDoctorClinicExists(doctorClinic.getId())) {
                // إذا كان موجودًا، قم بتحديثه
                return updateDoctorClinic(doctorClinic);
            }

            // إذا لم يكن موجودًا، قم بإضافته
            doctorClinic.setCreatedAt(new Timestamp(System.currentTimeMillis())); // تعيين وقت الإنشاء
            doctorClinic.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // تعيين وقت التحديث
            return addDoctorClinic(doctorClinic);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getIdByClinicDoctor(int clinicId, int doctorId){
        String query = "SELECT id FROM doctor_clinic WHERE clinic_id = ? AND doctor_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, clinicId);
            pst.setInt(2, doctorId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    private boolean addDoctorClinic(DoctorClinic doctorClinic) throws SQLException {
        Integer existingId = getIdByClinicDoctor(doctorClinic.getClinicId(), doctorClinic.getDoctorID());
         System.out.println( "mmmmmmmmmmmmmmmmmmmm" + existingId);
        // إذا كان existingId نل، نتابع في عملية الإدراج
        if (existingId == null) {
            String query = "INSERT INTO doctor_clinic (clinic_id, doctor_id, created_at, created_by, updated_at, updated_by, deleted_at, deleted_by) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setInt(1, doctorClinic.getClinicId());
                pst.setInt(2, doctorClinic.getDoctorID());
                pst.setTimestamp(3, doctorClinic.getCreatedAt());
                pst.setString(4, doctorClinic.getCreatedBy());
                pst.setTimestamp(5, doctorClinic.getUpdatedAt());
                pst.setString(6, doctorClinic.getUpdatedBy());
                pst.setTimestamp(7, doctorClinic.getDeletedAt());
                pst.setString(8, doctorClinic.getDeletedBy());

                return pst.executeUpdate() > 0;
            }
        }
        // إذا كان existingId ليس نل، لن يتم إدخال أي سجل
        return false;
    }


    private boolean updateDoctorClinic(DoctorClinic doctorClinic) throws SQLException {

        Integer existingId = getIdByClinicDoctor(doctorClinic.getClinicId(), doctorClinic.getDoctorID());


        if (existingId != null && existingId.equals(doctorClinic.getId())) {
            String query = "UPDATE doctor_clinic SET clinic_id = ?, doctor_id = ?, updated_at = ?, updated_by = ? WHERE id = ?";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setInt(1, doctorClinic.getClinicId());
                pst.setInt(2, doctorClinic.getDoctorID());
                pst.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // وقت التحديث الحالي
                pst.setString(4, doctorClinic.getUpdatedBy());
                pst.setInt(5, doctorClinic.getId());

                return pst.executeUpdate() > 0;
            }
        }


        return false;
    }

}
