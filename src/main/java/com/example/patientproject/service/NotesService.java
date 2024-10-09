package com.example.patientproject.service;

import com.example.patientproject.models.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotesService {

    @Autowired
    private DataSource dataSource;  // التأكد من إعداد مصدر البيانات

    // دالة للتحقق من إذا كانت الملاحظة موجودة بناءً على organId
    public boolean addOrUpdateNotes(Notes note) {
        String checkQuery = "SELECT COUNT(*) FROM notes WHERE organid = ?";  // استخدم organid هنا

        try (Connection conn = dataSource.getConnection();
             PreparedStatement checkPst = conn.prepareStatement(checkQuery)) {

            checkPst.setInt(1, note.getOrganId());  // تمرير organId للتحقق

            try (ResultSet rs = checkPst.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return updateNotesByOrganId(note);  // إذا كانت الملاحظة موجودة، يتم تحديثها
                } else {
                    return addNotes(note);  // إذا لم تكن موجودة، يتم إضافتها
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // إذا حدث خطأ في التنفيذ
        }
    }

    // دالة لإضافة الملاحظات الجديدة
    public boolean addNotes(Notes note) {
        // تحقق من أن checkValue تساوي true
        if (note.isCheckValue()) { // يتم الإدراج فقط إذا كانت القيمة true
            String insertQuery = "INSERT INTO notes (visitid, systemid, organid, checkvalue, note) VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pst = conn.prepareStatement(insertQuery)) {

                // إعداد القيم للاستعلام
                pst.setInt(1, note.getVisitId());
                pst.setInt(2, note.getSystemId());
                pst.setInt(3, note.getOrganId());
                pst.setBoolean(4, note.isCheckValue());
                pst.setString(5, note.getNotes());

                // تنفيذ الإدراج وإرجاع true إذا تمت العملية بنجاح
                return pst.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false; // إرجاع false في حالة حدوث خطأ
            }
        }

        // إذا كانت checkValue لا تساوي true، لا يتم فعل أي شيء ويتم إرجاع true لعدم وجود خطأ
        return true;
    }


    // دالة لتحديث الملاحظات بناءً على organId
    public boolean updateNotesByOrganId(Notes note) {
        String updateQuery = "UPDATE notes SET visitid = ?, systemid = ?, checkvalue = ?, note = ? WHERE organid = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(updateQuery)) {

            pst.setInt(1, note.getVisitId());
            pst.setInt(2, note.getSystemId());
            pst.setBoolean(3, note.isCheckValue());
            pst.setString(4, note.getNotes());
            pst.setInt(5, note.getOrganId());  // التحديث يتم بناءً على organId

            return pst.executeUpdate() > 0;  // يتحقق من إذا تم التحديث بنجاح
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // دالة لاسترجاع جميع الملاحظات من الجدول
    public List<Notes> getAllNotes() {
        String selectQuery = "SELECT id, visitid, systemid, organid, checkvalue, note FROM notes";
        List<Notes> notesList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(selectQuery);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Notes note = new Notes();
                note.setId(rs.getInt("id"));
                note.setVisitId(rs.getInt("visitid"));
                note.setSystemId(rs.getInt("systemid"));
                note.setOrganId(rs.getInt("organid"));
                note.setCheckValue(rs.getBoolean("checkvalue"));
                note.setNotes(rs.getString("note"));

                notesList.add(note);  // إضافة الملاحظة إلى القائمة
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notesList;  // إرجاع القائمة التي تحتوي على جميع الملاحظات
    }


}
