package com.example.patientproject.service;

import com.example.patientproject.models.Status;
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
public class StatusService {

    @Autowired
    private DataSource dataSource;

    // Retrieve statuses by physical examination ID
    public List<Status> getStatusesByPhysicalExaminationId(int physicalExaminationId) {
        List<Status> statuses = new ArrayList<>();
        String query = "SELECT * FROM status WHERE physical_examination_id = ?"; // Assuming the table name is 'status'

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, physicalExaminationId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Status status = new Status();
                    status.setId(rs.getInt("id"));
                    status.setName(rs.getString("name"));
                    status.setPhysicalexaminationId(rs.getInt("physical_examination_id"));
                    statuses.add(status);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statuses;
    }
}
