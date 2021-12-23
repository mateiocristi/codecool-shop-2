package com.codecool.shop.model;

import com.codecool.shop.dao.DBmodels.UserModel;

import java.util.List;

public class User {

    private int id;
    private String email;
    private String first_name;
    private String last_name;
    private String password;
    private String cart;
    private String orders;

    public User(String email, String first_name, String last_name, String password) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
    }

    public User(String email, String first_name, String last_name, String password, String cart, String orders) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.cart = cart;
        this.orders = orders;
    }

    public User(UserModel userModel) {
        this.id = userModel.getId();
        this.email = userModel.getEmail();
        this.first_name = userModel.getFirstName();
        this.last_name = userModel.getLastName();
        if (userModel.getCartProductsId().size() > 0)
            this.cart = setList(userModel.getCartProductsId());
        if (userModel.getOrdersId().size() > 0)
            this.orders = setList(userModel.getOrdersId());

    }

    private String setList(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append((int)list.get(i));
            if (i + 1< list.size())
                sb.append(",");
        }
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }
}
