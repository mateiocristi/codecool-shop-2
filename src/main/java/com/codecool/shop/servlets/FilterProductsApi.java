package com.codecool.shop.servlets;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
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

@WebServlet(urlPatterns = "/api/get/products")
public class FilterProductsApi extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson;
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductService productService = new ProductService(productDataStore,productCategoryDataStore, supplierDataStore);

        String category = request.getParameter("category");
        String supplier = request.getParameter("supplier");


        List<Product> products;
        if(category.equals("All")) {
            // if no category and no supplier selected
            if (supplier.equals("All"))
                products = productDataStore.getAll();
            // if no category selected
            else
                products = productDataStore.getAll().stream().filter(t -> t.getSupplier().getName().equals(supplier)).collect(Collectors.toList());
            // if category selected
        } else {
            products = productService.getProductsForCategory(category);
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
