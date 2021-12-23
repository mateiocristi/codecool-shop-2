package com.codecool.shop.dao.DBmodels;

import com.codecool.shop.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserModel {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Integer> cartProductsId;
    private List<Integer> ordersId;

    public UserModel(int id, String firstName, String lastName, String email, List<Integer> cartProductsId, List<Integer> ordersId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cartProductsId = new ArrayList<>();
        this.ordersId = new ArrayList<>();
    }

    public UserModel(User user) {
        this.id = user.getId();
        this.firstName = user.getFirst_name();
        this.lastName = user.getLast_name();
        this.email = user.getEmail();
        this.cartProductsId = new ArrayList<>();
        this.ordersId = new ArrayList<>();
    }

    public void addToCart(int id) {
        this.cartProductsId.add(id);
    }

    public void removeFromCart(int id) {
        this.cartProductsId.remove(Integer.valueOf(id));
    }

    public void addOrder(int id) {
        this.ordersId.add(id);
    }


    public List<Integer> getCartProductsId() {
        return cartProductsId;
    }

    public void setCartProductsId(List<Integer> cartProductsId) {
        this.cartProductsId = cartProductsId;
    }

    public List<Integer> getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(List<Integer> ordersId) {
        this.ordersId = ordersId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
