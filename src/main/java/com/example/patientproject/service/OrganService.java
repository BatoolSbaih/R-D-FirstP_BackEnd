package com.example.patientproject.service;

import com.example.patientproject.models.Organ;
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
public class OrganService {

    @Autowired
    private DataSource dataSource;

    // Retrieve organs by system ID
    public List<Organ> getOrgansBySystemId(int systemId) {
        List<Organ> organs = new ArrayList<>();
        String query = "SELECT * FROM organ WHERE systemid = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, systemId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Organ organ = new Organ();
                    organ.setId(rs.getInt("id"));
                    organ.setName(rs.getString("organname"));
                    organ.setSystemId(rs.getInt("systemid"));
                    organs.add(organ);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return organs;
    }
}
