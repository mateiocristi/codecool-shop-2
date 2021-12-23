package com.codecool.shop.dao;

import com.codecool.shop.dao.DBmodels.ProductModel;

import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {

    private DataSource dataSource;

    public ProductDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public void add(ProductModel product) throws IOException {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO product (name, description, def_price, actual_price, default_currency, category_id, supplier_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, Double.parseDouble(product.getDefaultPrice().toString()));
            statement.setDouble(4, Double.parseDouble(product.getActualPrice().toString()));
            statement.setString(5, product.getDefaultCurrency().toString());
            statement.setInt(6, product.getCategoryId());
            statement.setInt(7, product.getSupplierId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            product.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductModel getById(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description, def_price, actual_price, default_currency, category_id, supplier_id FROM product WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs =st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            ProductModel product = new ProductModel(rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8));
            product.setId(rs.getInt(1));
            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) throws IOException {

    }

    @Override
    public List<ProductModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM product";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            return getProducts(rs, sql);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<ProductModel> getBySupplier(int supplierId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description, def_price, actual_price, default_currency, category_id, supplier_id FROM product WHERE supplier_id LIKE ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, supplierId);
            ResultSet rs = st.executeQuery();
            return getProducts(rs, sql);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<ProductModel> getByCategory(int productCategoryId) {
        System.out.println("category id: " + productCategoryId);
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description, def_price, actual_price, default_currency, category_id, supplier_id FROM product WHERE category_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, productCategoryId);
            ResultSet rs = st.executeQuery();
            return getProducts(rs, sql);
        } catch (SQLException e) {
            System.out.println("exception: " + e);
            throw new RuntimeException();
        }
    }

    private List<ProductModel> getProducts(ResultSet rs, String sql) throws SQLException {
        List<ProductModel> products = new ArrayList<>();
        while(rs.next()) {
            ProductModel product = new ProductModel(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8));
            product.setId(rs.getInt(1));
            products.add(product);
        }
        return products;
    }

}
