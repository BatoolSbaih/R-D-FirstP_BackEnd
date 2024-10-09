package com.example.patientproject.service;

import com.example.patientproject.models.SetUp;
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
public class SetUpService {

    @Autowired
    private DataSource dataSource;
    public boolean deleteSetUp(Long id, String deletedBy) {
        // تحقق مما إذا كان السجل موجودًا
        if (!checkIfSetUpExistsById(id)) {
            return false; // السجل غير موجود
        }

        String query = "UPDATE setup SET deleted_by = ?, deleted_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, deletedBy);  // تعيين قيمة deletedBy
            pst.setLong(2, id);            // تعيين قيمة id

            int rowsAffected = pst.executeUpdate();

            return rowsAffected > 0; // إرجاع true إذا تم تحديث السجل بنجاح
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // إرجاع false في حالة حدوث خطأ
        }
    }
    public boolean restoreSetUp(Long id) {
        if (!checkIfSetUpExistsById(id)) {
            return false;
        }

        String query = "UPDATE setup SET deleted_by = NULL, deleted_at = NULL WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setLong(1, id);

            int rowsAffected = pst.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve SetUp records by type
    public List<SetUp> getSetUpsByType(String type) {
        List<SetUp> setups = new ArrayList<>();
        String query = "SELECT * FROM setup WHERE type = ? "; // Assuming the table name is 'setup'

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, type); // Set the type parameter only
            // No need to set deleted_by, as it's already handled in the query

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    SetUp setup = new SetUp();
                    setup.setId(rs.getLong("id"));
                    setup.setType(rs.getString("type"));
                    setup.setPrice(rs.getInt("price"));
                    setup.setName(rs.getString("name"));
                    setup.setCurrency(rs.getString("currency"));
                    setup.setCode(rs.getString("code"));
                    setup.setTiming(rs.getTime("timing"));
                    setup.setCreatedAt(rs.getTimestamp("created_at"));
                    setup.setCreatedBy(rs.getString("created_by"));
                    setup.setUpdatedAt(rs.getTimestamp("updated_at"));
                    setup.setUpdatedBy(rs.getString("updated_by"));
                    setup.setDeletedAt(rs.getTimestamp("deleted_at"));
                    setup.setDeletedBy(rs.getString("deleted_by"));

                    setups.add(setup);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return setups;
    }
    public List<SetUp> getSetUpsByTypeANDNullDeleted(String type) {
        List<SetUp> setups = new ArrayList<>();
        String query = "SELECT * FROM setup WHERE type = ? AND deleted_by IS NULL"; // Assuming the table name is 'setup'

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, type); // Set the type parameter only
            // No need to set deleted_by, as it's already handled in the query

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    SetUp setup = new SetUp();
                    setup.setId(rs.getLong("id"));
                    setup.setType(rs.getString("type"));
                    setup.setPrice(rs.getInt("price"));
                    setup.setName(rs.getString("name"));
                    setup.setCurrency(rs.getString("currency"));
                    setup.setCode(rs.getString("code"));
                    setup.setTiming(rs.getTime("timing"));
                    setup.setCreatedAt(rs.getTimestamp("created_at"));
                    setup.setCreatedBy(rs.getString("created_by"));
                    setup.setUpdatedAt(rs.getTimestamp("updated_at"));
                    setup.setUpdatedBy(rs.getString("updated_by"));
                    setup.setDeletedAt(rs.getTimestamp("deleted_at"));
                    setup.setDeletedBy(rs.getString("deleted_by"));

                    setups.add(setup);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return setups;
    }

    // Retrieve all SetUp records
    public List<SetUp> getAllSetUps() {
        List<SetUp> setups = new ArrayList<>();
        String query = "SELECT * FROM setup WHERE deleted_by IS NULL"; // استرجاع السجلات حيث deleted_by null

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                SetUp setup = new SetUp();
                setup.setId(rs.getLong("id"));
                setup.setType(rs.getString("type"));
                setup.setPrice(rs.getInt("price"));
                setup.setName(rs.getString("name"));
                setup.setCurrency(rs.getString("currency"));
                setup.setCode(rs.getString("code"));
                setup.setTiming(rs.getTime("timing"));
                setup.setCreatedAt(rs.getTimestamp("created_at"));
                setup.setCreatedBy(rs.getString("created_by"));
                setup.setUpdatedAt(rs.getTimestamp("updated_at"));
                setup.setUpdatedBy(rs.getString("updated_by"));
                setup.setDeletedAt(rs.getTimestamp("deleted_at"));
                setup.setDeletedBy(rs.getString("deleted_by"));

                setups.add(setup);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return setups;
    }


    public boolean checkIfSetUpExistsById(Long id) {
        String query = "SELECT COUNT(*) FROM setup WHERE id = ?"; // التحقق من وجود السجل عبر ID

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // إذا كانت القيمة أكبر من 0 فهذا يعني أن السجل موجود
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // إذا لم يتم العثور على أي سجل
    }

    public SetUp getSetUpById(Long id) {
        String query = "SELECT * FROM setup WHERE id = ? AND deleted_by IS NULL"; // Only retrieve non-deleted records

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setLong(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    SetUp setup = new SetUp();
                    setup.setId(rs.getLong("id"));
                    setup.setType(rs.getString("type"));
                    setup.setPrice(rs.getInt("price"));
                    setup.setName(rs.getString("name"));
                    setup.setCurrency(rs.getString("currency"));
                    setup.setCode(rs.getString("code"));
                    setup.setTiming(rs.getTime("timing"));
                    setup.setCreatedAt(rs.getTimestamp("created_at"));
                    setup.setCreatedBy(rs.getString("created_by"));
                    setup.setUpdatedAt(rs.getTimestamp("updated_at"));
                    setup.setUpdatedBy(rs.getString("updated_by"));
                    setup.setDeletedAt(rs.getTimestamp("deleted_at"));
                    setup.setDeletedBy(rs.getString("deleted_by"));

                    return setup; // Return the found setup
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if not found
    }

    public SetUp getSetUpByNameAndType(String name, String type) {
        SetUp setup = null;
        String query = "SELECT * FROM setup WHERE name = ? AND type = ? AND deleted_by IS NULL";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, name);
            pst.setString(2, type);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    setup = new SetUp();
                    setup.setId(rs.getLong("id"));
                    setup.setType(rs.getString("type"));
                    setup.setPrice(rs.getInt("price"));
                    setup.setName(rs.getString("name"));
                    setup.setCurrency(rs.getString("currency"));
                    setup.setCode(rs.getString("code"));
                    setup.setTiming(rs.getTime("timing"));
                    setup.setCreatedAt(rs.getTimestamp("created_at"));
                    setup.setCreatedBy(rs.getString("created_by"));
                    setup.setUpdatedAt(rs.getTimestamp("updated_at"));
                    setup.setUpdatedBy(rs.getString("updated_by"));
                    setup.setDeletedAt(rs.getTimestamp("deleted_at"));
                    setup.setDeletedBy(rs.getString("deleted_by"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return setup;
    }
    public SetUp getSetUpTypeANDCode(String type,String code) {
        SetUp setup = null;
        String query = "SELECT * FROM setup WHERE  type = ? AND code=? ";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
;
            pst.setString(1, type);
            pst.setString(2, code);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    setup = new SetUp();
                    setup.setId(rs.getLong("id"));
                    setup.setType(rs.getString("type"));
                    setup.setPrice(rs.getInt("price"));
                    setup.setName(rs.getString("name"));
                    setup.setCurrency(rs.getString("currency"));
                    setup.setCode(rs.getString("code"));
                    setup.setTiming(rs.getTime("timing"));
                    setup.setCreatedAt(rs.getTimestamp("created_at"));
                    setup.setCreatedBy(rs.getString("created_by"));
                    setup.setUpdatedAt(rs.getTimestamp("updated_at"));
                    setup.setUpdatedBy(rs.getString("updated_by"));
                    setup.setDeletedAt(rs.getTimestamp("deleted_at"));
                    setup.setDeletedBy(rs.getString("deleted_by"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return setup;
    }

    public boolean addOrUpdateSetUp(SetUp setup) {
        SetUp existingSetup = getSetUpByNameAndType(setup.getName(), setup.getType());
        SetUp existingSetupTwo = getSetUpTypeANDCode(setup.getType(), setup.getCode());

        if (existingSetup != null || existingSetupTwo != null) {
            Long idToUpdate = (existingSetup != null) ? existingSetup.getId() : existingSetupTwo.getId();
            return updateSetUp(idToUpdate, setup, setup.getUpdatedBy());
        } else {
            return addSetUp(setup);
        }
    }


    public boolean checkIfSetUpExistsByTypeAndName(String type, String name) {
        String query = "SELECT COUNT(*) FROM setup WHERE type = ? AND name = ?"; // البحث عن السجلات التي لم يتم حذفها
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, type);
            pst.setString(2, name);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // إذا كانت القيمة أكبر من 0 فهذا يعني أن السجل موجود
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // إذا لم يتم العثور على أي سجل
    }

    public boolean checkIfSetUpExistsAndNotDeleted(Long id) {
        String query = "SELECT COUNT(*) FROM setup WHERE id = ? AND deleted_by IS NULL";

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


    public boolean addSetUp(SetUp setup) {
        // التحقق إذا كان ID موجود
        if (setup.getId() != null && checkIfSetUpExistsById(setup.getId())) {
            System.out.println("The SetUp with ID " + setup.getId() + " already exists.");
            return false; // إذا كان السجل موجودًا بالفعل، لا يتم الإضافة
        }

        // التحقق إذا كان هناك سجل بنفس النوع (type) والاسم (name)
        if (checkIfSetUpExistsByTypeAndName(setup.getType(), setup.getName())) {
            System.out.println("A SetUp with type '" + setup.getType() + "' and name '" + setup.getName() + "' already exists.");
            return false; // إذا كان هناك سجل بنفس النوع والاسم، لا يتم الإضافة
        }

        String insertSetUpQuery = "INSERT INTO setup (type, price, name, currency, code, timing, created_at, created_by, updated_at, updated_by, deleted_at, deleted_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(insertSetUpQuery)) {

            pst.setString(1, setup.getType());
            pst.setInt(2, setup.getPrice());
            pst.setString(3, setup.getName());
            pst.setString(4, setup.getCurrency());
            pst.setString(5, setup.getCode());
            pst.setTime(6, setup.getTiming());
            pst.setTimestamp(7, setup.getCreatedAt());
            pst.setString(8, setup.getCreatedBy());
            pst.setTimestamp(9, setup.getUpdatedAt());
            pst.setString(10, setup.getUpdatedBy());
            pst.setTimestamp(11, setup.getDeletedAt());
            pst.setString(12, setup.getDeletedBy());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0; // تحقق من نجاح الإضافة
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateSetUp(Long id, SetUp newSetUpData, String updatedBy) {
        // تحقق مما إذا كان هناك سجل آخر بنفس type و code
        String checkQuery = "SELECT COUNT(*) FROM setup WHERE type = ? AND code = ? AND id <> ?";

        // استعلام التحديث
        String updateQuery = "UPDATE setup SET type = ?, price = ?, name = ?, currency = ?, code = ?, timing = ?, updated_at = CURRENT_TIMESTAMP, updated_by = ? WHERE id = ? AND deleted_by IS NULL";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement checkPst = conn.prepareStatement(checkQuery);
             PreparedStatement updatePst = conn.prepareStatement(updateQuery)) {

            // تعيين قيم الاستعلام للتحقق
            checkPst.setString(1, newSetUpData.getType());
            checkPst.setString(2, newSetUpData.getCode());
            checkPst.setLong(3, id);

            // تنفيذ استعلام التحقق
            ResultSet rs = checkPst.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // إذا كان هناك سجل بنفس type و code، إرجاع false
                return false;
            }

            // تعيين قيم استعلام التحديث
            updatePst.setString(1, newSetUpData.getType());
            updatePst.setInt(2, newSetUpData.getPrice());
            updatePst.setString(3, newSetUpData.getName());
            updatePst.setString(4, newSetUpData.getCurrency());
            updatePst.setString(5, newSetUpData.getCode());
            updatePst.setTime(6, newSetUpData.getTiming());
            updatePst.setString(7, updatedBy);
            updatePst.setLong(8, id);

            // تنفيذ استعلام التحديث
            int rowsAffected = updatePst.executeUpdate();
            return rowsAffected > 0; // إذا تم التحديث بنجاح، إرجاع true
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
