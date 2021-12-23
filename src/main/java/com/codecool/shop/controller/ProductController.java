package com.codecool.shop.controller;

import com.codecool.shop.dao.CategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.database.DatabaseManager;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.run();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        List<Product> productList = databaseManager.getAllProducts();
        WebContext context = new WebContext(req, resp, req.getServletContext());

        System.out.println("session Id: " + session.getAttribute("userId"));

        context.setVariable("categories", databaseManager.getAllCategories());
        context.setVariable("category", "All");
        context.setVariable("products", databaseManager.getAllProducts());
        context.setVariable("supplier", "All");
        context.setVariable("suppliers", databaseManager.getAllSuppliers());
        // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        engine.process("product/index.html", context, resp.getWriter());
    }

}
