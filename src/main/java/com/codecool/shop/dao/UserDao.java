package com.codecool.shop.dao;

import com.codecool.shop.model.User;

import java.util.List;

public interface UserDao {
    User checkUser(String email, String password);
    User add(User user);
    User get(int userId);
    List<User> getAll();
    void updateCart(User user);
    void updateOrder(User user);

}
