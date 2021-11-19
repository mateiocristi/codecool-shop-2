package com.codecool.shop.servlets;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.serialization.ProductSerializer;
import com.codecool.shop.service.ProductService;
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
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/api/shoppingCart/add")
public class AddToCartApi extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson;
        CartDaoMem cartDaoMem = CartDaoMem.getInstance();
        ProductDaoMem productDaoMem = ProductDaoMem.getInstance();
        String productId = request.getParameter("id");

        Product product = productDaoMem.getBy(Integer.parseInt(productId));

        if (cartDaoMem.find(Integer.parseInt(productId)) == null) {
            cartDaoMem.add(product);
        }

        List<Product> cartProducts = cartDaoMem.getAll();

        PrintWriter out = response.getWriter();
        gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductSerializer()).create();
        out.println(gson.toJson(cartProducts));

    }
}

