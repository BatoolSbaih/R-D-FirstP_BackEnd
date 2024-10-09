package com.example.patientproject.service;

import com.example.patientproject.models.Clinic;
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
public class ClinicService {
    @Autowired
    private DataSource dataSource;

    public boolean addClinic(Clinic clinic) {
        if (checkIfClinicExistsByEmail(clinic.getFormalEmail())) {
            System.out.println("This clinic with email " + clinic.getFormalEmail() + " already exists.");
            return false;
        }
        String insertClinicQuery = "INSERT INTO clinic (name, formal_email, phone, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement pst = conn.prepareStatement(insertClinicQuery);
            pst.setString(1, clinic.getName());
            pst.setString(2, clinic.getFormalEmail());
            pst.setInt(3, clinic.getPhone()); // تحقق من أن getPhone() ترجع قيمة صحيحة
            pst.setString(4, clinic.getAddress());


            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Clinic added successfully.");
                return true;
            } else {
                System.out.println("Failed to add clinic.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private boolean checkIfClinicExistsByEmail(String formalEmail) {
        // التحقق مما إذا كانت العيادة موجودة باستخدام البريد الإلكتروني
        String checkEmailQuery = "SELECT COUNT(*) FROM clinic WHERE formal_email = ?";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement pst = conn.prepareStatement(checkEmailQuery);
            pst.setString(1, formalEmail);
            ResultSet rs = pst.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Clinic> getClinics() {
        String query = "SELECT * FROM clinic";
        List<Clinic> clinics = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                // افترض أن `Clinic` يحتوي على طريقة `set` لتعيين القيم
                Clinic clinic = new Clinic();
                clinic.setId(rs.getInt("id"));
                clinic.setName(rs.getString("name"));
                clinic.setPhone(rs.getInt("phone"));
                clinic.setAddress(rs.getString("address"));
                clinic.setFormalEmail(rs.getString("formal_email"));
                clinics.add(clinic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clinics;
    }


    public boolean removeClinic(Integer id) {
        String query = "DELETE FROM clinic WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            // تعيين قيمة المعامل في الاستعلام
            pst.setInt(1, id);

            // تنفيذ الاستعلام
            int rowsAffected = pst.executeUpdate();

            // التحقق مما إذا كانت عملية الحذف ناجحة
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateClinic(Clinic updatedClinic) {
        // الخطوة 1: تحقق مما إذا كانت العيادة موجودة في قاعدة البيانات
        Clinic existingClinic = getClinicById(updatedClinic.getId());

        if (existingClinic != null) {
            // الخطوة 2: إذا كانت العيادة موجودة، قم بتحديث البيانات
            String query = "UPDATE clinic SET name = ?, formal_email = ?, phone = ?, address = ? WHERE id = ?";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pst = conn.prepareStatement(query)) {

                // تعيين القيم في الاستعلام بناءً على الكائن updatedClinic
                pst.setString(1, updatedClinic.getName());
                pst.setString(2, updatedClinic.getFormalEmail());
                pst.setInt(3, updatedClinic.getPhone()); // غيرت pst.setInt إلى pst.setString للتوافق مع نوع البيانات
                pst.setString(4, updatedClinic.getAddress());
                pst.setInt(5, updatedClinic.getId());

                // تنفيذ الاستعلام
                int rowsAffected = pst.executeUpdate();

                // التحقق مما إذا كانت عملية التحديث ناجحة
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            // إذا لم تكن العيادة موجودة، أعد false
            return false;
        }
    }

    public Clinic getClinicById(int id) {
        String query = "SELECT * FROM clinic WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            // تعيين قيمة id في الاستعلام
            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // إنشاء كائن Clinic وتعيين القيم من نتيجة الاستعلام
                    Clinic clinic = new Clinic();
                    clinic.setId(rs.getInt("id"));
                    clinic.setName(rs.getString("name"));
                    clinic.setPhone(rs.getInt("phone")); // استخدم setString لتخزين الأرقام كنص
                    clinic.setAddress(rs.getString("address"));
                    clinic.setFormalEmail(rs.getString("formal_email"));
                    return clinic;
                } else {
                    return null; // إذا لم يتم العثور على أي سجل
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}











//package com.example.pateintproject.service;
//
//import com.example.pateintproject.models.Clinic;
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
//import java.util.UUID;
//
//@Service
//public class ClinicService {
//@Autowired
//private DataSource dataSource;
//
//    private List<Clinic> clinics = new ArrayList<>();
//
//    public List<Clinic> getClinics() {
//        return clinics;
//    }
//
//    public Clinic getClinicByEmail(String formalEmail) {
//        return clinics.stream()
//                .filter(clinic -> clinic.getFormalEmail().equals(formalEmail))
//                .findFirst()
//                .orElse(null);
//    }
//

//    public boolean addClinic(Clinic clinic) {
//        // التحقق مما إذا كانت العيادة موجودة بالفعل باستخدام البريد الإلكتروني الرسمي
//        if (checkIfClinicExistsByEmail(clinic.getFormalEmail())) {
//            return false;
//        }
//
//        // إضافة العيادة إلى قاعدة البيانات
//        String insertClinicQuery = "INSERT INTO clinic (name, formal_email, phone, address) VALUES (?, ?, ?, ?)";
//        try (Connection conn = dataSource.getConnection()) {
//            PreparedStatement pst = conn.prepareStatement(insertClinicQuery);
//            pst.setString(1, clinic.getName());
//            pst.setString(2, clinic.getFormalEmail());
//            pst.setInt(3, clinic.getPhone());
//            pst.setString(4, clinic.getAddress());
//
//            int rowsAffected = pst.executeUpdate();
//            return rowsAffected > 0; // إرجاع true إذا تم إدراج صف واحد على الأقل
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    private boolean checkIfClinicExistsByEmail(String formalEmail) {
//        // Check if a clinic with the given formal email already exists in the database
//        String checkEmailQuery = "SELECT COUNT(*) FROM clinic WHERE formal_email = ?";
//        try (Connection conn = dataSource.getConnection()) {
//            PreparedStatement pst = conn.prepareStatement(checkEmailQuery);
//            pst.setString(1, formalEmail);
//            ResultSet rs = pst.executeQuery();
//            return rs.next() && rs.getInt(1) > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean removeClinic(String id) {
//        Clinic clinic = getClinicById(id);
//        if (clinic != null) {
//            clinics.remove(clinic);
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public boolean updateClinic(Clinic updatedClinic) {
//        Clinic existingClinic = getClinicById(updatedClinic.getId());
//        if (existingClinic != null) {
//            // Update fields as necessary
//            existingClinic.setName(updatedClinic.getName());
//            existingClinic.setFormalEmail(updatedClinic.getFormalEmail());
//            existingClinic.setPhone(updatedClinic.getPhone());
//            existingClinic.setAddress(updatedClinic.getAddress());
//            return true;
//        }
//        return false;
//    }
//
//    public String printClinics() {
//        StringBuilder output = new StringBuilder("[");
//        for (Clinic clinic : clinics) {
//            output.append("{")
//                    .append(clinic.getName()).append(", ")
//                    .append(clinic.getFormalEmail()).append(", ")
//                    .append(clinic.getAddress()).append(", ")
//                    .append(clinic.getPhone()).append("}\n");
//        }
//        return output.append("]").toString();
//    }
//}
