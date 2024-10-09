package com.example.patientproject.service;

import com.example.patientproject.models.Systems;
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
public class SystemService {
    @Autowired
    private DataSource dataSource;
    public List<Systems> getSystems() {
        String query = "SELECT * FROM systems";
        List<Systems> systemsList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Systems system = new Systems();
                system.setId(rs.getInt("id"));
                system.setName(rs.getString("systemname"));
                systemsList.add(system);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return systemsList;
    }
}
