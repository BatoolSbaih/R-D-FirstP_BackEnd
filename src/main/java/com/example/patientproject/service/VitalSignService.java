package com.example.patientproject.service;

import com.example.patientproject.models.VitalSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VitalSignService {

    @Autowired
    private DataSource dataSource;

    public boolean addVitalSign(VitalSign vitalSign) {
        String insertQuery = "INSERT INTO vital_signs (date, bp, pulse, temperature, username ,visitid) VALUES (?, ?, ?, ?, ?,?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(insertQuery)) {

            pst.setDate(1, Date.valueOf(vitalSign.getDate()));
            pst.setString(2, vitalSign.getBp());
            pst.setInt(3, vitalSign.getPulse());
            pst.setInt(4, vitalSign.getTemperature());
            pst.setString(5, vitalSign.getUserName());
            pst.setInt(6,vitalSign.getVisitId());
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<VitalSign> getVitalSigns() {
        String query = "SELECT * FROM vital_signs";
        List<VitalSign> vitalSignsList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                VitalSign vitalSign = new VitalSign();
                vitalSign.setId(rs.getInt("id"));
                vitalSign.setDate(rs.getDate("date").toLocalDate());
                vitalSign.setBp(rs.getString("bp"));
                vitalSign.setPulse(rs.getInt("pulse"));
                vitalSign.setTemperature(rs.getInt("temperature"));
                vitalSign.setUserName(rs.getString("username"));
                vitalSign.setVisitId(rs.getInt("visitid"));
                vitalSignsList.add(vitalSign);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vitalSignsList;
    }

    public VitalSign getVitalSignById(int id) {
        String query = "SELECT * FROM vital_signs WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    VitalSign vitalSign = new VitalSign();
                    vitalSign.setId(rs.getInt("id"));
                    vitalSign.setDate(rs.getDate("date").toLocalDate());
                    vitalSign.setBp(rs.getString("bp"));
                    vitalSign.setPulse(rs.getInt("pulse"));
                    vitalSign.setTemperature(rs.getInt("temperature"));
                    vitalSign.setUserName(rs.getString("username"));
                    vitalSign.setVisitId(rs.getInt("visitid"));
                    return vitalSign;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // If no vital sign found
    }

    public boolean removeVitalSign(int id) {
        String query = "DELETE FROM vital_signs WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, id);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateVitalSign(VitalSign updatedVitalSign) {
        VitalSign existingVitalSign = getVitalSignById(updatedVitalSign.getId());

        if (existingVitalSign != null) {
            String query = "UPDATE vital_signs SET date = ?, bp = ?, pulse = ?, temperature = ?, username = ? ,visitid=? WHERE id = ?";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pst = conn.prepareStatement(query)) {

                pst.setDate(1, Date.valueOf(updatedVitalSign.getDate()));
                pst.setString(2, updatedVitalSign.getBp());
                pst.setInt(3, updatedVitalSign.getPulse());
                pst.setInt(4, updatedVitalSign.getTemperature());
                pst.setString(5, updatedVitalSign.getUserName());
                pst.setInt(6, updatedVitalSign.getVisitId());
                pst.setInt(7, updatedVitalSign.getId());

                int rowsAffected = pst.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false; // If vital sign to update is not found
    }
}

