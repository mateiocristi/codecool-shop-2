package com.codecool.shop.dao;

import com.codecool.shop.model.Category;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoJdbc implements CategoryDao {

    private DataSource dataSource;

    public CategoryDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Category category) throws IOException {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO category (name, description, department) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.setString(3, category.getDepartment());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            category.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category getById(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description, department FROM category WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            return getCategory(st);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category getByName(String name) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description, department FROM category WHERE name = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, name);
            return getCategory(st);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) throws IOException {

    }

    @Override
    public List<Category> getAll() {

        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description, department FROM category";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Category> categories = new ArrayList<>();
            while(rs.next()) {

                Category category = new Category(rs.getString(2), rs.getString(3), rs.getString(4));
                category.setId(rs.getInt(1));
                categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private Category getCategory(PreparedStatement st) throws SQLException {
        ResultSet rs = st.executeQuery();
        if (!rs.next()) {
            return null;
        }
        Category category = new Category(rs.getString(2), rs.getString(3), rs.getString(4));
        category.setId(rs.getInt(1));
        return category;
    }
}
