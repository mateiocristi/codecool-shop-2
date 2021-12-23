package com.codecool.shop.dao;

import com.codecool.shop.dao.DBmodels.SupplierModel;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJdbc implements SupplierDao {

    private DataSource dataSource;

    public SupplierDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public void add(Supplier supplier) throws IOException {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO supplier (name, description) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getDescription());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            supplier.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Supplier getById(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description FROM supplier WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);

            return getSupplier(st);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Supplier getByName(String name) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description FROM supplier WHERE name = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, name);

            return getSupplier(st);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) throws IOException {

    }

    @Override
    public List<Supplier> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description FROM supplier";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Supplier> suppliers = new ArrayList<>();
            while(rs.next()) {
                Supplier supplier = new Supplier(rs.getString(2), rs.getString(3));
                supplier.setId(rs.getInt(1));
                suppliers.add(supplier);
            }
            return suppliers;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private Supplier getSupplier(PreparedStatement st) throws SQLException {
        ResultSet rs = st.executeQuery();
        if (!rs.next()) {
            return null;
        }
        Supplier  supplier= new Supplier(rs.getString(2), rs.getString(3));
        supplier.setId(rs.getInt(1));
        return supplier;
    }
}
