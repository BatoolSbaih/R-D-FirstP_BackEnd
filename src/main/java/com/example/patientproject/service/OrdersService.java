package com.example.patientproject.service;

import com.example.patientproject.models.Orders;
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
public class OrdersService {

    @Autowired
    private DataSource dataSource;

    // حذف طلب وتحديث حقل deletedBy
    public boolean deleteOrder(Long id, String deletedBy) {
        if (!checkIfOrderExistsById(id)) {
            return false; // الطلب غير موجود
        }

        String query = "UPDATE orders SET deleted_by = ?, deleted_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, deletedBy);
            pst.setLong(2, id);

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Orders> getAllOrders() {
        List<Orders> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE deleted_by IS NULL"; // استرجاع الطلبات غير المحذوفة

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Orders order = new Orders();
                order.setId(rs.getLong("id"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setCreatedBy(rs.getString("created_by"));
                order.setUpdatedAt(rs.getTimestamp("updated_at"));
                order.setUpdatedBy(rs.getString("updated_by"));
                order.setDeletedAt(rs.getTimestamp("deleted_at"));
                order.setDeletedBy(rs.getString("deleted_by"));
                order.setVisiteId(rs.getLong("visite_id"));
                order.setSetupId(rs.getLong("setup_id"));
                order.setStatus(rs.getString("status"));
                order.setPriority(rs.getString("priority"));
                order.setType(rs.getString("type"));
                order.setName(rs.getString("name"));
                order.setCode(rs.getString("code"));
                order.setRequstDate(rs.getTimestamp("requestofdate"));
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public boolean checkIfOrderExists(Long visitId, Long setupId) {
        String query = "SELECT COUNT(*) FROM orders WHERE visite_id = ? AND setup_id = ? AND deleted_by IS NULL";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setLong(1, visitId);
            pst.setLong(2, setupId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0; // التحقق مما إذا كانت التركيبة موجودة
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // افتراض عدم الوجود في حالة حدوث خطأ
    }

    // إضافة طلب جديد أو تحديث طلب موجود
    public boolean addOrUpdateOrder(Orders order) {
        Long existingOrderId = findOrderIdByVisitIdAndTestId(order.getVisiteId(), order.getSetupId());
 System.out.println(existingOrderId);
        if (existingOrderId != null) {
            return updateOrder(existingOrderId, order);
        } else {
            return addOrder(order);
        }
    }

    // دالة البحث باستخدام visitId و testId لإرجاع id الطلب
    public Long findOrderIdByVisitIdAndTestId(Long visitId, Long setupId) {
        String query = "SELECT id FROM orders WHERE visite_id = ? AND setup_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, visitId);
            statement.setLong(2, setupId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // إذا لم يتم إيجاد الطلب
    }



    // إضافة طلب جديد
    public boolean addOrder(Orders order) {
        String insertOrderQuery = "INSERT INTO orders (created_at, created_by, updated_at, updated_by, visite_id, setup_id, status, priority, deleted_at, deleted_by, type, name, code, requestofdate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(insertOrderQuery)) {

            pst.setTimestamp(1, order.getCreatedAt());
            pst.setString(2, order.getCreatedBy());
            pst.setTimestamp(3, order.getUpdatedAt());
            pst.setString(4, order.getUpdatedBy());
            pst.setLong(5, order.getVisiteId());
            pst.setLong(6, order.getSetupId());
            pst.setString(7, order.getStatus());
            pst.setString(8, order.getPriority());
            pst.setTimestamp(9, order.getDeletedAt());
            pst.setString(10, order.getDeletedBy());
            pst.setString(11, order.getType());
            pst.setString(12, order.getName());
            pst.setString(13, order.getCode());
            pst.setTimestamp(14, order.getRequstDate());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0; // التحقق من نجاح الإضافة
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // تحديث طلب موجود
    public boolean updateOrder(Long id, Orders newOrderData) {
        String query = "UPDATE orders SET status = ?, priority = ?, updated_at = CURRENT_TIMESTAMP, updated_by = ?, type = ?, name = ?, code = ?, requestofdate = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, newOrderData.getStatus());
            pst.setString(2, newOrderData.getPriority());
            pst.setString(3, newOrderData.getUpdatedBy());
            pst.setString(4, newOrderData.getType());
            pst.setString(5, newOrderData.getName());
            pst.setString(6, newOrderData.getCode());
            pst.setTimestamp(7, newOrderData.getRequstDate());
            pst.setLong(8, id);

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // التحقق مما إذا كان الطلب موجودًا باستخدام المعرف
    public boolean checkIfOrderExistsById(Long id) {
        String query = "SELECT COUNT(*) FROM orders WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
