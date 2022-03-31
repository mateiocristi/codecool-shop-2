package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.DBmodels.UserModel;
import com.codecool.shop.dao.ProductDao;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (session.getAttribute("userId") != null) {
            String userId = session.getAttribute("userId").toString();
            DatabaseManager dbManager = new DatabaseManager();
            dbManager.run();

            UserModel user = dbManager.getUser(Integer.parseInt(userId));
            List<Product> cartProducts = new ArrayList<>();
            double totalPrice = 0;

            for (int i = 0; i < user.getCartProductsId().size(); i++) {
                cartProducts.add(dbManager.getProduct(user.getCartProductsId().get(i)));
                totalPrice += cartProducts.get(i).getActualPrice().doubleValue();
            }

            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            WebContext context = new WebContext(req, resp, req.getServletContext());

            // get from database

            context.setVariable("totalPrice", String.format("%.2f",totalPrice));
            context.setVariable("cartProducts", cartProducts);
            context.setVariable("products", dbManager.getAllProducts());

            engine.process("product/cart.html", context, resp.getWriter());
        } else {
            resp.sendRedirect("/");
        }


    }
}
