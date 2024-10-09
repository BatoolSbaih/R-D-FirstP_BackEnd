package com.example.patientproject.service;

import com.example.patientproject.models.PhysicalExamination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

@Service
public class PhysicalExaminationService {

    @Autowired
    private DataSource dataSource;

    public List<PhysicalExamination> getPhysicalExaminations() {
        String query = "SELECT * FROM physicalexamination";  // Assuming the table name is 'physical_examinations'
        List<PhysicalExamination> examinationList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                PhysicalExamination examination = new PhysicalExamination();
                examination.setExaminationId(rs.getInt("id")); // Assuming the column name is 'examination_id'
                examination.setExaminationName(rs.getString("name")); // Assuming the column name is 'examination_name'
                examinationList.add(examination);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return examinationList;
    }
}
