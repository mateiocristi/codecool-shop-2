package com.codecool.shop.servlets;

import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.serialization.ProductSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/api/shoppingCart/remove")
public class RemoveFromCartApi extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson;
        CartDaoMem cartDaoMem = CartDaoMem.getInstance();
        ProductDaoMem productDaoMem = ProductDaoMem.getInstance();
        String productId = request.getParameter("id");

        cartDaoMem.remove(Integer.parseInt(productId));
        List<Product> cartProducts = cartDaoMem.getAll();

        PrintWriter out = response.getWriter();
        gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductSerializer()).create();
        out.println(gson.toJson(cartProducts));

    }
}

