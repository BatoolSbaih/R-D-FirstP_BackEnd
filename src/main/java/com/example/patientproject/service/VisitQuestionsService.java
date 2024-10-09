package com.example.patientproject.service;

import com.example.patientproject.models.VisitQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class VisitQuestionsService {

    @Autowired
    private DataSource dataSource;

    // Add or Update VisitQuestions based on visitId
    public boolean addOrUpdateVisitQuestions(VisitQuestions visitQuestions) {
        // Check if visitid exists
        String checkQuery = "SELECT COUNT(*) FROM VisitQuestions WHERE visitid = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement checkPst = conn.prepareStatement(checkQuery)) {

            checkPst.setInt(1, visitQuestions.getVisitId());
            try (ResultSet rs = checkPst.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // If visitid exists, perform update
                    return updateVisitQuestionsByVisitId(visitQuestions);
                } else {
                    // If visitid doesn't exist, perform insert
                    return addVisitQuestions(visitQuestions);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to insert new VisitQuestions
    public boolean addVisitQuestions(VisitQuestions visitQuestions) {
        String insertQuery = "INSERT INTO VisitQuestions (firstQuestionA, firstQuestionB, secondQuestion, thirdQuestion, boxQuestion, visitid) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(insertQuery)) {

            pst.setString(1, visitQuestions.getFirstQuestionA());
            pst.setString(2, visitQuestions.getFirstQuestionB());
            pst.setString(3, visitQuestions.getSecondQuestion());
            pst.setString(4, visitQuestions.getThirdQuestion());
            pst.setString(5, visitQuestions.getBoxQuestion());
            pst.setInt(6, visitQuestions.getVisitId());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update VisitQuestions by visitid
    private boolean updateVisitQuestionsByVisitId(VisitQuestions visitQuestions) {
        String updateQuery = "UPDATE VisitQuestions SET firstQuestionA = ?, firstQuestionB = ?, secondQuestion = ?, thirdQuestion = ?, boxQuestion = ? WHERE visitid = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(updateQuery)) {

            pst.setString(1, visitQuestions.getFirstQuestionA());
            pst.setString(2, visitQuestions.getFirstQuestionB());
            pst.setString(3, visitQuestions.getSecondQuestion());
            pst.setString(4, visitQuestions.getThirdQuestion());
            pst.setString(5, visitQuestions.getBoxQuestion());
            pst.setInt(6, visitQuestions.getVisitId());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve all VisitQuestions
    public List<VisitQuestions> getVisitQuestions() {
        String query = "SELECT * FROM VisitQuestions";
        List<VisitQuestions> visitQuestionsList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                VisitQuestions visitQuestions = new VisitQuestions();
                visitQuestions.setId(rs.getInt("id"));
                visitQuestions.setFirstQuestionA(rs.getString("firstQuestionA"));
                visitQuestions.setFirstQuestionB(rs.getString("firstQuestionB"));
                visitQuestions.setSecondQuestion(rs.getString("secondQuestion"));
                visitQuestions.setThirdQuestion(rs.getString("thirdQuestion"));
                visitQuestions.setBoxQuestion(rs.getString("boxQuestion"));
                visitQuestions.setVisitId(rs.getInt("visitid"));
                visitQuestionsList.add(visitQuestions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return visitQuestionsList;
    }

    // Method to retrieve VisitQuestions by id
    public VisitQuestions getVisitQuestionsById(int id) {
        String query = "SELECT * FROM VisitQuestions WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    VisitQuestions visitQuestions = new VisitQuestions();
                    visitQuestions.setId(rs.getInt("id"));
                    visitQuestions.setFirstQuestionA(rs.getString("firstQuestionA"));
                    visitQuestions.setFirstQuestionB(rs.getString("firstQuestionB"));
                    visitQuestions.setSecondQuestion(rs.getString("secondQuestion"));
                    visitQuestions.setThirdQuestion(rs.getString("thirdQuestion"));
                    visitQuestions.setBoxQuestion(rs.getString("boxQuestion"));
                    visitQuestions.setVisitId(rs.getInt("visitid"));
                    return visitQuestions;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // If no visit questions found
    }

    // Method to remove VisitQuestions by id
    public boolean removeVisitQuestions(int id) {
        String query = "DELETE FROM VisitQuestions WHERE id = ?";
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
}
