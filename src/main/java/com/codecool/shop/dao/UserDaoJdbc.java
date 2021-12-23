package com.codecool.shop.dao;

import com.codecool.shop.dao.DBmodels.ProductModel;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbc implements UserDao{

    private DataSource dataSource;

    public UserDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User checkUser(String email, String password) {
        System.out.println("email is " + email);
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, email, first_name, last_name, password, cart, orders FROM users WHERE email = ? AND password = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                 return null;
            }
            System.out.println("result " + rs.getString(1));
            User user = new User(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            user.setId(rs.getInt(1));
            return user;
        } catch (SQLException e) {
            System.out.println("sql error: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public User get(int userId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, email, first_name, last_name, password, cart, orders FROM users WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs =st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            System.out.println("get user result " + rs.getString(1));
            User user = new User(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            user.setId(rs.getInt(1));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User add(User user) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO users (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getFirst_name());
            statement.setString(2, user.getLast_name());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getInt(1));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, email, first_name, last_name, password, cart, orders FROM users";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            return getUsers(rs, sql);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void updateCart(User user) {
        System.out.println("user cart: " + user.getCart());
        System.out.println(("user id: " + user.getId()));
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE users SET cart = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user.getCart());
            statement.setInt(2, user.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateOrder(User user) {

        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE users SET orders = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user.getOrders());
            statement.setInt(2, user.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<User> getUsers(ResultSet rs, String sql) throws SQLException {
        List<User> users = new ArrayList<>();
        while(rs.next()) {
            User user = new User(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            user.setId(rs.getInt(1));
            users.add(user);
        }
        return users;
    }
}
