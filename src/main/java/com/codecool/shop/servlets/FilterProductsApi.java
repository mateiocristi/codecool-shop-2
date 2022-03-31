package com.codecool.shop.servlets;

import com.codecool.shop.dao.CategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.database.DatabaseManager;
import com.codecool.shop.model.Category;
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
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/api/get/products")
public class FilterProductsApi extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson;
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.run();

        String category = request.getParameter("category");
        String supplier = request.getParameter("supplier");


        List<Product> products;
        if(category.equals("All")) {
            // if no category and no supplier selected
            if (supplier.equals("All"))
                products = databaseManager.getAllProducts();
            // if no category selected
            else
                products = databaseManager.getAllProducts().stream().filter(t -> t.getSupplier().getName().equals(supplier)).collect(Collectors.toList());
            // if category selected
        } else {
            Category categ = databaseManager.getCategory(category);
            products = databaseManager.getProductsByCategory(categ.getId());
            // if supplier selected
            if (!supplier.equals("All")) {
                products = products.stream().filter(t -> t.getSupplier().getName().equals(supplier)).collect(Collectors.toList());
            }
        }
        PrintWriter out = response.getWriter();
        gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductSerializer()).create();
        out.println(gson.toJson(products));


    }
}
